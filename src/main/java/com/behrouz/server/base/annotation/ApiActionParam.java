package com.behrouz.server.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * created by: Hapi
 **/


@Target( ElementType.PARAMETER )
@Retention( RetentionPolicy.RUNTIME)

public @interface ApiActionParam {

    boolean nullable() default true;



}
