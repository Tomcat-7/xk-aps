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
public class XkApsResourceDto extends BaseDto{

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
    @ApiModelProperty(value = "资源名称")
    private String resourceName;
    /**
    *资源组
    */
    @ApiModelProperty(value = "资源组")
    private String resourceGroup;
    /**
    *资源区分
    */
    @ApiModelProperty(value = "资源区分")
    private String resourceDistinguish;
    /**
    *资源种类
    */
    @ApiModelProperty(value = "资源种类")
    private String resourceCategory;
    /**
    *资源量制约
    */
    @ApiModelProperty(value = "资源量制约")
    private String resourceRestriction;
    /**
    *资源顺序
    */
    @ApiModelProperty(value = "资源顺序")
    private String resourceSort;
    /**
    *备注
    */
    @ApiModelProperty(value = "备注")
    private String resourceNote;
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
    *数值规格1最小值
    */
    @ApiModelProperty(value = "数值规格1最小值")
    private String numericalSpecificationOneMin;
    /**
    *数值规格1最大值
    */
    @ApiModelProperty(value = "数值规格1最大值")
    private String numericalSpecificationOneMax;
    /**
    *数值规格2最小值
    */
    @ApiModelProperty(value = "数值规格2最小值")
    private String numericalSpecificationTwoMin;
    /**
    *数值规格2最大值
    */
    @ApiModelProperty(value = "数值规格2最大值")
    private String numericalSpecificationTwoMax;
    /**
    *数值规格3最小值
    */
    @ApiModelProperty(value = "数值规格3最小值")
    private String numericalSpecificationThreeMin;
    /**
    *数值规格3最大值
    */
    @ApiModelProperty(value = "数值规格3最大值")
    private String numericalSpecificationThreeMax;
    /**
    *数值规格4最小值
    */
    @ApiModelProperty(value = "数值规格4最小值")
    private String numericalSpecificationFourMin;
    /**
    *数值规格4最大值
    */
    @ApiModelProperty(value = "数值规格4最大值")
    private String numericalSpecificationFourMax;
    /**
    *颜色
    */
    @ApiModelProperty(value = "颜色",example = "0")
    private Integer resourceColor;
    /**
    *从此模板生成的资源
    */
    @ApiModelProperty(value = "从此模板生成的资源")
    private String resourceSon;
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
