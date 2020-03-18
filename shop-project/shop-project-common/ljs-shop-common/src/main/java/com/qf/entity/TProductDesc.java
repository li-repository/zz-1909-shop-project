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
public class TProductDesc implements Serializable {

    private Long id;
    private Long pid;
    private String pdesc;
    private Integer flag;
    private Date createTime;
    private Date updateTime;
    private Long createUser;
    private Long updateUser;

}
