package com.xk.aps.model.dto;

import lombok.Data;

/**
 * @author sy
 * @date 2022/1/6 - 11:25
 */
@Data
public class XkApsMatlabInDto {
    /**
     * 订单序号
     */
    private Integer sortCode;
    /**
     * 交货期中间值(交货期长度的一半)
     */
    private Float medianOrderDeliveryTime;
    /**
     * 订单中环锻件的碾制外径值
     */
    private Float millingOutsideDiameter;
    /**
     * 订单中环锻件的碾环硬度值
     */
    private Float millingHardness;

}
