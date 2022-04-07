package com.xk.aps.controller;


import com.xk.aps.model.dto.XkApsAttendanceDto;
import com.xk.aps.service.IXkApsBomService;
import com.xk.framework.common.APIResponse;
import com.xk.framework.common.PageDto;
import com.xk.framework.common.PageQueryDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.xk.aps.model.dto.XkApsBomDto;
import com.xk.aps.model.entity.XkApsBomEntity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
* 描述：一键生成单表模块控制层
* @author xk
* @since 2021-12-28
*/
@RestController
@RequestMapping("/api/base/xk/xkApsBom")
@Api(tags = {"一键生成单表模块管理"}, description = "提供一键生成单表模块模块增删查改接口")
public class XkApsBomController {

    @Autowired
    private IXkApsBomService xkApsBomService;

    @ApiOperation(value = "分页获取一键生成单表模块", httpMethod = "GET", notes = "分页获取数据，注意分页参数")
    @RequestMapping(value = "/", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页码", name = "pageIndex", required = true, dataType = "int",defaultValue = "0"),
            @ApiImplicitParam(value = "每页条目", name = "pageSize", required = true,dataType = "int" ,defaultValue = "10"),
    })
    public APIResponse<XkApsBomDto> page(PageQueryDto<XkApsBomEntity> pageDto) {
        //System.out.println(pageDto.getSearchData());
        String key = pageDto.getSearchData();
        String searchData = "[{name:\"delFlag\",value:\""+0+"\"},{name:\"like_itemCode\",value:\""+key+"\"}]";

        pageDto.setSearchData(searchData);
        //System.out.println(searchData);
        PageDto<XkApsBomDto> pd = xkApsBomService.page(pageDto);
        return new APIResponse<XkApsBomDto>(pd);
    }

    /**
     * 描述：获取Bom表所有数据
     * @param
     */
    @ApiOperation(value = "获取所有Bom表数据", notes = "获取所有Bom表数据",httpMethod="GET")
    @ApiImplicitParam(name = "list", value = "获取所有Bom表数据", required = true, dataType = "String")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public APIResponse<List<XkApsBomDto>> list() {
        return new APIResponse<List<XkApsBomDto>>(xkApsBomService.listAll());
    }

    /**
    * 描述：根据Id查询
    * @param id  一键生成单表模块id
    */
    @ApiOperation(value = "获取详细信息", notes = "根据url的id来获取详细信息",httpMethod="GET")
    @ApiImplicitParam(name = "id", value = "主键ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public APIResponse<XkApsBomDto> get(@PathVariable("id") String id) {
         return new APIResponse<XkApsBomDto>(xkApsBomService.getById(id));
    }

    /**
    * 描述:创建一键生成单表模块
    * @param formData  一键生成单表模块DTO
    */
    @ApiOperation(value = "保存一键生成单表模块信息", httpMethod = "POST", notes = "保存信息，注意保存时需要传递的参数")
    @ApiImplicitParam(name = "formData", value = "{table_annotation}信息", required = true, dataType = "XkApsBomDto")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public APIResponse<XkApsBomDto> save(@RequestBody XkApsBomDto formData)  {
            if (null != formData) {
                return new APIResponse<XkApsBomDto>(xkApsBomService.save(formData));
            }
            return new APIResponse<XkApsBomDto>(-1, "数据传输为空");
    }


    /**
    * 描述：删除单个一键生成单表模块
    * @param id 一键生成单表模块id
    */
    @ApiOperation(value = "删除单个一键生成单表模块信息", notes = "根据url的id来指定删除对象", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public APIResponse<XkApsBomDto> remove(@PathVariable("id") String id) {
            xkApsBomService.remove(id);
        return new APIResponse<XkApsBomDto>(0, "数据删除成功");
    }

    /**
    * 描述：删除多个一键生成单表模块
    * @param ids 一键生成单表模块ids
    */
    @ApiOperation(value = "删除多个一键生成单表模块信息", notes = "根据url的id来指定删除对象", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "id1,id2,id3....(多个主键，逗号分割)", required = true, dataType = "String")
    @RequestMapping(value = "/multiDel", method = RequestMethod.DELETE)
    public APIResponse<XkApsBomDto> multiDel(String ids) {
            xkApsBomService.removeMulti(ids);
        return new APIResponse<XkApsBomDto>(0, "数据删除成功");
     }

    /**
    * 描述：更新一键生成单表模块
    * @param
    */
    @ApiOperation(value = "更新一键生成单表模块数据", httpMethod = "PUT")
    @ApiImplicitParams({
    @ApiImplicitParam(required = true, name = "id", paramType = "path", dataType = "String", value = "id"),
    @ApiImplicitParam(name = "formData", value = "更新信息", required = true, dataType = "XkApsBomDto")})
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public APIResponse<XkApsBomDto> update(@RequestBody XkApsBomDto formData) throws Exception {
        if (null != formData) {
            return new APIResponse<XkApsBomDto>(xkApsBomService.save(formData));
        }
        return new APIResponse<XkApsBomDto>(-1, "数据传输为空");
    }
}
