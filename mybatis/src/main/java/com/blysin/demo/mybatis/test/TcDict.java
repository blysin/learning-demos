package com.blysin.demo.mybatis.test;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * (TcDict)实体类
 *
 * @author code generate
 * @date 2019-09-24 19:14:13
 */
@Data
@TableName("TcDict")
public class TcDict implements Serializable {


        @TableId(type = IdType.AUTO)
    private Integer id;

    
       /**
     * 类型（全局-global）
     */
        private String dictType;
    
       /**
     * 名称
     */
        private String dictName;
    
       /**
     * key
     */
        private String dictKey;
    
       /**
     * value
     */
        private String dictValue;
    
       /**
     * 页面展示文本
     */
        private String dictLabel;
    
       /**
     * 排序
     */
        private Integer dictSort;
    
       /**
     * 是否默认
     */
        private Integer isDefault;
    
       /**
     * 描述
     */
        private String dictDesc;
    
       /**
     * 是否启用：1-启用，0-禁用
     */
        private Integer dictStatus;
    
       /**
     * 创建时间
     */
        private Date createTime;
    
       /**
     * 修改时间
     */
        private Date updateTime;
    
       /**
     * 备注
     */
        private String remark;
    
       /**
     * 车场id
     */
        private Integer parkId;

}