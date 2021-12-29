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
public class XkApsCalendarDto extends BaseDto{

    /**
    *项目工程
    */
    @ApiModelProperty(value = "项目工程")
    private String project;
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
    *生产时间(例如周一->周五,全日期)
    */
    @ApiModelProperty(value = "生产时间(例如周一->周五,全日期)")
    private String workDate;
    /**
    *出勤模式代码
    */
    @ApiModelProperty(value = "出勤模式代码")
    private String attendanceCode;
    /**
    *出勤模式优先级
    */
    @ApiModelProperty(value = "出勤模式优先级",example = "0")
    private Integer calendarPriority;
    /**
    *备注
    */
    @ApiModelProperty(value = "备注")
    private String calendarNote;
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
