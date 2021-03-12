package cn.blysin.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author daishaokun
 * @date 2021/3/11
 */
@Data
public class OrderDTO {
    private Integer id;
    private String name;
    private Date createTime;
}
