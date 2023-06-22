package org.enroll.service.impl;

import org.enroll.mapper.DepartmentMapper;
import org.enroll.mapper.MajorMapper;
import org.enroll.mapper.StatusMapper;
import org.enroll.mapper.StudentMapper;
import org.enroll.pojo.EnrollStatus;
import org.enroll.pojo.Log;
import org.enroll.service.interfaces.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StatusServiceImpl implements IStatusService {

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private MajorMapper majorMapper;


    @Override
    public Integer getStatus() {
        return statusMapper.getStatus();
    }//获取系统最新状态


    @Override
    public List<Log> getLogList(){
        return statusMapper.getLogList();
    }//获取日志信息


    @Override
    public void reset() {//重置系统(清空所有表中信息)

        studentMapper.resetTable();
        majorMapper.resetTable();
        departmentMapper.resetTable();
        statusMapper.addLog("重置系统", EnrollStatus.START.ordinal());
    }
}
