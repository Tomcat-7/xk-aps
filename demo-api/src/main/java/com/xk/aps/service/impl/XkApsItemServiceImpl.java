package com.xk.aps.service.impl;

import com.xk.aps.model.dto.XkApsItemDto;
import com.xk.aps.service.IXkApsItemService;
import com.xk.framework.common.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xk.aps.dao.XkApsItemRepository;
import com.xk.aps.model.entity.XkApsItemEntity;

import java.util.List;

/**
* 描述：一键生成单表模块服务实现层
* @author xk
* @date 2021-12-28
*/
@Service
public class XkApsItemServiceImpl implements IXkApsItemService {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private XkApsItemRepository xkApsItemRepository;


    /**
    * 保存
    *
    * @param formData 保存或者更新的实体
    * @return XkApsItemDto
    */
    @Override
    @Transactional
    public XkApsItemDto save(XkApsItemDto formData) {
        try {
            XkApsItemEntity xkApsItemEntity = new XkApsItemEntity();
            if (formData != null && !StringUtils.isEmpty(formData.getId())) {
                xkApsItemEntity = xkApsItemRepository.xkFindById(formData.getId());
            }

            BeanMapperUtils.map(formData, xkApsItemEntity);
            // 保存
            return BeanMapperUtils.map(xkApsItemRepository.saveAndFlush(xkApsItemEntity), XkApsItemDto.class);
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
    * @return XkApsItemDto
    */
    @Override
    @Transactional(readOnly = true)
    public XkApsItemDto getById(String id) {
        try {
            XkApsItemEntity xkApsItemEntity = xkApsItemRepository.xkFindById(id);
            if (xkApsItemEntity == null) {
            return null;
            }
            return BeanMapperUtils.map(xkApsItemEntity, XkApsItemDto.class);
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
            xkApsItemRepository.removeMuiltByIds(ids);
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
             xkApsItemRepository.remove(id);
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
    * @return PageDto {@code<XkApsItemDto>}
    */
    @Override
    @Transactional(readOnly = true)
    public PageDto<XkApsItemDto> page(PageQueryDto<XkApsItemEntity> pageDto) {
        try {
            Page<XkApsItemEntity> pageData = xkApsItemRepository.queryPage(pageDto);
            if (pageData == null || pageData.getContent() == null || pageData.getContent().size() <= 0) {
                return null;
            }
            List<XkApsItemDto> lists = BeanMapperUtils.mapList(pageData.getContent(), XkApsItemEntity.class,
                XkApsItemDto.class);

            // 设置当前的记录，总记录数，总页数，当前页
            return new PageDto<XkApsItemDto>(lists, pageData.getTotalElements(), pageData.getTotalPages(),pageData.getNumber());
        } catch (Exception e) {
            logger.error("查询数据失败,{}",e);
            e.printStackTrace();
            throw new ServiceException("查询数据失败", CommonErrorCode.SELECT_ERROR);
        }
    }



}

