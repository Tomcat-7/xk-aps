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
@Table(name="xk_aps_calendar")
public class XkApsCalendarEntity extends BaseEntity  {

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
    *生产时间(例如周一->周五,全日期)
    */
    @Column(name = "work_date")
    private String workDate;
    /**
    *出勤模式代码
    */
    @Column(name = "attendance_code")
    private String attendanceCode;
    /**
    *出勤模式优先级
    */
    @Column(name = "calendar_priority")
    private Integer calendarPriority;
    /**
    *备注
    */
    @Column(name = "calendar_note")
    private String calendarNote;

}
