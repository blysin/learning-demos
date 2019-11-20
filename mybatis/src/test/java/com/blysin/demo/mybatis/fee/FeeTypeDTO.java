package com.blysin.demo.mybatis.fee;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 时段配置
 *
 * @author daishaokun
 * @date 2019-09-25
 */
@Data
@ApiModel("时段配置")
public class FeeTypeDTO {
    @ApiModelProperty(value = "id，可以为空", position = 1)
    private Integer id;

    /**
     * 费用说明(类型名称)
     */
    @ApiModelProperty(value = "费用说明(类型名称)", position = 2)
    private String feeType;

    /**
     * 计费时长
     */
    @ApiModelProperty(value = "计费时长（分钟）", position = 3)
    @Min(value = 0,message = "时段配置计费时长必须大于0")
    @NotNull(message = "时段配置计费时长不能为空")
    private Long timeLong;

    /**
     * 费率
     */
    @ApiModelProperty(value = "费率（元）", position = 4)
    @Min(value = 0,message = "时段配置费率必须大于0")
    @NotNull(message = "时段配置费率不能为空")
    private Long timeFee;

}