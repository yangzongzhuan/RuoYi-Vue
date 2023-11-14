<template>
  <div id="app" style="margin: auto;background-color: #f9f9f9;padding-top: 15px;padding-bottom: 30px;padding-right: 15px;padding-left: 15px;min-height: 900px">
    <div style="background-color: white;line-height: 70px;height: 70px;text-align: center">
      <el-autocomplete
        placeholder="请输入公司名称/统一社会信用代码"
        clearable
        style="width: 610px;"
        class="filter-item"
      >
        <el-button slot="append" icon="el-icon-search"/>
      </el-autocomplete>
    </div>
    <div style="clear: both;background-color: white;margin-top: 15px;min-height: 300px;padding:15px">
      <div style="font-size: 18px;font-weight: bolder">
        北京捷通华声科技股份有限公司
      </div>
      <div class="c-data-h1">绩效评价信息</div>

      <el-table
        :data="dataGrid.list"
        border
        fit
        highlight-current-row
        :header-row-style="{height:'40px',fontSize: '13px'}"
        :header-cell-style="{padding:'0px'}"
        :row-style="{height:'40px',fontSize: '13px'}"
        :cell-style="{padding:'0px'}"
        style="width: 100%;"
      >
        <el-table-column label="序号" width="60" align="center">
          <template slot-scope="scope">{{ scope.$index+1 }}</template>
        </el-table-column>
        <el-table-column prop="supplierId" label="供应商ID"></el-table-column>
        <el-table-column prop="supplierName" label="供应商名称"></el-table-column>
        <el-table-column prop="internalManagementEvaluation" label="内部管理评价"></el-table-column>
        <el-table-column prop="qualityManagementEvaluation" label="质量管理评价"></el-table-column>
        <el-table-column prop="progressManagementEvaluation" label="进度管理评价"></el-table-column>
        <el-table-column prop="costManagementEvaluation" label="成本管理评价"></el-table-column>
        <el-table-column prop="safetyCultureEvaluation" label="安全文明评价"></el-table-column>
        <el-table-column prop="communicationCoordinationEvaluation" label="沟通协调评价"></el-table-column>
        <el-table-column prop="afterSalesServiceEvaluation" label="售后服务评价"></el-table-column>
        <el-table-column prop="performanceEvaluationScore" label="绩效评价分数"></el-table-column>
        <el-table-column label="详情">
          <template slot-scope="scope">
            <el-link size="mini" type="primary" @click="dialogVisibleDetail = true">详情</el-link>
          </template>
        </el-table-column>
      </el-table>
      <pagination style="text-align: right;" :total="dataGrid.total" :page.sync="dataGrid.listQuery.page" :limit.sync="dataGrid.listQuery.limit" @pagination="getList" />
    </div>
    <el-dialog
      :visible.sync="dialogVisibleDetail"
      title="履约信息详情表"
    >
      <div class="c-data-h1">
        履约信息汇总表
      </div>
      <div>
        <table class="c-base-table" style="width: 100%">
          <tr>
            <td class="c-td-title">供应商ID：</td>
            <td class="c-td-text">123456</td>
            <td class="c-td-title">供应商名称：</td>
            <td class="c-td-text">ABC公司</td>
          </tr>
          <tr>
            <td class="c-td-title">合同编号：</td>
            <td class="c-td-text">HTBH123</td>
            <td class="c-td-title">合同名称：</td>
            <td class="c-td-text">合同名称1</td>
          </tr>
          <tr>
            <td class="c-td-title">应交货日期：</td>
            <td class="c-td-text">10万</td>
            <td class="c-td-title">货品名称：</td>
            <td class="c-td-text">10万</td>
          </tr>
          <tr>
            <td class="c-td-title">货品数量：</td>
            <td class="c-td-text">2023-10-25</td>
            <td class="c-td-title">货品规格：</td>
            <td class="c-td-text">2023-10-08</td>
          </tr>
          <tr>
            <td class="c-td-title">实际交货日期：</td>
            <td class="c-td-text">10万</td>
            <td class="c-td-title">交货批次：</td>
            <td class="c-td-text">2023-10-04</td>
          </tr>
          <tr>
            <td class="c-td-title">货品合格率：</td>
            <td class="c-td-text">10万</td>
            <td class="c-td-title">是否通过验收：</td>
            <td class="c-td-text">2023-10-04</td>
          </tr>
          <tr>
            <td class="c-td-title">验收时间：</td>
            <td class="c-td-text">10万</td>
            <td class="c-td-title">数据日期：</td>
            <td class="c-td-text">2023-10-04</td>
          </tr>
        </table>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import Pagination from "@/components/Pagination";
export default({
  name: "EvaluationInfo",
  components: { Pagination },
  data(){
    return {
      dialogVisibleDetail: false,
      dataGrid: {
        list: [
          {
            supplierId: 123456,
            supplierName: 'ABC公司',
            internalManagementEvaluation: 8,
            qualityManagementEvaluation: 7,
            progressManagementEvaluation: 9,
            costManagementEvaluation: 6,
            safetyCultureEvaluation: 8,
            communicationCoordinationEvaluation: 9,
            afterSalesServiceEvaluation: 7,
            performanceEvaluationScore: 85,
          },
          {
            supplierId: 789012,
            supplierName: 'XYZ公司',
            internalManagementEvaluation: 6,
            qualityManagementEvaluation: 8,
            progressManagementEvaluation: 7,
            costManagementEvaluation: 9,
            safetyCultureEvaluation: 7,
            communicationCoordinationEvaluation: 8,
            afterSalesServiceEvaluation: 9,
            performanceEvaluationScore: 92,
          },
          {
            supplierId: 345678,
            supplierName: 'EFG公司',
            internalManagementEvaluation: 9,
            qualityManagementEvaluation: 6,
            progressManagementEvaluation: 8,
            costManagementEvaluation: 7,
            safetyCultureEvaluation: 9,
            communicationCoordinationEvaluation: 7,
            afterSalesServiceEvaluation: 8,
            performanceEvaluationScore: 78,
          },
          {
            supplierId: 901234,
            supplierName: 'HIJ公司',
            internalManagementEvaluation: 7,
            qualityManagementEvaluation: 9,
            progressManagementEvaluation: 6,
            costManagementEvaluation: 8,
            safetyCultureEvaluation: 6,
            communicationCoordinationEvaluation: 9,
            afterSalesServiceEvaluation: 7,
            performanceEvaluationScore: 89,
          },
          {
            supplierId: 567890,
            supplierName: 'LMN公司',
            internalManagementEvaluation: 8,
            qualityManagementEvaluation: 7,
            progressManagementEvaluation: 9,
            costManagementEvaluation: 6,
            safetyCultureEvaluation: 8,
            communicationCoordinationEvaluation: 9,
            afterSalesServiceEvaluation: 7,
            performanceEvaluationScore: 85,
          },
          {
            supplierId: 123456,
            supplierName: 'OPQ公司',
            internalManagementEvaluation: 6,
            qualityManagementEvaluation: 8,
            progressManagementEvaluation: 7,
            costManagementEvaluation: 9,
            safetyCultureEvaluation: 7,
            communicationCoordinationEvaluation: 8,
            afterSalesServiceEvaluation: 9,
            performanceEvaluationScore: 92,
          },
          {
            supplierId: 789012,
            supplierName: 'RST公司',
            internalManagementEvaluation: 9,
            qualityManagementEvaluation: 6,
            progressManagementEvaluation: 8,
            costManagementEvaluation: 7,
            safetyCultureEvaluation: 9,
            communicationCoordinationEvaluation: 7,
            afterSalesServiceEvaluation: 8,
            performanceEvaluationScore: 78,
          },
          {
            supplierId: 345678,
            supplierName: 'UVW公司',
            internalManagementEvaluation: 7,
            qualityManagementEvaluation: 9,
            progressManagementEvaluation: 6,
            costManagementEvaluation: 8,
            safetyCultureEvaluation: 6,
            communicationCoordinationEvaluation: 9,
            afterSalesServiceEvaluation: 7,
            performanceEvaluationScore: 89,
          },
          {
            supplierId: 901234,
            supplierName: 'XYZ公司',
            internalManagementEvaluation: 8,
            qualityManagementEvaluation: 7,
            progressManagementEvaluation: 9,
            costManagementEvaluation: 6,
            safetyCultureEvaluation: 8,
            communicationCoordinationEvaluation: 9,
            afterSalesServiceEvaluation: 7,
            performanceEvaluationScore: 85,
          },
          {
            supplierId: 567890,
            supplierName: 'ABC公司',
            internalManagementEvaluation: 6,
            qualityManagementEvaluation: 8,
            progressManagementEvaluation: 7,
            costManagementEvaluation: 9,
            safetyCultureEvaluation: 7,
            communicationCoordinationEvaluation: 8,
            afterSalesServiceEvaluation: 9,
            performanceEvaluationScore: 92,
          },
        ],
        listLoading: false,
        listQuery: {
          page: 1,
          limit: 10,
          keywords: undefined
        },
        total: 0
      }
    }
  },
  methods: {
    getList() {
    }
  }
})
</script>
<style scoped lang="scss">
.c-data-h1{
  margin: 10px 0px 10px 0px;
  border-left: 7px solid #009EFF;
  padding-left: 5px;
  height: 25px;
  line-height: 25px;
  font-size: 14px;
  color: #464c5b;
  font-weight: bolder;
}

.c-td-title{
  background:#fcfcfc;
  border:1px solid #fcfcfc;
  padding-left: 10px;
  font-size:11px;
  font-weight:bolder;
  height: 30px;
  color:rgba(96,96,96,1);
}
.c-popper-class{
  height: 20px;
}
.c-base-table,.c-base-table tr th, .c-base-table tr td {
  border:1px solid #D0CECE;
}
.c-base-table { border-collapse: collapse;}
.c-th-header{
  text-align: left;
  font-size:11px;
  height: 30px;
  padding-left: 10px;
  background:#f9f9f9;
  font-weight:bolder;
  color:rgba(96,96,96,1);
  border:1px solid #fcfcfc;
}
.c-td-text{
  text-align: left;
  height: 30px;
  line-height: 25px;
  font-size: 11px;
  padding-left: 10px;
  font-weight:400;
  color:rgba(96,96,96,1);
  background:white;
  border:1px solid #fcfcfc;
}
</style>
