package cn.blysin.demo.shardingjdbc.carema.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * (TCarema)表实体类
 *
 * @author makejava
 * @since 2020-09-05 18:26:28
 */
@SuppressWarnings("serial")
@TableName("t_carema")
@Data
public class Carema extends Model<Carema> {
    /**
     * 唯一标识
     */
    private Long pid;
    /**
     * 车场ID
     */
    private Integer lotCode;
    /**
     * 同步时间
     */
    private Date lotSyncDate;
    /**
     * 车场记录id
     */
    private Integer id;
    /**
     * 节点ID    与t_node相关联
     */
    private Integer nodeId;
    /**
     * 摄相机在DSP中的端口号
     */
    private Integer nodePort;
    /**
     * 照相机类型(0:模拟;1数值;16车主监控)
     */
    private Integer caremaType;
    /**
     * 摄相机状态
     */
    private Integer state;
    /**
     * 上传标志(0未上传；1上传中；2上传成功)
     */
    private Integer cliFlag;
    /**
     * 上传时间
     */
    private Date cliDate;
    /**
     * 摄像机IP
     */
    private String caremaIp;
    /**
     * 0 未删除,1 已删除
     */
    private Integer isDelete;
    /**
     * 摄像机描述
     */
    private String caremaDes;

}