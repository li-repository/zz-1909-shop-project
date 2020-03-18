package com.qf.utils;

import java.io.Serializable;

/**
 * 本类保存一些字符串常量
 * @author li
 * @date 2020/3/10 0010 15:40
 */
public class ConstantsUtil implements Serializable {

    //短信验证   redis keyword
    //队列
    public static final String PHONE_REGISTER_QUEUE = "phone_register_queue";
    //交换机
    public static final String PHONE_REGISTER_TOPIC_EXCHANGE = "phone_register_topic_exchange";
    //手机注册  redis key前缀
    public static final String PHONE_REGISTER = "phone:register:";
    //phoneRegister接口访问的服务名称
    public static final String PHONE_REGISTER_URI = "shop-register-service";

    //邮箱注册  Redis key前缀
    public static final String EMAIL_REGISTER = "email:register:";


    //sso 登陆
    //cookie key
    public static final String SSO_LOGIN_COOKIE_KEY = "sso_login_cookie";
    //用户登录时需要保存在redis中的用户信息的 key
    public static final String SSO_LOGIN_INFO_PRE = "login:userInfo:";



    //cart 购物车
    //cookie key
    public static final String  CART_COOKIE_KEY = "user_cart";
    //保存在redis中的购物车数据 key
    public static final String CART_REDIS_PRE = "user:cart:";
    //在redis中缓存 详细的商品信息 的key
    public static final String PRODUCT_REDIS_PRE = "product:";









}
