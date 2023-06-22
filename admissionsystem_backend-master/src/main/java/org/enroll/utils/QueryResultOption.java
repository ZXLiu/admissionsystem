package org.enroll.utils;

import lombok.Data;

@Data//提高代码的简洁，使用这个注解可以省去代码中大量的get()、 set()、 toString()等方法
public class QueryResultOption {

    private Integer rank;//排名

    private Integer departmentId;//学院ID

    private String majorId;//专业ID


}
