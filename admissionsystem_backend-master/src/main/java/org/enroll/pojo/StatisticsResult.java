package org.enroll.pojo;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Setter//自动为类生成setter方法
@Getter//自动为类生成getter方法
@Alias("statisticsResult")//修改别名，可以在mapper.xml 文件中resultType中可以直接使用
@Data//提高代码的简洁，使用这个注解可以省去代码中大量的get()、 set()、 toString()等方法
public class StatisticsResult {//统计结果实体类

    private Integer topRank;//最高排名

    private Integer bottomRank;//最低排名

    private Integer maxGrade;//最高分

    private Integer minGrade;//最低分

    private Integer averageGrade;//平均分

    private String groupName;//分组名称
}
