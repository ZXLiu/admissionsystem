package org.enroll.controller;

import org.enroll.service.interfaces.IStatusService;
import org.enroll.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class StatusController {

    @Autowired
    private IStatusService statusService;


    @RequestMapping("/getStatus")
    public JsonResponse getStatus(){//获取系统最新状态
        return new JsonResponse(JsonResponse.OK, statusService.getStatus(), null);
    }


    @RequestMapping("/getLogList")
    public JsonResponse getLogList(){//获取日志信息
        return new JsonResponse(JsonResponse.OK, statusService.getLogList(), null);
    }

    @RequestMapping("/reset")
    public JsonResponse reset() {//重置系统(清空所有表中信息)
        statusService.reset();
        return new JsonResponse(JsonResponse.OK, null, null);
    }
}
