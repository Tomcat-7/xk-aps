package com.xk.aps.model.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.xk.framework.jpa.reposiotry.base.BaseEntity;
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
@Table(name="xk_aps_resource")
public class XkApsResourceEntity extends BaseEntity  {

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
    *资源组
    */
    @Column(name = "resource_group")
    private String resourceGroup;
    /**
    *资源区分
    */
    @Column(name = "resource_distinguish")
    private String resourceDistinguish;
    /**
    *资源种类
    */
    @Column(name = "resource_category")
    private String resourceCategory;
    /**
    *资源量制约
    */
    @Column(name = "resource_restriction")
    private String resourceRestriction;
    /**
    *资源顺序
    */
    @Column(name = "resource_sort")
    private String resourceSort;
    /**
    *备注
    */
    @Column(name = "resource_note")
    private String resourceNote;
    /**
    *规格1
    */
    @Column(name = "specification_one")
    private String specificationOne;
    /**
    *规格2
    */
    @Column(name = "specification_two")
    private String specificationTwo;
    /**
    *规格3
    */
    @Column(name = "specification_three")
    private String specificationThree;
    /**
    *规格4
    */
    @Column(name = "specification_four")
    private String specificationFour;
    /**
    *数值规格1最小值
    */
    @Column(name = "numerical_specification_one_min")
    private String numericalSpecificationOneMin;
    /**
    *数值规格1最大值
    */
    @Column(name = "numerical_specification_one_max")
    private String numericalSpecificationOneMax;
    /**
    *数值规格2最小值
    */
    @Column(name = "numerical_specification_two_min")
    private String numericalSpecificationTwoMin;
    /**
    *数值规格2最大值
    */
    @Column(name = "numerical_specification_two_max")
    private String numericalSpecificationTwoMax;
    /**
    *数值规格3最小值
    */
    @Column(name = "numerical_specification_three_min")
    private String numericalSpecificationThreeMin;
    /**
    *数值规格3最大值
    */
    @Column(name = "numerical_specification_three_max")
    private String numericalSpecificationThreeMax;
    /**
    *数值规格4最小值
    */
    @Column(name = "numerical_specification_four_min")
    private String numericalSpecificationFourMin;
    /**
    *数值规格4最大值
    */
    @Column(name = "numerical_specification_four_max")
    private String numericalSpecificationFourMax;
    /**
    *颜色
    */
    @Column(name = "resource_color")
    private Integer resourceColor;
    /**
    *从此模板生成的资源
    */
    @Column(name = "resource_son")
    private String resourceSon;

}
