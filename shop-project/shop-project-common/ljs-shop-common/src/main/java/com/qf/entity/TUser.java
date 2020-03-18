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
public class TUser implements Serializable {

    private Long id;
    private String uname;
    private String password;
    private String phone;
    private String email;
    private Integer flag;
    private Date createTime;
    private Long createUser;
    private Date updateTime;
    private Long updateUser;



}
