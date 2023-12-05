package com.ruoyi.web.service;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.web.domain.*;
import com.ruoyi.web.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class EntInfoService {

    @Autowired
    private ITEnterpriseBasicMapper itEnterpriseBasicMapper;

    @Autowired
    private ITPatentsRelationsMapper itPatentsRelationsMapper;

    @Autowired
    private ITTrademarksMapper itTrademarksMapper;

    @Autowired
    private ITNewJobsMapper itNewJobsMapper;

    @Autowired
    private ITEnterpriseDatasetMapper itEnterpriseDatasetMapper;

    // 插入数据集-企业关系表
    @Autowired
    private ITDatasetEnterpriseMapper itDatasetEnterpriseMapper;

    @Autowired
    private ITEnterpriseStockholderMapper itEnterpriseStockholderMapper;

    @Autowired
    private ITEnterpriseInvestmentMapper itEnterpriseInvestmentMapper;

    @Autowired
    private ITSupplierRelevanceMapper itSupplierRelevanceMapper;


    public JSONObject searchInfoByKeyword(JSONObject searchParams, int pageNum, int pageSize) {

        QueryWrapper<TEnterpriseBasicDto> queryWrapper = new QueryWrapper<>();

        List<TEnterpriseBasicDto> basicList = Lists.newArrayList();
        List<TPatentsRelations> patentsList = Lists.newArrayList();
        List<TTrademarks> trademarksList = Lists.newArrayList();
        List<TNewJobs> newJobsList = Lists.newArrayList();
        if (searchParams.containsKey("keyword") && StringUtils.isNotBlank(searchParams.getString("keyword"))) {
            String keyword = searchParams.getString("keyword");
            queryWrapper.like("entname",keyword)
                    .or().like("opscope",keyword)
                    .or().like("reg_addr",keyword);

            QueryWrapper<TPatentsRelations> queryWrapperPatents = new QueryWrapper<>();
            QueryWrapper<TTrademarks> queryWrapperTrademarks = new QueryWrapper<>();
            QueryWrapper<TNewJobs> queryWrapperNewJobs = new QueryWrapper<>();
            queryWrapperPatents.like("patent_name",keyword)
                    .or().like("brief",keyword);
            queryWrapperTrademarks.like("name",keyword);
            queryWrapperNewJobs.like("description",keyword);

            basicList = itEnterpriseBasicMapper.selectList(queryWrapper);
            patentsList = itPatentsRelationsMapper.selectList(queryWrapperPatents);
            trademarksList = itTrademarksMapper.selectList(queryWrapperTrademarks);
            newJobsList = itNewJobsMapper.selectList(queryWrapperNewJobs);
        }

        List<String> uniScidList = Lists.newArrayList();
        basicList.forEach(basic -> {
            uniScidList.add(basic.getUniscid());
        });
        patentsList.forEach(patents -> {
            uniScidList.add(patents.getUniscid());
        });
        trademarksList.forEach(trademarks -> {
            uniScidList.add(trademarks.getUniscid());
        });
        newJobsList.forEach(newJobs -> {
            uniScidList.add(newJobs.getUniscid());
        });
        // 提取所有的uniscid，合并数据
        queryWrapper = new QueryWrapper<>();
        if (uniScidList.size() > 0) {
            queryWrapper.in("uniscid",uniScidList);
        }

        Page<TEnterpriseBasicDto> page = new Page<>(pageNum,pageSize);
        Page<TEnterpriseBasicDto> pageResult = itEnterpriseBasicMapper.selectPage(page, queryWrapper);
        for (TEnterpriseBasicDto basic : pageResult.getRecords()) {
            String uniscid = basic.getUniscid();
            if (patentsList.size() > 0) {
                List<TPatentsRelations> patentsRelationsList = Lists.newArrayList();
                for (TPatentsRelations patents : patentsList) {
                    if (uniscid.equals(patents.getUniscid())) {
                        patentsRelationsList.add(patents);
                    }
                }
                basic.setTPatentsRelationsList(patentsRelationsList);
            }else {
                basic.setTPatentsRelationsList(itPatentsRelationsMapper.selectList(
                        new QueryWrapper<TPatentsRelations>()
                                .eq("uniscid",uniscid)
                                .last("limit 3")
                ));
            }
            if (trademarksList.size() > 0) {
                List<TTrademarks> trademarksRelationsList = Lists.newArrayList();
                for (TTrademarks trademarks : trademarksList) {
                    if (uniscid.equals(trademarks.getUniscid())) {
                        trademarksRelationsList.add(trademarks);
                    }
                }
                basic.setTTrademarksList(trademarksRelationsList);
            }else {
                basic.setTTrademarksList(itTrademarksMapper.selectList(
                        new QueryWrapper<TTrademarks>()
                                .eq("uniscid",uniscid)
                                .last("limit 3")
                ));
            }
            if (newJobsList.size() > 0) {
                List<TNewJobs> newJobsRelationsList = Lists.newArrayList();
                for (TNewJobs newJobs : newJobsList) {
                    if (uniscid.equals(newJobs.getUniscid())) {
                        newJobsRelationsList.add(newJobs);
                    }
                }
                basic.setTNewJobsList(newJobsRelationsList);
            }else {
                basic.setTNewJobsList(itNewJobsMapper.selectList(
                        new QueryWrapper<TNewJobs>()
                                .eq("uniscid",uniscid)
                                .last("limit 3")
                ));
            }

            String regcap = basic.getRegcap();
            // 10000.000000 -> 10000
            if (StringUtils.isNotBlank(regcap)) {
                basic.setRegcap(regcap.split("\\.")[0]);
            }
            basic.setChecked(false);
        }

        // 查询专利信息
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total",pageResult.getTotal());
        jsonObject.put("pages",pageResult.getPages());
        jsonObject.put("pageNum",pageResult.getCurrent());
        jsonObject.put("pageSize",pageResult.getSize());
        jsonObject.put("item",pageResult.getRecords());
        return jsonObject;
    }

    public AjaxResult searchInfoByKeywordSimple(JSONObject searchParams, int pageNum, int pageSize) {

        QueryWrapper<TEnterpriseBasicDto> queryWrapper = new QueryWrapper<>();
        if (searchParams.containsKey("keyword") && StringUtils.isNotBlank(searchParams.getString("keyword"))) {
            String keyword = searchParams.getString("keyword");
            queryWrapper.like("entname",keyword)
                    .or().eq("uniscid",keyword);
        }
        Page<TEnterpriseBasicDto> page = new Page<>(pageNum,pageSize);
        Page<TEnterpriseBasicDto> pageResult = itEnterpriseBasicMapper.selectPage(page, queryWrapper);
        List<TEnterpriseBasicDto> basicList = Lists.newArrayList();

        for (TEnterpriseBasicDto basic : pageResult.getRecords()) {
            basic.setRegcap(basic.getRegcap().split("\\.")[0]);
            basicList.add(basic);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total",pageResult.getTotal());
        jsonObject.put("pages",pageResult.getPages());
        jsonObject.put("pageNum",pageResult.getCurrent());
        jsonObject.put("pageSize",pageResult.getSize());
        jsonObject.put("item",basicList);
        return AjaxResult.success(jsonObject);
    }

    public AjaxResult getShareHolderAndInvestment(String uniscid) {
        List<TEnterpriseStockholder> stockholderList = itEnterpriseStockholderMapper.selectList(
                new QueryWrapper<TEnterpriseStockholder>().eq("uniscid",uniscid)
        );
        List<TEnterpriseInvestment> investmentList = itEnterpriseInvestmentMapper.selectList(
                new QueryWrapper<TEnterpriseInvestment>().eq("uniscid",uniscid)
        );
        TEnterpriseBasicDto baseEnterpriseInfo = itEnterpriseBasicMapper.selectOne(
                new QueryWrapper<TEnterpriseBasicDto>().eq("uniscid", uniscid)
        );
        JSONObject basic = new JSONObject();
        basic.put("stockholderList",stockholderList);
        basic.put("investmentList",investmentList);
        basic.put("baseEnterpriseInfo",baseEnterpriseInfo);
        return AjaxResult.success(basic);
    }

    public AjaxResult getSupplierRelation(String uniscid) {
        List<TSupplierRelevance> supplierRelevanceList = itSupplierRelevanceMapper.selectList(
                new QueryWrapper<TSupplierRelevance>().eq("uniscid",uniscid)
        );
        TEnterpriseBasicDto baseEnterpriseInfo = itEnterpriseBasicMapper.selectOne(
                new QueryWrapper<TEnterpriseBasicDto>().eq("uniscid", uniscid)
        );
        JSONObject basic = new JSONObject();
        basic.put("supplierRelevanceList",supplierRelevanceList);
        basic.put("baseEnterpriseInfo",baseEnterpriseInfo);
        return AjaxResult.success(basic);
    }

    public AjaxResult createDataSet(JSONObject paramsBody, Long userId) {

        TEnterpriseDataset tEnterpriseDataset = new TEnterpriseDataset();
        String id = UUID.randomUUID().toString().replace("-","").toUpperCase().substring(0,29);
        tEnterpriseDataset.setId(id);
        tEnterpriseDataset.setCreateUserId(userId.intValue());
        tEnterpriseDataset.setDatasetName(paramsBody.getString("datasetName"));
        tEnterpriseDataset.setVisibleDataset(paramsBody.getIntValue("visibleDataset"));
        itEnterpriseDatasetMapper.insert(tEnterpriseDataset);

        // 插入数据集-企业关系表
        JSONArray uniscidList = paramsBody.getJSONArray("uniscidList");
        for (int i = 0; i < uniscidList.size(); i++) {
            TDatasetEnterprise itDatasetEnterprise = new TDatasetEnterprise();
            itDatasetEnterprise.setDatasetId(id);
            itDatasetEnterprise.setUniscid(uniscidList.getString(i));
            itDatasetEnterpriseMapper.insert(itDatasetEnterprise);
        }
        return AjaxResult.success();
    }

    public AjaxResult deleteDataSet(String datasetId) {
        itDatasetEnterpriseMapper.delete(new QueryWrapper<TDatasetEnterprise>().eq("dataset_id",datasetId));
        itEnterpriseDatasetMapper.deleteById(datasetId);
        return AjaxResult.success();
    }

    public AjaxResult getDataSetList(long userId, JSONObject paramsBody, int pageNum, int pageSize) {
        QueryWrapper<TEnterpriseDataset> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("create_user_id",userId);
        if (paramsBody.containsKey("datasetName") && StringUtils.isNotBlank(paramsBody.getString("datasetName"))) {
            queryWrapper.like("dataset_name",paramsBody.getString("datasetName"));
        }
        Page<TEnterpriseDataset> page = new Page<>(pageNum,pageSize);
        Page<TEnterpriseDataset> pageResult = itEnterpriseDatasetMapper.selectPage(page, queryWrapper);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total",pageResult.getTotal());
        jsonObject.put("pages",pageResult.getPages());
        jsonObject.put("pageNum",pageResult.getCurrent());
        jsonObject.put("pageSize",pageResult.getSize());
        jsonObject.put("item",pageResult.getRecords());
        return AjaxResult.success(jsonObject);
    }

    public AjaxResult getDataSetDetail(String datasetId) {
        List<TDatasetEnterprise> tDatasetEnterpriseList = itDatasetEnterpriseMapper.selectList(
                new QueryWrapper<TDatasetEnterprise>().eq("dataset_id",datasetId)
        );
        List<String> uniscidList = Lists.newArrayList();
        tDatasetEnterpriseList.forEach(tDatasetEnterprise -> {
            uniscidList.add(tDatasetEnterprise.getUniscid());
        });
        QueryWrapper<TEnterpriseBasicDto> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("uniscid",uniscidList);
        List<TEnterpriseBasicDto> basicList = itEnterpriseBasicMapper.selectList(queryWrapper);
        return AjaxResult.success(basicList);
    }

    public AjaxResult updateDataSetDetail(String datasetId, JSONObject paramsBody) {

        // 更新数据集
        TEnterpriseDataset tEnterpriseDataset = new TEnterpriseDataset();
        tEnterpriseDataset.setId(datasetId);
        tEnterpriseDataset.setDatasetName(paramsBody.getString("datasetName"));
        tEnterpriseDataset.setVisibleDataset(paramsBody.getIntValue("visibleDataset"));
        tEnterpriseDataset.setUpdateTime(new Date());
        itEnterpriseDatasetMapper.updateById(tEnterpriseDataset);
        return AjaxResult.success();
    }
}
