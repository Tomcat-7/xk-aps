package com.xk.aps.service.impl;

import com.xk.aps.model.dto.XkApsCalendarDto;
import com.xk.aps.model.dto.XkApsItemDto;
import com.xk.aps.model.entity.XkApsCalendarEntity;
import com.xk.aps.service.IXkApsItemService;
import com.xk.common.core.user.model.entity.CapUserEntity;
import com.xk.framework.common.*;

import com.xk.framework.jpa.specification.SimpleSpecificationBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xk.aps.dao.XkApsItemRepository;
import com.xk.aps.model.entity.XkApsItemEntity;

import java.lang.annotation.ElementType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                if (xkApsItemEntity == null){
                    xkApsItemEntity = new XkApsItemEntity();
                }
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
     * 获取所有品目表信息
     */
    //@Cacheable(value = {"Item"}, key = "#root.method.name",sync = true)
    @Override
    public List<XkApsItemDto> listAll() {
        try {
            List<XkApsItemEntity> itemEntities = xkApsItemRepository.findAll();
            if (itemEntities == null) {
                return null;
            }
            List<XkApsItemDto> collect = itemEntities.stream().map((item) -> {
                XkApsItemDto xkApsItemDto = BeanMapperUtils.map(item, XkApsItemDto.class);
                return xkApsItemDto;
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

    /**
     * 根据品目代码获得品目名称
     * @param objectCode
     * @return
     */
    @Override
    public String getObjectName(String objectCode){
        try {
            SimpleSpecificationBuilder<XkApsItemEntity> ssb1 = new SimpleSpecificationBuilder<XkApsItemEntity>();
            ssb1.add("item_code",Constants.OPER_EQ,objectCode);;
            List<XkApsItemEntity> itemEntities=xkApsItemRepository.findAll(ssb1.generateSpecification());
            if( itemEntities!=null && itemEntities.size()>0){
                return itemEntities.get(0).getItemName();
            }else {
                return null;
            }
        } catch (Exception e) {
            logger.error("查询数据失败,{}",e);
            e.printStackTrace();
            throw new ServiceException("查询数据失败", CommonErrorCode.SELECT_ERROR);
        }
    }

    @Override
    public void refresh(List<XkApsItemDto> list) {
        try {
            if (list != null && list.size() != 0){
                list.forEach((formData)->{
                    XkApsItemDto curItemDto = new XkApsItemDto();
                    curItemDto.setId(formData.getId());
                    if (formData.getProject() == null && formData.getProject().equalsIgnoreCase("")){
                        curItemDto.setProject("1");
                    } else {
                        curItemDto.setProject(formData.getProject());
                    }
                    curItemDto.setItemCode(formData.getItemCode());
                    curItemDto.setItemName(formData.getItemName());
                    this.save(curItemDto);
                });
            }
        } catch (Exception e) {
            logger.error("更新数据失败,{}",e);
            e.printStackTrace();
            throw new ServiceException("更新数据失败", CommonErrorCode.SELECT_ERROR);
        }
    }
}
