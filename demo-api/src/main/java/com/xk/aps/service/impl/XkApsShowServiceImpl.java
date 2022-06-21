package com.xk.aps.service.impl;


import com.xk.aps.dao.XkApsBomRepository;
import com.xk.aps.dao.XkApsOrderRepository;
import com.xk.aps.dao.XkApsShowRepository;
import com.xk.aps.model.dto.*;
import com.xk.aps.model.entity.XkApsBomEntity;
import com.xk.aps.model.entity.XkApsOrderEntity;
import com.xk.aps.service.IXkApsItemService;
import com.xk.aps.service.IXkApsOrderService;
import com.xk.framework.common.*;

import com.xk.framework.jpa.specification.SimpleSpecificationBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xk.aps.model.entity.XkApsShowEntity;
import com.xk.aps.service.IXkApsShowService;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
* 描述：一键生成单表模块服务实现层
* @author xk
* @date 2021-12-28
*/
@Service
public class XkApsShowServiceImpl implements IXkApsShowService {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private XkApsShowRepository xkApsShowRepository;

    @Autowired
    private XkApsBomRepository xkApsBomRepository;

    @Autowired
    private XkApsOrderRepository xkApsOrderRepository;

    @Autowired
    private IXkApsItemService xkApsItemService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
    * 保存
    *
    * @param formData 保存或者更新的实体
    * @return XkApsShowDto
    */
    @Override
    @Transactional
    public XkApsShowDto save(XkApsShowDto formData) {
        try {
            XkApsShowEntity xkApsShowEntity = new XkApsShowEntity();
            if (formData != null && !StringUtils.isEmpty(formData.getId())) {
                xkApsShowEntity = xkApsShowRepository.xkFindById(formData.getId());
            }

            BeanMapperUtils.map(formData, xkApsShowEntity);
            // 保存
            return BeanMapperUtils.map(xkApsShowRepository.saveAndFlush(xkApsShowEntity), XkApsShowDto.class);
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
    * @return XkApsShowDto
    */
    @Override
    @Transactional(readOnly = true)
    public XkApsShowDto getById(String id) {
        try {
            XkApsShowEntity xkApsShowEntity = xkApsShowRepository.xkFindById(id);
            if (xkApsShowEntity == null) {
            return null;
            }
            return BeanMapperUtils.map(xkApsShowEntity, XkApsShowDto.class);
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
            xkApsShowRepository.removeMuiltByIds(ids);
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
             xkApsShowRepository.remove(id);
        } catch (Exception e) {
            logger.error("删除数据失败,{}",e);
            e.printStackTrace();
            throw new ServiceException("删除数据失败", CommonErrorCode.DELETE_ERROR);
        }
    }
    /**
     * 获取所有排程结果表信息
     */
    //@Cacheable(value = {"Resource"}, key = "#root.method.name",sync = true)
    @Override
    public List<XkApsShowDto> listAll() {
        try {
            List<XkApsShowEntity> showEntities = xkApsShowRepository.findAll();
            if (showEntities == null) {
                return null;
            }
            List<XkApsShowDto> collect = showEntities.stream().map((item) -> {
                XkApsShowDto xkApsShowDto = BeanMapperUtils.map(item, XkApsShowDto.class);
                return xkApsShowDto;
            }).collect(Collectors.toList());
            return collect;
        } catch (Exception e) {
            logger.error("查询所有数据失败,{}",e);
            e.printStackTrace();
            throw new ServiceException("查询所有数据失败", CommonErrorCode.SELECT_ERROR);
        }
    }

    @Override
    public void handlerScheduleData(List<XkApsMatlabInDto> schedule) {

        //TODO 排程后的逻辑处理
        if(schedule !=null && schedule.size()>0){
            List<XkApsShowDto> showDtoList = new ArrayList<>();
            //原子计数器
            AtomicInteger sort = new AtomicInteger();
            //对订单遍历
            schedule.stream().map((xkApsMatlabInDto) -> {
                //获取订单最早开始时间
                Date mostEarlyTime = xkApsMatlabInDto.getMostEarlyTime();
                //根据订单生产的产品代码查出该产品的生产BOM集合
                List<XkApsBomEntity> bomByItemCode = this.getBomByItemCode(xkApsMatlabInDto.getItemCode());
                //循环遍历bom表中生产该品目的所有数据
                for(XkApsBomEntity bomEntity:bomByItemCode){
                    //判断该BOM表数据是否是加工操作
                    if (bomEntity.getInstructionCode() == "M") {
                        //如果是加工操作，将这天BOM表的数据写入showDto中，作为一条资源工作时间信息
                        XkApsShowDto xkApsShowDto = new XkApsShowDto();
                        Double beforeTime = bomEntity.getBeforeTime();
                        Double manufacturingTime = bomEntity.getManufacturingTime();
                        Double afterTime = bomEntity.getAfterTime();
                        double theResourceManufactureLongTime = beforeTime + manufacturingTime + afterTime;
                        xkApsShowDto.setTimeLength(theResourceManufactureLongTime);
                        xkApsShowDto.setResourceCode(bomEntity.getObjectCode());
                        String objectName = xkApsItemService.getObjectName(bomEntity.getObjectCode());
                        if (!StringUtils.isEmpty(objectName)) {
                            xkApsShowDto.setResourceName(objectName);
                        }
                        BeanUtils.copyProperties(xkApsMatlabInDto, xkApsShowDto);
                        xkApsShowDto.setStartTime(mostEarlyTime);
                        xkApsShowDto.setShowWork(bomEntity.getItemCode());
                        xkApsShowDto.setSort(sort.get());
                        showDtoList.add(xkApsShowDto);
                        sort.getAndIncrement();
                    }
                }
                return null;
            });
            //将得到的showList数组处理，此时数组中每个元素只有基本信息和工作时长,包括最早开始时间，订单排列序号
            //如 第一个要生产的订单品目所需要的所有资源 都有加工序号0
            //TODO 对得到的初步结果集合 showDtoList 处理，将集合按订单代码和资源代码分别分类，并给集合中每个数据加上开始时间和结束时间
        }
    }

    /**
     * 订单gantt图数据
     * @return
     */
    @Override
    public List<XkApsGanttDto> orderGanttList() {
        List<XkApsGanttDto> ganttDtoList=new ArrayList<>();
        //查出结果表所有数据 （这里要注意，查询的结果包含已经逻辑删除的数据）
        List<XkApsShowEntity> showEntities = xkApsShowRepository.findAll();
        //将所有的开始时间和结束时间全部放入一个时间数组，得到最大和最小值作为甘特图的时间启示和结束时间
        List<Date> timeLine = new ArrayList<>();
        //对查询到的结果通过订单编号分类放入Map数组中
        if(showEntities.size()>0 && showEntities != null){
            Map<String, List<XkApsShowEntity>> map = showEntities.stream().collect(Collectors.groupingBy(XkApsShowEntity::getOrderCode));
            //遍历Map数组，将数据中的数组组装放入Gantt数据数组中
            for (Map.Entry<String, List<XkApsShowEntity>> entry : map.entrySet()) {
                String mapKey = entry.getKey();
                List<XkApsShowEntity> mapValue = entry.getValue();

                XkApsGanttDto xkApsGanttDto = new XkApsGanttDto();
                List<XkApsGanttItemDto> xkApsGanttItemDtos = new ArrayList<>();

                xkApsGanttDto.setId(mapKey);
                xkApsGanttDto.setName(mapKey);

                //***********************************
                String dark ="rgb(247, 167, 71,0.8)";
                String light ="rgb(247, 167, 71,0.1)";
                XkApsGanttDto.ColorPair colorPair = new XkApsGanttDto.ColorPair();
                colorPair.setDark(dark);
                colorPair.setLight(light);
                xkApsGanttDto.setColorPair(colorPair);
                //***********************************

                if(mapValue.size()>0 && mapValue != null){
                    mapValue.stream().forEach((item)->{
                        XkApsGanttItemDto xkApsGanttItemDto = new XkApsGanttItemDto();
                        BeanUtils.copyProperties(item,xkApsGanttItemDto);
                        xkApsGanttItemDto.setStart(item.getStartTime());
                        xkApsGanttItemDto.setEnd(item.getEndTime());
                        xkApsGanttItemDtos.add(xkApsGanttItemDto);
                        timeLine.add(item.getStartTime());
                        timeLine.add(item.getEndTime());
                    });
                    if(timeLine.size()>0 && timeLine != null){
                        xkApsGanttDto.setStartTime(Collections.min(timeLine));
                        xkApsGanttDto.setEndTime(Collections.max(timeLine));
                    }
                    Collections.sort(xkApsGanttItemDtos);
                    xkApsGanttDto.setGtArray(xkApsGanttItemDtos);
                }
                ganttDtoList.add(xkApsGanttDto);
            }
        }

        for(int i=1;i<ganttDtoList.size();i++){
            if(ganttDtoList.get(0).getStartTime().getTime() > ganttDtoList.get(i).getStartTime().getTime()){
                ganttDtoList.get(0).setStartTime(ganttDtoList.get(i).getStartTime());
            }
            if(ganttDtoList.get(0).getEndTime().getTime() < ganttDtoList.get(i).getEndTime().getTime()){
                ganttDtoList.get(0).setEndTime(ganttDtoList.get(i).getEndTime());
            }
        }
        return ganttDtoList;
    }

    /**
     * 资源gantt图数据
     * @return
     */
    @Override
    public List<XkApsGanttDto> resourceGanttList() {
        List<XkApsGanttDto> ganttDtoList=new ArrayList<>();
        //查出结果表所有数据 （这里要注意，查询的结果包含已经逻辑删除的数据）
        List<XkApsShowEntity> showEntities = xkApsShowRepository.findAll();

        //将所有的开始时间和结束时间全部放入一个时间数组，得到最大和最小值作为甘特图的时间启示和结束时间
        List<Date> timeLine = new ArrayList<>();
        //对查询到的结果通过资源代码分类放入Map数组中
        if(showEntities.size()>0 && showEntities != null){
            Map<String, List<XkApsShowEntity>> map = showEntities.stream().collect(Collectors.groupingBy(XkApsShowEntity::getResourceCode));
            //遍历Map数组，将数据中的数组组装放入Gantt数据数组中
            for (Map.Entry<String, List<XkApsShowEntity>> entry : map.entrySet()) {
                String mapKey = entry.getKey();
                List<XkApsShowEntity> mapValue = entry.getValue();

                XkApsGanttDto xkApsGanttDto = new XkApsGanttDto();
                List<XkApsGanttItemDto> xkApsGanttItemDtos = new ArrayList<>();

                xkApsGanttDto.setId(mapKey);

                //***********************************
//                String dark ="rgb(247, 167, 71,0.8)";
//                String light ="rgb(247, 167, 71,0.1)";
                String dark ="rgb(255, 255, 0,0.8)";
                String light ="rgb(255, 255, 0,0.1)";
                XkApsGanttDto.ColorPair colorPair = new XkApsGanttDto.ColorPair();
                colorPair.setDark(dark);
                colorPair.setLight(light);
                xkApsGanttDto.setColorPair(colorPair);
                //***********************************

                if(mapValue.size()>0 && mapValue != null){
                    mapValue.stream().forEach((item)->{
                        XkApsGanttItemDto xkApsGanttItemDto = new XkApsGanttItemDto();
                        BeanUtils.copyProperties(item,xkApsGanttItemDto);
                        xkApsGanttItemDto.setStart(item.getStartTime());
                        xkApsGanttItemDto.setEnd(item.getEndTime());
                        xkApsGanttItemDtos.add(xkApsGanttItemDto);
                        timeLine.add(item.getStartTime());
                        timeLine.add(item.getEndTime());
                        xkApsGanttDto.setName(item.getResourceName());
                    });


                    if(timeLine.size()>0 && timeLine != null){
                        xkApsGanttDto.setStartTime(Collections.min(timeLine));
                        xkApsGanttDto.setEndTime(Collections.max(timeLine));
                    }
                    Collections.sort(xkApsGanttItemDtos);
                    xkApsGanttDto.setGtArray(xkApsGanttItemDtos);
                }
                ganttDtoList.add(xkApsGanttDto);

            }
        }

        for(int i=1;i<ganttDtoList.size();i++){
            if(ganttDtoList.get(0).getStartTime().getTime() > ganttDtoList.get(i).getStartTime().getTime()){
                ganttDtoList.get(0).setStartTime(ganttDtoList.get(i).getStartTime());
            }
            if(ganttDtoList.get(0).getEndTime().getTime() < ganttDtoList.get(i).getEndTime().getTime()){
                ganttDtoList.get(0).setEndTime(ganttDtoList.get(i).getEndTime());
            }
        }
        return ganttDtoList;
    }

    /**
     * 能耗率画图数据
     */
    @Override
    public List<String> getEnergyConsumption() {
        List<String> result = new ArrayList<>();
        String[] strings = new String[]{"78%" , "82%" , "85%" , "87%" , "89%" , "79%" , "76%" , "80%" , "84%" , "78%" , "73%" , "71%" , "69%" , "65%" , "69%" ,"73%" , "77%" , "79%" , "86%", "89%" , "85%" , "81%" , "87%" , "80%" , "78%" , "75%" , "70%" , "74%" , "77%" , "80%"};
        for (String item : strings) {
            result.add(item);
        }
        return result;
    }

    /**
     * 订单生产数量画图数据
     */
    @Override
    public List<XkApsShowChartDto> getOrderNumber() {
        List<XkApsOrderEntity> orderList = xkApsOrderRepository.findAll();
        Map<String , Integer> map = new HashMap<>();
        for (XkApsOrderEntity xkApsOrderEntity : orderList) {
            map.put(xkApsOrderEntity.getItemCode() , map.getOrDefault(xkApsOrderEntity.getItemCode() , 0 ) + xkApsOrderEntity.getOrderNumber());
        }
        List<XkApsShowChartDto> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            XkApsShowChartDto curItem = new XkApsShowChartDto();
            curItem.setItemCode(entry.getKey());
            curItem.setProductionNumber(entry.getValue());

            result.add(curItem);
        }
        return result;
    }

    /**
     * 产品工序数目画图数据
     */
    @Override
    public List<XkApsShowChartDto> getProcessesNumber() {
        List<XkApsBomEntity> bomRepositoryAll = xkApsBomRepository.findAll();
        Map<String , Integer> map = new HashMap<>();
        for (XkApsBomEntity item : bomRepositoryAll) {
            map.put(item.getItemCode() , Math.max(map.getOrDefault(item.getItemCode() , 0) , item.getBeforeCode()));
        }
        List<XkApsShowChartDto> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            XkApsShowChartDto curDto = new XkApsShowChartDto();
            curDto.setItemCode(entry.getKey());
            curDto.setProcessesNumber(entry.getValue());
            result.add(curDto);
        }
        return result;
    }

    /**
     * 产品瓶颈工序
     */
    @Override
    public List<XkApsShowChartDto> BottleneckProcess() {
        List<XkApsBomEntity> bomRepositoryAll = xkApsBomRepository.findAll();
        Map<String , Double[]> map = new HashMap<>();
        for (XkApsBomEntity item : bomRepositoryAll) {
            if (!map.containsKey(item.getItemCode())){
                map.put(item.getItemCode() , new Double[]{item.getBeforeCode() * 1.0 , item.getManufacturingTime()});
            }
            if (item.getManufacturingTime() > map.get(item.getItemCode())[1]){
                map.put(item.getItemCode() , new Double[]{item.getBeforeCode() * 1.0 , item.getManufacturingTime()});
            }
        }
        List<XkApsShowChartDto> result = new ArrayList<>();
        for (Map.Entry<String, Double[]> entry : map.entrySet()) {
            XkApsShowChartDto curDto = new XkApsShowChartDto();
            curDto.setItemCode(entry.getKey());
            curDto.setBottleneckProcess(String.valueOf(entry.getValue()[0]));
            curDto.setBottleneckProcessTime(String.valueOf(entry.getValue()[1]));
            result.add(curDto);
        }
        return result;
    }


    public List<XkApsBomEntity> getBomByItemCode(String itemCode){
        SimpleSpecificationBuilder<XkApsBomEntity> ssb1 = new SimpleSpecificationBuilder<XkApsBomEntity>();
        ssb1.add("item_code",Constants.OPER_EQ,itemCode);;
        List<XkApsBomEntity> xkApsBomEntityList=xkApsBomRepository.findAll(ssb1.generateSpecification());
        return xkApsBomEntityList;
    }

    /**
    * 分页获取一键生成单表模块信息
    *
    * @param pageDto 分页的数据
    * @return PageDto {@code<XkApsShowDto>}
    */
    @Override
    @Transactional(readOnly = true)
    public PageDto<XkApsShowDto>  page(PageQueryDto<XkApsShowEntity> pageDto) {
        pageDto.setSortField("createtime");
        pageDto.setSortOrder("DESC");
        try {
            Page<XkApsShowEntity> pageData = xkApsShowRepository.queryPage(pageDto);
            if (pageData == null || pageData.getContent() == null || pageData.getContent().size() <= 0) {
                return null;
            }
            List<XkApsShowDto> lists = BeanMapperUtils.mapList(pageData.getContent(), XkApsShowEntity.class,
                XkApsShowDto.class);

            // 设置当前的记录，总记录数，总页数，当前页
            return new PageDto<XkApsShowDto>(lists, pageData.getTotalElements(), pageData.getTotalPages(),pageData.getNumber());
        } catch (Exception e) {
            logger.error("查询数据失败,{}",e);
            e.printStackTrace();
            throw new ServiceException("查询数据失败", CommonErrorCode.SELECT_ERROR);
        }
    }

}

