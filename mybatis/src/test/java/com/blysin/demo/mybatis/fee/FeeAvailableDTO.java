package com.blysin.demo.mybatis.fee;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * 分时间端设置
 *
 * @author daishaokun
 * @date 2019-09-25
 */
@Data
@ApiModel("分时间段设置")
public class FeeAvailableDTO {
    @ApiModelProperty(value = "id，可以为空", position = 0)
    private Integer id;

    @ApiModelProperty(value = "时段有效：开始日期，格式yyyy-MM-dd", position = 1)
    @Pattern(regexp="[0-9]{4}-[0-9]{2}-[0-9]{2}",message = "分时间段开始日期格式错误")
    @NotBlank(message = "分时间段开始日期不能为空")
    private String fromDate;

    @ApiModelProperty(value = "时段有效：结束日期，格式yyyy-MM-dd", position = 2)
    @Pattern(regexp="[0-9]{4}-[0-9]{2}-[0-9]{2}",message = "分时间段结束日期格式错误")
    @NotBlank(message = "分时间段结束日期不能为空")
    private String endDate;

    @ApiModelProperty(value = "开始时间段，格式：HH:mm:ss", position = 3)
    @Pattern(regexp="[0-9]{2}:[0-9]{2}:[0-9]{2}",message = "分时间段开始时间段格式错误")
    @NotBlank(message = "分时间段开始时间段不能为空")
    private String sTime;

    @ApiModelProperty(value = "结束时间段，格式：HH:mm:ss", position = 4)
    @Pattern(regexp="[0-9]{2}:[0-9]{2}:[0-9]{2}",message = "分时间段结束时间段格式错误")
    @NotBlank(message = "分时间段结束时间段不能为空")
    private String eTime;

    @ApiModelProperty(value = "有效类型：0:日期有效 1 星期有效'", position = 5)
    private Integer feeType;

    @ApiModelProperty(value = "有效星期：用|隔开，例如：1|2|4|7", position = 6)
    private String weekData;

    @ApiModelProperty(value = "是否固免：0 超过不免，1 固定减免", position = 7)
    private Integer ifFreeTime;

    @ApiModelProperty(value = "免费时间，分钟", position = 8)
    @Min(0)
    private Integer freeTime;

    @ApiModelProperty(value = "使用抵用卷：0不给免费时间，1给免费时间", position = 9)
    private Integer useTicket;

    @ApiModelProperty(value = "封顶费用，元", position = 10)
    @Min(0)
    private Integer feeMoney;

    @ApiModelProperty(value = "跨时间段后的计费按某条计费规则计费", position = 11)
    private Integer feeByType;

    @ApiModelProperty(value = "时段规则配置列表，要按顺序排列", position = 12)
    @Valid
    @NotEmpty(message = "时段配置不能为空")
    private List<FeeTypeDTO> feeTypes;
}