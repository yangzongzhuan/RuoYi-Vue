package com.ruoyi.web.controller.ent;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.domain.TEnterpriseBasicDto;
import com.ruoyi.web.domain.TEnterpriseStockholder;
import com.ruoyi.web.service.EntInfoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("企业信息管理")
@RestController
@RequestMapping("/entInfo")
public class EntInfoController extends BaseController {


    @Autowired
    private EntInfoService entInfoService;


    /**
     * 根据关键字搜索企业信息
     * @param searchParams
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/searchInfoByKeyword")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "searchParams", value = "搜索参数", dataType = "JSONObject"),
                    @ApiImplicitParam(name = "pageNum", value = "页码", required = false, dataType = "int"),
                    @ApiImplicitParam(name = "pageSize", value = "每页数量", required = false, dataType = "int")
            }
    )
    @ApiResponse(code = 200, message = "success", response = TEnterpriseBasicDto.class)
    @ApiOperation("根据关键字搜索企业信息")
    public JSONObject searchInfoByKeyword(@RequestBody JSONObject searchParams,
                                          @RequestParam(required = false,defaultValue = "1") int pageNum,
                                          @RequestParam(required = false,defaultValue = "10") int pageSize) {
        return entInfoService.searchInfoByKeyword(searchParams,pageNum,pageSize);
    }

    /**
     * 根据关键字搜索企业信息简单版，只查询基本信息
     * @param searchParams
     * @param pageNum
     * @param pageSize
     * @return
     */

    @PostMapping("/searchInfoByKeywordSimple")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "searchParams", value = "搜索参数", dataType = "JSONObject"),
                    @ApiImplicitParam(name = "pageNum", value = "页码", required = false, dataType = "int"),
                    @ApiImplicitParam(name = "pageSize", value = "每页数量", required = false, dataType = "int")
            }
    )
    @ApiResponse(code = 200, message = "success", response = TEnterpriseBasicDto.class)
    @ApiOperation("根据关键字搜索企业信息简单版，只查询基本信息")
    public AjaxResult searchInfoByKeywordSimple(@RequestBody JSONObject searchParams,
                                          @RequestParam(required = false,defaultValue = "1") int pageNum,
                                          @RequestParam(required = false,defaultValue = "10") int pageSize) {
        return entInfoService.searchInfoByKeywordSimple(searchParams,pageNum,pageSize);
    }

    /**
     * 根据uniscid查询企业企业股东和对外投资
     * @param uniscid
     * @return
     */
    @GetMapping("/getShareHolderAndInvestment/{uniscid}")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "uniscid", value = "uniscid", dataType = "String"),
            }
    )
    @ApiResponse(code = 200, message = "success", response = TEnterpriseStockholder.class)
    @ApiOperation("根据uniscid查询企业企业股东和对外投资")
    public AjaxResult getShareHolderAndInvestment(@PathVariable String uniscid) {
        return entInfoService.getShareHolderAndInvestment(uniscid);
    }

    /**
     * 根据uniscid查询企业供应商关系
     * @param uniscid
     * @return
     */
    @GetMapping("/getSupplierRelation/{uniscid}")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "uniscid", value = "uniscid", dataType = "String"),
            }
    )
    @ApiResponse(code = 200, message = "success", response = TEnterpriseStockholder.class)
    @ApiOperation("根据uniscid查询企业供应商关系")
    public AjaxResult getSupplierRelation(@PathVariable String uniscid) {
        return entInfoService.getSupplierRelation(uniscid);
    }


    /**
     * 创建数据集
     * @return
     */
    @PostMapping("/createDataSet")
    @ApiOperation("创建数据集")
    public AjaxResult createDataSet(@RequestBody JSONObject paramsBody) {

        long userId = getUserId();
        return entInfoService.createDataSet(paramsBody,userId);
    }

    /**
     * 删除数据集
     * @param datasetId
     * @return
     */
    @DeleteMapping("/deleteDataSet/{datasetId}")
    @ApiOperation("删除数据集")
    public AjaxResult deleteDataSet(@PathVariable String datasetId) {
        return entInfoService.deleteDataSet(datasetId);
    }

    /**
     * 获取数据集列表
     * @return
     */
    @PostMapping("/getDataSetList")
    @ApiOperation("获取数据集列表")
    public AjaxResult getDataSetList(@RequestBody JSONObject paramsBody,
                                     @RequestParam(required = false,defaultValue = "1") int pageNum,
                                     @RequestParam(required = false,defaultValue = "10") int pageSize) {
        long userId = getUserId();
        return entInfoService.getDataSetList(userId,paramsBody,pageNum,pageSize);
    }

    /**
     * 获取数据集详情
     * @param datasetId
     */
    @GetMapping("/getDataSetDetail/{datasetId}")
    @ApiOperation("获取数据集详情")
    public AjaxResult getDataSetDetail(@PathVariable String datasetId) {
        return entInfoService.getDataSetDetail(datasetId);
    }

    /**
     * 更新数据集详情
     * @param datasetId
     */
    @PostMapping("/updateDataSetDetail/{datasetId}")
    @ApiOperation("更新数据集详情")
    public AjaxResult updateDataSetDetail(@PathVariable String datasetId,@RequestBody JSONObject paramsBody) {
        return entInfoService.updateDataSetDetail(datasetId,paramsBody);
    }

}
