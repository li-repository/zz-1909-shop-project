package com.qf.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author li
 * @date 2020/3/14 0014 17:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem implements Serializable {

    private Long ProductId;
    private int count;
    private Date updateTime;
}
