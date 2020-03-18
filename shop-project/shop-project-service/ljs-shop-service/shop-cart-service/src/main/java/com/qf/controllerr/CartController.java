package com.qf.controllerr;

import com.qf.service.CartService;
import com.qf.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author li
 * @date 2020/3/14 0014 16:46
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService service;

    @Autowired
    private RedisTemplate redisTemplate;


    //向购物车中添加商品
    @RequestMapping("/add/{productId}/{count}")
    public ResultBean addProduct(String uuid,
                                 @PathVariable("productId") Long productId,
                                 @PathVariable("count") int count) {

       return service.addProduct(uuid,productId,count);

    }

    //清空购物车
    @RequestMapping("/clear")
    public ResultBean clearCart(String uuid){

        return service.clearCart(uuid);

    }

    //更新购物车信息
    @RequestMapping("/update/{productId}/{count}")
    public ResultBean updateCart(String uuid,
                                 @PathVariable("productId") Long productId,
                                 @PathVariable("count") int count){

        return service.updateCart(uuid,productId,count);

    }

    //展示购物车数据
    @RequestMapping("/show")
    public ResultBean showCart(String uuid){

        return service.showCart(uuid);

    }


    //登录时 购物车的合并问题
    @RequestMapping("/merge")
    public ResultBean mergeCart(String uuid, String uid){

        return service.mergeCart(uuid,uid);

    }

}
