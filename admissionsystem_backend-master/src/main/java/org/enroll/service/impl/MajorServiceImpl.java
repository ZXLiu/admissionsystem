package org.enroll.service.impl;

import org.enroll.excel.pojo.ExcelMajor;
import org.enroll.mapper.MajorMapper;
import org.enroll.mapper.StatusMapper;
import org.enroll.pojo.EnrollStatus;
import org.enroll.pojo.Major;
import org.enroll.service.interfaces.IMajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorServiceImpl implements IMajorService {


    @Autowired
    private MajorMapper majorMapper;

    @Autowired
    private StatusMapper statusMapper;


    @Override
    public void updateMajorPlan(String majorId, int count) {//更新专业招生计划
        Integer status = statusMapper.getStatus();//获取系统最新状态
        if (status == null || status != EnrollStatus.FILE_READY.ordinal() && status != EnrollStatus.WITHOUT_STUDENT.ordinal()){//在执行录取之后不能修改招生计划
            throw new RuntimeException("此时不能修改招生计划");
        }
        majorMapper.updatePlanStudentCount(majorId, count);
    }


    @Override
    public List<ExcelMajor> getMajorPlan(){
        return majorMapper.getMajorPlan();
    }//获取专业招生计划

    @Override
    public List<Major> getMajors() {
        return majorMapper.getMajors();
    }//获取专业ID以及名称

    @Override
    public List<Major> getMajorsByDepartment(int departmentId) {//获取指定学院的专业ID以及名称
        return majorMapper.getMajorsByDepartment(departmentId);
    }
}
