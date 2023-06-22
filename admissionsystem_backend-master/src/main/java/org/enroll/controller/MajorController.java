package org.enroll.controller;

import org.enroll.service.interfaces.IMajorService;
import org.enroll.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/major")
public class MajorController {

    @Autowired
    private IMajorService majorService;

    @RequestMapping("/getMajorPlan")
    public JsonResponse getMajorPlan(){//获取专业招生计划
        return new JsonResponse(JsonResponse.OK, majorService.getMajorPlan(), null);
    }

    @RequestMapping("/updateMajorPlan")
    public JsonResponse updateMajorPlan(String majorId, int count){//更新专业招生计划
        if (count < 0) return new JsonResponse(JsonResponse.INVALID_REQUEST, null, "输入数字不正确");//输入不合法
        majorService.updateMajorPlan(majorId, count );//更新专业招生计划
        return new JsonResponse(JsonResponse.OK, null, null);
    }

    @RequestMapping("/getMajors")
    public JsonResponse getMajors(){//获取专业ID以及名称
        return new JsonResponse(JsonResponse.OK, majorService.getMajors(), null);
    }

    @RequestMapping("/getMajorsByDepartment")
    public JsonResponse getMajorsByDepartment(int departmentId){//获取指定学院的专业ID以及名称
        return new JsonResponse(JsonResponse.OK, majorService.getMajorsByDepartment(departmentId), null);
    }
}
