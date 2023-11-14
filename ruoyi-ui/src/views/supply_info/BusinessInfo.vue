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
      <div class="c-data-h1">财务指标表</div>

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
        <el-table-column label="供应商名称" prop="gys_name"></el-table-column>
        <el-table-column label="合同编号" prop="he_tong_bian_hao"></el-table-column>
        <el-table-column label="合同名称" prop="he_tong_ming_cheng">
          <el-table-column label="详情">
            <template slot-scope="scope">
              <el-link size="mini" type="primary" @click="dialogVisibleDetail = true">{{scope.row.he_tong_ming_cheng}}</el-link>
            </template>
          </el-table-column>
        </el-table-column>
        <el-table-column label="合同签订时间" prop="he_tong_qian_ding_shi_jian"></el-table-column>
        <el-table-column label="合同到期时间" prop="he_tong_dao_qi_shi_jian"></el-table-column>
        <el-table-column label="合同金额" prop="he_tong_jin_e"></el-table-column>
        <el-table-column label="交付时间" prop="jiao_fu_shi_jian"></el-table-column>
        <el-table-column label="付款方式" prop="fu_kuan_fang_shi"></el-table-column>
        <el-table-column label="合作状态" prop="he_zuo_zhuang_tai"></el-table-column>
        <el-table-column label="服务形式" prop="fu_wu_xing_shi"></el-table-column>
        <el-table-column label="产品规格" prop="chan_pin_gui_ge"></el-table-column>
        <el-table-column label="产品数量" prop="chan_pin_shu_liang"></el-table-column>
        <el-table-column label="是否延期" prop="shi_fou_yan_qi"></el-table-column>
        <el-table-column label="数据日期" prop="shu_ju_ri_qi"></el-table-column>
      </el-table>
      <pagination style="text-align: right;" :total="dataGrid.total" :page.sync="dataGrid.listQuery.page" :limit.sync="dataGrid.listQuery.limit" @pagination="getList" />
    </div>
    <el-dialog
      :visible.sync="dialogVisibleDetail"
      title="合同详情"
    >
      <div class="c-data-h1">
        合同信息表
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
            <td class="c-td-title">最近一笔付款金额：</td>
            <td class="c-td-text">10万</td>
            <td class="c-td-title">已付款金额：</td>
            <td class="c-td-text">10万</td>
          </tr>
          <tr>
            <td class="c-td-title">最近一笔付款时间：</td>
            <td class="c-td-text">2023-10-25</td>
            <td class="c-td-title">首次付款时间：</td>
            <td class="c-td-text">2023-10-08</td>
          </tr>
          <tr>
            <td class="c-td-title">剩余付款金额：</td>
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
            gys_id: 123456,
            gys_name: 'ABC公司',
            he_tong_bian_hao: 'HTBH123',
            he_tong_ming_cheng: '合同名称1',
            he_tong_qian_ding_shi_jian: '2022-01-01',
            he_tong_dao_qi_shi_jian: '2022-12-31',
            he_tong_jin_e: 10000,
            jiao_fu_shi_jian: '2022-02-01',
            fu_kuan_fang_shi: '银行转账',
            he_zuo_zhuang_tai: '已合作',
            fu_wu_xing_shi: '售后服务',
            chan_pin_gui_ge: '规格1',
            chan_pin_shu_liang: 100,
            shi_fou_yan_qi: false,
            shu_ju_ri_qi: '2022-05-01'
          },
          {
            gys_id: 654321,
            gys_name: 'XYZ公司',
            he_tong_bian_hao: 'HTBH456',
            he_tong_ming_cheng: '合同名称2',
            he_tong_qian_ding_shi_jian: '2022-02-01',
            he_tong_dao_qi_shi_jian: '2022-12-31',
            he_tong_jin_e: 20000,
            jiao_fu_shi_jian: '2022-03-01',
            fu_kuan_fang_shi: '支付宝',
            he_zuo_zhuang_tai: '未合作',
            fu_wu_xing_shi: '技术支持',
            chan_pin_gui_ge: '规格2',
            chan_pin_shu_liang: 200,
            shi_fou_yan_qi: true,
            shu_ju_ri_qi: '2022-05-02'
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
