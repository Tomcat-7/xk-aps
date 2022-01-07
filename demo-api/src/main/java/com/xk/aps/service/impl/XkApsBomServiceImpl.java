package com.xk.aps.service.impl;

import com.xk.aps.model.dto.XkApsBomDto;
import com.xk.aps.model.entity.XkApsAttendanceEntity;
import com.xk.aps.service.IXkApsBomService;
import com.xk.framework.common.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xk.aps.dao.XkApsBomRepository;
import com.xk.aps.model.entity.XkApsBomEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
* 描述：一键生成单表模块服务实现层
* @author xk
* @date 2021-12-28
*/
@Service
public class XkApsBomServiceImpl implements IXkApsBomService {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private XkApsBomRepository xkApsBomRepository;


    /**
    * 保存
    *
    * @param formData 保存或者更新的实体
    * @return XkApsBomDto
    */
    @Override
    @Transactional
    public XkApsBomDto save(XkApsBomDto formData) {
        try {
            XkApsBomEntity xkApsBomEntity = new XkApsBomEntity();
            if (formData != null && !StringUtils.isEmpty(formData.getId())) {
                xkApsBomEntity = xkApsBomRepository.xkFindById(formData.getId());
            }

            BeanMapperUtils.map(formData, xkApsBomEntity);
            // 保存
            return BeanMapperUtils.map(xkApsBomRepository.saveAndFlush(xkApsBomEntity), XkApsBomDto.class);
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
    * @return XkApsBomDto
    */
    @Override
    @Transactional(readOnly = true)
    public XkApsBomDto getById(String id) {
        try {
            XkApsBomEntity xkApsBomEntity = xkApsBomRepository.xkFindById(id);
            if (xkApsBomEntity == null) {
            return null;
            }
            return BeanMapperUtils.map(xkApsBomEntity, XkApsBomDto.class);
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
            xkApsBomRepository.removeMuiltByIds(ids);
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
             xkApsBomRepository.remove(id);
        } catch (Exception e) {
            logger.error("删除数据失败,{}",e);
            e.printStackTrace();
            throw new ServiceException("删除数据失败", CommonErrorCode.DELETE_ERROR);
        }
    }

    /**
     * 获取Bom表所有信息
     * @return PageDto {@code<XkApsBomDto>}
     */
    //@Cacheable(value = {"Bom"}, key = "#root.method.name",sync = true)
    @Override
    public List<XkApsBomDto> listAll() {
        try {
            List<XkApsBomEntity> bomEntities = xkApsBomRepository.findAll();
            if (bomEntities == null) {
                return null;
            }
            List<XkApsBomDto> collect = bomEntities.stream().map((item) -> {
                XkApsBomDto XkApsBomDto = BeanMapperUtils.map(item, XkApsBomDto.class);
                return XkApsBomDto;
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
    * @return PageDto {@code<XkApsBomDto>}
    */
    @Override
    @Transactional(readOnly = true)
    public PageDto<XkApsBomDto> page(PageQueryDto<XkApsBomEntity> pageDto) {
        try {
            Page<XkApsBomEntity> pageData = xkApsBomRepository.queryPage(pageDto);
            if (pageData == null || pageData.getContent() == null || pageData.getContent().size() <= 0) {
                return null;
            }
            List<XkApsBomDto> lists = BeanMapperUtils.mapList(pageData.getContent(), XkApsBomEntity.class,
                XkApsBomDto.class);

            // 设置当前的记录，总记录数，总页数，当前页
            return new PageDto<XkApsBomDto>(lists, pageData.getTotalElements(), pageData.getTotalPages(),pageData.getNumber());
        } catch (Exception e) {
            logger.error("查询数据失败,{}",e);
            e.printStackTrace();
            throw new ServiceException("查询数据失败", CommonErrorCode.SELECT_ERROR);
        }
    }



}

