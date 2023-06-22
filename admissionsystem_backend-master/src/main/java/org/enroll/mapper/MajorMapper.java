package org.enroll.mapper;

import org.apache.ibatis.annotations.Param;
import org.enroll.excel.pojo.ExcelMajor;
import org.enroll.pojo.Major;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorMapper {

    void insertMajor( @Param("majorList") List<ExcelMajor> list);//插入专业

    List<ExcelMajor> getMajorPlan();//获取专业招生计划

    List<ExcelMajor> getMajorPlanForEnroll();//获取各专业计划招生人数

    List<ExcelMajor> getMajorPlanForAdjust();//执行录取后获取各专业的计划和实际招生人数

    void updateStudentCount(@Param("majors") List<ExcelMajor> majors);//录取后更新实际招生人数

    void updatePlanStudentCount(@Param("majorId") String majorId, @Param("count") int count);//修改专业计划招生人数

    void resetMajor();//执行录取之前应将实际招生人数记为0

    void resetTable();//重置专业信息(删除专业信息)

    List<Major> getMajors();//获取专业信息

    List<Major> getMajorsByDepartment(@Param("departmentId") int departmentId);//通过学院ID获取该学院下的专业信息
}
