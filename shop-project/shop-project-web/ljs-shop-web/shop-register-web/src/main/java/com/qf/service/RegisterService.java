package com.qf.service;

import com.qf.entity.TUser;
import com.qf.utils.ConstantsUtil;
import com.qf.utils.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author li
 * @date 2020/3/11 0011 14:07
 */
@FeignClient(value = ConstantsUtil.PHONE_REGISTER_URI)
public interface RegisterService {

    //发送验证码
    @RequestMapping("/register/phone/send/{phone}")
    public ResultBean sendVeryCode(@PathVariable("phone") String phone);

    //验证码核对
    @RequestMapping("/register/check/veryCode/{checkVeryCode}/{phone}")
    public ResultBean phoneCheckCode(@PathVariable("checkVeryCode") String checkVeryCode,
                                     @PathVariable("phone") String phone);


    //手机号注册
    @RequestMapping("/register/phone/{phone}/{password}")
    public ResultBean phoneRegister(@PathVariable("phone") String phone,
                                    @PathVariable("password") String password);

    //发送邮件
    @RequestMapping("/register/email/send/{email}/{uuid}")
    public ResultBean sendEmail(@PathVariable("email") String email,@PathVariable("uuid") String uuid);


    //邮箱注册-激活账号
    @RequestMapping("/register/email/active/{uuid}")
    public ResultBean activeAccount(@PathVariable("uuid") String uuid);
}
