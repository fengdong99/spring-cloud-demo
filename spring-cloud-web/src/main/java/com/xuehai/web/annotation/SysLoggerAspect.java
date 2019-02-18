package com.xuehai.web.annotation;

import com.xuehai.web.dao.SysLoggerDao;
import com.xuehai.web.entity.SysLoggerEntity;
import com.xuehai.web.service.SysLoggerService;
import com.xuehai.web.tool.CommonUtil;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by 39450 on 2019/2/17.
 */
@Component
@Aspect
public class SysLoggerAspect implements Ordered {

    private static final Logger logger = Logger.getLogger(SysLoggerAspect.class);

    @Autowired
    private SysLoggerService sysLoggerService;

    private long startTime = 0;

    /**
     * 环绕通知（Around advice） ：包围一个连接点的通知，类似Web中Servlet规范中的Filter的doFilter方法。可以在方法的调用前后完成自定义的行为，也可以选择不执行。
     */
    @Around("actionAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("=====SysLoggerAspect 环绕通知开始=====");
        startTime = System.currentTimeMillis();
//        handleLogger(joinPoint, null);
        Object obj = joinPoint.proceed();
        System.out.println("=====SysLoggerAspect 环绕通知结束=====");
        return obj;
    }

    /**
     * 定义Pointcut，Pointcut的名称，此方法不能有返回值，该方法只是一个标示
     */
    @Pointcut(value = "@annotation(com.xuehai.web.annotation.SysLogger)")
    public void actionAspect() {

    }

    /**
     * 前置通知（Before advice） ：在某连接点（JoinPoint）之前执行的通知，但这个通知不能阻止连接点前的执行。
     */
    @Before("actionAspect()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("=====SysLoggerAspect 前置通知开始=====");
    }

    /**
     * 后通知（After advice） ：当某连接点退出的时候执行的通知（不论是正常返回还是异常退出）。
     */
    @AfterReturning(pointcut = "actionAspect()")
    public void doAfter(JoinPoint joinPoint) {
        System.out.println("=====SysLoggerAspect 后置通知开始=====");
        handleLogger(joinPoint, null);
    }

    /**
     * 抛出异常后通知（After throwing advice） ： 在方法抛出异常退出时执行的通知。
     */
    @AfterThrowing(value = "actionAspect()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Exception e) {
        System.out.println("=====SysLoggerAspect 异常通知开始=====");
        handleLogger(joinPoint, e);
    }


    /**
     * 日志处理
     */
    private void handleLogger(JoinPoint joinPoint, Exception e) {

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
        } catch (Exception exception) {
            logger.error("异常信息:"+exception.getMessage());
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


    /**
     * spring的事务aop执行的先后顺序,值越小，越先被执行
     */
    @Override
    public int getOrder() {
        return 1;
    }
}
