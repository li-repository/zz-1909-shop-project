package com.qf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author li
 * @date 2020/3/11 0011 14:03
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.qf.service")
public class RegisterWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegisterWebApplication.class,args);
    }


}
