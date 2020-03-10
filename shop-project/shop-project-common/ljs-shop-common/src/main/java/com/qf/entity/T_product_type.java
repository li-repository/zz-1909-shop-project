package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author li
 * @date 2020/3/10 0010 13:11
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class T_product_type {

    public Long cid;
    public String cname;
    public Long pid;
    public Integer flag;
    public Date createTime;
    public Date updateTime;
    public Long createUser;
    public Long updateUser;

}
