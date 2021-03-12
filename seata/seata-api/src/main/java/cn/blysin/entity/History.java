package cn.blysin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author daishaokun
 * @date 2021/3/11
 */
@Data
@TableName("t_history")
public class History {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Date createTime;
}
