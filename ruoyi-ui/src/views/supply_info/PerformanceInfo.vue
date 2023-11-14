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
      <div class="c-data-h1">履约信息汇总表</div>

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
        <el-table-column label="供应商ID" prop="gys_name"></el-table-column>
        <el-table-column label="供应商名称" prop="he_tong_bian_hao"></el-table-column>
        <el-table-column label="历史是否出现过延期交付" prop="he_tong_ming_cheng"></el-table-column>
        <el-table-column label="延期交付次数" prop="he_tong_qian_ding_shi_jian"></el-table-column>
        <el-table-column label="产品质量不合格次数" prop="he_tong_dao_qi_shi_jian"></el-table-column>
        <el-table-column label="产品或服务被投诉次数" prop="he_tong_jin_e"></el-table-column>
        <el-table-column label="是否出现过安全生产事故" prop="jiao_fu_shi_jian"></el-table-column>
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
  name: "BusinessInfo",
  components: { Pagination },
  data(){
    return {
      dialogVisibleDetail: false,
      dataGrid: {
        list: [
          {
            gys_name: "a1c1d3e4-1234-5678-9abc-123456789abc",
            he_tong_bian_hao: "ABC Company",
            he_tong_ming_cheng: true,
            he_tong_qian_ding_shi_jian: 1,
            he_tong_dao_qi_shi_jian: 2,
            he_tong_jin_e: 3,
            jiao_fu_shi_jian: false
          },
          {
            gys_name: "b2c2d4e5-2345-6789-abcd-23456789abcd",
            he_tong_bian_hao: "XYZ Corporation",
            he_tong_ming_cheng: false,
            he_tong_qian_ding_shi_jian: 4,
            he_tong_dao_qi_shi_jian: 5,
            he_tong_jin_e: 6,
            jiao_fu_shi_jian: true
          },
          {
            gys_name: "c3d3e6f7-3456-789a-bcde-3456789abcde",
            he_tong_bian_hao: "123 Industries",
            he_tong_ming_cheng: true,
            he_tong_qian_ding_shi_jian: 7,
            he_tong_dao_qi_shi_jian: 8,
            he_tong_jin_e: 9,
            jiao_fu_shi_jian: false
          },
          {
            gys_name: "d4e4f8g9-4567-89ab-cdef-456789abcdef",
            he_tong_bian_hao: "456 Enterprises",
            he_tong_ming_cheng: false,
            he_tong_qian_ding_shi_jian: 10,
            he_tong_dao_qi_shi_jian: 11,
            he_tong_jin_e: 12,
            jiao_fu_shi_jian: true
          },
          {
            gys_name: "e5f5g0h1-5678-9abc-defg-56789abcdefg",
            he_tong_bian_hao: "789 LLC",
            he_tong_ming_cheng: true,
            he_tong_qian_ding_shi_jian: 13,
            he_tong_dao_qi_shi_jian: 14,
            he_tong_jin_e: 15,
            jiao_fu_shi_jian: false
          },
          {
            gys_name: "f6g6h2i3-6789-abcd-efgh-6789abcdefgh",
            he_tong_bian_hao: "XYZ Corporation",
            he_tong_ming_cheng: false,
            he_tong_qian_ding_shi_jian: 16,
            he_tong_dao_qi_shi_jian: 17,
            he_tong_jin_e: 18,
            jiao_fu_shi_jian: true
          },
          {
            gys_name: "g7h7i4j5-789a-bcde-fghi-789abcdefg",
            he_tong_bian_hao: "123 Industries",
            he_tong_ming_cheng: true,
            he_tong_qian_ding_shi_jian: 19,
            he_tong_dao_qi_shi_jian: 20,
            he_tong_jin_e: 21,
            jiao_fu_shi_jian: false
          },
          {
            gys_name: "h8i8j6k7-89ab-cdef-ghij-89abcdefghi",
            he_tong_bian_hao: "456 Enterprises",
            he_tong_ming_cheng: false,
            he_tong_qian_ding_shi_jian: 22,
            he_tong_dao_qi_shi_jian: 23,
            he_tong_jin_e: 24,
            jiao_fu_shi_jian: true
          },
          {
            gys_name: "i9j9k8l9-9abc-defg-hijk-9abcdefghijkl",
            he_tong_bian_hao: "789 LLC",
            he_tong_ming_cheng: true,
            he_tong_qian_ding_shi_jian: 25,
            he_tong_dao_qi_shi_jian: 26,
            he_tong_jin_e: 27,
            jiao_fu_shi_jian: false
          },
          {
            gys_name: "j0k0l1m2-abcde-fghi-jklm-abcdefghijkl",
            he_tong_bian_hao: "ABC Company",
            he_tong_ming_cheng: false,
            he_tong_qian_ding_shi_jian: 28,
            he_tong_dao_qi_shi_jian: 29,
            he_tong_jin_e: 30,
            jiao_fu_shi_jian: true
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
