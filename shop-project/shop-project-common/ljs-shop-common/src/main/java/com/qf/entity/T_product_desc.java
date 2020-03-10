package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author li
 * @date 2020/3/10 0010 13:08
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class T_product_desc implements Serializable {

    public Long id;
    public Long pid;
    public String pdesc;
    public Integer flag;
    public Date createTime;
    public Date updateTime;
    public Long createUser;
    public Long updateUser;

}
