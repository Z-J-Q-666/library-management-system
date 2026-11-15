package com.lms;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 图书馆管理系统启动类。
 *
 * <p>注意 {@code annotationClass = Mapper.class}：只把标了 {@code @Mapper} 的接口注册成 Mapper，
 * 否则会把 {@code @RequireRole} 这类注解接口也扫进去。</p>
 */
@SpringBootApplication
@EnableCaching
@MapperScan(basePackages = "com.lms", annotationClass = Mapper.class)
public class LmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LmsApplication.class, args);
    }
}
