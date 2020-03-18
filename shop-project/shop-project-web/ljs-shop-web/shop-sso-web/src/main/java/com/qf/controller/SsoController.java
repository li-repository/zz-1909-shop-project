package com.qf.controller;

import com.qf.entity.TUser;
import com.qf.service.CartService;
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


/**
 * @author li
 * @date 2020/3/12 0012 22:10
 */
@RestController
@RequestMapping("/sso")
public class SsoController {

    @Autowired
    private SsoService service;

    @Autowired
    private CartService cartService;

    @Autowired
    private RedisTemplate redisTemplate;


    //去登陆
    @RequestMapping("/gotoLogin")
    public String gotoLogin(){
        return "登陆页面";
    }

    //登陆验证  用户名和密码是否正确
    @RequestMapping("/login/{uname}/{password}")
    public ResultBean checkLogin(@CookieValue(name = ConstantsUtil.CART_COOKIE_KEY,required = false)String cartUuid,
                                 @CookieValue(name = ConstantsUtil.SSO_LOGIN_COOKIE_KEY,required = false)String loginUuid,
                                 @PathVariable("uname") String uname,
                                 @PathVariable("password") String password,
                                 HttpServletResponse response){

        //用户登陆
        service.checkLogin(uname,password);

        //用户在登陆时需要合并已登录和未登录状态下的购物车数据

        //根据loginUuid获取去redis当前用户的信息
        String redisKey = StringUtil.format(ConstantsUtil.SSO_LOGIN_INFO_PRE, loginUuid);
        TUser tUser = (TUser) redisTemplate.opsForValue().get(redisKey);

        //合并购物车数据
        ResultBean mergeResult = cartService.mergeCart(cartUuid, tUser.getId().toString());

        //清楚未登陆下购物车cookie

        if (cartUuid != null && "".equals(cartUuid)){
            Cookie cookie = new Cookie(ConstantsUtil.CART_COOKIE_KEY, "");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return ResultBean.success(mergeResult.getData(),"登陆成功");

    }

    //检查用户是否已经登陆
    @RequestMapping("/isLogin")
    public ResultBean checkIsLogin(){

        return service.checkIsLogin();

    }



}
