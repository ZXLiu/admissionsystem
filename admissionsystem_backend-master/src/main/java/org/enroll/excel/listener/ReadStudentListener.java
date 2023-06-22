package org.enroll.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.enroll.excel.pojo.ExcelStudent;
import org.enroll.exception.ReadExcelException;
import org.enroll.mapper.StudentMapper;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ReadStudentListener extends AnalysisEventListener<ExcelStudent> {
    private static List<ExcelStudent> list = new ArrayList<>();
    private static final int BATCH_COUNT = 20;//每批导入数据库招生计划数量

    private StudentMapper studentMapper;

    public ReadStudentListener(StudentMapper studentMapper){
        this.studentMapper = studentMapper;
    }


    @Override
    public void invoke(ExcelStudent excelStudent, AnalysisContext analysisContext) {//每解析一条数据，都会来调用该方法对所有数据进行校验，在此增加校验逻辑
        list.add(excelStudent);
        if (list.size() >= BATCH_COUNT) {
            this.save();
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {//所有数据都解析完成后，会调用该方法
        this.save();
        list.clear();
    }

    private void save(){
        if (list.size() > 0) {
            if (StringUtils.isEmpty(list.get(0).getStudentName())) {//如果学生姓名为空
                list.clear();
                throw new ReadExcelException("导入Excel失败，请检查文件格式");
            }
            this.studentMapper.insertStudent(list);//写回数据库
        }

    }

    @Override
    public void onException(Exception exception, AnalysisContext context){//检查Excel文件格式是否正确
        throw new ReadExcelException("导入Excel失败，请检查文件格式");
    }


}
