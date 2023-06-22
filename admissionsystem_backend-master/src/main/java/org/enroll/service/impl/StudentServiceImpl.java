package org.enroll.service.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.enroll.excel.pojo.ExcelMajor;
import org.enroll.excel.pojo.ExcelStudent;
import org.enroll.mapper.MajorMapper;
import org.enroll.mapper.StatusMapper;
import org.enroll.mapper.StudentMapper;
import org.enroll.pojo.EnrollStatus;
import org.enroll.pojo.StatisticsResult;
import org.enroll.pojo.StudentResult;
import org.enroll.service.interfaces.IStudentService;
import org.enroll.utils.QueryResultOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private MajorMapper majorMapper;

    @Autowired
    private StatusMapper statusMapper;


    @Override
    public PageInfo getStudentRaw(int currentPage) {//获取所有学生信息
        PageHelper.startPage(currentPage, 50);
        return new PageInfo<>(studentMapper.getStudentRaw());
    }

    @Override
    public PageInfo getAdjustStudentRaw(int currentPage){//获取调剂学生的信息
        PageHelper.startPage(currentPage, 50);
        log.info("准备开始获取");
        return new PageInfo<>(studentMapper.getAdjustStudentRaw());
    }

    @Override
    public PageInfo getExitStudentRaw(int currentPage){//获取退档学生的信息
        PageHelper.startPage(currentPage, 50);
        return new PageInfo<>(studentMapper.getExitStudentRaw());
    }

    @Override
    public void doEnroll() {//执行录取
        Integer status = statusMapper.getStatus();//获取系统最新状态
        if (status == null || status != EnrollStatus.FILE_READY.ordinal()){//导入招生计划文件和平行志愿文件之后才能开始执行录取
            throw new RuntimeException("这个状态不能录取");
        }
        List<ExcelMajor> majors = majorMapper.getMajorPlanForEnroll();//获取各专业的招生计划
        Map<String,ExcelMajor> map = new HashMap<>();
        for (ExcelMajor major : majors) {
            map.put(major.getMajorId(),major);
        }
        int current = 0, size = 200;
        while(true){
            List<ExcelStudent> students = studentMapper.getStudentRawForEnroll(current, size);//按排名获取size名学生
            if (students.size() == 0)
                break;
            for (ExcelStudent student : students) {//按志愿执行录取
                if(doEnroll(map.get(student.getWill1()))){
                    student.setAcceptedType(1);//设置录取类型(第几志愿录取)
                    student.setAcceptedMajorId(student.getWill1());//设置录取专业ID
                } else if(doEnroll(map.get(student.getWill2()))){
                    student.setAcceptedType(2);
                    student.setAcceptedMajorId(student.getWill2());
                } else if(doEnroll(map.get(student.getWill3()))){
                    student.setAcceptedType(3);
                    student.setAcceptedMajorId(student.getWill3());
                }else if(doEnroll(map.get(student.getWill4()))){
                    student.setAcceptedType(4);
                    student.setAcceptedMajorId(student.getWill4());
                }else if(doEnroll(map.get(student.getWill5()))){
                    student.setAcceptedType(5);
                    student.setAcceptedMajorId(student.getWill5());
                }else if(doEnroll(map.get(student.getWill6()))){
                    student.setAcceptedType(6);
                    student.setAcceptedMajorId(student.getWill6());
                } else {
                    if(student.getAdjust() != 1)//不愿接受调剂的考生
                        student.setAcceptedType(-1);//归入退档队列
                    else {
                        student.setAcceptedType(0);//归入调剂队列
                    }
                }
            }
            studentMapper.updateAccepted(students);//将录取ID以及录取类型写回数据库
            current = current + size;
        }
        majorMapper.updateStudentCount(majors);//将各专业录取后的人数写入数据库

        statusMapper.addLog("录取完成", EnrollStatus.ENROLLED.ordinal());//写入日志

    }

    private boolean doEnroll(ExcelMajor major){//判断该专业是否可以被录取
        if (major != null && major.getPlanStudentCount() > major.getRealisticStudentCount()){
            major.setRealisticStudentCount(major.getRealisticStudentCount()+1);
            return true;
        }
        return false;
    }


    private void updateSingleStudent(List<ExcelStudent> students){
        for (ExcelStudent student : students) {
            studentMapper.updateSingleAccepted(student);
        }
    }


    @Override
    public void doAdjust(){//执行调剂
        Integer status = statusMapper.getStatus();//获取系统最新状态
        if (status == null || status != EnrollStatus.ENROLLED.ordinal()){//录取完才能开始调剂
            throw new RuntimeException("这个状态不能调剂");
        }
        List<ExcelMajor> majors = majorMapper.getMajorPlanForAdjust();//获取能调剂的专业(该专业未招满人)
        int start = 0, size = 100, index = 0;
        while(true){
            List<ExcelStudent> students = studentMapper.getStudentRawForAdjust(start, size);//获取需要调剂学生的信息
            if(students.size() == 0)
                break;
            for (int i = 0;i<students.size();) {
                ExcelStudent student = students.get(i);
                if(index < majors.size()){
                    ExcelMajor major = majors.get(index);
                    if (major.getRealisticStudentCount() < major.getPlanStudentCount()){//该专业实际招生人数小于计划招生人数时
                        student.setAcceptedType(7);//设置为第七志愿录取(即调剂录取)
                        student.setAcceptedMajorId(major.getMajorId());//设置录取专业ID
                        major.setRealisticStudentCount(major.getRealisticStudentCount()+1);//该专业实际招生人数加一
                        i++;
                    } else {
                        index++;
                    }
                } else {//所有专业都招满了
                    student.setAcceptedType(-1);//退档学生
                    i++;
                }
            }
            studentMapper.updateAdjust(students);//将录取ID以及录取类型写回数据库
            //不能改变start
        }
        majorMapper.updateStudentCount(majors);//将各专业调剂后的最终录取人数写入数据库

        statusMapper.addLog("调剂完成", EnrollStatus.ADJUSTED.ordinal());//写入日志

    }

    @Override
    public PageInfo getResult(int currentPage, boolean desc, QueryResultOption option) {//按照学院和专业查询录取的学生信息(优先按照排名查询)
        PageHelper.startPage(currentPage,50);
        return new PageInfo<>(studentMapper.getStudent(desc, option));
    }

    @Override
    public PageInfo getResultByDepartment(int departmentId, int currentPage, boolean desc) {//按照学院查询录取学生的信息
        PageHelper.startPage(currentPage, 50);
        return new PageInfo<>(studentMapper.getStudentByDepartment(departmentId, desc));
    }

    @Override
    public PageInfo getResultByMajor(String majorId, int currentPage, boolean desc) {//按照专业查询录取学生的信息
        PageHelper.startPage(currentPage, 50);
        return new PageInfo<>(studentMapper.getStudentByMajor(majorId, desc));
    }

    @Override
    public PageInfo searchStudent(int currentPage, String keyword){//按照姓名查询录取学生的信息
        PageHelper.startPage(currentPage, 50);
        return new PageInfo<>(studentMapper.searchStudent(keyword));
    }

    @Override
    public PageInfo searchStudentByCandidate(int currentPage, String keyword){//按照考号查询录取学生的信息
        PageHelper.startPage(currentPage, 50);
        return new PageInfo<>(studentMapper.searchStudentByCandidate(keyword));
    }

    @Override
    public PageInfo getStudentBeforeRank(int currentPage, int rank){//查询排名在前rank名的学生录取结果信息
        PageHelper.startPage(currentPage, 50);
        return new PageInfo<>(studentMapper.getStudentBeforeRank(rank));
    }

    @Override
    public List<StatisticsResult> getStatisticsResult(){//按照全校录取学生进行成绩分析
        return studentMapper.getStatisticsResult();
    }

    @Override
    public List<StatisticsResult> getStatisticsResultInDepartment() {//按照学院录取学生进行成绩分析
        return studentMapper.getStatisticsResultInDepartment();
    }

    @Override
    public List<StatisticsResult> getStatisticsResultInMajor() {//按照专业录取学生进行成绩分析
        List<StatisticsResult> result = studentMapper.getStatisticsResultInMajor();
        return result;
    }

    @Override
    public List<Map<String, Object>> getDistribute() {//录取学生的省份分布
        return studentMapper.getDistribute();
    }

    @Override
    public List<Map<String, Object>> getDistributeInProvince(String province) {//某省份录取学生中各城市的人数分布
        return studentMapper.getDistributeInProvince(province);
    }

    @Override
    public List<Map<String, Object>> getGradeDistribute() {//全部录取学生成绩区间分布
        return studentMapper.getGradeDistribute();
    }

    @Override
    public List<Map<String, Object>> getGradeDistributeByDepartment(int departmentId) {//按照学院查询录取学生成绩区间分布
        return studentMapper.getGradeDistributeByDepartment(departmentId);
    }

    @Override
    public List<Map<String, Object>> getGradeDistributeByMajor(String majorId) {//按照专业查询录取学生成绩区间分布
        return studentMapper.getGradeDistributeByMajor(majorId);
    }

    @Override
    public List<Map<String, Object>> getCountDistributeInDepartment() {//按照学院查询该学院录取学生的人数
        return studentMapper.getCountDistributeInDepartment();
    }

    @Override
    public List<Map<String, Object>> getCountDistributeInMajor() {//按照专业查询该专业录取学生的人数
        return studentMapper.getCountDistributeInMajor();
    }

    @Override
    public List<Map<String, Object>> getCountDistributeInMajorByDepartment(int departmentId) {//按照学院查询该学院下辖各专业录取学生的人数
        return studentMapper.getCountDistributeInMajorByDepartment(departmentId);
    }

    @Override
    @Deprecated//该类已过时，不推荐使用
    public void reset(){
//        Integer status = statusMapper.getStatus();
//        if (status == null || status != EnrollStatus.PRE_ENROLL.ordinal() && status != EnrollStatus.PRE_ADJUST.ordinal()){
//            throw new RuntimeException("这个状态不能重置");
//        }
//        majorMapper.resetMajor();
//        studentMapper.resetStudent();
//        statusMapper.addLog("重置成功", EnrollStatus.FILE_READY.ordinal());
    }

    @Override
    @Deprecated//该类已过时，不推荐使用
    public void formallyReady(){
//        Integer status = statusMapper.getStatus();
//        if (status == null || status != EnrollStatus.PRE_ADJUST.ordinal()){
//            throw new RuntimeException("这个状态不能准备录取");
//        }
//        majorMapper.resetMajor();
//        studentMapper.resetStudent();
//        statusMapper.addLog("准备录取", EnrollStatus.READY.ordinal());
    }
}
