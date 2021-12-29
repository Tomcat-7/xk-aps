package com.xk.aps.service;

import com.xk.aps.model.dto.XkApsAttendanceDto;
import com.xk.aps.model.entity.XkApsAttendanceEntity;
import com.xk.framework.common.PageDto;
import com.xk.framework.common.PageQueryDto;


/**
* 描述：一键生成单表模块 服务实现层接口
* @author xk
* @date 2021-12-28
*/
public interface IXkApsAttendanceService{

    /**
    * 分页查询一键生成单表模块
    *
    * @param pageQueryDto
    * @return
    */
    PageDto<XkApsAttendanceDto> page(PageQueryDto<XkApsAttendanceEntity> pageQueryDto);

    /**
    * 保存一键生成单表模块信息
    *
    * @param formData
    * @return
    */
    XkApsAttendanceDto save(XkApsAttendanceDto formData);

    /**
    * 删除一键生成单表模块信息
    *
    * @return
    */
    void removeMulti(String ids);

    /**
    * 根据一键生成单表模块id查询一键生成单表模块信息
    *
    * @param id 主键id
    * @return
    */
   XkApsAttendanceDto getById(String id);

    /**
    * 根据id删除一键生成单表模块
    *
    * @param id 主键
    */
    void remove(String id);

}
