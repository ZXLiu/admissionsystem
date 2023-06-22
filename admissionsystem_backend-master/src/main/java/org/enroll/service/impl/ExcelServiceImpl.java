package org.enroll.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.enroll.excel.listener.ReadMajorListener;
import org.enroll.excel.listener.ReadStudentListener;
import org.enroll.excel.pojo.ExcelMajor;
import org.enroll.excel.pojo.ExcelStudent;
import org.enroll.mapper.DepartmentMapper;
import org.enroll.mapper.MajorMapper;
import org.enroll.mapper.StatusMapper;
import org.enroll.mapper.StudentMapper;
import org.enroll.pojo.EnrollStatus;
import org.enroll.pojo.StudentResult;
import org.enroll.service.interfaces.IExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class ExcelServiceImpl implements IExcelService {

    @Autowired
    private MajorMapper majorMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StatusMapper statusMapper;

    public void ReadMajorExcel(MultipartFile file) throws IOException {//读取专业招生计划表格
        Integer status = statusMapper.getStatus();//获取系统最新状态
        majorMapper.resetTable();//清空t_major表
        studentMapper.resetStudent();//将t_student表中录取专业设为null，录取类型设为-2
        if (status != null && status != EnrollStatus.START.ordinal()){//导入过文件后执行录取期间不能再导入文件
            throw new RuntimeException("现在不能导入招生计划文件");
        }
        EasyExcel.read(file.getInputStream(), ExcelMajor.class, new ReadMajorListener(majorMapper, departmentMapper)).sheet().doRead();//读入文件
        statusMapper.addLog("导入专业招生计划文件", EnrollStatus.WITHOUT_STUDENT.ordinal());//写入日志
    }

    public void ReadStudentExcel(MultipartFile file) throws IOException {//读取学生志愿信息表格
        Integer status = statusMapper.getStatus();//获取系统最新状态
        if (status != EnrollStatus.WITHOUT_STUDENT.ordinal()){//导入过文件后执行录取期间不能再导入文件
            throw new RuntimeException("现在不能导入考生志愿文件");
        }
        studentMapper.resetTable();//清空t_student表格
        majorMapper.resetMajor();//将t_major表中的实际招生人数设为0
        EasyExcel.read(file.getInputStream(), ExcelStudent.class,new ReadStudentListener(studentMapper)).sheet().doRead();//读入文件
        statusMapper.addLog("导入考生志愿文件", EnrollStatus.FILE_READY.ordinal());//写入日志
    }

    @Override
    public void doExport(OutputStream os){//导出录取学生信息
        int start = 0, size = 200;
        ExcelWriter excelWriter = null;
        Integer status = statusMapper.getStatus();//获取系统最新状态
        if (status != EnrollStatus.ADJUSTED.ordinal()){//系统流程未执行完成时
            throw new RuntimeException("未结束流程不能导出结果");
        }
        try {
            excelWriter = EasyExcel.write(os, StudentResult.class).build();
            WriteSheet writeSheet = EasyExcel.writerSheet("录取结果").build();//建立名为"录取结果"的Excel文件
            while(true) {
                List<StudentResult> results = studentMapper.getStudentForExport(start,size);//每次读取200条录取学生的信息
                if(results.size() == 0)//读取完毕退出
                    break;
                excelWriter.write(results, writeSheet);//写入Excel
                start += size;
            }
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    @Override
    public void exportExitStudent(OutputStream os) {//导出退档学生信息
        int start = 0, size = 200;
        ExcelWriter excelWriter = null;
        Integer status = statusMapper.getStatus();//获取系统最新状态
        if (status != EnrollStatus.ADJUSTED.ordinal()){//系统流程未执行完成时
            throw new RuntimeException("未结束流程不能导出结果");
        }
        try {
            excelWriter = EasyExcel.write(os, ExcelStudent.class).build();
            WriteSheet writeSheet = EasyExcel.writerSheet("退档结果").build();//建立名为"退档结果"的Excel文件
            while(true) {
                List<ExcelStudent> results = studentMapper.getExitStudentForExport(start,size);//每次读取200条退档学生的信息
                if(results.size() == 0)//读取完毕退出
                    break;
                excelWriter.write(results, writeSheet);//写入Excel
                start += size;
            }
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }
}
