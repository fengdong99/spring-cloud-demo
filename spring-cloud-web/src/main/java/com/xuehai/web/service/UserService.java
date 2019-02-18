package com.xuehai.web.service;

import com.xuehai.web.annotation.SysLogger;
import com.xuehai.web.dao.UserDao;
import com.xuehai.web.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 39450 on 2018/10/1.
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @SysLogger(name = "service 测试",startTopic = true,topicId = "myTopic123")
    public UserEntity selectUserInfo(Integer id) {
        return userDao.selectUserInfo(id);
    }
}
