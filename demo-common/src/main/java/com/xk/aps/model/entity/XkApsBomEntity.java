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
@Table(name="xk_aps_bom")
public class XkApsBomEntity extends BaseEntity  {

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
    *工序代码
    */
    @Column(name = "operation_code")
    private String operationCode;
    /**
    *工序名称
    */
    @Column(name = "operation_name")
    private String operationName;
    /**
    *指令种类(输入，使用，输出)
    */
    @Column(name = "instruction_type")
    private String instructionType;
    /**
    *指令代码(IN:输入,M:使用,OUT:输出)
    */
    @Column(name = "instruction_code")
    private String instructionCode;
    /**
    *品目资源代码
    */
    @Column(name = "object_code")
    private String objectCode;
    /**
    *先行工序编号
    */
    @Column(name = "before_code")
    private String beforeCode;
    /**
    *加工前需要等待时间(分钟单位)
    */
    @Column(name = "before_time")
    private Long beforeTime;
    /**
    *加工前需要的时间(分钟单位)
    */
    @Column(name = "manufacturing_time")
    private Long manufacturingTime;
    /**
    *加工后需要等待时间(分钟单位)
    */
    @Column(name = "after_time")
    private Long afterTime;
    /**
    *接续方法(默认为ES)
    */
    @Column(name = "method")
    private String method;
    /**
    *物料移动时间(分钟单位)
    */
    @Column(name = "move_time")
    private Long moveTime;
    /**
    *废品数量
    */
    @Column(name = "scrapped_number")
    private Integer scrappedNumber;
    /**
    *成品率
    */
    @Column(name = "yield")
    private Long yield;
    /**
    *资源优先度
    */
    @Column(name = "resource_priority")
    private Integer resourcePriority;
    /**
    *必要资源量(默认为1)
    */
    @Column(name = "must_resource")
    private Integer mustResource;
    /**
    *制造效率(默认为1表示100%)
    */
    @Column(name = "manufacturing_efficiency")
    private Long manufacturingEfficiency;
    /**
    *备注
    */
    @Column(name = "bom_note")
    private String bomNote;

}
