package com.chinocarbon.judgement.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Hedon Wang
 * @create 2020-07-18 13:49
 */
@Configuration
public class MybatisPlusConfig
{
    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer()
    {
        return configuration -> configuration.addInterceptor(new com.github.pagehelper.PageInterceptor());
    }
}
