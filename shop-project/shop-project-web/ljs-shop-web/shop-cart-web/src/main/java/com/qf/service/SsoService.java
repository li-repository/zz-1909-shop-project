package com.qf.service;

import com.qf.utils.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author li
 * @date 2020/3/14 0014 17:28
 */
@FeignClient(value = "SHOP-SSO-SERVICE")
public interface SsoService {

    //检查用户是否已经登陆
    @RequestMapping("/sso/isLogin")
    public ResultBean checkIsLogin();


}
