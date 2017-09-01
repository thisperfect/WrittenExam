
package com.dayee.writtenExam.mongo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: dayee_yangkai
 * @Description: mongodb中的自增长Id的注解
 * @Date: Created in 18:01 2017/7/12
 * @Modified By:
 * @Version 1.0.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface GeneratedValue {

    String myName() default "GeneratedValue";
}
