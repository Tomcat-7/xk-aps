package com.xk.aps.utils;

/**
 * @author sy
 * @date 2022/1/6 - 15:54
 */

import com.xk.aps.model.dto.XkApsMatlabInDto;
import com.xk.aps.model.dto.XkApsOrderDto;
import com.xk.aps.model.dto.XkApsResourceDto;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Aps派程算法
 */
@Service
public class ApsSchedule {
    public List<XkApsOrderDto> scheduling(List<XkApsOrderDto> scheduleData) {


        /***************************************下面是第二版算法************************************/

        BigDecimal rat;
        Date nowDate = new Date();

        for (int i = 0; i < scheduleData.size(); i++) {
            rat = BigDecimal.valueOf(0);
            if(scheduleData.get(i).getOrderDateDelivery() != null && scheduleData.get(i).getOrderDateDelivery().getTime()>0){
                BigDecimal x = BigDecimal.valueOf(scheduleData.get(i).getOrderDateDelivery().getTime() - nowDate.getTime());
                BigDecimal d = x.divide(BigDecimal.valueOf(scheduleData.get(i).getSpecificationTwo() * 24 * 3600 * 1000), new MathContext(2));
                rat = rat.add(d);
                scheduleData.get(i).setSortRatCode(rat);
            }else {
                System.out.println("有空元素");
            }
        }

        Collections.sort(scheduleData);
        return scheduleData;
    }

    public List<XkApsOrderDto> stoveScheduling(List<XkApsOrderDto> scheduleData, List<XkApsResourceDto> resourceDtos){
        Collections.sort(resourceDtos);
        int i = 0;//炉子序号
        int j = 0;//订单序号
        int cur = 0 ;
        /**
         * 逻辑 ：
         *      每个资源都是炉子  把炉子按照容量大小排序, 把订单按照 CR 值 从小到大排序
         *      循环遍历每个炉子 如果炉子的容量可以完成这个订单，将该炉子与订单匹配上 如果不能完成该订单就直接退出循环
         */
        //如果炉子不为空
        while(i < resourceDtos.size() && j < scheduleData.size()){
            //如果炉子可以容纳下订单的要求数量 将该炉子与订单绑定  炉子容纳量更新
            if(resourceDtos.get(i).getResourceRestriction() > scheduleData.get(j).getNumericalSpecificationOne()){
                scheduleData.get(j).setSpecificationFour(resourceDtos.get(i).getResourceCode());
                resourceDtos.get(i).setResourceRestriction(resourceDtos.get(i).getResourceRestriction() - scheduleData.get(j).getNumericalSpecificationOne());
                j++;
            }else if(resourceDtos.get(i).getResourceRestriction() < scheduleData.get(j).getNumericalSpecificationOne()){
                if(cur == j){
                    j++;
                }
                i++;
                cur = j;
            }

        }
        return scheduleData;
    }

    /***************************************下面是第一版算法************************************/

//        int n = scheduleData.size();
//        //初始温度
//        Float T = 100F*n;
//        //每个温度下的搜索次数
//        Float L = 100F;
//        //温度迭代系数
//        Float K = 0.99F;
//
////        List<Object> collect = scheduleData.stream().map((item) -> {
////            Float x = item.getMedianOrderDeliveryTime();
////            Float z = item.getMillingHardness();
////            Float y = item.getMillingOutsideDiameter();
////            int k = item.getSortCode();
////            return null;
////        }).collect(Collectors.toList());
//
//        if(T>1){
//            for(int i = 0; i<L; i++){
//                exchange(scheduleData,T,n);
////                Float len1 = len(scheduleData);
////                Float p1 = 1+n*Math.random();
////                Float p2 = 1+n*Math.random();
////                if(!p1.equals(p2)){
////                    List<XkApsMatlabInDto> newData = scheduleData;
////                    newData.set((int) Math.round(p1),scheduleData.get((int) Math.round(p2)));
////                    newData.set((int) Math.round(p2),scheduleData.get((int) Math.round(p1)));
////                    Float len2 = len(newData);
////                    Float delta = len2 - len1;
////                    if(delta<0){
////                        scheduleData = newData;
////                    }else if((delta/T+delta%T)>Math.random()){
////                        scheduleData = newData;
////                    }
////                }else {
////                    scheduling(scheduleData);
////                }
//            }
//            int l= 0;
//            l=l+1;
//            T=T*K;
//        }
//
//        return scheduleData;
//    }
//
//    public Float len(List<XkApsMatlabInDto> data){
//        Float len =0.00F;
//        Float t = 0.15F;
//        for(int i = 1; i<data.size(); i++){
//            //相邻数据的距离
//            Float x =  t*(float) (Math.pow((data.get(i-1).getMedianOrderDeliveryTime() - data.get(i).getMedianOrderDeliveryTime()) ,2));
//            Float y = t*(float) (Math.pow((data.get(i-1).getMillingOutsideDiameter() - data.get(i).getMillingOutsideDiameter()),2));
//            Float z = t*(float) (Math.pow((data.get(i-1).getMillingHardness() - data.get(i).getMillingHardness()),2));
//            len = len +(float) ((Math.sqrt(x+y+z)));
//        }
//        return len;
//    }
//
//    public List<XkApsMatlabInDto> exchange(List<XkApsMatlabInDto> scheduleData,Float T,int n){
//        Float len1 = len(scheduleData);
//        int p1 = (int) Math.round(n*(float)Math.random());
//        int p2 = (int) Math.round(n*(float)Math.random());
//        if(p1>n-1){
//            p1 = p1-1;
//        }
//        if(p2>n-1){
//            p2 = p2-1;
//        }
//        if(p1 != p2){
//            List<XkApsMatlabInDto> newData = scheduleData;
//            if(p1>n){
//                p1 = p1-1;
//            }
//            if(p2>n){
//                p2 = p2-1;
//            }
//            XkApsMatlabInDto xkApsMatlabInDto1= scheduleData.get((int) Math.round(p2));
//            XkApsMatlabInDto xkApsMatlabInDto2= scheduleData.get((int) Math.round(p1));
//            newData.set(p1,xkApsMatlabInDto1);
//            newData.set(p2,xkApsMatlabInDto2);
//            Float len2 = len(newData);
//            Float delta = len2 - len1;
//            if(delta<0){
//                scheduleData = newData;
//            }else if((delta/T+delta%T)>Math.random()){
//                scheduleData = newData;
//            }
//        }else {
//            exchange(scheduleData,T,n);
//        }
//        return scheduleData;
//    }
}
