package com.qf.service;

import com.qf.entity.TUser;
import com.qf.utils.ResultBean;

/**
 * @author li
 * @date 2020/3/9 0009 22:26
 */
public interface RegisterService {

    /**
     * 发送验证码
     * @param phone
     * @return
     */
    public ResultBean sendVeryCode(String phone);

    /**
     * 验证码核对
     * @param checkVeryCode
     * @return
     */
    public ResultBean phoneCheckCode(String checkVeryCode,String phone);

    /**
     * 手机号注册
     * @param phone
     * @param password
     * @return
     */
    public ResultBean phoneRegister(String phone,String password);

    /**
     * 邮箱注册-发送邮件
     * @param email
     * @return
     */
    public ResultBean sendEmail(String email,String uuid);

    /**
     * 邮箱注册-激活账号
     * @param uuid
     * @return
     */
    public ResultBean activeAccount(String uuid);





}
