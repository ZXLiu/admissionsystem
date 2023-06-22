package org.enroll.excel.pojo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import lombok.*;
import org.apache.poi.ss.usermodel.FillPatternType;

@Data//提高代码的简洁，使用这个注解可以省去代码中大量的get()、 set()、 toString()等方法
@NoArgsConstructor//生成无参构造函数
@Setter//自动为类生成setter方法
@Getter//自动为类生成getter方法
@ToString//自动为类生成toString方法
@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 9)//设置标题样式
@HeadFontStyle(fontHeightInPoints = 10)//设置标题字体格式(设置字体高度为10)
@ExcelIgnoreUnannotated//没有注解的字段都不转换成Excel列名属性
public class ExcelStudent {//学生实体类

    private int studentId;//学生ID

    @ExcelProperty("准考证号")//用于匹配excel和实体类的匹配
    private String candidate;//准考证号

    @ExcelProperty("姓名")//用于匹配excel和实体类的匹配
    private String studentName;//姓名

    @ExcelProperty("总分")//用于匹配excel和实体类的匹配
    private int totalGrade;//总分

    @ExcelProperty("志愿1")//用于匹配excel和实体类的匹配
    private String will1;//第一志愿

    @ExcelProperty("志愿2")//用于匹配excel和实体类的匹配
    private String will2;//第二志愿

    @ExcelProperty("志愿3")//用于匹配excel和实体类的匹配
    private String will3;//第三志愿

    @ExcelProperty("志愿4")//用于匹配excel和实体类的匹配
    private String will4;//第四志愿

    @ExcelProperty("志愿5")//用于匹配excel和实体类的匹配
    private String will5;//第五志愿

    @ExcelProperty("志愿6")//用于匹配excel和实体类的匹配
    private String will6;//第六志愿

    @ExcelProperty("调剂")//用于匹配excel和实体类的匹配
    private int adjust;//是否调剂

    @ExcelProperty("排位")//用于匹配excel和实体类的匹配
    private int rank;//排名

    @ExcelProperty("省份")//用于匹配excel和实体类的匹配
    private String province;//省份

    @ExcelProperty("城市")//用于匹配excel和实体类的匹配
    private String city;//城市

    @ExcelProperty("科类")//用于匹配excel和实体类的匹配
    private String subjectType;//科类

    private String acceptedMajorId;//录取专业ID

    private int acceptedType;//录取类型(第几志愿录取)
}
