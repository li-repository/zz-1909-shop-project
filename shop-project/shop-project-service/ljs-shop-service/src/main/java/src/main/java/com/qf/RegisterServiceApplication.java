package src.main.java.com.qf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author li
 * @date 2020/3/10 0010 13:46
 */

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.qf.mapper")
public class RegisterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegisterServiceApplication.class,args);
    }

}
