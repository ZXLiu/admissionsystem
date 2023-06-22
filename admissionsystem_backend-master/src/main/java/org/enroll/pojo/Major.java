package org.enroll.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data//提高代码的简洁，使用这个注解可以省去代码中大量的get()、 set()、 toString()等方法
@Alias("major")//修改别名，可以在mapper.xml 文件中resultType中可以直接使用
public class Major {//专业实体类

    private String majorId;//专业ID

    private String majorName;//专业名称
}
