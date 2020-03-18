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
public class TProduct implements Serializable {

    private Long pid;
    private String pname;
    private BigDecimal price;
    private BigDecimal salePrice; //销售价格
    private Long typeId;
    private Integer status;
    private String pimage;
    private Integer flag;
    private Date createTime;
    private Date updateTime;
    private Long createUser;
    private Long updateUser;


}
