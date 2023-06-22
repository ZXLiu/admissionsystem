package org.enroll.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;

public interface IExcelService {


    void ReadMajorExcel(MultipartFile file) throws IOException ;//读取专业招生计划表格

    void ReadStudentExcel(MultipartFile file) throws IOException ;//读取学生报考信息表格

    void doExport(OutputStream os);//导出录取学生信息

    void exportExitStudent(OutputStream os);//导出退档学生信息
}
