package org.enroll.service.interfaces;

import org.enroll.pojo.Log;

import java.util.List;

public interface IStatusService {

    Integer getStatus();//获取系统最新状态

    List<Log> getLogList();//获取日志信息

    void reset();//重置系统(清空所有表中信息)
}
