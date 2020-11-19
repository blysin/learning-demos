package com.blysin.demo.web.domian;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * (TcPlatformUser)实体类
 *
 * @author daishaokun
 * @date 2019-09-24 15:59:56
 */
@Data
@TableName("tc_platform_user")
public class PlatformUser implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 邮箱
     */
    private String email;

    /**
     *
     */
    @TableLogic(value = "0", delval = "1")
    private Integer isDelete;

}