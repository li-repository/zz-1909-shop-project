package com.qf.service;

import com.qf.entity.TUser;
import com.qf.utils.ResultBean;

import javax.servlet.http.HttpServletResponse;

/**
 * @author li
 * @date 2020/3/12 0012 16:43
 */
public interface SsoService {

    /**
     * 登陆验证  用户名和密码是否正确
     * @param uname
     * @param password
     * @return
     */
    //,String userInfoUuid
    public ResultBean checkLogin(String uname, String password);

    /**
     * 检查用户是否已经登陆
     * @param uuid
     * @return
     */
    public ResultBean checkIsLogin(String uuid);

}
