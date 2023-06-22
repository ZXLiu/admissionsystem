package org.enroll.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Data//提高代码的简洁，使用这个注解可以省去代码中大量的get()、 set()、 toString()等方法
@Getter//自动为类生成getter方法
@Setter//自动为类生成setter方法
@Alias("department")//修改别名，可以在mapper.xml 文件中resultType中可以直接使用
public class Department {//学院实体类

    private int departmentId;//学院ID

    private String departmentName;//学院名称
}
