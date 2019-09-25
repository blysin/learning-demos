package com.blysin.demo.mybatis.test;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 对账同步日志表(TcLogComp)实体类
 *
 * @author code generate
 * @date 2019-09-24 19:14:16
 */
@Data
@TableName("TcLogComp")
public class TcLogComp implements Serializable {


    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 车场ID
     */
    private Integer lotcode;

    /**
     * 同步明细表ID
     */
    private Integer detailsId;

    /**
     * 同步汇总表ID
     */
    private Integer detailId;

    private String uuid;

    /**
     * 同步内容
     */
    private String syncContent;

    /**
     * 同步时间
     */
    private Date syncTime;

    /**
     * 备注
     */
    private String remark;

}