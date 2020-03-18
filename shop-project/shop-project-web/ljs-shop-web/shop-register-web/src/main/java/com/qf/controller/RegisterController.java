package com.qf.controller;

import com.qf.entity.TUser;
import com.qf.service.RegisterService;
import com.qf.utils.ConstantsUtil;
import com.qf.utils.ResultBean;
import com.qf.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author li
 * @date 2020/3/10 0010 21:29
 */

@RequestMapping("/register")
@RestController
public class RegisterController {

    @Autowired
    private RegisterService service;

    @Autowired
    private RedisTemplate redisTemplate;

    //根据用户输入的手机号发送验证码
    @RequestMapping("/phone/send/{phone}")
    public ResultBean sendVeryCode(@PathVariable("phone") String phone){

        return service.sendVeryCode(phone);

    }


    //手机号注册
    @RequestMapping("/phone/{phone}/{password}/{checkVeryCode}")
    public ResultBean phoneCheckCodeAndRegister(@PathVariable("phone") String phone,
                                                @PathVariable("password") String password,
                                                @PathVariable("checkVeryCode") String checkVeryCode){

        //验证码核对
        ResultBean result = service.phoneCheckCode(checkVeryCode, phone);
        //判断验证码是否一致
        if (result.getError() == 0){
            //一致 注册
            return service.phoneRegister(phone,password);
        } else {
            return ResultBean.error("验证码不一致");
        }

    }

    //发送邮件
    @RequestMapping("/email/send/{email}/{password}")
    public ResultBean sendEmail(@PathVariable("email") String email, @PathVariable("password") String password){

        //生成一个UUID 作为 Redis中的key
        String uuid = UUID.randomUUID().toString();
        //把用户邮箱存入Redis中
        String redisKey = StringUtil.format(ConstantsUtil.EMAIL_REGISTER, uuid);
        redisTemplate.opsForValue().set(redisKey,email,15, TimeUnit.MINUTES);

        //把用户信息存入redis中
        TUser tUser = new TUser();
        tUser.setUname(email.substring(0,email.lastIndexOf('@')));
        tUser.setEmail(email);
        tUser.setPassword(password);
        tUser.setFlag(0);
        tUser.setCreateTime(new Date());
        String userInfo = StringUtil.format(ConstantsUtil.EMAIL_REGISTER,email);
        redisTemplate.opsForValue().set(userInfo,tUser,15,TimeUnit.MINUTES);

        System.out.println(uuid);
        //发送邮件
        return service.sendEmail(email, uuid);

    }

    //邮箱注册-激活账号
    @RequestMapping("email/active/{uuid}")
    public ResultBean activeAccount(@PathVariable("uuid") String uuid){

        return service.activeAccount(uuid);

    }


}
