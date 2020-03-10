package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author li
 * @date 2020/3/10 0010 12:20
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class T_product implements Serializable {

    public Long pid;
    public String pname;
    public BigDecimal price;
    public BigDecimal salePrice; //销售价格
    public Long typeId;
    public Integer status;
    public String pimage;
    public Integer flag;
    public Date createTime;
    public Date updateTime;
    public Long createUser;
    public Long updateUser;


}
