package com.xk.aps.service.impl;


import com.xk.aps.model.dto.XkApsItemDto;
import com.xk.aps.model.dto.XkApsOperationDto;
import com.xk.aps.model.entity.XkApsItemEntity;
import com.xk.framework.common.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xk.aps.dao.XkApsOperationRepository;
import com.xk.aps.model.entity.XkApsOperationEntity;
import com.xk.aps.service.IXkApsOperationService;

import java.util.List;
import java.util.stream.Collectors;

/**
* 描述：一键生成单表模块服务实现层
* @author xk
* @date 2021-12-28
*/
@Service
public class XkApsOperationServiceImpl implements IXkApsOperationService {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private XkApsOperationRepository xkApsOperationRepository;


    /**
    * 保存
    *
    * @param formData 保存或者更新的实体
    * @return XkApsOperationDto
    */
    @Override
    @Transactional
    public XkApsOperationDto save(XkApsOperationDto formData) {
        try {
            XkApsOperationEntity xkApsOperationEntity = new XkApsOperationEntity();
            if (formData != null && !StringUtils.isEmpty(formData.getId())) {
                xkApsOperationEntity = xkApsOperationRepository.xkFindById(formData.getId());
            }

            BeanMapperUtils.map(formData, xkApsOperationEntity);

            if(xkApsOperationEntity.getOperationSort() == null){
                List<XkApsOperationEntity> all = xkApsOperationRepository.findAll();
                xkApsOperationEntity.setOperationSort(all.size() + 1);
            }

            // 保存
            return BeanMapperUtils.map(xkApsOperationRepository.saveAndFlush(xkApsOperationEntity), XkApsOperationDto.class);
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
    * @return XkApsOperationDto
    */
    @Override
    @Transactional(readOnly = true)
    public XkApsOperationDto getById(String id) {
        try {
            XkApsOperationEntity xkApsOperationEntity = xkApsOperationRepository.xkFindById(id);
            if (xkApsOperationEntity == null) {
            return null;
            }
            return BeanMapperUtils.map(xkApsOperationEntity, XkApsOperationDto.class);
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
            xkApsOperationRepository.removeMuiltByIds(ids);
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
             xkApsOperationRepository.remove(id);
        } catch (Exception e) {
            logger.error("删除数据失败,{}",e);
            e.printStackTrace();
            throw new ServiceException("删除数据失败", CommonErrorCode.DELETE_ERROR);
        }
    }
    /**
     * 获取所有品目表信息
     */
    //@Cacheable(value = {"Operation"}, key = "#root.method.name",sync = true)
    @Override
    public List<XkApsOperationDto> listAll() {
        try {
            List<XkApsOperationEntity> operationEntities = xkApsOperationRepository.findAll();
            if (operationEntities == null) {
                return null;
            }
            List<XkApsOperationDto> collect = operationEntities.stream().map((item) -> {
                XkApsOperationDto xkApsOperationDto = BeanMapperUtils.map(item, XkApsOperationDto.class);
                return xkApsOperationDto;
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
    * @return PageDto {@code<XkApsOperationDto>}
    */
    @Override
    @Transactional(readOnly = true)
    public PageDto<XkApsOperationDto> page(PageQueryDto<XkApsOperationEntity> pageDto) {
        try {
            Page<XkApsOperationEntity> pageData = xkApsOperationRepository.queryPage(pageDto);
            if (pageData == null || pageData.getContent() == null || pageData.getContent().size() <= 0) {
                return null;
            }
            List<XkApsOperationDto> lists = BeanMapperUtils.mapList(pageData.getContent(), XkApsOperationEntity.class,
                XkApsOperationDto.class);

            // 设置当前的记录，总记录数，总页数，当前页
            return new PageDto<XkApsOperationDto>(lists, pageData.getTotalElements(), pageData.getTotalPages(),pageData.getNumber());
        } catch (Exception e) {
            logger.error("查询数据失败,{}",e);
            e.printStackTrace();
            throw new ServiceException("查询数据失败", CommonErrorCode.SELECT_ERROR);
        }
    }



}

