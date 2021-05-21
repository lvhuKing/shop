package com.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author Mr.findelist
 * @program: seckillplus
 * @Date 2020/7/22  21:15
 **/


@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IsPhoneValidation.class})
public @interface IsPhone {
    String message() default "手机号格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}



