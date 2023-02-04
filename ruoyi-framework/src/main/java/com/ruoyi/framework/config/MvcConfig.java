package com.ruoyi.framework.config;

import com.ruoyi.common.config.RuoYiConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * mvc配置
 *
 * @author nikyotensai
 * @since 2023-02-04
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(RuoYiConfig.getPathPrefix(), c -> c.isAnnotationPresent(RestController.class));
    }

}