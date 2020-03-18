package com.qf.service.impl;

import com.qf.entity.TProduct;
import com.qf.mapper.CartMapper;
import com.qf.service.CartService;
import com.qf.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author li
 * @date 2020/3/14 0014 16:49
 */
@Service
public class CartServiceImpl implements CartService {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CartMapper mapper;

    //向购物车中添加商品
    public ResultBean addProduct(String id,Long productId,int count) {

        //从Redis中获取购物车商品数据
        String redisKey = StringUtil.format(ConstantsUtil.CART_REDIS_PRE, id);
        Object o = redisTemplate.opsForValue().get(redisKey);

        //判断数据是否为空 如果为空 说明这是第一次添加购物车
        if (o == null){
            //创建一个购物车集合
            ArrayList<CartItem> cartItems = new ArrayList<CartItem>();
            //封装添加商品信息
            CartItem cartItem = new CartItem(productId, count, new Date());
            //把商品添加到购物车中
            cartItems.add(cartItem);
            //把购物车数据存入Redis
            redisTemplate.opsForValue().set(redisKey,cartItems);

            //添加成功 返回成功信息
            return ResultBean.success(cartItems,"添加购物车成功");
        }

        //Redis中没有购物车数据 说明已经存在购物车
        ArrayList<CartItem> cartItems = (ArrayList<CartItem>)o;
        //遍历
        for (CartItem cartItem : cartItems) {
            //判断当前商品的id购物车中是否已存在
            if (productId.longValue() == cartItem.getProductId().longValue()){
                //已存在
                //当前商品数量+1
                cartItem.setCount(count+1);
                //更新时间
                cartItem.setUpdateTime(new Date());
                //更新Redis中的购物车数据
                redisTemplate.opsForValue().set(redisKey,cartItems);

                //添加成功 返回成功信息
                return ResultBean.success(cartItems,"添加购物车成功");
            }
        }
        //不存在
        //封装商品信息  把当前商品添加到集合中
        CartItem cartItem = new CartItem(productId, count, new Date());
        cartItems.add(cartItem);

        //更新Redis中的购物车数据
        redisTemplate.opsForValue().set(redisKey,cartItems);

        //添加成功 返回成功信息
        return ResultBean.success(cartItems,"添加购物车成功");

    }

    //清空购物车
    public ResultBean clearCart(String uuid) {

        //根据key del
        String redisKey = StringUtil.format(ConstantsUtil.CART_REDIS_PRE, uuid);
        redisTemplate.delete(redisKey);

        return ResultBean.success("清空购物车成功");
    }

    //更新购物车数据
    public ResultBean updateCart(String uuid, Long productId, int count) {

        if (uuid != null && !"".equals(uuid)){
            //获取redis中的购物车数据
            String redisKey = StringUtil.format(ConstantsUtil.CART_REDIS_PRE, uuid);
            Object o = redisTemplate.opsForValue().get(redisKey);

            if (o != null) {
                //购物车中有数据
                ArrayList<CartItem> cartItems = (ArrayList<CartItem>) o;
                for (CartItem cartItem : cartItems) {
                    if (cartItem.getProductId().longValue() == productId.longValue()){
                        //当当前商品id与购物车中的商品id相同时
                        cartItem.setCount(count);
                        cartItem.setUpdateTime(new Date());

                        //更新Redis中的购物车数据
                        redisTemplate.opsForValue().set(redisKey,cartItems);

                        return ResultBean.success(cartItems,"更新购物车成功");

                    }
                }

            }
        }

        //当前用户没有购物车
        return ResultBean.error("当前用户没有购物车");

    }

    //展示购物车数据
    public ResultBean showCart(String uuid) {

        if (uuid != null && !"".equals(uuid)) {
            //有购物车
            //获取redis中的购物车数据
            String redisKey = StringUtil.format(ConstantsUtil.CART_REDIS_PRE, uuid);
            Object o = redisTemplate.opsForValue().get(redisKey);

            if (o != null) {
                //购物车中有数据
                ArrayList<CartItem> cartItems = (ArrayList<CartItem>) o;
                //创建一个存有商品的相信信息的集合
                List<TProductCartDTO> products = new ArrayList<TProductCartDTO>();

                for (CartItem cartItem : cartItems) {

                    //去redis中取商品相信
                    String productId_redis = StringUtil.format(ConstantsUtil.PRODUCT_REDIS_PRE, cartItem.getProductId().toString());
                    TProduct tProduct = (TProduct)redisTemplate.opsForValue().get(productId_redis);

                    //第一次redis中没有缓存
                    if (tProduct == null) {
                        //去数据库中查询商品信息
                        TProduct pro = mapper.findProductById(cartItem.getProductId());
                        //存入redis
                        redisTemplate.opsForValue().set(productId_redis,pro);
                    }

                    //封装TProductCartDTO
                    TProductCartDTO tProductCartDTO = new TProductCartDTO(tProduct,cartItem.getCount(),cartItem.getUpdateTime());

                    //存入集合中
                    products.add(tProductCartDTO);
                }

                //对集合中的元素进行排序
                Collections.sort(products, new Comparator<TProductCartDTO>() {
                    public int compare(TProductCartDTO o1, TProductCartDTO o2) {
                        return (int)(o1.getUpdateTime().getTime() - o2.getUpdateTime().getTime());
                    }
                });
                return ResultBean.success(products);
            }
        }

        return ResultBean.error("当前用户没有购物车");
    }


    //登录时 购物车的合并问题
    public ResultBean mergeCart(String uuid, String uid) {

        //获取未登录和已登录的购物车数据
        String noLoginKey = StringUtil.format(ConstantsUtil.CART_REDIS_PRE, uuid);
        String loginKey = StringUtil.format(ConstantsUtil.CART_REDIS_PRE, uid);
        ArrayList<CartItem> noLoginCarts = (ArrayList<CartItem>)redisTemplate.opsForValue().get(noLoginKey);
        ArrayList<CartItem> loginCarts = (ArrayList<CartItem>)redisTemplate.opsForValue().get(loginKey);

        //如果未登陆时没有购物车
        if (noLoginCarts == null && loginCarts != null) {
            return ResultBean.success(loginCarts,"不需要合并");
        }

        //如果已登陆时没有购物车
        if (loginCarts == null && noLoginCarts != null) {

            //清空未登陆下的购物车
            clearCart(uuid);

            return ResultBean.success(noLoginCarts,"合并成功");
        }

        //如果未登陆和已登录都有购物车
        if (noLoginCarts != null && loginCarts != null) {
            //创建一个map 存放合并后的购物车数据
            Map<Long,CartItem> map = new HashMap<Long, CartItem>();

            //遍历未登录购物车数据
            for (CartItem noLoginCartItem : noLoginCarts) {
                //把商品id作为key 商品数据作为val存入map中
                map.put(noLoginCartItem.getProductId(),noLoginCartItem);
            }

            //遍历已登录的购物车数据
            for (CartItem loginCartItem : loginCarts) {
                //根据已登录下的商品id作为key 获取map中的商品对象
                CartItem currentCartItem = map.get(loginCartItem.getProductId());
                //不为空 说明商品重复 把数量相加
                if (currentCartItem != null) {
                    currentCartItem.setCount(currentCartItem.getCount() + loginCartItem.getCount());
                }else {
                    //不存在 直接放
                    map.put(loginCartItem.getProductId(),loginCartItem);
                }
            }

            //清空未登陆状态下的购物车数据
            clearCart(uuid);

            //把map的values放入新的购物车集合中
            List<CartItem> mergeCarts = new ArrayList<CartItem>(map.values());

            return ResultBean.success(mergeCarts,"合并成功");
        }

        //没有购物车
        return ResultBean.success("没有购物车");

    }


}
