package com.ruoyi.framework.config;

import com.ruoyi.framework.config.properties.TokenProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Token 配置启动校验器
 * 检查 JWT 密钥配置是否安全
 *
 * @author ruoyi
 */
@Component
public class TokenConfigValidator implements CommandLineRunner
{
    private static final Logger log = LoggerFactory.getLogger(TokenConfigValidator.class);

    private static final String DEFAULT_WEAK_SECRET = "abcdefghijklmnopqrstuvwxyz";

    @Autowired
    private TokenProperties tokenProperties;

    @Override
    public void run(String... args)
    {
        validateTokenSecret();
    }

    /**
     * 验证 Token 密钥配置
     */
    private void validateTokenSecret()
    {
        String secret = tokenProperties.getSecret();

        // 检查密钥是否为空
        if (secret == null || secret.trim().isEmpty())
        {
            String msg = "JWT 密钥未配置。请在环境变量中设置 JWT_SECRET，或在 application.yml 的 token.secret 中配置安全的密钥。";
            log.error(msg);
            throw new IllegalStateException(msg);
        }

        // 检查密钥长度（建议至少 32 位）
        if (secret.length() < 32)
        {
            String msg = "JWT 密钥长度过短（{}），至少需要 32 个字符。请使用更复杂的密钥以确保安全。" + secret.length();
            log.error(msg);
            throw new IllegalStateException(msg);
        }

        // 检查是否使用了默认弱密钥
        if (DEFAULT_WEAK_SECRET.equals(secret))
        {
            String msg = "JWT 密钥使用了默认弱密钥，存在安全风险。请修改 token.secret 配置或设置环境变量 JWT_SECRET 为复杂密钥。";
            log.error(msg);
            throw new IllegalStateException(msg);
        }

        log.debug("JWT 密钥配置校验通过");
    }
}
