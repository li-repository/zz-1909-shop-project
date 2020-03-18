package com.qf.service;

import com.qf.utils.ConstantsUtil;
import com.qf.utils.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author li
 * @date 2020/3/14 0014 17:22
 */

@FeignClient(value = "shop-cart-service")
public interface CartService {

    //向购物车中添加商品
    @RequestMapping("/cart/add/{productId}/{count}")
    public ResultBean addProduct(@RequestParam("uuid") String uuid,
                                 @PathVariable("productId") Long productId,
                                 @PathVariable("count") int count);

    //清空购物车
    @RequestMapping("/cart/clear")
    public ResultBean clearCart(@RequestParam("uuid")String uuid);

    //更新购物车信息
    @RequestMapping("/cart/update/{productId}/{count}")
    public ResultBean updateCart(@RequestParam("uuid")String uuid,
                                 @PathVariable("productId") Long productId,
                                 @PathVariable("count") int count);

    //展示购物车数据
    @RequestMapping("/cart/show")
    public ResultBean showCart(@RequestParam("uuid")String uuid);

}
