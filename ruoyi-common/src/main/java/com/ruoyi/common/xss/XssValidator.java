package com.ruoyi.common.xss;

import com.ruoyi.common.utils.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * 自定义xss校验注解实现
 * 
 * @author ruoyi
 */
public class XssValidator implements ConstraintValidator<Xss, String>
{
    /** 匹配常见 HTML 标签 */
    private static final Pattern HTML_TAG_PATTERN = Pattern.compile(
        "<(\\w+)[^>]*>.*?</\\1>|<(\\w+)[^>]*/?>",
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL
    );

    /** 匹配事件属性（onxxx=）和 javascript: 伪协议 */
    private static final Pattern DANGEROUS_ATTR_PATTERN = Pattern.compile(
        "\\bon\\w+\\s*=|javascript\\s*:|vbscript\\s*:|data\\s*:",
        Pattern.CASE_INSENSITIVE
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext)
    {
        if (StringUtils.isBlank(value))
        {
            return true;
        }
        return !containsHtml(value);
    }

    public static boolean containsHtml(String value)
    {
        return HTML_TAG_PATTERN.matcher(value).find()
                || DANGEROUS_ATTR_PATTERN.matcher(value).find();
    }
}