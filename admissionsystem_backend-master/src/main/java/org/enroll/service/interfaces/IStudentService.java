package org.enroll.service.interfaces;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.enroll.excel.pojo.ExcelStudent;
import org.enroll.pojo.StatisticsResult;
import org.enroll.utils.QueryResultOption;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface IStudentService {

    PageInfo getStudentRaw(int currentPage);//获取所有学生信息

    PageInfo getAdjustStudentRaw(int currentPage);//获取调剂学生的信息

    PageInfo getExitStudentRaw(int currentPage);//获取退档学生的信息

    void doEnroll();//执行录取

    void doAdjust();//执行调剂

    PageInfo getResult(int currentPage, boolean desc, QueryResultOption option);//按照学院和专业查询录取的学生信息(优先按照排名查询)

    PageInfo getResultByDepartment( int departmentId, int currentPage, boolean desc);//按照学院查询录取学生的信息

    PageInfo getResultByMajor( String majorId, int currentPage, boolean desc);//按照专业查询录取学生的信息

    PageInfo searchStudent(int currentPage, String keyword);//按照姓名查询录取学生的信息

    PageInfo searchStudentByCandidate(int currentPage, String keyword);//按照考号查询录取学生的信息

    PageInfo getStudentBeforeRank(int currentPage, int rank);//查询排名在前rank名的学生录取结果信息

    List<StatisticsResult> getStatisticsResult();//按照全校录取学生进行成绩分析

    List<StatisticsResult> getStatisticsResultInDepartment();//按照学院录取学生进行成绩分析

    List<StatisticsResult> getStatisticsResultInMajor();//按照专业录取学生进行成绩分析

    List<Map<String, Object>> getDistribute();//录取学生的省份分布

    List<Map<String, Object>> getDistributeInProvince(String province);//某省份录取学生中各城市的人数分布

    List<Map<String, Object>> getGradeDistribute();//全部录取学生成绩区间分布

    List<Map<String, Object>> getGradeDistributeByDepartment( int departmentId);//按照学院查询录取学生成绩区间分布

    List<Map<String, Object>> getGradeDistributeByMajor(String majorId);//按照专业查询录取学生成绩区间分布

    List<Map<String, Object>> getCountDistributeInDepartment();//按照学院查询该学院录取学生的人数

    List<Map<String, Object>> getCountDistributeInMajor();//按照专业查询该专业录取学生的人数

    List<Map<String, Object>> getCountDistributeInMajorByDepartment(int departmentId);//按照学院查询该学院下辖各专业录取学生的人数

    void reset();

    void formallyReady();
}
