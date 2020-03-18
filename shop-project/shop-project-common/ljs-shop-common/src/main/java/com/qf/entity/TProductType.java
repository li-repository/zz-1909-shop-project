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
public class TProductType {

    private Long cid;
    private String cname;
    private Long pid;
    private Integer flag;
    private Date createTime;
    private Date updateTime;
    private Long createUser;
    private Long updateUser;

}
