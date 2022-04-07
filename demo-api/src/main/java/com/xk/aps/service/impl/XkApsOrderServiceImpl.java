package com.xk.aps.service.impl;


import com.xk.aps.dao.*;
import com.xk.aps.model.dto.*;
import com.xk.aps.model.entity.*;
import com.xk.aps.service.IXkApsOrderService;
import com.xk.aps.utils.ApsSchedule;
import com.xk.aps.utils.ga.GAResult;
import com.xk.aps.utils.ga.GASchedule;
import com.xk.framework.common.*;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.Collections.*;

/**
* 描述：一键生成单表模块服务实现层
* @author xk
* @date 2021-12-28
*/
@Service
public class XkApsOrderServiceImpl implements IXkApsOrderService {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private XkApsOrderRepository xkApsOrderRepository;

    @Autowired
    private ApsSchedule apsSchedule;

    @Autowired
    private XkApsResourceRepository xkApsResourceRepository;

    @Autowired
    private XkApsResourceServiceImpl xkApsResourceService;

    @Autowired
    private XkApsBomRepository xkApsBomRepository;

    @Autowired
    private XkApsShowRepository xkApsShowRepository;

    @Autowired
    private XkApsShowServiceImpl xkApsShowService;

    @Autowired
    private XkApsOperationRepository xkApsOperationRepository;
    @Autowired
    private XkApsOperationServiceImpl xkApsOperationService;

    /**
    * 保存
    *
    * @param formData 保存或者更新的实体
    * @return XkApsOrderDto
    */
    @Override
    @Transactional
    public XkApsOrderDto save(XkApsOrderDto formData) {
        try {
            XkApsOrderEntity xkApsOrderEntity = new XkApsOrderEntity();
            if (formData != null && !StringUtils.isEmpty(formData.getId())) {
                xkApsOrderEntity = xkApsOrderRepository.xkFindById(formData.getId());
            }

            BeanMapperUtils.map(formData, xkApsOrderEntity);
            // 保存
            return BeanMapperUtils.map(xkApsOrderRepository.saveAndFlush(xkApsOrderEntity), XkApsOrderDto.class);
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
    * @return XkApsOrderDto
    */
    @Override
    @Transactional(readOnly = true)
    public XkApsOrderDto getById(String id) {
        try {
            XkApsOrderEntity xkApsOrderEntity = xkApsOrderRepository.xkFindById(id);
            if (xkApsOrderEntity == null) {
            return null;
            }
            return BeanMapperUtils.map(xkApsOrderEntity, XkApsOrderDto.class);
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
            xkApsOrderRepository.removeMuiltByIds(ids);
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
             xkApsOrderRepository.remove(id);
        } catch (Exception e) {
            logger.error("删除数据失败,{}",e);
            e.printStackTrace();
            throw new ServiceException("删除数据失败", CommonErrorCode.DELETE_ERROR);
        }
    }
    /**
     * 获取所有订单表信息
     */
    //@Cacheable(value = {"Order"}, key = "#root.method.name",sync = true)
    @Override
    public List<XkApsOrderDto> listAll() {
        try {
            List<XkApsOrderEntity> orderEntities = xkApsOrderRepository.findAll();
            if (orderEntities == null) {
                return null;
            }
            List<XkApsOrderDto> collect = orderEntities.stream().map((item) -> {
                XkApsOrderDto xkApsOrderDto = BeanMapperUtils.map(item, XkApsOrderDto.class);
                return xkApsOrderDto;
            }).collect(Collectors.toList());
            return collect;
        } catch (Exception e) {
            logger.error("查询所有数据失败,{}",e);
            e.printStackTrace();
            throw new ServiceException("查询所有数据失败", CommonErrorCode.SELECT_ERROR);
        }
    }

    /**
     * 订单排序
     * @param ids
     * @return
     */
    @Override
    public List<XkApsOrderDto> schedule(String ids) {
        //获取所有资源数据
        List<XkApsResourceEntity> resourceEntities = xkApsResourceRepository.findAll();
        List<XkApsResourceDto> resourceDtos = resourceEntities.stream().map((item) -> {
            XkApsResourceDto xkApsResourceDto = BeanMapperUtils.map(item, XkApsResourceDto.class);
            return xkApsResourceDto;
        }).collect(Collectors.toList());

        //获取所有bom表数据
        List<XkApsBomEntity> bomEntityList = xkApsBomRepository.findAll();
        List<XkApsBomDto> bomDtos = bomEntityList.stream().map((item) -> {
            XkApsBomDto xkApsBomDto = BeanMapperUtils.map(item, XkApsBomDto.class);
            return xkApsBomDto;
        }).collect(Collectors.toList());

        //获取所有show表数据
        List<XkApsShowEntity> showEntities = xkApsShowRepository.findAll();
        List<XkApsShowDto> showDtos = showEntities.stream().map((item) -> {
            XkApsShowDto xkApsShowDto = BeanMapperUtils.map(item, XkApsShowDto.class);
            return xkApsShowDto;
        }).collect(Collectors.toList());

        //获取所有操作表数据
        List<XkApsOperationEntity> operationEntities = xkApsOperationRepository.findAll();
        List<XkApsOperationDto> operationDtos = operationEntities.stream().map((item) -> {
            XkApsOperationDto xkApsOperationDto = BeanMapperUtils.map(item, XkApsOperationDto.class);
            return xkApsOperationDto;
        }).collect(Collectors.toList());
        try {

            String[] split = ids.split(",");
            List<XkApsOrderDto> xkApsOrderDtoList =new ArrayList<>();

            for(int i=0;i<split.length;i++){
                XkApsOrderDto xkApsOrderDto = new XkApsOrderDto();
                XkApsOrderEntity xkApsOrderEntity = xkApsOrderRepository.xkFindById(split[i]);

                xkApsOrderEntity.setOrderColor(0);
                xkApsOrderEntity.setOrderStatus("未分派");
                xkApsOrderRepository.saveAndFlush(xkApsOrderEntity);

                BeanUtils.copyProperties(xkApsOrderEntity,xkApsOrderDto);
//                xkApsOrderDto.setOrderColor(i+1);
//                this.save(xkApsOrderDto);
                xkApsOrderDtoList.add(xkApsOrderDto);
            }
            if(xkApsOrderDtoList !=null && xkApsOrderDtoList.size()>0){
                //showDataAfterSchedule(resourceDtos, bomDtos, showDtos, operationDtos, xkApsOrderDtoList);
                return xkApsOrderDtoList;
            }else {
                return null;
            }
        } catch (Exception e) {
            logger.error("排序失败,{}",e);
            e.printStackTrace();
            throw new ServiceException("排序失败", CommonErrorCode.BAD_REQUEST);
        }
    }



    /**
    * 分页获取一键生成单表模块信息
    *
    * @param pageDto 分页的数据
    * @return PageDto {@code<XkApsOrderDto>}
    */
    @Override
    @Transactional(readOnly = true)
    public PageDto<XkApsOrderDto> page(PageQueryDto<XkApsOrderEntity> pageDto) {
        String key = pageDto.getSearchData();
        String searchData = "[{name:\"delFlag\",value:\""+0+"\"},{name:\"like_orderCode\",value:\""+key+"\"}]";
        pageDto.setSortField("createtime");
        pageDto.setSortOrder("DESC");
        pageDto.setSearchData(searchData);
        try {
            Page<XkApsOrderEntity> pageData = xkApsOrderRepository.queryPage(pageDto);
            if (pageData == null || pageData.getContent() == null || pageData.getContent().size() <= 0) {
                return null;
            }
            List<XkApsOrderDto> lists = BeanMapperUtils.mapList(pageData.getContent(), XkApsOrderEntity.class,
                XkApsOrderDto.class);

            // 设置当前的记录，总记录数，总页数，当前页
            return new PageDto<XkApsOrderDto>(lists, pageData.getTotalElements(), pageData.getTotalPages(),pageData.getNumber());
        } catch (Exception e) {
            logger.error("查询数据失败,{}",e);
            e.printStackTrace();
            throw new ServiceException("查询数据失败", CommonErrorCode.SELECT_ERROR);
        }
    }

}

@Data
class Result{
    public double[] bestOne;
    public List<ArrayList<Double>> workEndList;
}

