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
@Table(name="xk_aps_attendance")
public class XkApsAttendanceEntity extends BaseEntity  {

    /**
    *项目工程
    */
    @Column(name = "project")
    private String project;
    /**
    *出勤模式代码
    */
    @Column(name = "attendance_code")
    private String attendanceCode;
    /**
    *出勤时间
    */
    @Column(name = "attendance_time")
    private String attendanceTime;
    /**
    *备注
    */
    @Column(name = "attendance_note")
    private String attendanceNote;

}
