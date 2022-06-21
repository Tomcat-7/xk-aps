package com.xk.aps.model.dto;

import lombok.Data;

/**
 * @author sy
 * @date 2022/6/21 - 13:47
 */
@Data
public class XkApsShowChartDto {
    private String itemCode;
    private Integer productionNumber;
    private Integer processesNumber;
    private String BottleneckProcess;
    private String BottleneckProcessTime;
}
