package com.qf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * @author li
 * @date 2020/3/11 0011 20:22
 */
@SpringBootTest
public class MyTest {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void sendEmail(){

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("测试邮件主题");
        mailMessage.setText("测试邮件内容");
        mailMessage.setFrom("2939741434@qq.com");
        mailMessage.setTo("ljs18215100977@163.com");
        sender.send(mailMessage);

    }

    @Test
    void redisTest(){

        Object li = redisTemplate.opsForValue().get("li");

        System.out.println(li);

    }


}
