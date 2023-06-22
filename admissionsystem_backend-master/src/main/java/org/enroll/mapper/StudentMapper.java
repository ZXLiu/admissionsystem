package org.enroll.mapper;

import org.apache.ibatis.annotations.Param;
import org.enroll.excel.pojo.ExcelStudent;

import org.enroll.pojo.Major;
import org.enroll.pojo.StatisticsResult;
import org.enroll.pojo.StudentResult;
import org.enroll.utils.QueryResultOption;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StudentMapper {

    void insertStudent(@Param("studentList") List<ExcelStudent> students);//插入学生信息

    List<ExcelStudent> getStudentRaw();//获取所有学生信息

    List<ExcelStudent> getAdjustStudentRaw();//获取调剂学生的信息

    List<ExcelStudent> getExitStudentRaw();//获取退档学生的信息

    List<ExcelStudent> getStudentRawForEnroll(@Param("start") int start, @Param("size") int size);//获取指定位次和数量的录取学生的信息

    List<ExcelStudent> getStudentRawForAdjust(@Param("start") int start, @Param("size") int size);//获取指定位次和数量的调剂学生的信息

    void updateAccepted(@Param("students") List<ExcelStudent> students);//更新数据库中学生表全部录取学生的录取专业以及录取志愿类型

    void updateSingleAccepted(ExcelStudent student);//录取后更新数据库中学生表单个学生的录取专业以及录取志愿类型

    void updateAdjust(@Param("students") List<ExcelStudent> students);//调剂后更新数据库中学生表全部调剂学生的录取专业以及录取志愿类型

    List<StudentResult> getStudent(@Param("desc") boolean desc, @Param("option") QueryResultOption option);//按照学院和专业查询录取的学生信息(优先按照排名查询)

    List<StudentResult> getStudentBeforeRank(@Param("rank") int rank);//查询排名在前rank名的学生录取结果信息

    List<StudentResult> getStudentForExport(@Param("start") int start, @Param("size") int size);//查询排名从start开始size个学生的录取结果信息

    List<ExcelStudent> getExitStudentForExport(@Param("start") int start, @Param("size") int size);//查询排名从start开始size个退档学生的信息

    List<StudentResult> getStudentByDepartment(@Param("depId") int departmentId, @Param("desc") boolean desc);//按照学院查询录取学生的信息

    List<StudentResult> getStudentByMajor(@Param("majorId") String majorId, @Param("desc") boolean desc);//按照专业查询录取学生的信息

    List<StudentResult> searchStudent(@Param("keyword") String keyword);//按照姓名查询录取学生的信息

    List<StudentResult> searchStudentByCandidate(@Param("candidate") String candidate);//按照考号查询录取学生的信息

    List<StatisticsResult> getStatisticsResult();//按照全校录取学生进行成绩分析

    List<StatisticsResult> getStatisticsResultInDepartment();//按照学院录取学生进行成绩分析

    List<StatisticsResult> getStatisticsResultInMajor();//按照专业录取学生进行成绩分析

    List<Map<String, Object>> getDistribute();//录取学生的省份分布

    List<Map<String, Object>> getDistributeInProvince(@Param("province") String province);//某省份录取学生中各城市的人数分布

    List<Map<String, Object>> getGradeDistribute();//全部录取学生成绩区间分布

    List<Map<String, Object>> getGradeDistributeByDepartment(@Param("departmentId") int departmentId);//按照学院查询录取学生成绩区间分布

    List<Map<String, Object>> getGradeDistributeByMajor(@Param("majorId") String majorId);//按照专业查询录取学生成绩区间分布

    List<Map<String, Object>> getCountDistributeInDepartment();//按照学院查询该学院录取学生的人数

    List<Map<String, Object>> getCountDistributeInMajor();//按照专业查询该专业录取学生的人数

    List<Map<String, Object>> getCountDistributeInMajorByDepartment(@Param("departmentId") int departmentId);//按照学院查询该学院下辖各专业录取学生的人数

    void resetStudent();//录取之前设置录取专业ID为空，录取类型为-2

    void resetTable();//重置(删除)学生表中的数据



}
