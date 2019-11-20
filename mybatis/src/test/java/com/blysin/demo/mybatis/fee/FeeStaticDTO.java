package com.blysin.demo.mybatis.fee;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 收费标准设置
 *
 * @author daishaokun
 * @date 2019-09-26
 */
@Data
@ApiModel("收费标准设置")
public class FeeStaticDTO {
    @ApiModelProperty(value = "主键", position = 1)
    private Integer id;

    @ApiModelProperty(value = "规则名称", position = 2)
    @NotBlank(message = "收费标准规则名称不能为空")
    private String feeName;

    @ApiModelProperty(value = "车辆类型,大型车|小型车等，调用字典接口，字典类型：CAR_STYLE", position = 3)
    @NotNull(message = "收费标准车辆类型不能为空")
    private Integer carStyle;

    @ApiModelProperty(value = "车牌类型,临时车|月租车等，调用字典接口，字典类型：CARD_TYPE", position = 4)
    @NotNull(message = "收费标准车牌类型不能为空")
    private Integer cardType;

    @ApiModelProperty(value = "封顶费用，元", position = 5)
    @Min(value = 0, message = "收费标准封顶费用必须大于0")
    @NotNull(message = "收费标准封顶费用不能为空")
    private Integer timeFee;

    @ApiModelProperty(value = "封顶时长，小时", position = 6)
    @Min(value = 0, message = "收费标准封顶时长必须大于0")
    @NotNull(message = "收费标准封顶时长不能为空")
    private Long timeLong;

    @ApiModelProperty(value = "封项类型：(0自然天[00:00-23:59]；1：封顶时长内)", position = 7)
    private Integer capType;

    @ApiModelProperty(value = "封顶后计费 0:重新循环;1:最后一条计费", position = 8)
    private Integer capCalFeeType;

    @ApiModelProperty(value = "免费时间,分钟", position = 9)
    private Integer freeTime;

    @ApiModelProperty(value = "是否固免：0 超过不免，1 固定减免", position = 10)
    private Integer ifFreeTime;

    @ApiModelProperty(value = "使用抵用卷：0不给免费时间，1给免费时间", position = 11)
    private Integer useTicket;

    @ApiModelProperty(value = "封顶内免费：（0：封顶后，再次进出重新计费；1：封顶后，再次进出当天或封顶时长内免费）", position = 12)
    private Integer capsIfFree;

    @ApiModelProperty(value = "所属区域", position = 13)
    private String belongAreas;

    @ApiModelProperty(value = "向前向后：0 向前靠  1 向后靠", position = 14)
    private Integer leftOrRight;

    @ApiModelProperty(value = "跨时计费：0否1是", position = 15)
    private Integer ifCross;

    @ApiModelProperty(value = "跨靠时间，分钟", position = 16)
    private Integer crossTimes;

    @ApiModelProperty(value = "合并计费：0分段计费，1合并计费", position = 17)
    private Integer ifMergeCalFee;

    @ApiModelProperty(value = "再缴限制：0系统默认；1以最后结算时间开始计费;2超出缓冲时间计费不给缓冲时间；4:给缓冲时间不给免费时间;8:中央计算费用时去掉缓冲时间", position = 18)
    private Integer calFeeMethod;

    @ApiModelProperty(value = "收费规则描述", position = 19)
    private String description;

    @ApiModelProperty(value = "车场id", position = 20)
    @NotNull(message = "车场id不能为空")
    private Integer lotCode;

    @ApiModelProperty(value = "分时间端设置列表", position = 21)
    @Valid
    private List<FeeAvailableDTO> feeAvailables;
}