package com.xk.aps.utils;

import com.xk.aps.model.dto.XkApsMatlabInDto;
import com.xk.aps.model.dto.XkApsOrderDto;
import com.xk.aps.model.dto.XkApsResourceDto;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author sy
 * @date 2022/2/12 - 14:47
 */
public class ApsNewSchedule {

    /**
     * 发运周期(完工 -》 打包 -》 仓库 -》出售 的时间)
     * 存放订单开工日期 ()
     * @param scheduleData
     * @return
     */

    public List<XkApsOrderDto> newScheduling(List<XkApsOrderDto> scheduleData){

        BigDecimal rat;
        Date nowDate = new Date();

        for(int i=0;i<scheduleData.size();i++){
            rat = BigDecimal.valueOf(0);
            BigDecimal x = BigDecimal.valueOf(scheduleData.get(i).getOrderDateDelivery().getTime() - nowDate.getTime());
            BigDecimal d = x.divide(BigDecimal.valueOf(scheduleData.get(i).getSpecificationTwo()*24*3600*1000), new MathContext(2));
            rat = rat.add(d);
            scheduleData.get(i).setSortRatCode(rat);
        }

        long dat = 0;
        long f = 7;

        for (int i = 0; i < scheduleData.size(); i++) {
            //交货期 - 生产周期 - 发运期
            dat = scheduleData.get(i).getOrderDateDelivery().getTime() - scheduleData.get(i).getSpecificationTwo()*24*3600*1000 - f;
            if(dat - nowDate.getTime() < 0){
                dat = nowDate.getTime();
            }
            scheduleData.get(i).setSpecificationThree(dat);
        }

        Collections.sort(scheduleData);

        for(int i=0;i<scheduleData.size();i++){
            scheduleData.get(i).setSortCode(i);
        }
        return scheduleData;
    }

    public List<XkApsOrderDto> stoveScheduling(List<XkApsOrderDto> scheduleData, List<XkApsResourceDto> xkApsResourceDtos){
        Collections.sort(xkApsResourceDtos);
        int i = 0;//炉子序号
        int j = 0;//订单序号
        List<XkApsResourceDto> resourceDtos = new ArrayList<>();
        xkApsResourceDtos.stream().forEach((item) -> {
            if(item.getResourceGroup().equalsIgnoreCase("stove")){
                resourceDtos.add(item);
            }
        });
        /**
         * 逻辑 ：
         *      每个资源都是炉子  把炉子按照容量大小排序, 把订单按照 CR 值 从小到大排序
         *      循环遍历每个炉子 如果炉子的容量可以完成这个订单，将该炉子与订单匹配上 如果不能完成该订单就直接退出循环
         */
        while(resourceDtos.get(i).getResourceRestriction() > scheduleData.get(j).getNumericalSpecificationOne() || resourceDtos.get(i) == null){
            scheduleData.get(j).setSpecificationFour(resourceDtos.get(i).getResourceCode());
            if(resourceDtos.get(i).getResourceRestriction() < scheduleData.get(j).getNumericalSpecificationOne()){
                i++;
            }else {
                i++;
                j++;
            }
        }
        return scheduleData;
    }

}
