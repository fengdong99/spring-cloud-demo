package com.xuehai.web.annotation;

import com.xuehai.web.service.SysLoggerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class SysTime implements Serializable {
    public static final long startTime = 1111;

    @Autowired
    private SysLoggerService sysLoggerService;


    public  SysLoggerService init(){
        return sysLoggerService;
    }
}
