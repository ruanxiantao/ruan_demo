package com.beijing.ruan.config;

import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@MapperScan(basePackages = "com.beijing.ruan.mapper")
public class springConfig {
}
