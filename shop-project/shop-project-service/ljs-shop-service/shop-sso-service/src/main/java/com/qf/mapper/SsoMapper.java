package com.qf.mapper;

import com.qf.entity.TUser;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

/**
 * @author li
 * @date 2020/3/12 0012 19:27
 */
@Repository
public interface SsoMapper {

    /**
     * 登陆验证  用户名和密码是否正确
     * 用户登录使用uname 和 password进行
     * @param tUser
     * @return
     */
    public Integer checkLogin(TUser tUser);

    /**
     * 查询用户全部信息 用于存入redis中
     * @param uname
     * @return
     */
    public TUser findTUserByUname(String uname);

}
