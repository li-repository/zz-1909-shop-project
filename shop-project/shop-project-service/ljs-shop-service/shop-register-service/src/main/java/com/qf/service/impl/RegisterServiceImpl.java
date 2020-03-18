package com.qf.service.impl;

import com.qf.entity.TUser;
import com.qf.mapper.RegisterMapper;
import com.qf.service.RegisterService;
import com.qf.utils.ConstantsUtil;
import com.qf.utils.ResultBean;
import com.qf.utils.SmsUtil;
import com.qf.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author li
 * @date 2020/3/10 0010 15:49
 */

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RegisterMapper mapper;

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private TemplateEngine templateEngine;

    //邮箱注册 参数
    @Value("${activeServerUrl}")
    private String activeServerUrl;

    @Value("${spring.mail.username}")
    private String fromEmail;

    //注入存储在yml配置文件中,发送信息需要的参数
    @Value("${sms.account}")
    private String account;

    @Value("${sms.password}")
    private String password;

    @Value("${sms.veryCode}")
    private String veryCode;

    @Value("${sms.http_url}")
    private String http_url;

    @Value("${sms.tplId}")
    private String tplId;


    //发送验证码
    public ResultBean sendVeryCode(String phone) {

        try {
            //生成验证码 并生成content
            String code = String.valueOf((Math.random() * 9 + 1) * 1000);
            String content ="@1@=" + code;

            //为 SmsUtil 中的静态变量赋值
            SmsUtil.account = account;
            SmsUtil.password = password;
            SmsUtil.veryCode = veryCode;
            SmsUtil.http_url  =http_url;

            //发送信息
            //SmsUtil.sendTplSms(phone, tplId, content);

            //把用户输入的手机号存入Redis 过期时间:一分钟
            String redisKey = StringUtil.format(ConstantsUtil.PHONE_REGISTER,phone);
            redisTemplate.opsForValue().set(redisKey,code,1, TimeUnit.MINUTES);

            //没有出现异常返回成功信息
            return ResultBean.success();
        } catch (Exception e) {
            e.printStackTrace();
            //出现异常 返回错误信息
            return ResultBean.error("短信发送失败");
        }
    }




    //验证码核对
    public ResultBean phoneCheckCode(String checkVeryCode,String phone) {

        //获取存入redis中的验证码
        String redisKey = StringUtil.format(ConstantsUtil.PHONE_REGISTER,phone);
        String veryCode = redisTemplate.opsForValue().get(redisKey).toString();

        //判断用户输入的和redis中的验证码是否一致

        if (veryCode.equals(checkVeryCode)) {
            //一致 返回成功信息
            return ResultBean.success("验证码一致");
        }else {
            //验证码不一致 注册失败 返回错误信息
            return ResultBean.error("验证码不一致");
        }
    }

    //手机号注册
    public ResultBean phoneRegister(String phone,String password) {
        //判断手机号是否可用
        Integer result = mapper.checkPhone(phone);
        if (result == 1){
            return ResultBean.error("该手机号已注册");
        }

        //把用户信息存入mysql中并设置flag值为1
        //封装用户信息
        TUser tUser = new TUser();
        tUser.setUname(phone);
        tUser.setPhone(phone);
        tUser.setPassword(password);
        tUser.setFlag(1);
        tUser.setCreateTime(new Date());

        //注册
        try {
            mapper.phoneRegister(tUser);
            //注册成功 返回成功信息
            return ResultBean.success("注册成功");
        } catch (Exception e) {
            e.printStackTrace();
            //失败 返回失败信息
            return ResultBean.error("请核对用户信息");
        }
    }


    //发送邮件
    public ResultBean sendEmail(String email,String uuid) {

        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper mailMessage = null;
        try {
            mailMessage = new MimeMessageHelper(message,true);
            mailMessage.setSubject("请激活您的账号");

            //读取模板内容
            Context context = new Context();
            //模板中的uname url
            context.setVariable("uname",email.substring(0,email.lastIndexOf('@')));
            context.setVariable("url",StringUtil.format(activeServerUrl,uuid));
            String info = templateEngine.process("emailTemplate", context);

            mailMessage.setText(info,true);

            mailMessage.setFrom(fromEmail);
            mailMessage.setTo(email);

            //发送邮件
            sender.send(message);

            //成功 返回成功信息
            return ResultBean.success("邮件发送成功");
        } catch (MessagingException e) {
            e.printStackTrace();
            //失败
            return ResultBean.error("请输入正确的邮箱地址");
        }

    }


    //邮箱注册-激活账号
    public ResultBean activeAccount(String uuid) {
        //获得存入Redis中的用户邮箱

        String redisKey = StringUtil.format(ConstantsUtil.EMAIL_REGISTER,uuid);
        String email = redisTemplate.opsForValue().get(redisKey).toString();
        //判断邮箱注册在 redis 中的key是否过期
        if (email != null) {
            //没过期 获得存入Redis中的用户信息
            String userInfo = StringUtil.format(ConstantsUtil.EMAIL_REGISTER,email);
            TUser tUser = (TUser) redisTemplate.opsForValue().get(userInfo);

            //判断该邮箱是否已经注册 已注册不可用
            Integer result = mapper.checkEmail(email);
            if (result == 0) {
                //可用 注册
                tUser.setFlag(1);
                mapper.emailRegister(tUser);
                return ResultBean.success("账号激活成功");
            }
            return ResultBean.error("该邮箱已注册");
        }

        return ResultBean.error("链接已过期,请重新注册");

    }


}
