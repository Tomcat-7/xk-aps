package com.xk.aps.controller;


import com.xk.aps.model.dto.XkApsMatlabInDto;
import com.xk.aps.model.entity.XkApsOrderEntity;
import com.xk.aps.service.IXkApsShowService;
import com.xk.framework.common.APIResponse;
import com.xk.framework.common.PageDto;
import com.xk.framework.common.PageQueryDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.xk.aps.model.dto.XkApsOrderDto;
import com.xk.aps.service.IXkApsOrderService;

import java.util.List;


/**
* 描述：一键生成单表模块控制层
* @author xk
* @since 2021-12-28
*/
@RestController
@RequestMapping("/api/aps/xkApsOrder")
@Api(tags = {"订单表模块管理"}, description = "提供订单表模块增删查改接口")
public class XkApsOrderController {

    @Autowired
    private IXkApsOrderService xkApsOrderService;

    @Autowired
    private IXkApsShowService xkApsShowService;

    @ApiOperation(value = "分页获取订单表信息", httpMethod = "GET", notes = "分页获取数据，注意分页参数")
    @RequestMapping(value = "/", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页码", name = "pageIndex", required = true, dataType = "int",defaultValue = "0"),
            @ApiImplicitParam(value = "每页条目", name = "pageSize", required = true,dataType = "int" ,defaultValue = "10"),
    })
    public APIResponse<XkApsOrderDto> page(PageQueryDto<XkApsOrderEntity> pageDto) {
        PageDto<XkApsOrderDto> pd = xkApsOrderService.page(pageDto);
        return new APIResponse<XkApsOrderDto>(pd);
    }

    /**
     * 对订单排程
     */
    @ApiOperation(value = "对选中的订单数据进行排程返回选中的订单数据(有更新)", notes = "根据url的id来确定排程的订单对象，对选中的订单数据更新并返回，并增加排程结果数据",httpMethod="POST")
    @ApiImplicitParam(name = "ids", value = "id1,id2,id3....(多个主键，逗号分割)", required = true, dataType = "String")
    @RequestMapping(value = "/schedule", method = RequestMethod.POST)
    public APIResponse<List<XkApsOrderDto>> schedule(String ids) {
        System.out.println(ids);
        List<XkApsOrderDto> schedule = xkApsOrderService.schedule(ids);
        return new APIResponse<List<XkApsOrderDto>>(schedule);
    }

    /**
     * 描述：更新所有订单信息
     */
    @ApiOperation(value = "从BPM系统远程更新所有订单详细信息", notes = "从BPM系统远程更新所有订单详细信息",httpMethod="POST")
    @ApiImplicitParam(name = "list", value = "订单表数据", required = true, dataType = "List")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void list(@RequestBody List<XkApsOrderDto> list) {
        xkApsOrderService.refresh(list);
    }

    /**
    * 描述：根据Id查询
    * @param id  一键生成单表模块id
    */
    @ApiOperation(value = "根据id获取订单表信息", notes = "根据url的id来获取详细信息",httpMethod="GET")
    @ApiImplicitParam(name = "id", value = "主键ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public APIResponse<XkApsOrderDto> get(@PathVariable("id") String id) {
         return new APIResponse<XkApsOrderDto>(xkApsOrderService.getById(id));
    }

    /**
    * 描述:创建一键生成单表模块
    * @param formData  一键生成单表模块DTO
    */
    @ApiOperation(value = "保存订单表信息", httpMethod = "POST", notes = "保存信息，注意保存时需要传递的参数")
    @ApiImplicitParam(name = "formData", value = "{table_annotation}信息", required = true, dataType = "XkApsOrderDto")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public APIResponse<XkApsOrderDto> save(@RequestBody XkApsOrderDto formData)  {
            if (null != formData) {
                return new APIResponse<XkApsOrderDto>(xkApsOrderService.save(formData));
            }
            return new APIResponse<XkApsOrderDto>(-1, "数据传输为空");
    }


    /**
    * 描述：删除单个一键生成单表模块
    * @param id 一键生成单表模块id
    */
    @ApiOperation(value = "删除单个订单表信息", notes = "根据url的id来指定删除对象", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public APIResponse<XkApsOrderDto> remove(@PathVariable("id") String id) {
            xkApsOrderService.remove(id);
        return new APIResponse<XkApsOrderDto>(0, "数据删除成功");
    }

    /**
    * 描述：删除多个一键生成单表模块
    * @param ids 一键生成单表模块ids
    */
    @ApiOperation(value = "删除多个订单表信息", notes = "根据url的id来指定删除对象", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "id1,id2,id3....(多个主键，逗号分割)", required = true, dataType = "String")
    @RequestMapping(value = "/multiDel", method = RequestMethod.DELETE)
    public APIResponse<XkApsOrderDto> multiDel(String ids) {
            xkApsOrderService.removeMulti(ids);
        return new APIResponse<XkApsOrderDto>(0, "数据删除成功");
     }

    /**
    * 描述：更新一键生成单表模块
    * @param
    */
    @ApiOperation(value = "更新订单表信息", httpMethod = "PUT")
    @ApiImplicitParams({
    @ApiImplicitParam(required = true, name = "id", paramType = "path", dataType = "String", value = "id"),
    @ApiImplicitParam(name = "formData", value = "更新信息", required = true, dataType = "XkApsOrderDto")})
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public APIResponse<XkApsOrderDto> update(@RequestBody XkApsOrderDto formData) throws Exception {
        if (null != formData) {
            return new APIResponse<XkApsOrderDto>(xkApsOrderService.save(formData));
        }
        return new APIResponse<XkApsOrderDto>(-1, "数据传输为空");
    }
}
