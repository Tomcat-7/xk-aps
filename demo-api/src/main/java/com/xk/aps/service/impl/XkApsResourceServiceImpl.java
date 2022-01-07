package com.xk.aps.service.impl;


import com.xk.aps.model.dto.XkApsOrderDto;
import com.xk.aps.model.entity.XkApsOrderEntity;
import com.xk.framework.common.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xk.aps.dao.XkApsResourceRepository;
import com.xk.aps.model.dto.XkApsResourceDto;
import com.xk.aps.model.entity.XkApsResourceEntity;
import com.xk.aps.service.IXkApsResourceService;

import java.util.List;
import java.util.stream.Collectors;

/**
* 描述：一键生成单表模块服务实现层
* @author xk
* @date 2021-12-28
*/
@Service
public class XkApsResourceServiceImpl implements IXkApsResourceService {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private XkApsResourceRepository xkApsResourceRepository;


    /**
    * 保存
    *
    * @param formData 保存或者更新的实体
    * @return XkApsResourceDto
    */
    @Override
    @Transactional
    public XkApsResourceDto save(XkApsResourceDto formData) {
        try {
            XkApsResourceEntity xkApsResourceEntity = new XkApsResourceEntity();
            if (formData != null && !StringUtils.isEmpty(formData.getId())) {
                xkApsResourceEntity = xkApsResourceRepository.xkFindById(formData.getId());
            }

            BeanMapperUtils.map(formData, xkApsResourceEntity);
            // 保存
            return BeanMapperUtils.map(xkApsResourceRepository.saveAndFlush(xkApsResourceEntity), XkApsResourceDto.class);
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
    * @return XkApsResourceDto
    */
    @Override
    @Transactional(readOnly = true)
    public XkApsResourceDto getById(String id) {
        try {
            XkApsResourceEntity xkApsResourceEntity = xkApsResourceRepository.xkFindById(id);
            if (xkApsResourceEntity == null) {
            return null;
            }
            return BeanMapperUtils.map(xkApsResourceEntity, XkApsResourceDto.class);
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
            xkApsResourceRepository.removeMuiltByIds(ids);
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
             xkApsResourceRepository.remove(id);
        } catch (Exception e) {
            logger.error("删除数据失败,{}",e);
            e.printStackTrace();
            throw new ServiceException("删除数据失败", CommonErrorCode.DELETE_ERROR);
        }
    }

    /**
     * 获取所有资源表信息
     */
    //@Cacheable(value = {"Resource"}, key = "#root.method.name",sync = true)
    @Override
    public List<XkApsResourceDto> listAll() {
        try {
            List<XkApsResourceEntity> resourceEntities = xkApsResourceRepository.findAll();
            if (resourceEntities == null) {
                return null;
            }
            List<XkApsResourceDto> collect = resourceEntities.stream().map((item) -> {
                XkApsResourceDto xkApsResourceDto = BeanMapperUtils.map(item, XkApsResourceDto.class);
                return xkApsResourceDto;
            }).collect(Collectors.toList());
            return collect;
        } catch (Exception e) {
            logger.error("查询所有数据失败,{}",e);
            e.printStackTrace();
            throw new ServiceException("查询所有数据失败", CommonErrorCode.SELECT_ERROR);
        }
    }

    /**
    * 分页获取一键生成单表模块信息
    *
    * @param pageDto 分页的数据
    * @return PageDto {@code<XkApsResourceDto>}
    */
    @Override
    @Transactional(readOnly = true)
    public PageDto<XkApsResourceDto> page(PageQueryDto<XkApsResourceEntity> pageDto) {
        try {
            Page<XkApsResourceEntity> pageData = xkApsResourceRepository.queryPage(pageDto);
            if (pageData == null || pageData.getContent() == null || pageData.getContent().size() <= 0) {
                return null;
            }
            List<XkApsResourceDto> lists = BeanMapperUtils.mapList(pageData.getContent(), XkApsResourceEntity.class,
                XkApsResourceDto.class);

            // 设置当前的记录，总记录数，总页数，当前页
            return new PageDto<XkApsResourceDto>(lists, pageData.getTotalElements(), pageData.getTotalPages(),pageData.getNumber());
        } catch (Exception e) {
            logger.error("查询数据失败,{}",e);
            e.printStackTrace();
            throw new ServiceException("查询数据失败", CommonErrorCode.SELECT_ERROR);
        }
    }



}

