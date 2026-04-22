package com.ruoyi.framework.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Token 配置属性
 *
 * @author ruoyi
 */
@Component
@ConfigurationProperties(prefix = "token")
public class TokenProperties
{
    /**
     * 令牌自定义标识
     */
    private String header;

    /**
     * 令牌密钥
     */
    private String secret;

    /**
     * 令牌有效期（分钟）
     */
    private Integer expireTime;

    public String getHeader()
    {
        return header;
    }

    public void setHeader(String header)
    {
        this.header = header;
    }

    public String getSecret()
    {
        return secret;
    }

    public void setSecret(String secret)
    {
        this.secret = secret;
    }

    public Integer getExpireTime()
    {
        return expireTime;
    }

    public void setExpireTime(Integer expireTime)
    {
        this.expireTime = expireTime;
    }
}
