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
    <div style="margin-left: 15px;">
      <div v-loading="loading" style="margin-top: 10px;">
        <div id="s-header-body">
          <div style="padding-left: 20px;line-height: 40px;height: 40px;background-color: white;font-size: 12px">
            为您找到 <span style="color: #2E4E8F;font-weight: bolder">{{ total }}</span> 家符合条件的企业，此处最多展示10000条。
          </div>
          <div v-for="(item,index) in top400List" :key="index" style="background-color: white;">
            <el-card class="box-card" shadow="hover" style="cursor: pointer;margin-top: 8px" @click.native="viewDetailInfo(item)">
              <div slot="header" class="clearfix">
                <span style="height:30px;font-size:14px;font-family:Microsoft YaHei;font-weight:400;color:rgba(68,68,68,1);line-height:30px;">{{ item.entname }}</span>
                <span> {{ item.enstatus_cn }} </span>
              </div>
              <div>
                <table style="font-size: 14px;font-family: 'Microsoft YaHei'">
                  <tbody>
                  <tr>
                    <td width="5%" align="left" style="height:14px;font-size:12px;font-family:Microsoft YaHei;font-weight:400;color:rgba(161,161,161,1);line-height:24px;">法定代表人：</td>
                    <td width="15%" style="height:14px;font-size:12px;font-family:Microsoft YaHei;font-weight:400;color:rgba(161,161,161,1);line-height:24px;"> {{ item.legal_name }}</td>
                    <td width="5%" align="left" style="height:14px;font-size:12px;font-family:Microsoft YaHei;font-weight:400;color:rgba(161,161,161,1);line-height:24px;">注册资本：</td>
                    <td width="30%" style="height:14px;font-size:12px;font-family:Microsoft YaHei;font-weight:400;color:rgba(161,161,161,1);line-height:24px;">{{ item.regcap.toLocaleString() }}{{ item.regcapcur_cn }}</td>
                  </tr>
                  <tr>
                    <td align="left" style="height:14px;font-size:12px;font-family:Microsoft YaHei;font-weight:400;color:rgba(161,161,161,1);line-height:24px;">成立时间：</td>
                    <td style="height:14px;font-size:12px;font-family:Microsoft YaHei;font-weight:400;color:rgba(161,161,161,1);line-height:24px;">{{ item.regdate }}</td>
                    <td align="left" style="height:12px;font-size:14px;font-family:Microsoft YaHei;font-weight:400;color:rgba(161,161,161,1);line-height:24px;padding-top: 10px;">登记地址：</td>
                    <td style="height:14px;font-size:12px;font-family:Microsoft YaHei;font-weight:400;color:rgba(161,161,161,1);line-height:24px;padding-top: 10px;text-overflow:ellipsis;display:-webkit-box;-webkit-box-orient:vertical;-webkit-line-clamp:2;">{{ item.regaddr }}</td>
                  </tr>
                  </tbody>
                </table>
              </div>

            </el-card>
          </div>
          <div class="block" style="text-align: right;width: 100%;">
            <el-pagination
              :current-page="currentPage"
              :page-size="pageSize"
              :total="total"
              layout="total, prev,pager, next"
              @current-change="handleCurrentChange"
            />
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
export default ({
  name: "SupplyGraphDetail",
  data() {
    return {
      loading: false,
      total: 0,
      currentPage: 1,
      pageSize: 10,
      top400List: [
        {
          "uniscid": "93411722MA468HH679",
          "regcapcur_cn": "万",
          "entid": "8AA60AEF45497083E0539601A8C0F8056",
          "entname": "上蔡县红红林木种植专业合作社",
          "regcap": 1.3683883396E10,
          "regdate": "2019-01-09",
          "tel": null,
          "legal_name": "王金红",
          "enstatus_cn": "在营",
          "regaddr": "上蔡县朱里镇东梁村",
          "email": null
        },
        {
          "uniscid": "91500105MA5UPC504U",
          "regcapcur_cn": "万人民币元",
          "entid": "6F274E588A767C57E0539601A8C0ACD65",
          "entname": "周生生（中国）商业有限公司重庆国金中心分店",
          "regcap": 2.5E9,
          "regdate": "2017-07-06",
          "tel": "023-67656552",
          "legal_name": "周琴",
          "enstatus_cn": "在营",
          "regaddr": "重庆市江北区江北城北大街38号国金中心二层L207商铺",
          "email": null
        },
        {
          "uniscid": "91500241MAABQTNR3Y",
          "regcapcur_cn": "万元人民币",
          "entid": "C3351EF2AA3542B4A1B9C25112903E614",
          "entname": "宏富润国物流重庆有限公司",
          "regcap": 1.399999999E9,
          "regdate": "2021-05-26",
          "tel": null,
          "legal_name": "谢继宏",
          "enstatus_cn": "在营",
          "regaddr": "重庆市秀山土家族苗族自治县乌杨街道园区路21号(县工业园区内周转房D栋7-263号)",
          "email": null
        },
        {
          "uniscid": "91440402MA56CJ922C",
          "regcapcur_cn": "万",
          "entid": "73E084574F0048D8B2EEEF8E3051138A4",
          "entname": "广东金义达建设工程有限公司",
          "regcap": 1.3E9,
          "regdate": "2021-04-30",
          "tel": null,
          "legal_name": "林静",
          "enstatus_cn": "在营",
          "regaddr": "珠海市香洲区兴国街4号中立信大厦304房A区",
          "email": null
        },
        {
          "uniscid": "91120105MA077KE64N",
          "regcapcur_cn": "万人民币",
          "entid": "10F21D184ECA4DA5AD1D563E045812524",
          "entname": "鑫源鸿昊（天津）科技有限公司",
          "regcap": 1.0E9,
          "regdate": "2020-12-23",
          "tel": null,
          "legal_name": "刘玉华",
          "enstatus_cn": "在营",
          "regaddr": "天津市河北区光复道街道悦海大厦1.2-318",
          "email": null
        },
        {
          "uniscid": "91120105MA077GQX05",
          "regcapcur_cn": "万人民币",
          "entid": "6BE7547DA06E4D7BB5EFFFBBFD26E3154",
          "entname": "天津善美文化传媒有限公司",
          "regcap": 8.3E8,
          "regdate": "2020-12-21",
          "tel": null,
          "legal_name": "张昊野",
          "enstatus_cn": "在营",
          "regaddr": "天津市河北区光复道街悦海大厦1.2-201-184",
          "email": null
        },
        {
          "uniscid": "91310114MA1GWLBF8W",
          "regcapcur_cn": "万人民币",
          "entid": "7C7E15ADE802491FBF4E96DEF85D324F4",
          "entname": "四币同铸企业发展（上海）有限公司",
          "regcap": 7.692E8,
          "regdate": "2019-11-02",
          "tel": null,
          "legal_name": "朱莉",
          "enstatus_cn": "在营",
          "regaddr": "上海市静安区新闸路669号4050室（实际楼层35层）",
          "email": null
        },
        {
          "uniscid": null,
          "regcapcur_cn": ",,万元",
          "entid": "35BF26B5F5AC4D3E84D282982C78896E4",
          "entname": "安徽玉龙地智慧餐饮有限公司",
          "regcap": 5.00002E8,
          "regdate": "2015-07-01",
          "tel": null,
          "legal_name": "陈林",
          "enstatus_cn": "在营",
          "regaddr": "合肥市庐阳区红星路143号",
          "email": null
        },
        {
          "uniscid": "91210242MA10ETFR0T",
          "regcapcur_cn": "万人民币",
          "entid": "92A6E8A291534F328C7244C71E2234314",
          "entname": "大连倡青建筑劳务服务有限公司",
          "regcap": 3.6E8,
          "regdate": "2020-06-15",
          "tel": null,
          "legal_name": "滕人良",
          "enstatus_cn": "在营",
          "regaddr": "辽宁省大连保税区自贸大厦813室",
          "email": null
        },
        {
          "uniscid": "91371726MA3U6BUH25",
          "regcapcur_cn": "万",
          "entid": "E9F44895854D45D8B991F8A3710C0A7E4",
          "entname": "鄄城县赢城化工有限公司",
          "regcap": 3.5E8,
          "regdate": "2020-10-16",
          "tel": null,
          "legal_name": "常鲁鲁",
          "enstatus_cn": "在营",
          "regaddr": "山东省菏泽市鄄城县永定路化工园区",
          "email": null
        }
      ],
      searchKey: '',
      searchType: 'entname',
      searchTypeOptions: [
        { value: 'entname', label: '企业名称' },
        { value: 'uniscid', label: '统一社会信用代码' }
      ]
    }
  },
  methods: {
    viewDetailInfo(item) {
      this.$router.push({
        path: '/supplier_info/supplyOneDetail',
      })
    },
  }
})

</script>
<style>

</style>
<style scoped>
/deep/ .el-cascader-menu__wrap{
  height: 234px;
}
/deep/ .el-popper .el-input--medium .el-input__inner{
  height: 26px;
  line-height: 26px;
}
/deep/ .el-popover{
  height: auto;
}
/deep/ .el-card__header{
  padding: 5px 8px;
}
/deep/ .el-card__body{
  padding: 4px 8px;
}
/deep/ .el-loading-mask{
  z-index: 1998;
}
/deep/ .el-checkbox-button__inner{
  border: 0px white solid;
}

.c-title-td{
  height:22px;
  font-size:14px;
  font-family:Microsoft YaHei;
  font-weight:400;
  color:rgba(169,169,169,1);
  line-height:22px;
}
.c-title-option{
  height:22px;
  line-height:22px;
  font-size:14px;
  font-family:Microsoft YaHei;
  cursor: pointer;
  font-weight:400;
  padding-bottom: 5px;
  color:rgba(114,114,114,1);
}
.c-options-text{
  float: left;
  height:30px;
  font-size:14px;
  margin-left: 20px;
  padding-top: 2px;
  font-family:Microsoft YaHei;
  font-weight:400;
  color:rgba(114,114,114,1);
  line-height:30px;
}
.c-options-select{
  float: left;
  height:30px;
  font-size:14px;
  margin-left: 20px;
  padding-top: 2px;
  font-family:Microsoft YaHei;
  font-weight:bolder;
  color:#66b1ff;
  line-height:30px;
}
.c-self-selectd{
  color:#66b1ff;
}
.c-suggestion-name {
  width: 60%;
  float: left;

}
.c-suggestion-addr {
  text-overflow: ellipsis;
  overflow: hidden;
  width: 40%;
  font-size: 12px;
  float: left;
  text-align: right;
  color: #43a2f1;
}
.c-suggestion-uniscid{
  width: 60%;
  float: left;
  font-size: 12px;
  color: #b4b4b4;
}
</style>
