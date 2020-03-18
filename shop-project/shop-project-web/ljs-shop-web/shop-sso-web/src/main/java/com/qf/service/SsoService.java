package com.qf.service;

import com.qf.utils.ConstantsUtil;
import com.qf.utils.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author li
 * @date 2020/3/12 0012 22:10
 */
@FeignClient(value = "SHOP-SSO-SERVICE")
public interface SsoService {

    //登陆验证  用户名和密码是否正确  String userInfoUuid
    @RequestMapping("/sso/login/{uname}/{password}")
    public ResultBean checkLogin(@PathVariable("uname") String uname, @PathVariable("password") String password);

    //检查用户是否已经登陆
    @RequestMapping("/sso/isLogin")
    public ResultBean checkIsLogin();



}
