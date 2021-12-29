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
@Table(name="xk_aps_order")
public class XkApsOrderEntity extends BaseEntity  {

    /**
    *项目工程
    */
    @Column(name = "project")
    private String project;
    /**
    *订单代码
    */
    @Column(name = "order_code")
    private String orderCode;
    /**
    *订单种类(0:销售，1:制造，2:采购)
    */
    @Column(name = "order_category")
    private Integer orderCategory;
    /**
    *订单区分(0:录入，1:补充)
    */
    @Column(name = "order_distinguish")
    private Integer orderDistinguish;
    /**
    *品目代码
    */
    @Column(name = "item_code")
    private String itemCode;
    /**
    *最早开始时间
    */
    @Column(name = "most_early_time")
    private Date mostEarlyTime;
    /**
    *交货期
    */
    @Column(name = "order_date_delivery")
    private Date orderDateDelivery;
    /**
    *订单数量
    */
    @Column(name = "order_number")
    private Integer orderNumber;
    /**
    *订单优先级
    */
    @Column(name = "order_priority")
    private Integer orderPriority;
    /**
    *订单客户，如果是补充订单，则客户为-1
    */
    @Column(name = "order_client")
    private String orderClient;
    /**
    *订单颜色
    */
    @Column(name = "order_color")
    private Integer orderColor;
    /**
    *备注
    */
    @Column(name = "order_note")
    private String orderNote;
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
    *交货提前期
    */
    @Column(name = "order_lead_time")
    private String orderLeadTime;
    /**
    *开始时间(排程后生成)
    */
    @Column(name = "start_time")
    private Date startTime;
    /**
    *结束时间(排程后生成)
    */
    @Column(name = "end_time")
    private Date endTime;
    /**
    *订单状态(0:未指定1:开始生产2:结束)
    */
    @Column(name = "order_status")
    private Integer orderStatus;
    /**
    *分派标志(0:未分派，1:已分派)
    */
    @Column(name = "order_dispatch_mark")
    private Integer orderDispatchMark;

}
