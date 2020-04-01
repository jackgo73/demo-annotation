package com.mjoker73.demo.annotation;

import com.mjoker73.demo.validators.IParameterValidator;
import com.mjoker73.demo.validators.NoValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({ FIELD, METHOD })
public @interface ParameterB {
    int cnt() default 0;

    String description() default "";

    Class<? extends IParameterValidator>[] validateWith() default NoValidator.class;
}
