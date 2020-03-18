package com.qf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author li
 * @date 2020/3/14 0014 17:21
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class CartWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartWebApplication.class,args);
    }

}
