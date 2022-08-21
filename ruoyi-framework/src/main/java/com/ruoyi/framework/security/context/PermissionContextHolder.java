package com.ruoyi.framework.security.context;

/**
 * 权限信息
 * 
 * @author ruoyi
 */
public class PermissionContextHolder
{
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setContext(String permission)
    {
        contextHolder.set(permission);
    }

    public static String getContext()
    {
        return contextHolder.get();
    }
}
