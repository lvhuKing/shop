package com.common.validation;

import org.springframework.util.StringUtils;
import java.util.regex.Pattern;

/**
 * @Author Mr.findelist
 * @program: seckillplus
 * @Date 2020/7/22  21:36
 * 雁阵手机号
 **/
public class IphoneValidationUtil {
    private static final Pattern PATTERN=Pattern.compile( "^1[345678]\\d{9}$");
    /**
     * 验证手机号
     */
    public static boolean isPhone(String iphone){
        if (StringUtils.isEmpty(iphone)) {
            return false;
        } else {
            return PATTERN.matcher(iphone).matches();
        }
    }
}



