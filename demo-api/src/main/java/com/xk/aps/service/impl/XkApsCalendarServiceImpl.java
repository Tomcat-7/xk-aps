package com.xk.aps.service.impl;


import com.xk.aps.dao.XkApsCalendarRepository;
import com.xk.aps.model.dto.XkApsCalendarDto;
import com.xk.aps.service.IXkApsCalendarService;
import com.xk.framework.common.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xk.aps.model.entity.XkApsCalendarEntity;

import java.util.List;

/**
* 描述：一键生成单表模块服务实现层
* @author xk
* @date 2021-12-28
*/
@Service
public class XkApsCalendarServiceImpl implements IXkApsCalendarService {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private XkApsCalendarRepository xkApsCalendarRepository;


    /**
    * 保存
    *
    * @param formData 保存或者更新的实体
    * @return XkApsCalendarDto
    */
    @Override
    @Transactional
    public XkApsCalendarDto save(XkApsCalendarDto formData) {
        try {
            XkApsCalendarEntity xkApsCalendarEntity = new XkApsCalendarEntity();
            if (formData != null && !StringUtils.isEmpty(formData.getId())) {
                xkApsCalendarEntity = xkApsCalendarRepository.xkFindById(formData.getId());
            }

            BeanMapperUtils.map(formData, xkApsCalendarEntity);
            // 保存
            return BeanMapperUtils.map(xkApsCalendarRepository.saveAndFlush(xkApsCalendarEntity), XkApsCalendarDto.class);
        } catch (Exception e) {
            logger.error("保存数据失败,{}",e);
            e.printStackTrace();
            throw new ServiceException("保存数据失败", CommonErrorCode.SAVE_ERROR);
        }
    }

    /**
    * 获取一键生成单表模块信息
    *
    * @param id 主键id
    * @return XkApsCalendarDto
    */
    @Override
    @Transactional(readOnly = true)
    public XkApsCalendarDto getById(String id) {
        try {
            XkApsCalendarEntity xkApsCalendarEntity = xkApsCalendarRepository.xkFindById(id);
            if (xkApsCalendarEntity == null) {
            return null;
            }
            return BeanMapperUtils.map(xkApsCalendarEntity, XkApsCalendarDto.class);
        } catch (Exception e) {
            logger.error("查询数据失败,{}",e);
            e.printStackTrace();
            throw new ServiceException("查询数据失败", CommonErrorCode.SELECT_ERROR);
        }
    }
    /**
    * 删除多个一键生成单表模块信息
    *
    * @param ids 删除的id
    */
    @Override
    @Transactional
    public void removeMulti(String ids) {
        try {
            xkApsCalendarRepository.removeMuiltByIds(ids);
        } catch (Exception e) {
            logger.error("删除数据失败,{}",e);
            e.printStackTrace();
            throw new ServiceException("删除数据失败", CommonErrorCode.DELETE_ERROR);
         }
    }

    /**
    * 删除单个一键生成单表模块信息
    *
    * @param id 一键生成单表模块编号
    */
    @Override
    @Transactional
    public void remove(String id) {
        try {
             xkApsCalendarRepository.remove(id);
        } catch (Exception e) {
            logger.error("删除数据失败,{}",e);
            e.printStackTrace();
            throw new ServiceException("删除数据失败", CommonErrorCode.DELETE_ERROR);
        }
    }

    /**
    * 分页获取一键生成单表模块信息
    *
    * @param pageDto 分页的数据
    * @return PageDto {@code<XkApsCalendarDto>}
    */
    @Override
    @Transactional(readOnly = true)
    public PageDto<XkApsCalendarDto> page(PageQueryDto<XkApsCalendarEntity> pageDto) {
        try {
            Page<XkApsCalendarEntity> pageData = xkApsCalendarRepository.queryPage(pageDto);
            if (pageData == null || pageData.getContent() == null || pageData.getContent().size() <= 0) {
                return null;
            }
            List<XkApsCalendarDto> lists = BeanMapperUtils.mapList(pageData.getContent(), XkApsCalendarEntity.class,
                XkApsCalendarDto.class);

            // 设置当前的记录，总记录数，总页数，当前页
            return new PageDto<XkApsCalendarDto>(lists, pageData.getTotalElements(), pageData.getTotalPages(),pageData.getNumber());
        } catch (Exception e) {
            logger.error("查询数据失败,{}",e);
            e.printStackTrace();
            throw new ServiceException("查询数据失败", CommonErrorCode.SELECT_ERROR);
        }
    }



}

