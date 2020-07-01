package com.jimie.handler.sql.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author litingjie
 * 2020-7-1
 */
@Configuration
public class SqlConfig {

    @Bean
    public Map<String,String> nameSqlMap(){
        Map<String,String> config = new HashMap<>(6);

        config.put("字符","varchar");
        config.put("浮点","float");
        config.put("整形","int");
        config.put("浮点（保留2位小数","float");
        config.put("字典","int");
        config.put("日期","float");

        return config;
    }

    @Bean
    public Map<String,String> nameBeanMap(){
        Map<String,String> config = new HashMap<>(6);

        config.put("字符","String");
        config.put("浮点","Float");
        config.put("整形","int");
        config.put("浮点（保留2位小数","Float");
        config.put("字典","Integer");
        config.put("日期","Long");

        return config;
    }
}
