package com.common.validation;

import org.springframework.util.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author Mr.findelist
 * @program: seckillplus
 * 泛型第一个自定义注解
 * 泛型第er个 校验参数的类型这里是手机号所以是  string
 * @Date 2020/7/22  21:23
 **/

public class IsPhoneValidation implements ConstraintValidator<IsPhone, String> {
    @Override
    public void initialize(IsPhone constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(value)) {
            return false;
        } else {
            return IphoneValidationUtil.isPhone(value);
        }
    }
}




