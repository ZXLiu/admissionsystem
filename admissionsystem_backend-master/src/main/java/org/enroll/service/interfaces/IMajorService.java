package org.enroll.service.interfaces;

import org.enroll.excel.pojo.ExcelMajor;
import org.enroll.pojo.Major;

import java.util.List;

public interface IMajorService {

    void updateMajorPlan(String majorId, int count);//更新专业招生计划

    List<ExcelMajor> getMajorPlan();//获取专业招生计划

    List<Major> getMajors();//获取专业ID以及名称

    List<Major> getMajorsByDepartment(int departmentId);//获取指定学院的专业ID以及名称
}
