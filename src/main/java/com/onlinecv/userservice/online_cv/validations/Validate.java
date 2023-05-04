package com.onlinecv.userservice.online_cv.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Validate {
    String NULL = "null";

    Validation value();

    String field() default NULL;

    int argumentPos() default 0;

    Class<?> entity() default Object.class;
}
