package com.blysin.demo.mybatis.domian;

import lombok.Data;

import java.util.Date;

/**
 * @author daishaokun
 * @date 2019-09-23
 */
@Data
public class NewPlatform {
    private Integer id;
    private Date createTime;
    private String parkName;
}