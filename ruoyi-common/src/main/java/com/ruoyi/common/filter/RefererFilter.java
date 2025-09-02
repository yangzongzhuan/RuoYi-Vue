package com.ruoyi.common.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 防盗链过滤器
 * 
 * @author ruoyi
 */
public class RefererFilter implements Filter
{
    /**
     * 允许的域名列表
     */
    public List<String> allowedDomains;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        String domains = filterConfig.getInitParameter("allowedDomains");
        this.allowedDomains = Arrays.asList(domains.split(","));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String referer = req.getHeader("Referer");

        // 如果Referer为空，拒绝访问
        if (referer == null || referer.isEmpty())
        {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied: Referer header is required");
            return;
        }

        // 检查Referer是否在允许的域名列表中
        boolean allowed = false;
        for (String domain : allowedDomains)
        {
            if (referer.contains(domain))
            {
                allowed = true;
                break;
            }
        }

        // 根据检查结果决定是否放行
        if (allowed)
        {
            chain.doFilter(request, response);
        }
        else
        {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied: Referer '" + referer + "' is not allowed");
        }
    }

    @Override
    public void destroy()
    {

    }
}