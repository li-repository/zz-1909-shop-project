package com.qf.utils;

import com.qf.entity.TProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author li
 * @date 2020/3/14 0014 19:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TProductCartDTO {

    private TProduct product;
    private Integer count;
    private Date updateTime;

}
