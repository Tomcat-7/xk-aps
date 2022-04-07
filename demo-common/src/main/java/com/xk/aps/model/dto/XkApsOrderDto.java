package com.xk.aps.model.dto;

import com.xk.framework.jpa.reposiotry.base.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

/**
* 描述：一键生成单表模块DTO
* @author xk
* @date 2021-12-28
*/
@Data
@ApiModel(description = "一键生成单表模块DTO")
public class XkApsOrderDto extends BaseDto implements Comparable<XkApsOrderDto>{

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
    *订单种类(0:销售，1:制造，2:采购)
    */
    @ApiModelProperty(value = "订单种类(0:销售，1:制造，2:采购)",example = "0")
    private String orderCategory;
    /**
    *订单区分(0:录入，1:补充)
    */
    @ApiModelProperty(value = "订单区分(0:录入，1:补充)",example = "0")
    private String orderDistinguish;
    /**
    *品目代码
    */
    @ApiModelProperty(value = "品目代码")
    private String itemCode;
    /**
    *最早开始时间
    */
    @ApiModelProperty(value = "最早开始时间")
    private Date mostEarlyTime;
    /**
    *交货期
    */
    @ApiModelProperty(value = "交货期")
    private Date orderDateDelivery;
//    /**
//     *生产周期
//     */
//    @ApiModelProperty(value = "生产周期")
//    private Long orderProductionCycle;
    /**
     * 订单序号
     */
    @ApiModelProperty(value = "订单序号")
    private Integer sortCode;
    /**
     * 订单计算序号
     */
    @ApiModelProperty(value = "订单计算序号")
    private BigDecimal sortRatCode;
    /**
    *订单数量
    */
    @ApiModelProperty(value = "订单数量",example = "0")
    private Integer orderNumber;
    /**
    *订单优先级
    */
    @ApiModelProperty(value = "订单优先级",example = "0")
    private Integer orderPriority;
    /**
    *订单客户，如果是补充订单，则客户为-1
    */
    @ApiModelProperty(value = "订单客户，如果是补充订单，则客户为-1")
    private String orderClient;
    /**
    *订单颜色
    */
    @ApiModelProperty(value = "订单颜色",example = "0")
    private Integer orderColor;
    /**
    *备注
    */
    @ApiModelProperty(value = "备注")
    private String orderNote;
    /**
    *规格1 (订单序号)
    */
    @ApiModelProperty(value = "规格1")
    private Integer specificationOne;
    /**
    *规格2  (生产周期)
    */
    @ApiModelProperty(value = "规格2")
    private Long specificationTwo;
    /**
    *规格3 (订单开工日期)
    */
    @ApiModelProperty(value = "规格3")
    private Long specificationThree;
    /**
    *规格4 (加热炉序号)
    */
    @ApiModelProperty(value = "规格4")
    private String specificationFour;
    /**
    *数值规格1 (物料质量)
    */
    @ApiModelProperty(value = "数值规格1")
    private Integer numericalSpecificationOne;
    /**
    *数值规格2
    */
    @ApiModelProperty(value = "数值规格2")
    private String numericalSpecificationTwo;
    /**
    *数值规格3
    */
    @ApiModelProperty(value = "数值规格3")
    private String numericalSpecificationThree;
    /**
    *数值规格4
    */
    @ApiModelProperty(value = "数值规格4")
    private Date numericalSpecificationFour;
    /**
    *交货提前期
    */
    @ApiModelProperty(value = "交货提前期")
    private String orderLeadTime;
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
    *订单状态(未指定,开始生产,结束)
    */
    @ApiModelProperty(value = "订单状态(未指定,开始生产,结束)",example = "未指定")
    private String orderStatus;
    /**
    *分派标志(未分派，已分派)
    */
    @ApiModelProperty(value = "分派标志(未分派，已分派)",example = "未分派")
    private String orderDispatchMark;

    /**
     * compareTo：
     *  如果指定的数与参数相等返回0
     *  如果指定的数小于参数返回 -1
     *  如果指定的数大于参数返回 1
     * @param o
     * @return
     */
    @Override
    public int compareTo(XkApsOrderDto o) {
        return o.getSortRatCode().compareTo(this.getSortRatCode());
    }
}
