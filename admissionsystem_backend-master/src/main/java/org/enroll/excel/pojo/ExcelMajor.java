package org.enroll.excel.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.apache.poi.ss.usermodel.FillPatternType;

@Data//提高代码的简洁，使用这个注解可以省去代码中大量的get()、 set()、 toString()等方法
@NoArgsConstructor//生成无参构造函数
@Getter//自动为类生成getter方法
@Setter//自动为类生成setter方法
@ToString//自动为类生成toString方法
public class ExcelMajor {//专业实体类

    @ExcelProperty("专业代号")//用于匹配excel和实体类的匹配
    private String majorId;//专业代号

    @ExcelProperty("专业代码")//用于匹配excel和实体类的匹配
    private String majorCode;//专业代码

    private int departmentId;//学院ID

    @ExcelProperty("学院")//用于匹配excel和实体类的匹配
    private String departmentName;//学院名称

    @ExcelProperty("专业名称")//用于匹配excel和实体类的匹配
    private String majorName;//专业名称

    @ExcelProperty("备注")//用于匹配excel和实体类的匹配
    private String comment;//备注

    @ExcelProperty("学制年限")//用于匹配excel和实体类的匹配
    private int period;//学制年限

    @ExcelProperty("招生计划数")
    private int planStudentCount;//计划招生人数

    private int realisticStudentCount;//实际招生人数
}
