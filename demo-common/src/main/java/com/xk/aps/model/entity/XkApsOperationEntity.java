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
@Table(name="xk_aps_operation")
public class XkApsOperationEntity extends BaseEntity  {

    /**
    *项目工程
    */
    @Column(name = "project")
    private String project;
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
    *组种类
    */
    @Column(name = "operation_group")
    private String operationGroup;
    /**
    *颜色
    */
    @Column(name = "operation_color")
    private Integer operationColor;
    /**
    *顺序
    */
    @Column(name = "operation_sort")
    private Integer operationSort;
    /**
    *备注
    */
    @Column(name = "operation_note")
    private String operationNote;
}
