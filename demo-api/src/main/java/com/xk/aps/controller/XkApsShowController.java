package com.xk.aps.controller;


import com.xk.aps.model.dto.XkApsGanttDto;
import com.xk.aps.model.dto.XkApsShowChartDto;
import com.xk.aps.model.dto.XkApsShowDto;
import com.xk.framework.common.APIResponse;
import com.xk.framework.common.PageDto;
import com.xk.framework.common.PageQueryDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.xk.aps.model.entity.XkApsShowEntity;
import com.xk.aps.service.IXkApsShowService;

import java.util.List;


/**
* 描述：一键生成单表模块控制层
* @author xk
* @since 2021-12-28
*/
@RestController
@RequestMapping("/api/aps/xkApsShow")
@Api(tags = {"排程结果信息模块管理"}, description = "提供排程结果信息模块增删查改接口")
public class XkApsShowController {

    @Autowired
    private IXkApsShowService xkApsShowService;

    @ApiOperation(value = "分页获取排程结果信息", httpMethod = "GET", notes = "分页获取数据，注意分页参数")
    @RequestMapping(value = "/", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页码", name = "pageIndex", required = true, dataType = "int",defaultValue = "0"),
            @ApiImplicitParam(value = "每页条目", name = "pageSize", required = true,dataType = "int" ,defaultValue = "10"),
    })
    public APIResponse<XkApsShowDto> page(PageQueryDto<XkApsShowEntity> pageDto) {
        String key = pageDto.getSearchData();
        String searchData = "[{name:\"delFlag\",value:\""+0+"\"},{name:\"like_orderCode\",value:\""+key+"\"}]";

        pageDto.setSearchData(searchData);
        PageDto<XkApsShowDto> pd = xkApsShowService.page(pageDto);
        return new APIResponse<XkApsShowDto>(pd);
    }

    /**
     * 描述：获取结果表所有信息
     * @param
     */
    @ApiOperation(value = "根据id获取排程结果详细信息", notes = "根据url的id来获取详细信息",httpMethod="GET")
    @ApiImplicitParam(name = "id", value = "主键ID", required = true, dataType = "String")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public APIResponse<List<XkApsShowDto>> list() {
        return new APIResponse<List<XkApsShowDto>>(xkApsShowService.listAll());
    }

    /**
     * 描述：获取订单甘特图所有信息
     * @param
     */
    @ApiOperation(value = "获取订单甘特图所有信息", notes = "获取按订单编号分类的排程结果信息",httpMethod="GET")
    @RequestMapping(value = "/order/gantt", method = RequestMethod.GET)
    public APIResponse<List<XkApsGanttDto>> orderGanttList() {
        return new APIResponse<List<XkApsGanttDto>>(xkApsShowService.orderGanttList());
    }

    /**
     * 描述：获取资源甘特图所有信息
     * @param
     */
    @ApiOperation(value = "获取资源甘特图所有信息", notes = "获取按资源编号分类的排程结果信息",httpMethod="GET")
    @RequestMapping(value = "/resource/gantt", method = RequestMethod.GET)
    public APIResponse<List<XkApsGanttDto>> resourceGanttList() {
        return new APIResponse<List<XkApsGanttDto>>(xkApsShowService.resourceGanttList());
    }

    /**
     * 描述：获取能耗率画图数据
     * @param
     */
    @ApiOperation(value = "获取能耗率画图数据", notes = "获取能耗率画图数据",httpMethod="GET")
    @RequestMapping(value = "/resource/energyConsumption", method = RequestMethod.GET)
    public APIResponse<List<String>> resourceEnergyConsumption() {
        return new APIResponse<List<String>>(xkApsShowService.getEnergyConsumption());
    }

    /**
     * 描述：获取订单生产产品数量画图数据
     * @param
     */
    @ApiOperation(value = "获取能耗率画图数据", notes = "获取能耗率画图数据",httpMethod="GET")
    @RequestMapping(value = "/resource/orderNumber", method = RequestMethod.GET)
    public APIResponse<List<XkApsShowChartDto>> resourceOrderNumber() {
        return new APIResponse<List<XkApsShowChartDto>>(xkApsShowService.getOrderNumber());
    }

    /**
     * 描述：获取产品加工工序数目画图数据
     * @param
     */
    @ApiOperation(value = "获取能耗率画图数据", notes = "获取能耗率画图数据",httpMethod="GET")
    @RequestMapping(value = "/resource/processesNumber", method = RequestMethod.GET)
    public APIResponse<List<XkApsShowChartDto>> resourceProcessesNumber() {
        return new APIResponse<List<XkApsShowChartDto>>(xkApsShowService.getProcessesNumber());
    }

    /**
     * 描述：获取产品瓶颈工序以及瓶颈工序时间画图数据
     * @param
     */
    @ApiOperation(value = "获取能耗率画图数据", notes = "获取能耗率画图数据",httpMethod="GET")
    @RequestMapping(value = "/resource/bottleneckProcess", method = RequestMethod.GET)
    public APIResponse<List<XkApsShowChartDto>> resourceBottleneckProcess() {
        return new APIResponse<List<XkApsShowChartDto>>(xkApsShowService.BottleneckProcess());
    }

    /**
    * 描述：根据Id查询
    * @param id  一键生成单表模块id
    */
    @ApiOperation(value = "根据id获取排程结果详细信息", notes = "根据url的id来获取详细信息",httpMethod="GET")
    @ApiImplicitParam(name = "id", value = "主键ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public APIResponse<XkApsShowDto> get(@PathVariable("id") String id) {
         return new APIResponse<XkApsShowDto>(xkApsShowService.getById(id));
    }

    /**
    * 描述:创建一键生成单表模块
    * @param formData  一键生成单表模块DTO
    */
    @ApiOperation(value = "保存排程结果信息", httpMethod = "POST", notes = "保存信息，注意保存时需要传递的参数")
    @ApiImplicitParam(name = "formData", value = "{table_annotation}信息", required = true, dataType = "XkApsShowDto")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public APIResponse<XkApsShowDto> save(@RequestBody XkApsShowDto formData)  {
            if (null != formData) {
                return new APIResponse<XkApsShowDto>(xkApsShowService.save(formData));
            }
            return new APIResponse<XkApsShowDto>(-1, "数据传输为空");
    }


    /**
    * 描述：删除单个一键生成单表模块
    * @param id 一键生成单表模块id
    */
    @ApiOperation(value = "删除单个排程结果信息", notes = "根据url的id来指定删除对象", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public APIResponse<XkApsShowDto> remove(@PathVariable("id") String id) {
            xkApsShowService.remove(id);
        return new APIResponse<XkApsShowDto>(0, "数据删除成功");
    }


    /**
    * 描述：删除多个一键生成单表模块
    * @param ids 一键生成单表模块ids
    */
    @ApiOperation(value = "删除多个排程结果信息", notes = "根据url的id来指定删除对象", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "id1,id2,id3....(多个主键，逗号分割)", required = true, dataType = "String")
    @RequestMapping(value = "/multiDel", method = RequestMethod.DELETE)
    public APIResponse<XkApsShowDto> multiDel(String ids) {
            xkApsShowService.removeMulti(ids);
        return new APIResponse<XkApsShowDto>(0, "数据删除成功");
     }

    /**
    * 描述：更新一键生成单表模块
    * @param
    */
    @ApiOperation(value = "更新排程结果数据", httpMethod = "PUT")
    @ApiImplicitParams({
    @ApiImplicitParam(required = true, name = "id", paramType = "path", dataType = "String", value = "id"),
    @ApiImplicitParam(name = "formData", value = "更新信息", required = true, dataType = "XkApsShowDto")})
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public APIResponse<XkApsShowDto> update(@RequestBody XkApsShowDto formData) throws Exception {
        if (null != formData) {
            return new APIResponse<XkApsShowDto>(xkApsShowService.save(formData));
        }
        return new APIResponse<XkApsShowDto>(-1, "数据传输为空");
    }
}
