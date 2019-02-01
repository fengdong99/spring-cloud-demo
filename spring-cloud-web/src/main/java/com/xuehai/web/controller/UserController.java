package com.xuehai.web.controller;

import com.xuehai.web.entity.UserEntity;
import com.xuehai.web.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 39450 on 2018/10/1.
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value="获取用户详细信息", notes="根据用户id来获取用户信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping({"/getUserInfo/{id}"})
    public UserEntity getUserInfo(@PathVariable Integer id){

       return userService.selectUserInfo(id);

    }
}
