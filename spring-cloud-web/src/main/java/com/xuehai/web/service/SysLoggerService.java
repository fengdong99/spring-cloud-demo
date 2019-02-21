package com.xuehai.web.service;

import com.xuehai.web.dao.SysLoggerDao;
import com.xuehai.web.entity.SysLoggerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLoggerService {
    @Autowired
    private SysLoggerDao sysLoggerDao;

    public void saveSysLooger(SysLoggerEntity sysLoggerEntity){
        try {
            Thread.sleep(1000 * 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sysLoggerDao.saveSysLooger(sysLoggerEntity);
    }
}
