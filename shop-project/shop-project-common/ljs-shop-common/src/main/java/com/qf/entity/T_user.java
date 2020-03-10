package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author li
 * @date 2020/3/10 0010 12:01
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class T_user implements Serializable {

    public Long id;
    public String uname;
    public String password;
    public String phone;
    public String email;
    public Integer flag;
    public Date createTime;
    public Long createUser;
    public Date updateTime;
    public Long updateUser;



}
