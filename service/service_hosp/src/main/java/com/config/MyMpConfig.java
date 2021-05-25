package com.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 创作时间：2021/4/26 9:50
 * 作者：
 */
@Configuration
@MapperScan("com.mapper")
public class MyMpConfig {
}
