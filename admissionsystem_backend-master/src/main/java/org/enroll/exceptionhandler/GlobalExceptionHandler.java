package org.enroll.exceptionhandler;

import com.alibaba.excel.exception.ExcelDataConvertException;
import org.enroll.exception.ReadExcelException;
import org.enroll.utils.JsonResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice//Controller增强器，作用是给Controller控制器添加统一的操作或处理
public class GlobalExceptionHandler {

    @ResponseBody//需要返回json串
    @ExceptionHandler({ReadExcelException.class})//用于捕获Controller中抛出的指定类型的异常，从而达到不同类型的异常区别处理的目的
    public JsonResponse handleExcelException(ReadExcelException e) {
        return new JsonResponse(JsonResponse.INVALID_REQUEST, null, "导入Excel失败，请检查文件格式");
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public JsonResponse handle(Exception e){
        if(e instanceof  IllegalStateException){
            return new JsonResponse(JsonResponse.INVALID_REQUEST, null, e.getMessage());
        }
        return new JsonResponse(JsonResponse.SYSTEM_ERROR, null, e.getMessage());
    }


}
