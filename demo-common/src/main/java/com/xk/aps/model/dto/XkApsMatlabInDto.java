package com.xk.aps.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author sy
 * @date 2022/1/6 - 11:25
 */
@Data
public class XkApsMatlabInDto implements Comparable<XkApsMatlabInDto>{

    /**
     * 订单计算序号
     */
    @ApiModelProperty(value = "订单计算序号")
    private BigDecimal sortRatCode;
    /**
     *交货期
     */
    @ApiModelProperty(value = "交货期")
    private Date orderDateDelivery;
    /**
     *生产周期
     */
    @ApiModelProperty(value = "生产周期")
    private Long productionCycle;
/*********************************************************/

    /**
     * 订单序号
     */
    @ApiModelProperty(value = "订单序号")
    private Integer sortCode;
    /**
     * 交货期中间值(交货期长度的一半)
     */
    @ApiModelProperty(value = "交货期中间值")
    private Float medianOrderDeliveryTime;
    /**
     * 订单中环锻件的碾制外径值
     */
    @ApiModelProperty(value = "订单中环锻件的碾制外径值")
    private Float millingOutsideDiameter;
    /**
     * 订单中环锻件的碾环硬度值
     */
    @ApiModelProperty(value = "订单中环锻件的碾环硬度值")
    private Float millingHardness;

/*********************************************************/

    /**
     * 订单ID
     */
    private String id;
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
     *规格1
     */
    @ApiModelProperty(value = "规格1")
    private String specificationOne;
    /**
     *规格2
     */
    @ApiModelProperty(value = "规格2")
    private String specificationTwo;
    /**
     *规格3
     */
    @ApiModelProperty(value = "规格3")
    private String specificationThree;
    /**
     *规格4
     */
    @ApiModelProperty(value = "规格4")
    private String specificationFour;
    /**
     *数值规格1
     */
    @ApiModelProperty(value = "数值规格1")
    private String numericalSpecificationOne;
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
    private String numericalSpecificationFour;
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
     *订单状态(0:未指定1:开始生产2:结束)
     */
    @ApiModelProperty(value = "订单状态(0:未指定1:开始生产2:结束)",example = "0")
    private Integer orderStatus;
    /**
     *分派标志(0:未分派，1:已分派)
     */
    @ApiModelProperty(value = "分派标志(0:未分派，1:已分派)",example = "0")
    private Integer orderDispatchMark;


    @Override
    public int compareTo(XkApsMatlabInDto o) {
        return this.getSortRatCode().compareTo(o.getSortRatCode());
    }
}
