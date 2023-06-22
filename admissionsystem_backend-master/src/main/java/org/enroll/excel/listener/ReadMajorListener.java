package org.enroll.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.enroll.excel.pojo.ExcelMajor;
import org.enroll.exception.ReadExcelException;
import org.enroll.mapper.DepartmentMapper;
import org.enroll.mapper.MajorMapper;
import org.enroll.pojo.Department;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j//用作日志输出
@NoArgsConstructor//生成无参构造函数
public class ReadMajorListener extends AnalysisEventListener<ExcelMajor> {

    private static List<ExcelMajor> list = new ArrayList<>();

    private static final int BATCH_COUNT = 20;//每批导入数据库招生计划数量

    private MajorMapper majorMapper;

    private DepartmentMapper departmentMapper;

    private Map<String, Integer> departmentIds = new HashMap<>();

    public ReadMajorListener(MajorMapper mapper, DepartmentMapper departmentMapper){//构造函数用于获取学院名称以及ID
        this.majorMapper = mapper;
        this.departmentMapper = departmentMapper;
        for (Department department : departmentMapper.getDepartments()) {
            departmentIds.put(department.getDepartmentName(),department.getDepartmentId());
        }
    }


    @Override
    public void invoke(ExcelMajor excelMajor, AnalysisContext analysisContext) {//每解析一条数据，都会来调用该方法对所有数据进行校验，在此增加校验逻辑
        list.add(excelMajor);
        if (list.size() >= BATCH_COUNT){//每读取20条数据就将其写入数据库，然后清空list，以便继续读取
            this.save();
            list.clear();
        }
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {//检查Excel文件格式是否正确
        throw new ReadExcelException("导入Excel失败，请检查文件格式");
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {//所有数据都解析完成后，会调用该方法
        System.out.println("finish");
        this.save();
        list.clear();
    }

    private void save(){
        for (ExcelMajor excelMajor : list) {
            if (StringUtils.isEmpty(excelMajor.getDepartmentName())) {//如果专业所属学院名称为空
                list.clear();
                throw new ReadExcelException("导入Excel失败，请检查文件格式");
            }

            Integer id = departmentIds.get(excelMajor.getDepartmentName());//获取该专业所属学院ID(因为Excel文件没有该字段)
            if(id == null){
                Department newDept = new Department();
                newDept.setDepartmentName(excelMajor.getDepartmentName());
                departmentMapper.insertDepartment(newDept);
                departmentIds.put(newDept.getDepartmentName(),newDept.getDepartmentId());
                id = newDept.getDepartmentId();
            }
            excelMajor.setDepartmentId(id);//设置学院ID字段
        }
        if (list.size() > 0)
            majorMapper.insertMajor(list);//写回数据库
    }
}
