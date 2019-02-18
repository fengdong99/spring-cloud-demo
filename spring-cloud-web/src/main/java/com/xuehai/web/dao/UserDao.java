package com.xuehai.web.dao;

import com.xuehai.web.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 39450 on 2018/10/1.
 */
@Mapper
public interface UserDao {
    UserEntity selectUserInfo(@Param(value = "id") Integer id);

    List<UserEntity> getUserInfo(@Param(value = "id") Integer id);
}
