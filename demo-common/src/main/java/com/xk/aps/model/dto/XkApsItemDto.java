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
public class XkApsItemDto extends BaseDto{

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
    *品目名称
    */
    @ApiModelProperty(value = "品目名称")
    private String itemName;
    /**
    *品目组
    */
    @ApiModelProperty(value = "品目组")
    private String itemGroup;
    /**
    *备料方法(0:内制优先,1:采购优先)
    */
    @ApiModelProperty(value = "备料方法(0:内制优先,1:采购优先)",example = "0")
    private Integer itemPreparation;
    /**
    *自动补充标志(0:是，1:否)
    */
    @ApiModelProperty(value = "自动补充标志(0:是，1:否)",example = "0")
    private Integer itemSupplement;
    /**
    *制造批量(max)
    */
    @ApiModelProperty(value = "制造批量(max)",example = "0")
    private Integer itemManufactureBatchMax;
    /**
    *制造批量(min)
    */
    @ApiModelProperty(value = "制造批量(min)",example = "0")
    private Integer itemManufactureBatchMin;
    /**
    *制造批量单位
    */
    @ApiModelProperty(value = "制造批量单位")
    private String itemManufactureUnit;
    /**
    *采购批量(max)
    */
    @ApiModelProperty(value = "采购批量(max)",example = "0")
    private Integer itemPurchaseBatchMax;
    /**
    *采购批量(min)
    */
    @ApiModelProperty(value = "采购批量(min)",example = "0")
    private Integer itemPurchaseBatchMin;
    /**
    *采购批量单位
    */
    @ApiModelProperty(value = "采购批量单位")
    private String itemPurchaseUnit;
    /**
    *品目种类
    */
    @ApiModelProperty(value = "品目种类")
    private String itemCategory;
    /**
    *品目优先级
    */
    @ApiModelProperty(value = "品目优先级",example = "0")
    private Integer itemPriority;
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
    *数值规格1
    */
    @ApiModelProperty(value = "数值规格1")
    private String numericalSpecificationOne;
    /**
    *数值规格2
    */
    @ApiModelProperty(value = "数值规格2")
    private String numericalSpecificationTwo;
    /**
    *数值规格3
    */
    @ApiModelProperty(value = "数值规格3")
    private String numericalSpecificationThree;
    /**
    *数值规格4
    */
    @ApiModelProperty(value = "数值规格4")
    private String numericalSpecificationFour;
    /**
    *制造效率(默认为1表示100%)
    */
    @ApiModelProperty(value = "制造效率(默认为1表示100%)",example = "0")
    private Long manufacturingEfficiency;
    /**
    *颜色
    */
    @ApiModelProperty(value = "颜色",example = "0")
    private Integer itemColor;
    /**
    *顺序
    */
    @ApiModelProperty(value = "顺序",example = "0")
    private Integer itemSort;
    /**
    *订单展开品目
    */
    @ApiModelProperty(value = "订单展开品目")
    private String oderUnfoldItem;
    /**
    *采购提前期
    */
    @ApiModelProperty(value = "采购提前期")
    private String itemPurchaseLeadTime;
    /**
    *包装配送提前期
    */
    @ApiModelProperty(value = "包装配送提前期")
    private String itemDistributionLeadTime;
    /**
    *目标库存MIN
    */
    @ApiModelProperty(value = "目标库存MIN",example = "0")
    private Integer targetInventoryMin;
    /**
    *库存MIN
    */
    @ApiModelProperty(value = "库存MIN",example = "0")
    private Integer inventoryMin;
    /**
    *库存MAX
    */
    @ApiModelProperty(value = "库存MAX",example = "0")
    private Integer inventoryMax;
    /**
    *库存有效期
    */
    @ApiModelProperty(value = "库存有效期")
    private String inventoryLife;
    /**
    *原材料制约标志
    */
    @ApiModelProperty(value = "原材料制约标志")
    private String materialControlMark;
    /**
    *库存增减标志
    */
    @ApiModelProperty(value = "库存增减标志")
    private String inventoryControlMark;
    /**
    *低等级代码
    */
    @ApiModelProperty(value = "低等级代码",example = "0")
    private Integer levelCode;
    /**
    *关联条件式
    */
    @ApiModelProperty(value = "关联条件式")
    private String associatedCondition;
    /**
    *自动固定外部关联
    */
    @ApiModelProperty(value = "自动固定外部关联")
    private String automaticFixationExternalAssociation;
    /**
    *工作分割数量
    */
    @ApiModelProperty(value = "工作分割数量",example = "0")
    private Integer splitNumber;
    /**
    *工作分割比率
    */
    @ApiModelProperty(value = "工作分割比率",example = "0")
    private Integer splitRatio;
    /**
    *工作并行数量
    */
    @ApiModelProperty(value = "工作并行数量",example = "0")
    private Integer parallelNumber;
    /**
    *工作批量MIN
    */
    @ApiModelProperty(value = "工作批量MIN",example = "0")
    private Integer workBatchMin;
    /**
    *工作批量MAX
    */
    @ApiModelProperty(value = "工作批量MAX",example = "0")
    private Integer workBatchMax;
    /**
    *工作批量单位
    */
    @ApiModelProperty(value = "工作批量单位")
    private String batchUnit;
    /**
    *时间固定级别(结束)
    */
    @ApiModelProperty(value = "时间固定级别(结束)",example = "0")
    private Integer timeFixedLevelOver;
    /**
    *确保分割之前的数量(默认为否)
    */
    @ApiModelProperty(value = "确保分割之前的数量(默认为否)")
    private String beforeSplitting;
    /**
    *时间固定级别(开始生产)
    */
    @ApiModelProperty(value = "时间固定级别(开始生产)",example = "0")
    private Integer timeFixedLevelBegin;
    /**
    *时间固定级别(确定)
    */
    @ApiModelProperty(value = "时间固定级别(确定)",example = "0")
    private Integer timeFixedLevelSure;
    /**
    *时间固定级别(指示完毕)
    */
    @ApiModelProperty(value = "时间固定级别(指示完毕)",example = "0")
    private Integer timeFixedLevelFinish;
    /**
    *时间固定级别(固定)
    */
    @ApiModelProperty(value = "时间固定级别(固定)",example = "0")
    private Integer timeFixedLevelFixed;
    /**
    *时间固定级别(计划完毕)
    */
    @ApiModelProperty(value = "时间固定级别(计划完毕)",example = "0")
    private Integer timeFixedLevelEnd;
    /**
    *数量固定级别(结束)
    */
    @ApiModelProperty(value = "数量固定级别(结束)",example = "0")
    private Integer numberFixedLevelOver;
    /**
    *输入指令
    */
    @ApiModelProperty(value = "输入指令")
    private String inInstruction;
    /**
    *输出指令
    */
    @ApiModelProperty(value = "输出指令")
    private String outInstruction;
    /**
    *数量固定级别(开始生产)
    */
    @ApiModelProperty(value = "数量固定级别(开始生产)",example = "0")
    private Integer numberFixedLevelBegin;
    /**
    *数量固定级别(确定)
    */
    @ApiModelProperty(value = "数量固定级别(确定)",example = "0")
    private Integer numberFixedLevelSure;
    /**
    *数量固定级别(指示完毕)
    */
    @ApiModelProperty(value = "数量固定级别(指示完毕)",example = "0")
    private Integer numberFixedLevelFinish;
    /**
    *数量固定级别(固定)
    */
    @ApiModelProperty(value = "数量固定级别(固定)",example = "0")
    private Integer numberFixedLevelFixed;
    /**
    *数量固定级别(计划完毕)
    */
    @ApiModelProperty(value = "数量固定级别(计划完毕)",example = "0")
    private Integer numberFixedLevelEnd;
    /**
    *数量固定级别(分割工作)
    */
    @ApiModelProperty(value = "数量固定级别(分割工作)",example = "0")
    private Integer numberFixedLevelSpilt;
    /**
    *自动生成中间产品标志(默认为否)
    */
    @ApiModelProperty(value = "自动生成中间产品标志(默认为否)")
    private String automaticGenerationIntermediateMark;
    /**
    *订单
    */
    @ApiModelProperty(value = "订单")
    private String itemOrder;
    /**
    *工序
    */
    @ApiModelProperty(value = "工序")
    private String itemOperation;
    /**
    *备注
    */
    @ApiModelProperty(value = "备注")
    private String itemNote;
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
