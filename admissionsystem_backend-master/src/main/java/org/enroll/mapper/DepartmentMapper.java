package org.enroll.mapper;

import org.apache.ibatis.annotations.Param;
import org.enroll.pojo.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentMapper {

    void insertDepartment(Department department);//插入学院

    List<Department> getDepartments();//获取学院信息

    void resetTable();//重置学院信息(删除学院信息)
}
