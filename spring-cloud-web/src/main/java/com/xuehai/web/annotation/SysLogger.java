package com.xuehai.web.annotation;

import java.lang.annotation.*;

/**
 * Created by 39450 on 2019/2/17.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface SysLogger {

    /**
     * 日志名称
     */
    String name() default "";

    /**
     * 主题ID
     */
    String topicId() default "";

    /**
     * 是否启动Topic，默认不启动
     */
    boolean startTopic() default false;

}
