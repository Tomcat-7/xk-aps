package com.xk.aps.service;

import com.xk.aps.model.dto.XkApsAttendanceDto;
import com.xk.framework.common.PageDto;
import com.xk.framework.common.PageQueryDto;
import com.xk.aps.model.dto.XkApsBomDto;
import com.xk.aps.model.entity.XkApsBomEntity;

import java.util.List;

/**
* 描述：一键生成单表模块 服务实现层接口
* @author xk
* @date 2021-12-28
*/
public interface IXkApsBomService{

    /**
    * 分页查询一键生成单表模块
    *
    * @param pageQueryDto
    * @return
    */
    PageDto<XkApsBomDto> page(PageQueryDto<XkApsBomEntity> pageQueryDto);

    /**
    * 保存一键生成单表模块信息
    *
    * @param formData
    * @return
    */
    XkApsBomDto save(XkApsBomDto formData);

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
   XkApsBomDto getById(String id);

    /**
    * 根据id删除一键生成单表模块
    *
    * @param id 主键
    */
   void remove(String id);

    List<XkApsBomDto> listAll();

    void refresh(List<XkApsBomDto> list);
}
