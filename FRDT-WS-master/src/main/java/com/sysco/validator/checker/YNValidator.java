package com.sysco.validator.checker;

import com.sysco.validator.YNPattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by james.zhu on 2018/9/10.
 */
public class YNValidator implements ConstraintValidator<YNPattern, String> {
    private String regexp;

    @Override
    public void initialize(YNPattern constraintAnnotation) {
        this.regexp = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if ("".equals(value) || value == null) {
            return true;
        } else if (value.matches(regexp)){
            return true;
        }
        return false;
    }
}
