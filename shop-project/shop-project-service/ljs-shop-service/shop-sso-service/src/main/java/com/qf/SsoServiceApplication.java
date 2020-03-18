package com.qf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author li
 * @date 2020/3/12 0012 16:37
 */

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.qf.mapper")
public class SsoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoServiceApplication.class,args);
    }

}
