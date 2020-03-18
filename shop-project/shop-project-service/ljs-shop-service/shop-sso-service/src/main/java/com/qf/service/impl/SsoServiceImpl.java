package com.qf.service.impl;

import com.qf.entity.TUser;
import com.qf.mapper.SsoMapper;
import com.qf.service.SsoService;
import com.qf.utils.ConstantsUtil;
import com.qf.utils.ResultBean;
import com.qf.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author li
 * @date 2020/3/12 0012 16:44
 */
@Service
public class SsoServiceImpl implements SsoService {

    @Autowired
    private SsoMapper mapper;

    @Autowired
    private RedisTemplate redisTemplate;

    //登陆验证  用户名和密码是否正确
    public ResultBean checkLogin(String uname, String password) {
        //去数据库查询用户名和密码
        //封装查询信息
        TUser tUser = new TUser();
        tUser.setUname(uname);
        tUser.setPassword(password);
        Integer count = mapper.checkLogin(tUser);

        if (count == 1){
            //信息正确
            return ResultBean.success();
        }
        //信息错误
        return ResultBean.error();
    }

    //检查用户是否已经登陆
    public ResultBean checkIsLogin(String uuid) {

        //判断uuid是否存在
        if (uuid != null && "".equals(uuid)){
            //根据这个uuid 去redis查询是否有用户信息
            String redisKey = StringUtil.format(ConstantsUtil.SSO_LOGIN_INFO_PRE, uuid);
            TUser tUser = (TUser) redisTemplate.opsForValue().get(redisKey);
            if (tUser != null){
                //数据传递时不携带密码
                tUser.setPassword("");
                return ResultBean.success("用户已登录");
            }
        }

        return ResultBean.error("用户未登陆");
    }


}
