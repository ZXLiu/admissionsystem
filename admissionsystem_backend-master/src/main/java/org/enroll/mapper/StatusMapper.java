package org.enroll.mapper;

import org.apache.ibatis.annotations.Param;
import org.enroll.pojo.Log;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StatusMapper {

    void addLog(@Param("content") String content, @Param("status") int status);//添加日志记录

    Integer getStatus();//获取系统最新状态

    List<Log> getLogList();//获取日志信息
}
