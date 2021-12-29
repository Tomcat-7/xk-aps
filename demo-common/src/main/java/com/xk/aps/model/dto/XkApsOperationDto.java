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
public class XkApsOperationDto extends BaseDto{

    /**
    *项目工程
    */
    @ApiModelProperty(value = "项目工程")
    private String project;
    /**
    *工序代码
    */
    @ApiModelProperty(value = "工序代码")
    private String operationCode;
    /**
    *工序名称
    */
    @ApiModelProperty(value = "工序名称")
    private String operationName;
    /**
    *组种类
    */
    @ApiModelProperty(value = "组种类")
    private String operationGroup;
    /**
    *颜色
    */
    @ApiModelProperty(value = "颜色",example = "0")
    private Integer operationColor;
    /**
    *顺序
    */
    @ApiModelProperty(value = "顺序",example = "0")
    private Integer operationSort;
    /**
    *备注
    */
    @ApiModelProperty(value = "备注")
    private String operationNote;
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
