package com.xk.aps.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author sy
 * @date 2022/1/28 - 18:18
 */
@Data
public class XkApsGanttDto{
    /**
     * 订单号
     */
    private String id;
    /**
     * 订单号
     */
    private String name;

    private ColorPair colorPair;

    private List<XkApsGanttItemDto> gtArray;

    /**
     * 甘特图时间轴开始时间
     */
    private Date startTime;
    /**
     * 甘特图时间轴结束时间
     */
    private Date endTime;
    /**
     * 默认时间轴的刻度值 单位:分钟
     */
    private Integer scale = 360;


    @Data
    public static class ColorPair {
        private String dark;
        private String light;
    }


}
