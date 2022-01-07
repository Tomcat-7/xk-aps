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
@Table(name="xk_aps_item")
public class XkApsItemEntity extends BaseEntity  {

    /**
    *项目工程
    */
    @Column(name = "project")
    private String project;
    /**
    *品目代码
    */
    @Column(name = "item_code")
    private String itemCode;
    /**
    *品目名称
    */
    @Column(name = "item_name")
    private String itemName;
    /**
    *品目组
    */
    @Column(name = "item_group")
    private String itemGroup;
    /**
    *备料方法(0:内制优先,1:采购优先)
    */
    @Column(name = "item_preparation")
    private String itemPreparation;
    /**
    *自动补充标志(0:是，1:否)
    */
    @Column(name = "item_supplement")
    private String itemSupplement;
    /**
    *制造批量(max)
    */
    @Column(name = "item_manufacture_batch_max")
    private Integer itemManufactureBatchMax;
    /**
    *制造批量(min)
    */
    @Column(name = "item_manufacture_batch_min")
    private Integer itemManufactureBatchMin;
    /**
    *制造批量单位
    */
    @Column(name = "item_manufacture_unit")
    private String itemManufactureUnit;
    /**
    *采购批量(max)
    */
    @Column(name = "item_purchase_batch_max")
    private Integer itemPurchaseBatchMax;
    /**
    *采购批量(min)
    */
    @Column(name = "item_purchase_batch_min")
    private Integer itemPurchaseBatchMin;
    /**
    *采购批量单位
    */
    @Column(name = "item_purchase_unit")
    private String itemPurchaseUnit;
    /**
    *品目种类
    */
    @Column(name = "item_category")
    private String itemCategory;
    /**
    *品目优先级
    */
    @Column(name = "item_priority")
    private Integer itemPriority;
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
    *数值规格1
    */
    @Column(name = "numerical_specification_one")
    private String numericalSpecificationOne;
    /**
    *数值规格2
    */
    @Column(name = "numerical_specification_two")
    private String numericalSpecificationTwo;
    /**
    *数值规格3
    */
    @Column(name = "numerical_specification_three")
    private String numericalSpecificationThree;
    /**
    *数值规格4
    */
    @Column(name = "numerical_specification_four")
    private String numericalSpecificationFour;
    /**
    *制造效率(默认为1表示100%)
    */
    @Column(name = "manufacturing_efficiency")
    private Long manufacturingEfficiency;
    /**
    *颜色
    */
    @Column(name = "item_color")
    private Integer itemColor;
    /**
    *顺序
    */
    @Column(name = "item_sort")
    private Integer itemSort;
    /**
    *订单展开品目
    */
    @Column(name = "oder_unfold_item")
    private String oderUnfoldItem;
    /**
    *采购提前期
    */
    @Column(name = "item_purchase_lead_time")
    private String itemPurchaseLeadTime;
    /**
    *包装配送提前期
    */
    @Column(name = "item_distribution_lead_time")
    private String itemDistributionLeadTime;
    /**
    *目标库存MIN
    */
    @Column(name = "target_inventory_min")
    private Integer targetInventoryMin;
    /**
    *库存MIN
    */
    @Column(name = "inventory_min")
    private Integer inventoryMin;
    /**
    *库存MAX
    */
    @Column(name = "inventory_max")
    private Integer inventoryMax;
    /**
    *库存有效期
    */
    @Column(name = "inventory_life")
    private String inventoryLife;
    /**
    *原材料制约标志
    */
    @Column(name = "material_control_mark")
    private String materialControlMark;
    /**
    *库存增减标志
    */
    @Column(name = "Inventory_control_mark")
    private String inventoryControlMark;
    /**
    *低等级代码
    */
    @Column(name = "level_code")
    private Integer levelCode;
    /**
    *关联条件式
    */
    @Column(name = "associated_condition")
    private String associatedCondition;
    /**
    *自动固定外部关联
    */
    @Column(name = "automatic_fixation_external_association")
    private String automaticFixationExternalAssociation;
    /**
    *工作分割数量
    */
    @Column(name = "split_number")
    private Integer splitNumber;
    /**
    *工作分割比率
    */
    @Column(name = "split_ratio")
    private Integer splitRatio;
    /**
    *工作并行数量
    */
    @Column(name = "parallel_number")
    private Integer parallelNumber;
    /**
    *工作批量MIN
    */
    @Column(name = "work_batch_min")
    private Integer workBatchMin;
    /**
    *工作批量MAX
    */
    @Column(name = "work_batch_max")
    private Integer workBatchMax;
    /**
    *工作批量单位
    */
    @Column(name = "batch_unit")
    private String batchUnit;
    /**
    *时间固定级别(结束)
    */
    @Column(name = "time_fixed_level_over")
    private Integer timeFixedLevelOver;
    /**
    *确保分割之前的数量(默认为否)
    */
    @Column(name = "before_splitting")
    private String beforeSplitting;
    /**
    *时间固定级别(开始生产)
    */
    @Column(name = "time_fixed_level_begin")
    private Integer timeFixedLevelBegin;
    /**
    *时间固定级别(确定)
    */
    @Column(name = "time_fixed_level_sure")
    private Integer timeFixedLevelSure;
    /**
    *时间固定级别(指示完毕)
    */
    @Column(name = "time_fixed_level_finish")
    private Integer timeFixedLevelFinish;
    /**
    *时间固定级别(固定)
    */
    @Column(name = "time_fixed_level_fixed")
    private Integer timeFixedLevelFixed;
    /**
    *时间固定级别(计划完毕)
    */
    @Column(name = "time_fixed_level_end")
    private Integer timeFixedLevelEnd;
    /**
    *数量固定级别(结束)
    */
    @Column(name = "number_fixed_level_over")
    private Integer numberFixedLevelOver;
    /**
    *输入指令
    */
    @Column(name = "in_instruction")
    private String inInstruction;
    /**
    *输出指令
    */
    @Column(name = "out_instruction")
    private String outInstruction;
    /**
    *数量固定级别(开始生产)
    */
    @Column(name = "number_fixed_level_begin")
    private Integer numberFixedLevelBegin;
    /**
    *数量固定级别(确定)
    */
    @Column(name = "number_fixed_level_sure")
    private Integer numberFixedLevelSure;
    /**
    *数量固定级别(指示完毕)
    */
    @Column(name = "number_fixed_level_finish")
    private Integer numberFixedLevelFinish;
    /**
    *数量固定级别(固定)
    */
    @Column(name = "number_fixed_level_fixed")
    private Integer numberFixedLevelFixed;
    /**
    *数量固定级别(计划完毕)
    */
    @Column(name = "number_fixed_level_end")
    private Integer numberFixedLevelEnd;
    /**
    *数量固定级别(分割工作)
    */
    @Column(name = "number_fixed_level_spilt")
    private Integer numberFixedLevelSpilt;
    /**
    *自动生成中间产品标志(默认为否)
    */
    @Column(name = "automatic_generation_intermediate_mark")
    private String automaticGenerationIntermediateMark;
    /**
    *订单
    */
    @Column(name = "item_order")
    private String itemOrder;
    /**
    *工序
    */
    @Column(name = "item_operation")
    private String itemOperation;
    /**
    *备注
    */
    @Column(name = "item_note")
    private String itemNote;


}
