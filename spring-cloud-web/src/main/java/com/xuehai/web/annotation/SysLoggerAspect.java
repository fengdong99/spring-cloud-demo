package com.xuehai.web.annotation;

import com.xuehai.integration.ThreadPoolManager;
import com.xuehai.web.service.SysLoggerService;
import com.xuehai.web.service.UserService;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * AOP
 * Created by 39450 on 2019/2/17.
 */
@Component
@Aspect
public class SysLoggerAspect implements Ordered {

    private static final Logger logger = Logger.getLogger(SysLoggerAspect.class);

    @Autowired
    private SysLoggerService sysLoggerService;

    private static long startTime = 0;

    /**
     * 定义Pointcut，Pointcut的名称，此方法不能有返回值，该方法只是一个标示
     */
    @Pointcut(value = "@annotation(com.xuehai.web.annotation.SysLogger)")
    public void actionAspect() {

    }

    /**
     * 环绕通知（Around advice） ：包围一个连接点的通知，类似Web中Servlet规范中的Filter的doFilter方法。可以在方法的调用前后完成自定义的行为，也可以选择不执行。
     */
    @Around("actionAspect()")
    public Object  doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        startTime = System.currentTimeMillis();
        System.out.println("=====SysLoggerAspect 环绕通知开始=====");
        //执行目标方法
        Object obj = joinPoint.proceed();
        System.out.println("=====SysLoggerAspect 环绕通知结束=====");
        return obj;
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
        ThreadPoolManager.getInstance().addTask(new HandleLogger(joinPoint,startTime,sysLoggerService,null));
//        new HandleLogger(joinPoint,null,sysLoggerService).run();
    }

    /**
     * 抛出异常后通知（After throwing advice） ： 在方法抛出异常退出时执行的通知。
     */
    @AfterThrowing(value = "actionAspect()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Exception e) {
        System.out.println("=====SysLoggerAspect 异常通知开始=====");
        ThreadPoolManager.getInstance().addTask(new HandleLogger(joinPoint,startTime,sysLoggerService,e));
    }


    /**
     * spring的事务aop执行的先后顺序,值越小，越先被执行
     */
    @Override
    public int getOrder() {
        return 1;
    }
}
