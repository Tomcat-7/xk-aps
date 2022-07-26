package com.xk.aps.controller;


import com.xk.aps.model.dto.XkApsAttendanceDto;
import com.xk.aps.model.entity.XkApsAttendanceEntity;
import com.xk.aps.service.IXkApsAttendanceService;
import com.xk.framework.common.APIResponse;
import com.xk.framework.common.PageDto;
import com.xk.framework.common.PageQueryDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
* 描述：一键生成单表模块控制层
* @author xk
* @since 2021-12-28
*/
@RestController
@RequestMapping("/api/aps/xkApsAttendance")
@Api(tags = {"出勤表管理"}, description = "提供出勤表的增删查改接口")
public class XkApsAttendanceController {

    @Autowired
    private IXkApsAttendanceService xkApsAttendanceService;

    @ApiOperation(value = "分页获取出勤表信息", httpMethod = "GET", notes = "分页获取数据，注意分页参数")
    @RequestMapping(value = "/", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页码", name = "pageIndex", required = true, dataType = "int",defaultValue = "0"),
            @ApiImplicitParam(value = "每页条目", name = "pageSize", required = true,dataType = "int" ,defaultValue = "10"),
    })
    public APIResponse<XkApsAttendanceDto> page(PageQueryDto<XkApsAttendanceEntity> pageDto) {
        String key = pageDto.getSearchData();
        String searchData = "[{name:\"delFlag\",value:\""+0+"\"},{name:\"like_attendanceCode\",value:\""+key+"\"}]";

        pageDto.setSearchData(searchData);
        PageDto<XkApsAttendanceDto> pd = xkApsAttendanceService.page(pageDto);
        return new APIResponse<XkApsAttendanceDto>(pd);
    }

//    /**
//     * 描述：获取出勤表所有数据
//     * @param
//     */
//    @ApiOperation(value = "获取所有出勤表数据", notes = "获取所有出勤表Attendance数据",httpMethod="GET")
//    @ApiImplicitParam(name = "list", value = "获取所有出勤表数据", required = true, dataType = "String")
//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    public APIResponse<List<XkApsAttendanceDto>> list(@RequestParam Map<String, Object> params) {
//        return new APIResponse<List<XkApsAttendanceDto>>(xkApsAttendanceService.listAll(params));
//    }

    /**
    * 描述：根据Id查询
    * @param id  一键生成单表模块id
    */
    @ApiOperation(value = "获取出勤详细信息", notes = "根据url的id来获取出勤表的详细信息",httpMethod="GET")
    @ApiImplicitParam(name = "id", value = "主键ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public APIResponse<XkApsAttendanceDto> get(@PathVariable("id") String id) {
         return new APIResponse<XkApsAttendanceDto>(xkApsAttendanceService.getById(id));
    }

    /**
    * 描述:创建一键生成单表模块
    * @param formData  一键生成单表模块DTO
    */
    @ApiOperation(value = "保存出勤表信息", httpMethod = "POST", notes = "保存出勤表信息，注意保存时需要传递的参数")
    @ApiImplicitParam(name = "formData", value = "{table_annotation}信息", required = true, dataType = "XkApsAttendanceDto")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public APIResponse<XkApsAttendanceDto> save(@RequestBody XkApsAttendanceDto formData)  {
            if (null != formData) {
                return new APIResponse<XkApsAttendanceDto>(xkApsAttendanceService.save(formData));
            }
            return new APIResponse<XkApsAttendanceDto>(-1, "数据传输为空");
    }


    /**
    * 描述：删除单个一键生成单表模块
    * @param id 一键生成单表模块id
    */
    @ApiOperation(value = "删除单个出勤表信息", notes = "根据url的id来指定删除的出勤信息对象", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public APIResponse<XkApsAttendanceDto> remove(@PathVariable("id") String id) {
            xkApsAttendanceService.remove(id);
        return new APIResponse<XkApsAttendanceDto>(0, "数据删除成功");
    }

    /**
    * 描述：删除多个一键生成单表模块
    * @param ids 一键生成单表模块ids
    */
    @ApiOperation(value = "删除多个出勤表信息", notes = "根据url的id来指定删除的多个出勤信息对象", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "id1,id2,id3....(多个主键，逗号分割)", required = true, dataType = "String")
    @RequestMapping(value = "/multiDel", method = RequestMethod.DELETE)
    public APIResponse<XkApsAttendanceDto> multiDel(String ids) {
        System.out.println(ids);
        xkApsAttendanceService.removeMulti(ids);
        return new APIResponse<XkApsAttendanceDto>(0, "数据删除成功");
    }
    /**
     * 描述：删除多个一键生成单表模块
     * @param ids 一键生成单表模块ids
     */
//    @ApiOperation(value = "删除多个一键生成单表模块信息", notes = "根据url的id来指定删除对象", httpMethod = "DELETE")
//    @ApiImplicitParam(name = "ids", value = "id1,id2,id3....(多个主键)", required = true)
//    @RequestMapping(value = "/multiDel", method = RequestMethod.DELETE)
//    public APIResponse<XkApsAttendanceDto> multiDel(@RequestBody List<String> ids) {
//        System.out.println(ids);
//        xkApsAttendanceService.deleteIds(ids);
//        return new APIResponse<XkApsAttendanceDto>(0, "数据删除成功");
//    }

    /**
    * 描述：更新一键生成单表模块
    * @param formData  一键生成单表模块DTO
    */
    @ApiOperation(value = "更新出勤表信息", httpMethod = "PUT")
    @ApiImplicitParams({
    @ApiImplicitParam(required = true, name = "id", paramType = "path", dataType = "String", value = "id"),
    @ApiImplicitParam(name = "formData", value = "更新出勤表的信息", required = true, dataType = "XkApsAttendanceDto")})
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public APIResponse<XkApsAttendanceDto> update(@RequestBody XkApsAttendanceDto formData) throws Exception {
        if (null != formData) {
            return new APIResponse<XkApsAttendanceDto>(xkApsAttendanceService.save(formData));
        }
        return new APIResponse<XkApsAttendanceDto>(-1, "数据传输为空");
    }
}
