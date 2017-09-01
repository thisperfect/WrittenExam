package com.dayee.writtenExam.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by yq.song on 2016/11/15.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Document
@Inherited
public @interface ScheduleQuartz {
	
	/**corn表达式**/
	String value() default "";
	
	/**任务描述**/
	String description() default "";
}
