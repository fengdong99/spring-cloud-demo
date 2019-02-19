package com.xuehai.web.annotation;

import com.xuehai.web.entity.SysLoggerEntity;
import com.xuehai.web.service.SysLoggerService;
import com.xuehai.web.tool.CommonUtil;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


public class HandleLogger {
    private static final Logger logger = Logger.getLogger(HandleLogger.class);

    @Autowired
    private static SysLoggerService sysLoggerService;


    private static long startTime = 0;

    /**
     * 日志处理
     */
    public static void processor(JoinPoint joinPoint, Exception e) {

        long endTime = System.currentTimeMillis() - startTime;
        startTime = 0;
        logger.info("exec time:"+ endTime);

        if(e != null){
            logger.info("errorMsg:" + e.getMessage());
        }

        try {
            //获得注解
            SysLogger sysLogger = checkAnnotation(joinPoint);
            if (sysLogger == null) {
                return;
            } else {
                logger.info(sysLogger.name());
                logger.info(sysLogger.startTopic());
                logger.info(sysLogger.topicId());
            }


            String methodName = joinPoint.getSignature().getName();

            String classType = joinPoint.getTarget().getClass().getName();
            Class<?> clazz = Class.forName(classType);
            Method[] methods = clazz.getDeclaredMethods();

            for (Method method : methods) {
                if (method.isAnnotationPresent(SysLogger.class) && method.getName().equals(methodName) ) {
                    String clazzName = clazz.getName();
                    logger.info("clazzName: " + clazzName + ", methodName: " + method.getName());
                }
            }


            sysLoggerService.saveSysLooger(new SysLoggerEntity(CommonUtil.getUuid(),sysLogger.name(),String.valueOf(endTime),CommonUtil.getSystemTime()));
        } catch (Exception exp) {
            logger.error("异常信息:"+exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 检查注解方法
     */
    private static SysLogger checkAnnotation(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(SysLogger.class);
        }
        return null;
    }


}
