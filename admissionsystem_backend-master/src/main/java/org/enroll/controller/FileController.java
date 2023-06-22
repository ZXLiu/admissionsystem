package org.enroll.controller;

import org.enroll.service.interfaces.IExcelService;
import org.enroll.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    IExcelService excelService;


    @ResponseBody
    @RequestMapping("/uploadMajor")
    public JsonResponse uploadMajorExcel(MultipartFile file) throws IOException {//读入专业招生计划文件
        excelService.ReadMajorExcel(file);
        return new JsonResponse(JsonResponse.OK,null,null);
    }

    @ResponseBody
    @RequestMapping("/uploadStudent")
    public JsonResponse uploadStudentExcel(MultipartFile file) throws IOException {//读入学生志愿信息文件
        excelService.ReadStudentExcel(file);
        return new JsonResponse(JsonResponse.OK,null,null);
    }

    @RequestMapping("/exportResult")
    public void export(HttpServletResponse response) throws IOException {//导出录取学生信息到Excel
        response.setContentType("application/vnd.ms-excel");//设置被发送到客户端的响应的内容类型
        response.setCharacterEncoding("utf-8");//设置被发送到客户端的响应的字符编码
        String fileName = URLEncoder.encode("录取结果", "UTF-8").replaceAll("\\+", "%20");//设置文件名
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");//设置一个带有给定的名称和值的Header
        excelService.doExport(response.getOutputStream());//导出文件
    }

    @RequestMapping("/exportExit")
    public void exportExit(HttpServletResponse response) throws IOException {//导出退档学生信息到Excel
        response.setContentType("application/vnd.ms-excel");//设置被发送到客户端的响应的内容类型
        response.setCharacterEncoding("utf-8");//设置被发送到客户端的响应的字符编码
        String fileName = URLEncoder.encode("退档结果", "UTF-8").replaceAll("\\+", "%20");//设置文件名
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");//设置一个带有给定的名称和值的Header
        excelService.exportExitStudent(response.getOutputStream());//导出文件
    }
}
