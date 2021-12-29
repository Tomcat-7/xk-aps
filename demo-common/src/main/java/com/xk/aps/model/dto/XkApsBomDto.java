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
public class XkApsBomDto extends BaseDto{

    /**
    *项目工程
    */
    @ApiModelProperty(value = "项目工程")
    private String project;
    /**
    *品目代码
    */
    @ApiModelProperty(value = "品目代码")
    private String itemCode;
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
    *指令种类(输入，使用，输出)
    */
    @ApiModelProperty(value = "指令种类(输入，使用，输出)")
    private String instructionType;
    /**
    *指令代码(IN:输入,M:使用,OUT:输出)
    */
    @ApiModelProperty(value = "指令代码(IN:输入,M:使用,OUT:输出)")
    private String instructionCode;
    /**
    *品目资源代码
    */
    @ApiModelProperty(value = "品目资源代码")
    private String objectCode;
    /**
    *先行工序编号
    */
    @ApiModelProperty(value = "先行工序编号")
    private String beforeCode;
    /**
    *加工前需要等待时间(分钟单位)
    */
    @ApiModelProperty(value = "加工前需要等待时间(分钟单位)",example = "0")
    private Long beforeTime;
    /**
    *加工前需要的时间(分钟单位)
    */
    @ApiModelProperty(value = "加工前需要的时间(分钟单位)",example = "0")
    private Long manufacturingTime;
    /**
    *加工后需要等待时间(分钟单位)
    */
    @ApiModelProperty(value = "加工后需要等待时间(分钟单位)",example = "0")
    private Long afterTime;
    /**
    *接续方法(默认为ES)
    */
    @ApiModelProperty(value = "接续方法(默认为ES)")
    private String method;
    /**
    *物料移动时间(分钟单位)
    */
    @ApiModelProperty(value = "物料移动时间(分钟单位)",example = "0")
    private Long moveTime;
    /**
    *废品数量
    */
    @ApiModelProperty(value = "废品数量",example = "0")
    private Integer scrappedNumber;
    /**
    *成品率
    */
    @ApiModelProperty(value = "成品率",example = "0")
    private Long yield;
    /**
    *资源优先度
    */
    @ApiModelProperty(value = "资源优先度",example = "0")
    private Integer resourcePriority;
    /**
    *必要资源量(默认为1)
    */
    @ApiModelProperty(value = "必要资源量(默认为1)",example = "0")
    private Integer mustResource;
    /**
    *制造效率(默认为1表示100%)
    */
    @ApiModelProperty(value = "制造效率(默认为1表示100%)",example = "0")
    private Long manufacturingEfficiency;
    /**
    *备注
    */
    @ApiModelProperty(value = "备注")
    private String bomNote;
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
