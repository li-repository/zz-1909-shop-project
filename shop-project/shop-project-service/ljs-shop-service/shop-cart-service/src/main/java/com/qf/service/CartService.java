package com.qf.service;

import com.qf.utils.ResultBean;

/**
 * @author li
 * @date 2020/3/14 0014 16:46
 */

public interface CartService {

    /**
     * 向购物车中添加商品
     * @param id
     * @param productId
     * @param count
     * @return
     */
    public ResultBean addProduct(String id,Long productId,int count);

    /**
     * 清空购物车
     * @param uuid
     * @return
     */
    public ResultBean clearCart(String uuid);


    /**
     * 更新购物车数据
     * @param uuid
     * @param productId
     * @param count
     * @return
     */
    public ResultBean updateCart(String uuid,Long productId,int count);

    /**
     * 展示购物车数据
     * @param uuid
     * @return
     */
    public ResultBean showCart(String uuid);


    /**
     * 登录时 购物车的合并问题
     * @param uuid
     * @param uid
     * @return
     */
    public ResultBean mergeCart(String uuid,String uid);



}
