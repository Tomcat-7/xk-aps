package com.xk.aps.controller;


import com.xk.aps.model.dto.XkApsCalendarDto;
import com.xk.framework.common.APIResponse;
import com.xk.framework.common.PageDto;
import com.xk.framework.common.PageQueryDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.xk.aps.model.entity.XkApsCalendarEntity;
import com.xk.aps.service.IXkApsCalendarService;


/**
* 描述：一键生成单表模块控制层
* @author xk
* @since 2021-12-28
*/
@RestController
@RequestMapping("/api/aps/xkApsCalendar")
@Api(tags = {"生产日历表管理"}, description = "提供生产日历表模块增删查改接口")
public class XkApsCalendarController {

    @Autowired
    private IXkApsCalendarService xkApsCalendarService;

    @ApiOperation(value = "分页获取生产日历表信息", httpMethod = "GET", notes = "分页获取数据，注意分页参数")
    @RequestMapping(value = "/", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页码", name = "pageIndex", required = true, dataType = "int",defaultValue = "0"),
            @ApiImplicitParam(value = "每页条目", name = "pageSize", required = true,dataType = "int" ,defaultValue = "10"),
    })
    public APIResponse<XkApsCalendarDto> page(PageQueryDto<XkApsCalendarEntity> pageDto) {
        String key = pageDto.getSearchData();
        String searchData = "[{name:\"delFlag\",value:\""+0+"\"},{name:\"like_resourceCode\",value:\""+key+"\"}]";

        pageDto.setSearchData(searchData);
        PageDto<XkApsCalendarDto> pd = xkApsCalendarService.page(pageDto);
        return new APIResponse<XkApsCalendarDto>(pd);
    }

//    /**
//     * 描述：获取所有生产日历信息
//     * @param
//     */
//    @ApiOperation(value = "获取所有生产日历信息", notes = "获取所有生产日历信息",httpMethod="GET")
//    @ApiImplicitParam(name = "id", value = "主键ID", required = true, dataType = "String")
//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    public APIResponse<XkApsCalendarDto> list() {
//        return new APIResponse<XkApsCalendarDto>(xkApsCalendarService.listAll());
//    }

    /**
    * 描述：根据Id查询
    * @param id  一键生成单表模块id
    */
    @ApiOperation(value = "根据id获取生产日历表详细信息", notes = "根据url的id来获取详细信息",httpMethod="GET")
    @ApiImplicitParam(name = "id", value = "主键ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public APIResponse<XkApsCalendarDto> get(@PathVariable("id") String id) {
         return new APIResponse<XkApsCalendarDto>(xkApsCalendarService.getById(id));
    }

    /**
    * 描述:创建一键生成单表模块
    * @param formData  一键生成单表模块DTO
    */
    @ApiOperation(value = "保存生产日历表信息", httpMethod = "POST", notes = "保存信息，注意保存时需要传递的参数")
    @ApiImplicitParam(name = "formData", value = "{table_annotation}信息", required = true, dataType = "XkApsCalendarDto")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public APIResponse<XkApsCalendarDto> save(@RequestBody XkApsCalendarDto formData)  {
            if (null != formData) {
                return new APIResponse<XkApsCalendarDto>(xkApsCalendarService.save(formData));
            }
            return new APIResponse<XkApsCalendarDto>(-1, "数据传输为空");
    }


    /**
    * 描述：删除单个一键生成单表模块
    * @param id 一键生成单表模块id
    */
    @ApiOperation(value = "删除单个生产日历表信息", notes = "根据url的id来指定删除对象", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public APIResponse<XkApsCalendarDto> remove(@PathVariable("id") String id) {
            xkApsCalendarService.remove(id);
        return new APIResponse<XkApsCalendarDto>(0, "数据删除成功");
    }

    /**
    * 描述：删除多个一键生成单表模块
    * @param ids 一键生成单表模块ids
    */
    @ApiOperation(value = "删除多个生产日历表信息", notes = "根据url的id来指定删除对象", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "id1,id2,id3....(多个主键，逗号分割)", required = true, dataType = "String")
    @RequestMapping(value = "/multiDel", method = RequestMethod.DELETE)
    public APIResponse<XkApsCalendarDto> multiDel(String ids) {
            xkApsCalendarService.removeMulti(ids);
        return new APIResponse<XkApsCalendarDto>(0, "数据删除成功");
     }

    /**
    * 描述：更新一键生成单表模块
    * @param
    */
    @ApiOperation(value = "更新生产日历表数据", httpMethod = "PUT")
    @ApiImplicitParams({
    @ApiImplicitParam(required = true, name = "id", paramType = "path", dataType = "String", value = "id"),
    @ApiImplicitParam(name = "formData", value = "更新信息", required = true, dataType = "XkApsCalendarDto")})
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public APIResponse<XkApsCalendarDto> update(@RequestBody XkApsCalendarDto formData) throws Exception {
        if (null != formData) {
            return new APIResponse<XkApsCalendarDto>(xkApsCalendarService.save(formData));
        }
        return new APIResponse<XkApsCalendarDto>(-1, "数据传输为空");
    }
}
