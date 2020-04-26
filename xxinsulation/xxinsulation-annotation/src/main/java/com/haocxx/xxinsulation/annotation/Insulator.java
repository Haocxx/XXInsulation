package com.haocxx.xxinsulation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Haocxx
 * on 2020-04-26
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Insulator {
}
