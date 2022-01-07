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
public class XkApsAttendanceDto extends BaseDto{

    /**
    *项目工程
    */
    @ApiModelProperty(value = "项目工程")
    private String project;
    /**
    *出勤模式代码
    */
    @ApiModelProperty(value = "出勤模式代码")
    private String attendanceCode;
    /**
    *出勤时间
    */
    @ApiModelProperty(value = "出勤时间")
    private String attendanceTime;
    /**
    *备注
    */
    @ApiModelProperty(value = "备注")
    private String attendanceNote;

}
