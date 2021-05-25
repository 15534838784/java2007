package com;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 创作时间：2021/4/25 20:09
 * 作者：张林
 */
@SpringBootApplication
public class ServiceHospApplication {
    public static void main(String[] args) {
        /**
         * SpringBoot启动类的注解，包括3个注解，4个是注解的注解
         * main里面启动，工程的入口
         */
        SpringApplication.run(ServiceHospApplication.class, args);
    }
}
