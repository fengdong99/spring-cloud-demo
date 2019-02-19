package com.xuehai.web.controller;

import com.xuehai.web.annotation.SysLogger;
import com.xuehai.web.annotation.SysTime;
import com.xuehai.web.entity.SysLoggerEntity;
import com.xuehai.web.entity.UserEntity;
import com.xuehai.web.service.SysLoggerService;
import com.xuehai.web.service.UserService;
import com.xuehai.web.tool.CommonUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by 39450 on 2018/10/1.
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {
    private static final  Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SysLoggerService sysLoggerService;

    @ApiOperation(value="获取用户详细信息", notes="根据用户id来获取用户信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping({"/getUserInfo/{id}"})
    public UserEntity getUserInfo(@PathVariable Integer id){

       return userService.selectUserInfo(id);

    }

    @SysLogger(name = "controller 测试",startTopic = true,topicId = "myTopic123")
    @RequestMapping({"/user/{id}"})
    public List<UserEntity> userInfo(@PathVariable Integer id, HttpServletRequest request){
        logger.info(request);
        return userService.getUserInfo(id);

    }

    @SysLogger(name = "Exception 测试",startTopic = true,topicId = "myTopic123")
    @RequestMapping({"/exceptionTest"})
    public String exceptionTest() throws Exception{

        try {
            int i = 1 / 0 ;
        } catch (Exception e){
            throw new Exception(" 分母不能为0");
        }

        return "";
    }

    @RequestMapping({"/intLog"})
    public void insertLoggerTest(){
        sysLoggerService.saveSysLooger(new SysLoggerEntity(CommonUtil.getUuid(),"logger 测试","100",CommonUtil.getSystemTime()));

    }


}
