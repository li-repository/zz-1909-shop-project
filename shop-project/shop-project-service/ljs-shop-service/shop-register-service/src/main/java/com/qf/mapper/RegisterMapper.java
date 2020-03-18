package com.qf.mapper;

import com.qf.entity.TUser;
import com.qf.utils.ResultBean;
import org.springframework.stereotype.Repository;

/**
 * @author li
 * @date 2020/3/9 0009 22:28
 */

@Repository
public interface RegisterMapper {

    /**
     * 手机号注册  mysql添加用户信息
     * @param tUser
     * @return
     */
    public Integer phoneRegister(TUser tUser);

    /**
     * 判断手机号是否可用
     * @param phone
     * @return
     */
    public Integer checkPhone(String phone);


    /**
     * 邮箱注册  mysql添加用户信息
     * @param tUser
     * @return
     */
    public Integer emailRegister(TUser tUser);

    /**
     * 判断邮箱是否可用
     * @param email
     * @return
     */
    public Integer checkEmail(String email);



}
