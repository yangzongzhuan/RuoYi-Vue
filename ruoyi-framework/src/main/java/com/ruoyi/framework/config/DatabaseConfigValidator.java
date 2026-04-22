package com.ruoyi.framework.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 数据库与 Redis 配置启动校验器
 * 检查数据库和 Redis 凭证配置是否安全
 *
 * @author ruoyi
 */
@Component
@Lazy(false)
public class DatabaseConfigValidator implements CommandLineRunner
{
    private static final Logger log = LoggerFactory.getLogger(DatabaseConfigValidator.class);

    private static final String DEFAULT_WEAK_PASSWORD = "password";
    private static final List<String> RECOGNIZED_INSECURE_PASSWORDS = Arrays.asList(
            "", "password", "123456", "root", "admin", "12345678", "123456789", "1234567890"
    );

    @Autowired
    private DruidConfig druidConfig;

    @Override
    public void run(String... args)
    {
        validateDruidMasterDataSource();
        validateDruidSlaveDataSource();
    }

    /**
     * 验证主库数据源配置
     */
    private void validateDruidMasterDataSource()
    {
        DruidDataSource masterDataSource = druidConfig.getMasterDataSource();
        if (masterDataSource == null)
        {
            log.warn("主库数据源未配置");
            return;
        }

        String username = masterDataSource.getUsername();
        String password = masterDataSource.getPassword();

        // 检查默认不安全密码
        if (isInsecurePassword(password))
        {
            String msg = String.format(
                    "主库数据源使用了不安全的密码配置。建议通过环境变量 DRUID_MASTER_PASSWORD 设置安全密码，当前用户名: %s",
                    username);
            log.error(msg);
            throw new IllegalStateException(msg);
        }

        log.debug("主库数据源配置校验通过");
    }

    /**
     * 验证从库数据源配置
     */
    private void validateDruidSlaveDataSource()
    {
        DruidDataSource slaveDataSource = druidConfig.getSlaveDataSource();
        if (slaveDataSource == null || !slaveDataSource.isEnable())
        {
            log.debug("从库数据源未启用，跳过校验");
            return;
        }

        String username = slaveDataSource.getUsername();
        String password = slaveDataSource.getPassword();

        // 只有在显式设置了用户名时才检查密码
        if (username != null && !username.trim().isEmpty())
        {
            if (isInsecurePassword(password))
            {
                String msg = String.format(
                        "从库数据源使用了不安全的密码配置。建议通过环境变量 DRUID_SLAVE_PASSWORD 设置安全密码，当前用户名: %s",
                        username);
                log.error(msg);
                throw new IllegalStateException(msg);
            }
        }

        log.debug("从库数据源配置校验通过");
    }

    /**
     * 检查密码是否为不安全的默认值
     */
    private boolean isInsecurePassword(String password)
    {
        if (password == null || password.trim().isEmpty())
        {
            // 空密码视为不安全（除非通过环境变量显式配置为空）
            return true;
        }

        // 转换为小写进行比较
        String lowerPassword = password.toLowerCase();
        for (String insecure : RECOGNIZED_INSECURE_PASSWORDS)
        {
            if (lowerPassword.equals(insecure))
            {
                return true;
            }
        }

        // 检查弱密钥模式
        if (isSequentialPattern(password))
        {
            return true;
        }

        return false;
    }

    /**
     * 检查是否为简单的顺序模式
     */
    private boolean isSequentialPattern(String password)
    {
        // 检查连续数字模式
        if (password.matches("^(0123456789|9876543210|1234567890|0987654321){1,}$"))
        {
            return true;
        }
        // 检查重复字符模式
        if (password.matches("^(.)\\1{7,}$"))
        {
            return true;
        }
        return false;
    }
}
