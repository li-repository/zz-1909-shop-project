package com.qf.controller;

import com.qf.entity.TUser;
import com.qf.service.RegisterService;
import com.qf.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author li
 * @date 2020/3/10 0010 13:53
 */

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterService service;

    //发送验证码
    @RequestMapping("/phone/send/{phone}")
    public ResultBean sendVeryCode(@PathVariable("phone") String phone) {

        return service.sendVeryCode(phone);

    }

    //验证码核对
    @RequestMapping("/check/veryCode/{checkVeryCode}/{phone}")
    public ResultBean phoneCheckCode(@PathVariable("checkVeryCode") String checkVeryCode,
                                     @PathVariable("phone") String phone) {

        return service.phoneCheckCode(checkVeryCode,phone);

    }


    //手机号注册
    @RequestMapping("/phone/{phone}/{password}")
    public ResultBean phoneRegister(@PathVariable("phone") String phone,
                                    @PathVariable("password") String password){

        return service.phoneRegister(phone,password);

    }


    //发送邮件
    @RequestMapping("/email/send/{email}/{uuid}")
    public ResultBean sendEmail(@PathVariable("email") String email,@PathVariable("uuid") String uuid){

        return service.sendEmail(email,uuid);

    }

    //邮箱注册-激活账号
    @RequestMapping("email/active/{uuid}")
    public ResultBean activeAccount(@PathVariable("uuid") String uuid){

        return service.activeAccount(uuid);

    }







}


