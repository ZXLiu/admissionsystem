package org.enroll.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Data//提高代码的简洁，使用这个注解可以省去代码中大量的get()、 set()、 toString()等方法
@Alias("log")//修改别名，可以在mapper.xml 文件中resultType中可以直接使用
public class Log {//日志类(用于记录操作过程)

    private String logContent;//操作内容

    private Date logTime;//操作时间
}
