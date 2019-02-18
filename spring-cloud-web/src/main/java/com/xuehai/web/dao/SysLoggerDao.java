package com.xuehai.web.dao;

import com.xuehai.web.entity.SysLoggerEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysLoggerDao {

    public void saveSysLooger(SysLoggerEntity sysLoggerEntity);

}
