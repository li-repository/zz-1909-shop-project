package com.qf.service;

import com.qf.utils.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author li
 * @date 2020/3/14 0014 21:57
 */
@FeignClient(value = "shop-cart-service")
public interface CartService {

    //登录时 购物车的合并问题
    @RequestMapping("/cart/merge")
    public ResultBean mergeCart(@RequestParam("uuid") String uuid,
                                @RequestParam("uid") String uid);

}
