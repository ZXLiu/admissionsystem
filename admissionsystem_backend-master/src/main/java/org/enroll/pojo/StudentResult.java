package org.enroll.pojo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.apache.poi.ss.usermodel.FillPatternType;

@Alias("studentResult")//修改别名，可以在mapper.xml 文件中resultType中可以直接使用
@Getter//自动为类生成getter方法
@Setter//自动为类生成setter方法
@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 9)//设置标题样式
@HeadFontStyle(fontHeightInPoints = 10)//设置标题字体格式(设置字体高度为10)
@ExcelIgnoreUnannotated//没有注解的字段都不转换成Excel列名属性
public class StudentResult {//学生实体结果类

    private int studentId;//ID

    @ExcelProperty("考号")//用于匹配excel和实体类的匹配
    private String candidate;//考号

    @ExcelProperty("姓名")//用于匹配excel和实体类的匹配
    private String studentName;//姓名

    @ExcelProperty("总分")//用于匹配excel和实体类的匹配
    private int totalGrade;//总分

    @ExcelProperty("排名")//用于匹配excel和实体类的匹配
    private int rank;//排名

    @ExcelProperty("省份")//用于匹配excel和实体类的匹配
    private String province;//省份

    @ExcelProperty("城市")//用于匹配excel和实体类的匹配
    private String city;//城市

    @ExcelProperty("专业")//用于匹配excel和实体类的匹配
    private String majorName;//专业名称

    @ExcelProperty("学院")//用于匹配excel和实体类的匹配
    private String departmentName;//所在学院名称

    private int acceptedType;//录取类型(第几志愿录取)
}
