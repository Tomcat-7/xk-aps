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

//        //获取所有订单表数据 并将所有订单表数据的序号置为0;
//        List<XkApsOrderEntity> orderEntities = xkApsOrderRepository.findAll();
//        List<XkApsOrderDto> orderDtos = orderEntities.stream().map((item) -> {
//            XkApsOrderDto xkApsOrderDto = BeanMapperUtils.map(item, XkApsOrderDto.class);
//            xkApsOrderDto.setOrderColor(0);
//            xkApsOrderDto.setOrderStatus("未分派");
//            this.save(xkApsOrderDto);
//            return xkApsOrderDto;
//        }).collect(Collectors.toList());

        //获取所有bom表数据
        List<XkApsBomEntity> bomEntityList = xkApsBomRepository.findAll();
        List<XkApsBomDto> bomDtos = bomEntityList.stream().map((item) -> {
            XkApsBomDto xkApsBomDto = BeanMapperUtils.map(item, XkApsBomDto.class);
            return xkApsBomDto;
        }).collect(Collectors.toList());

        //获取所有操作表数据
        List<XkApsOperationEntity> operationEntities = xkApsOperationRepository.findAll();
        List<XkApsOperationDto> operationDtos = operationEntities.stream().map((item) -> {
            XkApsOperationDto xkApsOperationDto = BeanMapperUtils.map(item, XkApsOperationDto.class);
            return xkApsOperationDto;
        }).collect(Collectors.toList());
        try {

            String[] split = ids.split(",");
            HashSet<String> hashSet= new HashSet<>();
            List<XkApsOrderDto> xkApsOrderDtoList =new ArrayList<>();

            for(int i=0;i<split.length;i++){
                XkApsOrderDto xkApsOrderDto = new XkApsOrderDto();
                XkApsOrderEntity xkApsOrderEntity = xkApsOrderRepository.xkFindById(split[i]);
                xkApsOrderEntity.setOrderColor(0);
                xkApsOrderEntity.setOrderStatus("未分派");
                xkApsOrderRepository.saveAndFlush(xkApsOrderEntity);
                BeanUtils.copyProperties(xkApsOrderEntity,xkApsOrderDto);
                xkApsOrderDtoList.add(xkApsOrderDto);

                hashSet.add(xkApsOrderEntity.getOrderCode());
            }

            //获取所有show表数据
            List<XkApsShowEntity> showEntities = xkApsShowRepository.findAll();
            List<XkApsShowDto> showDtos = showEntities.stream().map((item) -> {
                XkApsShowDto xkApsShowDto = BeanMapperUtils.map(item, XkApsShowDto.class);
                if(hashSet.contains(xkApsShowDto.getOrderCode())){
                    xkApsShowService.remove(xkApsShowDto.getId());
                }
                return xkApsShowDto;
            }).collect(Collectors.toList());

            if(xkApsOrderDtoList !=null && xkApsOrderDtoList.size()>0){
                showDataAfterSchedule(resourceDtos, bomDtos, showDtos, operationDtos, xkApsOrderDtoList);
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

    private List<XkApsOrderDto> showDataAfterSchedule(List<XkApsResourceDto> resourceDtos, List<XkApsBomDto> bomDtos, List<XkApsShowDto> showDtos, List<XkApsOperationDto> operationDtos, List<XkApsOrderDto> scheduling) {
        //将资源表 按照资源Code分类 得到每种资源的hashmap
        Map<Integer, XkApsResourceDto> resourceMap = new HashMap<>();
        for (int i = 0; i < resourceDtos.size(); i++) {
            resourceMap.put(resourceDtos.get(i).getResourceCode().hashCode() , resourceDtos.get(i));
        }

        //将BOM表分类 得到每种品目的生产BOM
        Map<Integer , List<XkApsBomDto>> BomHashMap = new HashMap<>();
        List<List<double[][]>> list = new ArrayList<>();
        //得到所有的加工数据
        AtomicInteger colorSort = new AtomicInteger(1);
        scheduling.stream().forEach((orderDto)->{
            orderDto.setOrderColor(colorSort.getAndIncrement());
            this.save(orderDto);

            List<XkApsBomDto> bomDtoList = new ArrayList<>();
            //获取每一个订单对应工件的加工机器与对应加工时间数据
            //将该订单品目的所有加工工序找出来
            for (int i = 0; i < bomDtos.size(); i++) {
                if(bomDtos.get(i).getItemCode().equalsIgnoreCase(orderDto.getItemCode())){
                    bomDtoList.add(bomDtos.get(i));
                }
            }
            //将加工工序按先后顺序排序
            sort(bomDtoList);
            BomHashMap.put(orderDto.getOrderColor() , bomDtoList);
            //将bomDtoList 按照加工顺序分类  这里因为每一步加工工序可能存在多个bom信息  表示这一个工序可以在多态设备加工  但加工时间可能不同
            Map<Integer, List<XkApsBomDto>> listMap = bomDtoList.stream().collect(Collectors.groupingBy(XkApsBomDto::getBeforeCode));

            //doublesItem这里面记录的是每一个工序的加工信息
            List<List<Double>> doublesItem = new ArrayList<>();

            //对一个工序在不同加工设备上的情况遍历
            for (Map.Entry<Integer, List<XkApsBomDto>> entry : listMap.entrySet()){
                List<Double> tmpResourceDouble = new ArrayList();
                //获得加工时间  单位分钟
                List<XkApsBomDto> bomDtoList1 = entry.getValue();
                //遍历每一个加工信息
                for (int i = 0; i < bomDtoList1.size(); i++) {
                    //获得加工时间  单位分钟
                    double timeMil = bomDtoList1.get(i).getBeforeTime() + bomDtoList1.get(i).getManufacturingTime() + bomDtoList1.get(i).getAfterTime();
                    //获得机器号码
                    String objectCode = bomDtoList1.get(i).getObjectCode();
                    //得到这个机器的hash值 作为唯一标识
                    tmpResourceDouble.add((double)objectCode.hashCode());
                    tmpResourceDouble.add(timeMil);
                }
                //把这个工序的信息加入doublesItem
                doublesItem.add(tmpResourceDouble);
            }

            //在每一个订单数据中加入工序数据  工序数量个数为bom表信息中的最大序号
            int maxLength = 0;
            for (int i = 0; i < bomDtoList.size(); i++) {
                maxLength = Math.max(bomDtoList.get(i).getBeforeCode() , maxLength);
            }

            //将每一个工序信息从doublesItem 转入 bomInfo
            List<double[][]> bomInfo = new ArrayList<>(doublesItem.size());
            for (int i = 0; i < doublesItem.size(); i++) {
                //doubles里面存每一个工序的信息
                double[][] doubles = new double[doublesItem.get(i).size()/2][2];
                int index = 0;
                for (int j = 0; j < doublesItem.get(i).size(); j=j+2) {
                    doubles[index][0] = doublesItem.get(i).get(j);
                    doubles[index++][1] = doublesItem.get(i).get(j + 1);
                }
                bomInfo.add(doubles);
            }
            list.add(bomInfo);
        });
        GASchedule gaSchedule = new GASchedule();
        GAResult result = gaSchedule.Schedule(list);
        afterGAScheduleHandle(scheduling, resourceMap, BomHashMap, result);

        return scheduling;
    }

    private void afterGAScheduleHandle(List<XkApsOrderDto> scheduling, Map<Integer, XkApsResourceDto> resourceMap, Map<Integer, List<XkApsBomDto>> bomHashMap, GAResult result) {
        double[] schedule = result.getBestOne();
        List<ArrayList<Double>> workEndList = result.getWorkEndList();

        //当前来到第i个工序
        int[] curCount = new int[scheduling.size()];
        //订单一共有j个工序
        int[] allCount = new int[scheduling.size()];
        for (int i = 0; i < (schedule.length >> 1); i++) {
            allCount[(int)schedule[i]-1]++;
        }
        Date mostEarlyTime = scheduling.get(0).getMostEarlyTime();
        for (int i = 0; i < (schedule.length >> 1); i++) {
            int finalI = i;
            XkApsOrderDto tmpOrderDto = new XkApsOrderDto();
            List<XkApsBomDto> bomDtoList = new ArrayList<>();
            List<Double> endTime = new ArrayList<>();
            for (int j = 0; j < scheduling.size(); j++) {
                if(schedule[i] == scheduling.get(j).getOrderColor()){
                    bomDtoList = bomHashMap.get(scheduling.get(j).getOrderColor());
                }
            }
            curCount[(int)schedule[i]-1]++;
            //找到这个基因对应哪一个订单
            for (int k = 0; k < scheduling.size(); k++) {
                if(scheduling.get(k).getOrderColor() == (int)schedule[finalI]){
                    tmpOrderDto = scheduling.get(k);
                    //找到这个订单对应的工序结束时间
                    endTime = workEndList.get(k);
                }
            }
            XkApsResourceDto xkApsResourceDto = new XkApsResourceDto();
            if(bomDtoList != null && bomDtoList.size() > 0){
                for (int j = 0; j < bomDtoList.size(); j++) {
                    if(bomDtoList.get(j).getBeforeCode() == curCount[(int)schedule[i]-1] && bomDtoList.get(j).getObjectCode().hashCode() == (int)schedule[i + (schedule.length >> 1)]){

                        xkApsResourceDto = resourceMap.get((int)schedule[i + (schedule.length >> 1)]);

                        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
                        //计算该工序的加工时间(分钟)
                        double timeMil = bomDtoList.get(j).getBeforeTime() + bomDtoList.get(j).getManufacturingTime() + bomDtoList.get(j).getAfterTime();

                        //开始写入show数据  对show数据填充数据
                        XkApsShowDto xkApsShowDto = new XkApsShowDto();

                        xkApsShowDto.setProject(tmpOrderDto.getProject());
                        xkApsShowDto.setOrderCode(tmpOrderDto.getOrderCode());
                        xkApsShowDto.setResourceCode(xkApsResourceDto.getResourceCode());
                        xkApsShowDto.setResourceName(xkApsResourceDto.getResourceName());
                        xkApsShowDto.setOrderNumber(tmpOrderDto.getOrderNumber());


                        //每一个订单的第一个工序 设置开始时间
                        long tmpDate = (long) ((endTime.get(curCount[(int)schedule[i]-1] - 1) - timeMil)*60*1000);
                        try {
                            xkApsShowDto.setStartTime(simpleDateFormat.parse(simpleDateFormat.format(mostEarlyTime.getTime() + tmpDate)));
                            if(curCount[(int)schedule[i]-1] == 1){
                                tmpOrderDto.setStartTime(simpleDateFormat.parse(simpleDateFormat.format(mostEarlyTime.getTime() + tmpDate)));
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        //获取结束时间
                        long workTime = mostEarlyTime.getTime() + (long)(endTime.get(curCount[(int)schedule[i]-1] - 1)*60*1000);

                        //写入结束时间
                        try {
                            xkApsShowDto.setEndTime(simpleDateFormat.parse(simpleDateFormat.format(workTime)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        xkApsShowDto.setOrderDateDelivery(tmpOrderDto.getOrderDateDelivery());
                        xkApsShowDto.setItemCode(tmpOrderDto.getItemCode());
                        xkApsShowDto.setShowWork(tmpOrderDto.getItemCode());
                        xkApsShowDto.setBomNote(bomDtoList.get(j).getBomNote());
                        //写完show表数据后, 将订单表中复用时间数据更新
                        tmpOrderDto.setNumericalSpecificationFour(xkApsShowDto.getEndTime());
                        tmpOrderDto.setOrderStatus("已分派");
                        //来到该订单最后一个工序 , 设置订单表结束时间
                        if(curCount[(int)schedule[i]-1] == allCount[(int)schedule[i]-1]){
                            tmpOrderDto.setEndTime(xkApsShowDto.getEndTime());
                        }
                        //更新订单表信息
                        this.save(tmpOrderDto);
                        //更新show表信息
                        xkApsShowService.save(xkApsShowDto);
                    }
                }
            }
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

