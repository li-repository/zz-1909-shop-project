package com.qf.controller;

import com.qf.entity.TUser;
import com.qf.mapper.SsoMapper;
import com.qf.service.SsoService;
import com.qf.utils.ConstantsUtil;
import com.qf.utils.ResultBean;
import com.qf.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author li
 * @date 2020/3/12 0012 20:52
 */

@RestController
@RequestMapping("/sso")
public class SsoController {

    @Autowired
    private SsoService service;

    @Autowired
    private SsoMapper mapper;

    @Autowired
    private RedisTemplate redisTemplate;

    //登陆验证  用户名和密码是否正确
    @RequestMapping("/login/{uname}/{password}")
    public ResultBean checkLogin(@PathVariable("uname") String uname, @PathVariable("password") String password,
                                 HttpServletResponse response){

        ResultBean result = service.checkLogin(uname, password);

        //判断用户名和密码是否正确
        if (result.getError() == 0){

            //在登陆时生成cookie 发送到用户浏览器
            String uuid = UUID.randomUUID().toString();
            Cookie cookie = new Cookie(ConstantsUtil.SSO_LOGIN_COOKIE_KEY, uuid);
            cookie.setMaxAge(30 * 24 * 60 *60);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);

            //把用户信息存入redis中
            String redisKey = StringUtil.format(ConstantsUtil.SSO_LOGIN_INFO_PRE, uuid);
            //查询用户信息
            TUser tUser = mapper.findTUserByUname(uname);
            redisTemplate.opsForValue().set(redisKey,tUser,30, TimeUnit.MINUTES);

            return ResultBean.success("登陆成功");
        }

        return ResultBean.error("登录失败");

    }

    //检查用户是否已经登陆
    @RequestMapping("/isLogin")
    public ResultBean checkIsLogin(@CookieValue(name = ConstantsUtil.SSO_LOGIN_COOKIE_KEY,required = false) String uuid) {

        return service.checkIsLogin(uuid);

    }
}
