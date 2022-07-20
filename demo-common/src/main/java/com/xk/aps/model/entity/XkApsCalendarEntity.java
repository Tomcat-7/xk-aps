package com.xk.aps.model.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.xk.framework.jpa.reposiotry.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
* 描述：一键生成单表模块模型
* @author xk
* @date 2021-12-28
*/
@Data
@Entity
@Table(name="xk_aps_calendar")
public class XkApsCalendarEntity extends BaseEntity  {

    /**
    *项目工程
    */
    @Column(name = "project")
    private String project;
    /**
    *资源代码
    */
    @Column(name = "resource_code")
    private String resourceCode;
    /**
    *资源名称
    */
    @Column(name = "resource_name")
    private String resourceName;
    /**
    *生产时间(例如周一->周五,全日期)
    */
    @Column(name = "work_date")
    private String workDate;
    /**
    *出勤模式代码
    */
    @Column(name = "attendance_code")
    private String attendanceCode;
    /**
    *出勤模式优先级
    */
    @Column(name = "calendar_priority")
    private Integer calendarPriority;
    /**
    *备注
    */
    @Column(name = "calendar_note")
    private String calendarNote;
    /**
     *设备电压
     */
    @ApiModelProperty(value = "电压")
    private String resourceVoltage;
    /**
     *设备电流1
     */
    @ApiModelProperty(value = "电流1")
    private String resourceCurrent1;
    /**
     *设备电流2
     */
    @ApiModelProperty(value = "电流2")
    private String resourceCurrent2;
    /**
     *设备电流3
     */
    @ApiModelProperty(value = "电流3")
    private String resourceCurrent3;
    /**
     *设备温度
     */
    @ApiModelProperty(value = "设备温度")
    private String resourceTemperature;
    /**
     *日度耗电量
     */
    @ApiModelProperty(value = "日度耗电量")
    private String powerConsumptionDay;
    /**
     *周度耗电量
     */
    @ApiModelProperty(value = "周度耗电量")
    private String powerConsumptionWeek;
    /**
     *月度耗电量
     */
    @ApiModelProperty(value = "周度耗电量")
    private String powerConsumptionMouth;
}
