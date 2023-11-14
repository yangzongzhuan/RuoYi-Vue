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
      <div class="c-data-h1">合作信息表</div>

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
        <el-table-column prop="supplier_id" label="供应商ID"></el-table-column>
        <el-table-column prop="supplier_name" label="供应商名称"></el-table-column>
        <el-table-column prop="first_application_time" label="首次申请时间"></el-table-column>
        <el-table-column prop="first_cooperation_time" label="首次合作时间"></el-table-column>
        <el-table-column prop="first_supplier_list_time" label="首次纳入供应商名单时间"></el-table-column>
        <el-table-column prop="last_supplier_list_time" label="最后一次纳入供应商名单时间"></el-table-column>
        <el-table-column prop="current_cooperation_duration" label="当前合作时长"></el-table-column>
        <el-table-column prop="main_cooperation_project" label="合作主要项目(内容)"></el-table-column>
        <el-table-column prop="total_cooperation_amount" label="合作总金额(人民币)"></el-table-column>
        <el-table-column prop="recent_three_years_cooperation_amount" label="最近三年合作总金额(人民币)"></el-table-column>
        <el-table-column prop="current_cooperation_status" label="当前合作状态"></el-table-column>
        <el-table-column prop="data_date" label="数据日期"></el-table-column>
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
        list:  [
          {
            supplier_id: 1,
            supplier_name: 'Supplier 1',
            first_application_time: '2021-01-01',
            first_cooperation_time: '2021-02-01',
            first_supplier_list_time: '2021-03-01',
            last_supplier_list_time: '2021-04-01',
            current_cooperation_duration: '1 year',
            main_cooperation_project: 'Project 1',
            total_cooperation_amount: 1000000,
            recent_three_years_cooperation_amount: 3000000,
            current_cooperation_status: 'Active',
            data_date: '2022-01-01'
          },
          {
            supplier_id: 2,
            supplier_name: 'Supplier 2',
            first_application_time: '2021-01-02',
            first_cooperation_time: '2021-02-02',
            first_supplier_list_time: '2021-03-02',
            last_supplier_list_time: '2021-04-02',
            current_cooperation_duration: '2 years',
            main_cooperation_project: 'Project 2',
            total_cooperation_amount: 2000000,
            recent_three_years_cooperation_amount: 4000000,
            current_cooperation_status: 'Inactive',
            data_date: '2022-01-02'
          },
          {
            supplier_id: 3,
            supplier_name: 'Supplier 3',
            first_application_time: '2021-01-03',
            first_cooperation_time: '2021-02-03',
            first_supplier_list_time: '2021-03-03',
            last_supplier_list_time: '2021-04-03',
            current_cooperation_duration: '3 years',
            main_cooperation_project: 'Project 3',
            total_cooperation_amount: 3000000,
            recent_three_years_cooperation_amount: 5000000,
            current_cooperation_status: 'Active',
            data_date: '2022-01-03'
          },
          {
            supplier_id: 4,
            supplier_name: 'Supplier 4',
            first_application_time: '2021-01-04',
            first_cooperation_time: '2021-02-04',
            first_supplier_list_time: '2021-03-04',
            last_supplier_list_time: '2021-04-04',
            current_cooperation_duration: '4 years',
            main_cooperation_project: 'Project 4',
            total_cooperation_amount: 4000000,
            recent_three_years_cooperation_amount: 6000000,
            current_cooperation_status: 'Inactive',
            data_date: '2022-01-04'
          },
          {
            supplier_id: 5,
            supplier_name: 'Supplier 5',
            first_application_time: '2021-01-05',
            first_cooperation_time: '2021-02-05',
            first_supplier_list_time: '2021-03-05',
            last_supplier_list_time: '2021-04-05',
            current_cooperation_duration: '5 years',
            main_cooperation_project: 'Project 5',
            total_cooperation_amount: 5000000,
            recent_three_years_cooperation_amount: 7000000,
            current_cooperation_status: 'Active',
            data_date: '2022-01-05'
          }
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
