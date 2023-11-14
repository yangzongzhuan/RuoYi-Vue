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
      <div class="c-data-h1">黑名单汇总信息</div>

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
        <el-table-column prop="enterpriseId" label="企业ID"></el-table-column>
        <el-table-column prop="enterpriseName" label="企业名称"></el-table-column>
        <el-table-column prop="registeredCapital" label="注册资本"></el-table-column>
        <el-table-column prop="establishmentDate" label="成立日期"></el-table-column>
        <el-table-column prop="legalRepresentative" label="法定代表人"></el-table-column>
        <el-table-column prop="blacklistType" label="黑名单类型"></el-table-column>
        <el-table-column prop="blacklistReason" label="纳入黑名单原因"></el-table-column>
        <el-table-column prop="recognizingDepartment" label="认定部门"></el-table-column>
        <el-table-column prop="dataSource" label="数据来源"></el-table-column>
        <el-table-column prop="isRemoved" label="是否移除"></el-table-column>
        <el-table-column prop="removalReason" label="移除原因"></el-table-column>
        <el-table-column prop="dataDate" label="数据日期"></el-table-column>
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
      title="黑名单详细信息"
    >
      <div class="c-data-h1">
        经营异常
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
  name: "BlacklistInfo",
  components: { Pagination },
  data(){
    return {
      dialogVisibleDetail: false,
      dataGrid: {
        list: [
            {
              enterpriseId: '1001',
              enterpriseName: '北京科技有限公司',
              registeredCapital: '1000万',
              establishmentDate: '2010-01-01',
              legalRepresentative: '张三',
              blacklistType: 'A级',
              blacklistReason: '违法违规',
              recognizingDepartment: '北京市工商局',
              dataSource: '公开信息',
              isRemoved: '否',
              removalReason: '',
              dataDate: '2021-10-01'
            },
            {
              enterpriseId: '1002',
              enterpriseName: '上海科技有限公司',
              registeredCapital: '2000万',
              establishmentDate: '2011-01-01',
              legalRepresentative: '李四',
              blacklistType: 'B级',
              blacklistReason: '欺诈行为',
              recognizingDepartment: '上海市工商局',
              dataSource: '公开信息',
              isRemoved: '否',
              removalReason: '',
              dataDate: '2021-10-02'
            },
            {
              enterpriseId: '1003',
              enterpriseName: '广州科技有限公司',
              registeredCapital: '3000万',
              establishmentDate: '2012-01-01',
              legalRepresentative: '王五',
              blacklistType: 'C级',
              blacklistReason: '环保问题',
              recognizingDepartment: '广州市工商局',
              dataSource: '公开信息',
              isRemoved: '否',
              removalReason: '',
              dataDate: '2021-10-03'
            },
            {
              enterpriseId: '1004',
              enterpriseName: '深圳科技有限公司',
              registeredCapital: '4000万',
              establishmentDate: '2013-01-01',
              legalRepresentative: '赵六',
              blacklistType: 'D级',
              blacklistReason: '质量问题',
              recognizingDepartment: '深圳市工商局',
              dataSource: '公开信息',
              isRemoved: '否',
              removalReason: '',
              dataDate: '2021-10-04'
            },
            {
              enterpriseId: '1005',
              enterpriseName: '成都科技有限公司',
              registeredCapital: '5000万',
              establishmentDate: '2014-01-01',
              legalRepresentative: '钱七',
              blacklistType: 'E级',
              blacklistReason: '安全问题',
              recognizingDepartment: '成都市工商局',
              dataSource: '公开信息',
              isRemoved: '否',
              removalReason: '',
              dataDate: '2021-10-05'
            },
            {
              enterpriseId: '1006',
              enterpriseName: '武汉科技有限公司',
              registeredCapital: '6000万',
              establishmentDate: '2015-01-01',
              legalRepresentative: '孙八',
              blacklistType: 'F级',
              blacklistReason: '违规操作',
              recognizingDepartment: '武汉市工商局',
              dataSource: '公开信息',
              isRemoved: '否',
              removalReason: '',
              dataDate: '2021-10-06'
            },
            {
              enterpriseId: '1007',
              enterpriseName: '南京科技有限公司',
              registeredCapital: '7000万',
              establishmentDate: '2016-01-01',
              legalRepresentative: '周九',
              blacklistType: 'G级',
              blacklistReason: '违反规定',
              recognizingDepartment: '南京市工商局',
              dataSource: '公开信息',
              isRemoved: '否',
              removalReason: '',
              dataDate: '2021-10-07'
            },
            {
              enterpriseId: '1008',
              enterpriseName: '杭州科技有限公司',
              registeredCapital: '8000万',
              establishmentDate: '2017-01-01',
              legalRepresentative: '吴十',
              blacklistType: 'H级',
              blacklistReason: '违法行为',
              recognizingDepartment: '杭州市工商局',
              dataSource: '公开信息',
              isRemoved: '否',
              removalReason: '',
              dataDate: '2021-10-08'
            },
            {
              enterpriseId: '1009',
              enterpriseName: '重庆科技有限公司',
              registeredCapital: '9000万',
              establishmentDate: '2018-01-01',
              legalRepresentative: '郑十一',
              blacklistType: 'I级',
              blacklistReason: '违规操作',
              recognizingDepartment: '重庆市工商局',
              dataSource: '公开信息',
              isRemoved: '否',
              removalReason: '',
              dataDate: '2021-10-09'
            },
            {
              enterpriseId: '1010',
              enterpriseName: '西安科技有限公司',
              registeredCapital: '10000万',
              establishmentDate: '2019-01-01',
              legalRepresentative: '赵十二',
              blacklistType: 'J级',
              blacklistReason: '违反规定',
              recognizingDepartment: '西安市工商局',
              dataSource: '公开信息',
              isRemoved: '否',
              removalReason: '',
              dataDate: '2021-10-10'
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
