package com.xuehai.web.entity;

public class SysLoggerEntity {


    public String id;
    public String name;
    public String execTime;
    public String createDate;

    public SysLoggerEntity() {
    }

    public SysLoggerEntity(String id, String name, String execTime, String createDate) {
        this.id = id;
        this.name = name;
        this.execTime = execTime;
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExecTime() {
        return execTime;
    }

    public void setExecTime(String execTime) {
        this.execTime = execTime;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
