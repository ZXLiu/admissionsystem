package org.enroll.controller;

import org.enroll.pojo.StatisticsResult;
import org.enroll.service.interfaces.IStudentService;
import org.enroll.utils.JsonResponse;
import org.enroll.utils.QueryResultOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    IStudentService studentService;

    @RequestMapping("/getStudentRaw")
    public JsonResponse getStudentRaw(@RequestParam(required = false, defaultValue = "1") Integer currentPage){//获取所有学生信息
        if(currentPage == null || currentPage<=0)//不合法数据
            return new JsonResponse(JsonResponse.INVALID_REQUEST,null, null);
        return new JsonResponse(JsonResponse.OK, studentService.getStudentRaw(currentPage), null);
    }

    @RequestMapping("/getAdjustStudentRaw")
    public JsonResponse getAdjustStudentRaw(@RequestParam(required = false, defaultValue = "1") int currentPage){//获取调剂学生的信息
        return new JsonResponse(JsonResponse.OK, studentService.getAdjustStudentRaw(currentPage), null);
    }

    @RequestMapping("/getExitStudentRaw")
    public JsonResponse getExitStudentRaw(@RequestParam(required = false, defaultValue = "1") int currentPage){//获取退档学生的信息
        return new JsonResponse(JsonResponse.OK, studentService.getExitStudentRaw(currentPage), null);
    }

    @RequestMapping("/doEnroll")
    public JsonResponse doEnroll(){//执行录取
        studentService.doEnroll();
        return new JsonResponse(JsonResponse.OK, null, null);
    }

    @RequestMapping("/doAdjust")
    public JsonResponse doAdjust(){//执行调剂
        studentService.doAdjust();
        return new JsonResponse(JsonResponse.OK, null, null);
    }

    @RequestMapping("/getResult")
    public JsonResponse getResult(@RequestParam(required = false, defaultValue = "1") int currentPage,
                                  @RequestParam(required = false, defaultValue = "false") boolean desc,
                                  QueryResultOption option){//按照学院和专业查询录取的学生信息(优先按照排名查询)
        return new JsonResponse(JsonResponse.OK, studentService.getResult(currentPage, desc, option), null);
    }

    @RequestMapping("/getResultByDepartment")
    @Deprecated//不推荐该方法
    public JsonResponse getResultByDepartment(int departmentId, @RequestParam(required = false, defaultValue = "1") int currentPage, @RequestParam(required = false, defaultValue = "false") boolean desc){//按照学院查询录取学生的信息
        return new JsonResponse(JsonResponse.OK, studentService.getResultByDepartment(departmentId, currentPage, desc), null);
    }

    @RequestMapping("/getResultByMajor")
    @Deprecated//不推荐该方法
    public JsonResponse getResultByMajor(String majorId, @RequestParam(required = false, defaultValue = "1") int currentPage, @RequestParam(required = false, defaultValue = "false") boolean desc){//按照专业查询录取学生的信息
        return new JsonResponse(JsonResponse.OK, studentService.getResultByMajor(majorId, currentPage, desc), null);
    }

    @RequestMapping("/searchStudent")
    @Deprecated
    public JsonResponse searchStudent(@RequestParam(required = false, defaultValue = "1") int currentPage,String keyword){//按照姓名查询录取学生的信息
        return new JsonResponse(JsonResponse.OK, studentService.searchStudent(currentPage,keyword), null);
    }


    @RequestMapping("/searchStudentByCandidate")
    public JsonResponse searchStudentByCandidate(@RequestParam(required = false, defaultValue = "1") int currentPage,String keyword){//按照考号查询录取学生的信息
        return new JsonResponse(JsonResponse.OK, studentService.searchStudentByCandidate(currentPage,keyword), null);
    }

    @RequestMapping("/getStudentBeforeRank")
    public JsonResponse getStudentBeforeRank(@RequestParam(required = false, defaultValue = "1") int currentPage, int rank){//查询排名在前rank名的学生录取结果信息
        return new JsonResponse(JsonResponse.OK, studentService.getStudentBeforeRank(currentPage, rank), null);
    }


    @RequestMapping("/getStatisticsResult")
    public JsonResponse getStatisticsResult(){//按照全校录取学生进行成绩分析
        return new JsonResponse(JsonResponse.OK, studentService.getStatisticsResult(), null);
    }
//    List<Map<String, Object>> getResultInDepartment(int departmentId);
    @RequestMapping("/getStatisticsResultInDepartment")
    public JsonResponse getStatisticsResultInDepartment(){//按照学院录取学生进行成绩分析
        return new JsonResponse(JsonResponse.OK, studentService.getStatisticsResultInDepartment(), null);
    }
//    List<Map<String, Object>> getResultInMajor(String majorId);
    @RequestMapping("/getStatisticsResultInMajor")
    public JsonResponse getStatisticsResultInMajor(){//按照专业录取学生进行成绩分析
        return new JsonResponse(JsonResponse.OK, studentService.getStatisticsResultInMajor(), null);
    }
    //    Map<String, Integer> getDistribute();
    @RequestMapping("/getDistribute")
    public JsonResponse getDistribute(){//录取学生的省份分布
        return new JsonResponse(JsonResponse.OK, studentService.getDistribute(), null);
    }
    //    Map<String, Integer> getDistributeInProvince(String province);
    @RequestMapping("/getDistributeInProvince")
    public JsonResponse getDistributeInProvince(String province){//某省份录取学生中各城市的人数分布
        return new JsonResponse(JsonResponse.OK, studentService.getDistributeInProvince(province), null);
    }
    //    Map<String, Integer> getGradeDistribute();
    @RequestMapping("/getGradeDistribute")
    public JsonResponse getGradeDistribute(){//全部录取学生成绩区间分布
        return new JsonResponse(JsonResponse.OK, studentService.getGradeDistribute(), null);
    }
    //    Map<String, Integer> getGradeDistributeByDepartment( int departmentId);
    @RequestMapping("/getGradeDistributeByDepartment")
    public JsonResponse getGradeDistributeByDepartment(int departmentId){//按照学院查询录取学生成绩区间分布
        return new JsonResponse(JsonResponse.OK, studentService.getGradeDistributeByDepartment(departmentId), null);
    }
    //    Map<String, Integer> getGradeDistributeByMajor(String majorId);
    @RequestMapping("/getGradeDistributeByMajor")
    public JsonResponse getGradeDistributeByMajor(String majorId){//按照专业查询录取学生成绩区间分布
        return new JsonResponse(JsonResponse.OK, studentService.getGradeDistributeByMajor(majorId), null);
    }
    //    Map<String, Integer> getCountDistributeInDepartment();
    @RequestMapping("/getCountDistributeInDepartment")
    public JsonResponse getCountDistributeInDepartment(){//按照学院查询该学院录取学生的人数
        return new JsonResponse(JsonResponse.OK, studentService.getCountDistributeInDepartment(), null);
    }
    //    Map<String, Integer> getCountDistributeInMajor();
    @RequestMapping("/getCountDistributeInMajor")
    public JsonResponse getCountDistributeInMajor(){//按照专业查询该专业录取学生的人数
        return new JsonResponse(JsonResponse.OK, studentService.getCountDistributeInMajor(), null);
    }
    //    Map<String, Integer> getCountDistributeInMajorByDepartment(int departmentId);
    @RequestMapping("/getCountDistributeInMajorByDepartment")
    public JsonResponse getCountDistributeInMajorByDepartment(int departmentId){//按照学院查询该学院下辖各专业录取学生的人数
        return new JsonResponse(JsonResponse.OK, studentService.getCountDistributeInMajorByDepartment(departmentId), null);
    }

    @RequestMapping("/reset")
    @Deprecated
    public JsonResponse reset(){
        studentService.reset();
        return new JsonResponse(JsonResponse.OK, null, null);
    }

    @RequestMapping("/formalReady")
    @Deprecated
    public JsonResponse formalReady(){
        studentService.formallyReady();
        return new JsonResponse(JsonResponse.OK, null, null);
    }
}
