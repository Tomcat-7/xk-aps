package com.xk.aps.model.dto;

import com.xk.framework.jpa.reposiotry.base.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
* 描述：一键生成单表模块DTO
* @author xk
* @date 2021-12-28
*/
@Data
@ApiModel(description = "一键生成单表模块DTO")
public class XkApsShowDto extends BaseDto{

    /**
    *项目工程
    */
    @ApiModelProperty(value = "项目工程")
    private String project;
    /**
    *订单代码
    */
    @ApiModelProperty(value = "订单代码")
    private String orderCode;
    /**
    *资源代码
    */
    @ApiModelProperty(value = "资源代码")
    private String resourceCode;
    /**
    *资源名称
    */
    @ApiModelProperty(value = "资源名称",example = "0")
    private Integer resourceName;
    /**
    *订单数量
    */
    @ApiModelProperty(value = "订单数量",example = "0")
    private Integer orderNumber;
    /**
    *开始时间(排程后生成)
    */
    @ApiModelProperty(value = "开始时间(排程后生成)")
    private Date startTime;
    /**
    *结束时间(排程后生成)
    */
    @ApiModelProperty(value = "结束时间(排程后生成)")
    private Date endTime;
    /**
    *交货期
    */
    @ApiModelProperty(value = "交货期")
    private Date orderDateDelivery;
    /**
    *订单品目代码
    */
    @ApiModelProperty(value = "订单品目代码")
    private String showWork;
    /**
    *该资源生产品目代码
    */
    @ApiModelProperty(value = "该资源生产品目代码")
    private String itemCode;
    /**
    *备注
    */
    @ApiModelProperty(value = "备注")
    private String bomNote;
    /**
    *
    */
    @ApiModelProperty(value = "")
    private Date createTime;
    /**
    *
    */
    @ApiModelProperty(value = "")
    private Date updateTime;

}
