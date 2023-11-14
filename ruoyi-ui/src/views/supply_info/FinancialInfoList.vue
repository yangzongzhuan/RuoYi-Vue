<template>
  <section style="margin: auto;background-color: #f9f9f9;padding-top: 15px;padding-bottom: 30px;padding-right: 15px;">
    <div style="margin-left: 15px;background-color: white;line-height: 70px;height: 70px;text-align: center">
      <el-autocomplete
        style="width: 610px;margin-left: 58px;"
        placeholder="请输入公司名称/统一社会信用代码"
        :trigger-on-focus="false"
      >
        <el-button slot="append" icon="el-icon-search" />
      </el-autocomplete>
    </div>
    <div style="margin-top: 10px;min-height: 300px;background-color: white;padding: 20px;margin-left: 15px;">
      <div style="font-size: 18px;font-weight: bolder">
        北京捷通华声科技股份有限公司
      </div>
      <div style="margin-top: 10px">
        <div class="c-data-h1">财务指标表</div>
        <table class="c-base-table" style="width: 100%;margin-top: 5px">
          <thead>
            <tr>
              <td class="c-th-header">序号</td>
              <td class="c-th-header"></td>
              <td class="c-th-header">最新数据</td>
              <td class="c-th-header">2022年度</td>
              <td class="c-th-header">同比值</td>
              <td class="c-th-header">2021年度</td>
              <td class="c-th-header">同比值</td>
            </tr>
            <tr>
              <td class="c-th-header">1</td>
              <td class="c-th-header">企业id</td>
              <td class="c-th-header">A12345678</td>
              <td class="c-th-header">A12345678</td>
              <td class="c-th-header">A12345678</td>
              <td class="c-th-header">A12345678</td>
              <td class="c-th-header">A12345678</td>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in finacialInfoList" :key="item.index">
              <td class="c-td-text">{{item.index}}</td>
              <td class="c-td-text">{{item.type}}</td>
              <td class="c-td-text">{{item.data_1}}</td>
              <td class="c-td-text">{{item.data_2}}</td>
              <td class="c-td-text">{{item.data_3}}</td>
              <td class="c-td-text">{{item.data_4}}</td>
              <td class="c-td-text">{{item.data_4}}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div style="margin-top: 10px">
        <div class="c-data-h1">资产负债表</div>
        <table class="c-base-table" style="width: 100%;margin-top: 5px">
          <thead>
          <tr>
            <td class="c-th-header">序号</td>
            <td class="c-th-header"></td>
            <td class="c-th-header">最新数据</td>
            <td class="c-th-header">2022年度</td>
            <td class="c-th-header">同比值</td>
            <td class="c-th-header">2021年度</td>
            <td class="c-th-header">同比值</td>
          </tr>
          <tr>
            <td class="c-th-header">1</td>
            <td class="c-th-header">企业id</td>
            <td class="c-th-header">A12345678</td>
            <td class="c-th-header">A12345678</td>
            <td class="c-th-header">A12345678</td>
            <td class="c-th-header">A12345678</td>
            <td class="c-th-header">A12345678</td>
          </tr>
          </thead>
          <tbody>
          <tr v-for="item in finacialInfoList" :key="item.index">
            <td class="c-td-text">{{item.index}}</td>
            <td class="c-td-text">{{item.type}}</td>
            <td class="c-td-text">{{item.data_1}}</td>
            <td class="c-td-text">{{item.data_2}}</td>
            <td class="c-td-text">{{item.data_3}}</td>
            <td class="c-td-text">{{item.data_4}}</td>
            <td class="c-td-text">{{item.data_4}}</td>
          </tr>
          </tbody>
        </table>
      </div>
      <div style="margin-top: 10px">
        <div class="c-data-h1">损益表</div>
        <table class="c-base-table" style="width: 100%;margin-top: 5px">
          <thead>
          <tr>
            <td class="c-th-header">序号</td>
            <td class="c-th-header"></td>
            <td class="c-th-header">最新数据</td>
            <td class="c-th-header">2022年度</td>
            <td class="c-th-header">同比值</td>
            <td class="c-th-header">2021年度</td>
            <td class="c-th-header">同比值</td>
          </tr>
          <tr>
            <td class="c-th-header">1</td>
            <td class="c-th-header">企业id</td>
            <td class="c-th-header">A12345678</td>
            <td class="c-th-header">A12345678</td>
            <td class="c-th-header">A12345678</td>
            <td class="c-th-header">A12345678</td>
            <td class="c-th-header">A12345678</td>
          </tr>
          </thead>
          <tbody>
          <tr v-for="item in finacialInfoList" :key="item.index">
            <td class="c-td-text">{{item.index}}</td>
            <td class="c-td-text">{{item.type}}</td>
            <td class="c-td-text">{{item.data_1}}</td>
            <td class="c-td-text">{{item.data_2}}</td>
            <td class="c-td-text">{{item.data_3}}</td>
            <td class="c-td-text">{{item.data_4}}</td>
            <td class="c-td-text">{{item.data_4}}</td>
          </tr>
          </tbody>
        </table>
      </div>
      <div style="margin-top: 10px">
        <div class="c-data-h1">现金流量表</div>
        <table class="c-base-table" style="width: 100%;margin-top: 5px">
          <thead>
          <tr>
            <td class="c-th-header">序号</td>
            <td class="c-th-header"></td>
            <td class="c-th-header">最新数据</td>
            <td class="c-th-header">2022年度</td>
            <td class="c-th-header">同比值</td>
            <td class="c-th-header">2021年度</td>
            <td class="c-th-header">同比值</td>
          </tr>
          <tr>
            <td class="c-th-header">1</td>
            <td class="c-th-header">企业id</td>
            <td class="c-th-header">A12345678</td>
            <td class="c-th-header">A12345678</td>
            <td class="c-th-header">A12345678</td>
            <td class="c-th-header">A12345678</td>
            <td class="c-th-header">A12345678</td>
          </tr>
          </thead>
          <tbody>
          <tr v-for="item in finacialInfoList" :key="item.index">
            <td class="c-td-text">{{item.index}}</td>
            <td class="c-td-text">{{item.type}}</td>
            <td class="c-td-text">{{item.data_1}}</td>
            <td class="c-td-text">{{item.data_2}}</td>
            <td class="c-td-text">{{item.data_3}}</td>
            <td class="c-td-text">{{item.data_4}}</td>
            <td class="c-td-text">{{item.data_4}}</td>
          </tr>
          </tbody>
        </table>
      </div>
      <div style="margin-top: 10px">
        <div class="c-data-h1">现金流量表</div>
        <table class="c-base-table" style="width: 100%;margin-top: 5px">
          <thead>
          <tr>
            <td class="c-th-header">序号</td>
            <td class="c-th-header"></td>
            <td class="c-th-header">最新数据</td>
            <td class="c-th-header">2022年度</td>
            <td class="c-th-header">同比值</td>
            <td class="c-th-header">2021年度</td>
            <td class="c-th-header">同比值</td>
          </tr>
          <tr>
            <td class="c-th-header">1</td>
            <td class="c-th-header">企业id</td>
            <td class="c-th-header">A12345678</td>
            <td class="c-th-header">A12345678</td>
            <td class="c-th-header">A12345678</td>
            <td class="c-th-header">A12345678</td>
            <td class="c-th-header">A12345678</td>
          </tr>
          </thead>
          <tbody>
          <tr v-for="item in finacialInfoList" :key="item.index">
            <td class="c-td-text">{{item.index}}</td>
            <td class="c-td-text">{{item.type}}</td>
            <td class="c-td-text">{{item.data_1}}</td>
            <td class="c-td-text">{{item.data_2}}</td>
            <td class="c-td-text">{{item.data_3}}</td>
            <td class="c-td-text">{{item.data_4}}</td>
            <td class="c-td-text">{{item.data_4}}</td>
          </tr>
          </tbody>
        </table>
      </div>

    </div>
  </section>
</template>
<script>
export default({
  name: "FinancialInfoList",
  data() {
    return {
      finacialInfoList: [
        {
          index: 2, type: '企业名称', data_1: '北京捷通华声科技股份有限公司',data_2: '北京捷通华声科技股份有限公司',data_3: '北京捷通华声科技股份有限公司',data_4: '北京捷通华声科技股份有限公司',data_5: '北京捷通华声科技股份有限公司'
        },
        {
          index: 3, type: '营业总收入（元）',data_1: 0,data_2: 0,data_3: 0,data_4: 0,data_5: 0
        },
        {
          index: 4, type: '营业收入（元）',data_1: 0,data_2: 0,data_3: 0,data_4: 0,data_5: 0
        },
        {
          index: 5, type: '营业总成本（元）',data_1: 0,data_2: 0,data_3: 0,data_4: 0,data_5: 0
        },
        {
          index: 6, type: '营业成本（元）',data_1: 0,data_2: 0,data_3: 0,data_4: 0,data_5: 0
        },
        {
          index: 7, type: '营业税金及附加（元）',data_1: 0,data_2: 0,data_3: 0,data_4: 0,data_5: 0
        },
        {
          index: 8, type: '销售费用（元）',data_1: 0,data_2: 0,data_3: 0,data_4: 0,data_5: 0
        },
        {
          index: 9, type: '管理费用（元）',data_1: 0,data_2: 0,data_3: 0,data_4: 0,data_5: 0
        },
        {
          index: 10, type: '财务费用（元）',data_1: 0,data_2: 0,data_3: 0,data_4: 0,data_5: 0
        },
        {
          index: 11, type: '营业总收入同比增长(%)',data_1: 0,data_2: 0,data_3: 0,data_4: 0,data_5: 0
        },
        {
          index: 12, type: '营业收入同比增长(%)',data_1: 0,data_2: 0,data_3: 0,data_4: 0,data_5: 0
        },
        {
          index: 13, type: '营业总成本同比增长(%)',data_1: 0,data_2: 0,data_3: 0,data_4: 0,data_5: 0
        }
      ],
    }
  }
})
</script>
<style scoped lang="scss">
.c-data-h1{
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
.c-td-text-padding{
}
</style>
