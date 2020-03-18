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
import java.util.UUID;

/**
 * @author li
 * @date 2020/3/14 0014 17:30
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private SsoService ssoService;

    @Autowired
    private RedisTemplate redisTemplate;


    //向购物车中添加商品
    @RequestMapping("/add/{productId}/{count}")
    public ResultBean addProduct(@PathVariable(name = ConstantsUtil.CART_COOKIE_KEY,required = false)String uuid,
                                 @PathVariable("productId") Long productId,
                                 @PathVariable("count") int count,
                                 HttpServletResponse response) {


        //判断用户是否已登录
        ResultBean result = ssoService.checkIsLogin();

        if (result.getError() == 0){
            //已登录
            //获取redis中的用户信息
            String redisKey = StringUtil.format(ConstantsUtil.CART_REDIS_PRE, uuid);
            TUser tUser = (TUser)redisTemplate.opsForValue().get(redisKey);

            //添加商品
            return cartService.addProduct(tUser.getId().toString(),productId,count);
        }

        //未登录
        //判断uuid是否存在 若不存在 向用户浏览器存放一个cookie
        if (uuid == null || "".equals(uuid)){
            uuid = UUID.randomUUID().toString();
            Cookie cookie = new Cookie(ConstantsUtil.CART_COOKIE_KEY, uuid);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        //未登陆添加购物车
        return cartService.addProduct(uuid,productId,count);

    }



    //清空购物车
    @RequestMapping("/clear")
    public ResultBean clearCart(@CookieValue(name = ConstantsUtil.CART_COOKIE_KEY , required = false)String uuid,
                                HttpServletResponse response){



        //判断用户是否已登录
        ResultBean result = ssoService.checkIsLogin();

        if (result.getError() == 0){
            //已登录
            //获取redis中的用户信息
            String redisKey = StringUtil.format(ConstantsUtil.CART_REDIS_PRE, uuid);
            TUser tUser = (TUser)redisTemplate.opsForValue().get(redisKey);

            return cartService.clearCart(tUser.getId().toString());
        }

        //未登录
        //清空用户浏览器的cookie
        if (uuid != null && !"".equals(uuid)){
            Cookie cookie = new Cookie(ConstantsUtil.CART_COOKIE_KEY, "");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            //删除redis中的购物车数据
            return cartService.clearCart(uuid);
        }

        return ResultBean.error("当前用户没有购物车");



    }

    //更新购物车信息
    @RequestMapping("/update/{productId}/{count}")
    public ResultBean updateCart(@CookieValue(name = ConstantsUtil.CART_COOKIE_KEY,required = false)String uuid,
                                 @PathVariable("productId") Long productId,
                                 @PathVariable("count") int count){

        //判断用户是否已登录
        ResultBean result = ssoService.checkIsLogin();

        if (result.getError() == 0){
            //已登录
            //获取redis中的用户信息
            String redisKey = StringUtil.format(ConstantsUtil.CART_REDIS_PRE, uuid);
            TUser tUser = (TUser)redisTemplate.opsForValue().get(redisKey);

            return cartService.updateCart(tUser.getId().toString(),productId,count);
        }

        //未登录
        return cartService.updateCart(uuid,productId,count);

    }

    //展示购物车数据
    @RequestMapping("/show")
    public ResultBean showCart(@CookieValue(name = ConstantsUtil.CART_COOKIE_KEY , required = false)String uuid){


        //判断用户是否已登录
        ResultBean result = ssoService.checkIsLogin();

        if (result.getError() == 0){
            //已登录
            //获取redis中的用户信息
            String redisKey = StringUtil.format(ConstantsUtil.CART_REDIS_PRE, uuid);
            TUser tUser = (TUser)redisTemplate.opsForValue().get(redisKey);

            return cartService.showCart(tUser.getId().toString());
        }

        //未登录
        System.out.println("-------"+uuid);
        return cartService.showCart(uuid);

    }


}
