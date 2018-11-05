package com.sysco.validator;

import com.sysco.validator.checker.YNValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by james.zhu on 2018/9/10.
 */
@Documented
@Target(value= {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=YNValidator.class)
public @interface YNPattern {

    String message() default "Does not meet the regex";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String regexp() default "[YN]";

    String value() default "mercy";
}
