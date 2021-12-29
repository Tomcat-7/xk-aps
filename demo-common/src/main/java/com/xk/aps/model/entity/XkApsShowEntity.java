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
@Table(name="xk_aps_show")
public class XkApsShowEntity extends BaseEntity  {

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
    *资源代码
    */
    @Column(name = "resource_code")
    private String resourceCode;
    /**
    *资源名称
    */
    @Column(name = "resource_name")
    private Integer resourceName;
    /**
    *订单数量
    */
    @Column(name = "order_number")
    private Integer orderNumber;
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
    *交货期
    */
    @Column(name = "order_date_delivery")
    private Date orderDateDelivery;
    /**
    *订单品目代码
    */
    @Column(name = "show_work")
    private String showWork;
    /**
    *该资源生产品目代码
    */
    @Column(name = "item_code")
    private String itemCode;
    /**
    *备注
    */
    @Column(name = "bom_note")
    private String bomNote;

}
