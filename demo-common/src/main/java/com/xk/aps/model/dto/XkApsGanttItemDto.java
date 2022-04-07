package com.xk.aps.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author sy
 * @date 2022/1/28 - 18:28
 */
@Data
public class XkApsGanttItemDto implements Comparable<XkApsGanttItemDto>{
    private String id;
    /**
     * 订单号
     */
    private String orderCode;
    /**
     *订单品目代码
     */
    private String itemCode;
    /**
     *订单数量
     */
    private Integer orderNumber;
    /**
     *交货期
     */
    private Date orderDateDelivery;
    /**
     *资源代码
     */
    private String resourceCode;
    /**
     *资源名称
     */
    private String resourceName;
    /**
     * 开始时间
     */
    private Date start;
    /**
     * 结束时间
     */
    private Date end;
    /**
     *前置信息
     */
    private String bomNote;

    @Override
    public int compareTo(XkApsGanttItemDto o) {
        return (int)((this.getStart().getTime() - o.getEnd().getTime())/1000);
    }
}
