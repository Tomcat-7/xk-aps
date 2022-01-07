package com.xk.aps.utils;

/**
 * @author sy
 * @date 2022/1/6 - 15:54
 */

import com.xk.aps.model.dto.XkApsMatlabOutDto;
import com.xk.aps.model.dto.XkApsMatlabInDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Aps派程算法
 */
public class ApsSchedule {
    public List<XkApsMatlabInDto> scheduling(List<XkApsMatlabInDto> scheduleData){
        int n = scheduleData.size();
        //初始温度
        Float T = 100F*n;
        //每个温度下的搜索次数
        Float L = 100F;
        //温度迭代系数
        Float K = 0.99F;

//        List<Object> collect = scheduleData.stream().map((item) -> {
//            Float x = item.getMedianOrderDeliveryTime();
//            Float z = item.getMillingHardness();
//            Float y = item.getMillingOutsideDiameter();
//            int k = item.getSortCode();
//            return null;
//        }).collect(Collectors.toList());

        if(T>1){
            for(int i = 0; i<L; i++){
                exchange(scheduleData,T,n);
//                Float len1 = len(scheduleData);
//                Float p1 = 1+n*Math.random();
//                Float p2 = 1+n*Math.random();
//                if(!p1.equals(p2)){
//                    List<XkApsMatlabInDto> newData = scheduleData;
//                    newData.set((int) Math.round(p1),scheduleData.get((int) Math.round(p2)));
//                    newData.set((int) Math.round(p2),scheduleData.get((int) Math.round(p1)));
//                    Float len2 = len(newData);
//                    Float delta = len2 - len1;
//                    if(delta<0){
//                        scheduleData = newData;
//                    }else if((delta/T+delta%T)>Math.random()){
//                        scheduleData = newData;
//                    }
//                }else {
//                    scheduling(scheduleData);
//                }
            }
            int l= 0;
            l=l+1;
            T=T*K;
        }

        return scheduleData;
    }

    public Float len(List<XkApsMatlabInDto> data){
        Float len =0.00F;
        Float t = 0.15F;
        for(int i = 1; i<data.size(); i++){
            //相邻数据的距离
            Float x =  t*(float) (Math.pow((data.get(i-1).getMedianOrderDeliveryTime() - data.get(i).getMedianOrderDeliveryTime()) ,2));
            Float y = t*(float) (Math.pow((data.get(i-1).getMillingOutsideDiameter() - data.get(i).getMillingOutsideDiameter()),2));
            Float z = t*(float) (Math.pow((data.get(i-1).getMillingHardness() - data.get(i).getMillingHardness()),2));
            len = len +(float) ((Math.sqrt(x+y+z)));
        }
        return len;
    }

    public List<XkApsMatlabInDto> exchange(List<XkApsMatlabInDto> scheduleData,Float T,int n){
        Float len1 = len(scheduleData);
        int p1 = (int) Math.round(n*(float)Math.random());
        int p2 = (int) Math.round(n*(float)Math.random());
        if(p1>n-1){
            p1 = p1-1;
        }
        if(p2>n-1){
            p2 = p2-1;
        }
        if(p1 != p2){
            List<XkApsMatlabInDto> newData = scheduleData;
            if(p1>n){
                p1 = p1-1;
            }
            if(p2>n){
                p2 = p2-1;
            }
            XkApsMatlabInDto xkApsMatlabInDto1= scheduleData.get((int) Math.round(p2));
            XkApsMatlabInDto xkApsMatlabInDto2= scheduleData.get((int) Math.round(p1));
            newData.set(p1,xkApsMatlabInDto1);
            newData.set(p2,xkApsMatlabInDto2);
            Float len2 = len(newData);
            Float delta = len2 - len1;
            if(delta<0){
                scheduleData = newData;
            }else if((delta/T+delta%T)>Math.random()){
                scheduleData = newData;
            }
        }else {
            exchange(scheduleData,T,n);
        }
        return scheduleData;
    }
}
