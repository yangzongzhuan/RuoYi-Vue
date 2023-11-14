<template>
  <div id="app" style="margin: auto;background-color: #f9f9f9;padding-top: 15px;padding-bottom: 30px;padding-right: 15px;padding-left: 15px;min-height: 900px">
    <div style="background-color: white;line-height: 70px;height: 70px;text-align: center">
      <el-autocomplete
        v-model="dataGrid.listQuery.keywords"
        :fetch-suggestions="querySearchAsync"
        placeholder="请输入公司名称/统一社会信用代码"
        clearable
        style="width: 610px;"
        class="filter-item"
        @input="handleInput"
        @select="handleSelect"
        @keyup.enter.native="handleFilter"
      >
        <el-button slot="append" icon="el-icon-search" @click="handleFilter" />
      </el-autocomplete>
    </div>
    <div style="clear: both;background-color: white;margin-top: 15px;min-height: 350px">
      <div style="height: 40px;padding: 10px">
        <span style="font-weight: bolder;color: black;font-size: 15px">北京阿里巴巴科技有限公司</span>
      </div>
      <div style="min-height: 300px;padding: 15px">
        <div class="c-data-h1">工商信息</div>
        <table class="c-base-table" style="width: 97%;margin-top: 5px">
          <tr>
            <td class="c-td-title" style="width: 13%">企业名称</td>
            <td class="c-td-text" style="width: 35%"> {{ currentBaseInfo.entname }}</td>
            <td class="c-td-title">注册资本</td>
            <td class="c-td-text"> {{ currentBaseInfo.regcap }}万{{ currentBaseInfo.regcapcur_cn }}</td>
          </tr>
          <tr>
            <td class="c-td-title" style="width: 13%">实缴资本<i class="el-icon-info" style="color: white" /></td>
            <td class="c-td-text" style="width: 35%"> - </td>
            <td class="c-td-title" style="width: 13%">实缴比例<i class="el-icon-info" style="color: white" /></td>
            <td class="c-td-text" style="width: 35%"> - </td>
          </tr>
          <tr>
            <td class="c-td-title" style="width: 13%">统一社会信用代码</td>
            <td class="c-td-text" style="width: 35%"> {{ currentBaseInfo.uniscid }}</td>
            <td class="c-td-title" style="width: 13%">工商注册号</td>
            <td class="c-td-text" style="width: 35%">{{ currentBaseInfo.regno }}</td>
          </tr>
          <tr>
            <td class="c-td-title">法定代表人</td>
            <td class="c-td-text"> {{ currentBaseInfo.legal_name }}</td>
            <td class="c-td-title">企业类型</td>
            <td class="c-td-text"> {{ currentBaseInfo.enttype }}-{{ currentBaseInfo.enttype_cn }}</td>
          </tr>
          <tr>
            <td class="c-td-title">注册日期</td>
            <td class="c-td-text"> {{ currentBaseInfo.regdate }}</td>
            <td class="c-td-title">核准日期</td>
            <td class="c-td-text"> {{ currentBaseInfo.apprdate }}</td>
          </tr>
          <tr>
            <td class="c-td-title">经营期限自</td>
            <td class="c-td-text"> {{ currentBaseInfo.opfrom }}</td>
            <td class="c-td-title">经营期限至</td>
            <td class="c-td-text">{{ currentBaseInfo.opto }}</td></tr>
          <tr>
            <td class="c-td-title">企业人员规模<i class="el-icon-info" style="color: gray" /></td>
            <td class="c-td-text"> {{ currentBaseInfo.empnum }} </td>
            <td class="c-td-title">参保总人数规模<i class="el-icon-info" style="color: gray" /></td>
            <td class="c-td-text"> - </td>
          </tr>
          <tr>
            <td class="c-td-title">登记机关</td>
            <td class="c-td-text"> {{ currentBaseInfo.regorg_cn }}</td>
            <td class="c-td-title">经营状态</td>
            <td class="c-td-text"><span> {{ currentBaseInfo.entstatus_cn }}</span></td>
          </tr>
          <tr>
            <td class="c-td-title">注册地址</td>
            <td class="c-td-text"> {{ currentBaseInfo.regaddr }}</td>
            <td class="c-td-title">注册地所在行政区<i class="el-icon-info" style="color: gray" /></td>
            <td class="c-td-text"> {{ currentBaseInfo.province }} - {{ currentBaseInfo.city }} - {{ currentBaseInfo.district }}</td>
          </tr>
          <tr>
            <td class="c-td-title" valign="top">经营范围</td>
            <td class="c-td-text" colspan="3" style="line-height: 30px"> {{ currentBaseInfo.opscope }}</td>
          </tr>
        </table>
      </div>
      <div style="min-height: 150px;padding: 15px">
        <div class="c-data-h1">股权信息</div>
        <div>
          <el-table
            :data="entbase_invs"
            border
            :header-row-style="{height:'40px',fontSize: '13px',backgroundColor: '#fcfcfc'}"
            :header-cell-style="{padding:'0px',backgroundColor: '#fcfcfc'}"
            :row-style="{height:'40px',fontSize: '13px'}"
            :cell-style="{padding:'0px'}"
            style="width: 97%;margin-top: 5px;"
          >
            <el-table-column label="序号" align="center" width="60">
              <template slot-scope="scope">{{ scope.$index+1 }}</template>
            </el-table-column>
            <el-table-column width="120" label="股东名称" align="center" prop="invname"/>
            <el-table-column label="持股比例" align="center" prop="invratio" />
            <el-table-column width="220" label="认缴出资额（万元）" align="center" prop="inv_r_amount" />
            <el-table-column width="220" label="实缴出资额（万元）" align="center" prop="inv_s_amount" />
            <el-table-column label="出资日期" align="center" prop="invdate" />
          </el-table>
        </div>
      </div>
      <div style="min-height: 150px;padding: 15px">
        <div class="c-data-h1">招投标信息</div>
        <div>
          <el-table
            :data="entbase_bidding"
            border
            :header-row-style="{height:'40px',fontSize: '13px',backgroundColor: '#fcfcfc'}"
            :header-cell-style="{padding:'0px',backgroundColor: '#fcfcfc'}"
            :row-style="{height:'40px',fontSize: '13px'}"
            :cell-style="{padding:'0px'}"
            style="width: 97%;margin-top: 5px;"
          >
            <el-table-column label="序号" align="center" width="60">
              <template slot-scope="scope">{{ scope.$index+1 }}</template>
            </el-table-column>
            <el-table-column width="120" label="描述" align="center" prop="invname"/>
            <el-table-column label="所属地区" align="center" prop="invratio" />
            <el-table-column width="220" label="项目分类" align="center" prop="inv_r_amount" />
            <el-table-column width="220" label="发布日期" align="center" prop="inv_s_amount" />
          </el-table>
        </div>
      </div>
      <div style="min-height: 150px;padding: 15px">
        <div class="c-data-h1">知识产权信息</div>
        <div style="padding: 15px">
          <div style="height: 30px;line-height: 30px;font-size: 13px">
            专利信息
          </div>
          <div>
            <el-table
              :data="entbase_bidding"
              border
              :header-row-style="{height:'40px',fontSize: '13px',backgroundColor: '#fcfcfc'}"
              :header-cell-style="{padding:'0px',backgroundColor: '#fcfcfc'}"
              :row-style="{height:'40px',fontSize: '13px'}"
              :cell-style="{padding:'0px'}"
              style="width: 97%;margin-top: 5px;"
            >
              <el-table-column label="序号" align="center" width="60">
                <template slot-scope="scope">{{ scope.$index+1 }}</template>
              </el-table-column>
              <el-table-column label="专利类型" align="center" prop="invname"/>
              <el-table-column label="公开号" align="center" prop="invratio" />
              <el-table-column label="公开日期" align="center" prop="inv_r_amount" />
              <el-table-column label="专利名称" align="center" prop="inv_s_amount" />
              <el-table-column label="IPC分类号" align="center" prop="inv_s_amount" />
              <el-table-column label="CPC分类号" align="center" prop="inv_s_amount" />
              <el-table-column label="详情" align="center" prop="inv_s_amount" />
            </el-table>
          </div>
        </div>
        <div style="padding: 15px">
          <div style="height: 30px;line-height: 30px;font-size: 13px">
            商标信息
          </div>
          <div>
            <el-table
              :data="entbase_bidding"
              border
              :header-row-style="{height:'40px',fontSize: '13px',backgroundColor: '#fcfcfc'}"
              :header-cell-style="{padding:'0px',backgroundColor: '#fcfcfc'}"
              :row-style="{height:'40px',fontSize: '13px'}"
              :cell-style="{padding:'0px'}"
              style="width: 97%;margin-top: 5px;"
            >
              <el-table-column label="序号" align="center" width="60">
                <template slot-scope="scope">{{ scope.$index+1 }}</template>
              </el-table-column>
              <el-table-column label="商标"></el-table-column>
              <el-table-column label="商标名称" align="center" prop="invname"/>
              <el-table-column label="当前状态" align="center" prop="invratio" />
              <el-table-column label="申请日期" align="center" prop="inv_r_amount" />
              <el-table-column label="注册号" align="center" prop="inv_s_amount" />
              <el-table-column label="国际分类" align="center" prop="inv_s_amount" />
              <el-table-column label="详情" align="center" prop="inv_s_amount" />
            </el-table>
          </div>
        </div>
        <div style="padding: 15px">
          <div style="height: 30px;line-height: 30px;font-size: 13px">
            软件著作权
          </div>
          <div>
            <el-table
              :data="entbase_bidding"
              border
              :header-row-style="{height:'40px',fontSize: '13px',backgroundColor: '#fcfcfc'}"
              :header-cell-style="{padding:'0px',backgroundColor: '#fcfcfc'}"
              :row-style="{height:'40px',fontSize: '13px'}"
              :cell-style="{padding:'0px'}"
              style="width: 97%;margin-top: 5px;"
            >
              <el-table-column label="序号" align="center" width="60">
                <template slot-scope="scope">{{ scope.$index+1 }}</template>
              </el-table-column>
              <el-table-column label="软件名称"></el-table-column>
              <el-table-column label="版本号" align="center" prop="invname"/>
              <el-table-column label="发布日期" align="center" prop="invratio" />
              <el-table-column label="登记号" align="center" prop="inv_r_amount" />
              <el-table-column label="软件简称" align="center" prop="inv_s_amount" />
              <el-table-column label="登记分类日期" align="center" prop="inv_s_amount" />
            </el-table>
          </div>
        </div>
      </div>
      <div style="min-height: 150px;padding: 15px">
        <div class="c-data-h1">行政处罚信息</div>
        <div>
          <el-table
            :data="entbase_bidding"
            border
            :header-row-style="{height:'40px',fontSize: '13px',backgroundColor: '#fcfcfc'}"
            :header-cell-style="{padding:'0px',backgroundColor: '#fcfcfc'}"
            :row-style="{height:'40px',fontSize: '13px'}"
            :cell-style="{padding:'0px'}"
            style="width: 97%;margin-top: 5px;"
          >
            <el-table-column label="序号" align="center" width="60">
              <template slot-scope="scope">{{ scope.$index+1 }}</template>
            </el-table-column>
            <el-table-column label="当事人名称"></el-table-column>
            <el-table-column label="注册号" align="center" prop="invname"/>
            <el-table-column label="法定代表人" align="center" prop="invratio" />
            <el-table-column label="统一社会信用代码" align="center" prop="inv_r_amount" />
            <el-table-column label="处罚决定书签发日期" align="center" prop="inv_s_amount" />
            <el-table-column label="作出行政处罚机关名称"></el-table-column>
            <el-table-column label="违法行为种类"></el-table-column>
            <el-table-column label="行政处罚决定文号"></el-table-column>
          </el-table>
        </div>
      </div>
      <div style="min-height: 150px;padding: 15px">
        <div class="c-data-h1">司法信息</div>
        <div>
          <el-table
            :data="entbase_bidding"
            border
            :header-row-style="{height:'40px',fontSize: '13px',backgroundColor: '#fcfcfc'}"
            :header-cell-style="{padding:'0px',backgroundColor: '#fcfcfc'}"
            :row-style="{height:'40px',fontSize: '13px'}"
            :cell-style="{padding:'0px'}"
            style="width: 97%;margin-top: 5px;"
          >
            <el-table-column label="序号" align="center" width="60">
              <template slot-scope="scope">{{ scope.$index+1 }}</template>
            </el-table-column>
            <el-table-column label="案件名称"></el-table-column>
            <el-table-column label="案件原告" align="center" prop="invname"/>
            <el-table-column label="案件被告" align="center" prop="invratio" />
            <el-table-column label="案件类型" align="center" prop="inv_r_amount" />
            <el-table-column label="案由" align="center" prop="inv_s_amount" />
            <el-table-column label="判决日期"></el-table-column>
            <el-table-column label="案号"></el-table-column>
            <el-table-column label="判决法院"></el-table-column>
            <el-table-column label="省份"></el-table-column>
          </el-table>
        </div>
      </div>
      <div style="min-height: 150px;padding: 15px">
        <div class="c-data-h1">综合实力评价信息</div>
        <ScoreDetail/>
      </div>
    </div>
  </div>
</template>
<script>
import ScoreDetail from "@/views/supplier_source/compents/ScoreDetail.vue";
export default ({
  name: "Report",
  components: {
    ScoreDetail
  },
  data() {
    return {
      dataGrid: {
        listQuery: {
          page: 1,
          limit: 10,
          keywords: undefined
        },
        total: 0,
        list: null,
        loading: true
      },
      // 招投标数据
      entbase_bidding:[],
      entbase_invs: [
        {
          invname: '张勇',
          invratio: '100%',
          inv_r_amount: '1000',
          inv_s_amount: '1000',
          invdate: '2009-04-22'
        }
      ],
      currentBaseInfo: {
        entname: '北京阿里巴巴科技有限公司',
        regcap: '1000',
        regcapcur_cn: '人民币',
        uniscid: '9111010858447343X3',
        regno: '110108000000016',
        legal_name: '张勇',
        enttype: '11',
        enttype_cn: '有限责任公司(自然人投资或控股)',
        regdate: '2009-04-22',
        apprdate: '2009-04-22',
        opfrom: '2009-04-22',
        opto: '2009-04-22',
        empnum: '1000',
        regorg_cn: '北京市工商行政管理局',
        entstatus: '1',
        entstatus_cn: '存续（在营、开业、在册）',
        regaddr: '北京市朝阳区酒仙桥路4号院1号楼',
        province: '北京市',
        city: '北京市',
        district: '朝阳区',
        opscope: '技术开发、技术转让、技术咨询、技术服务、技术培训；销售开发后的产品、计算机、软件及辅助设备、机械设备、电子产品、通讯设备；技术进出口、货物进出口、代理进出口；互联网信息服务（除新闻、出版、教育、医疗保健、药品、医疗器械和ＢＢＳ以外的内容）。（市场主体依法自主选择经营项目，开展经营活动；依法须经批准的项目，经相关部门批准后依批准的内容开展经营活动；不得从事国家和本市产业政策禁止和限制类项目的经营活动。）'
      }
    }
  },
  methods: {
    handleFilter() {
      this.dataGrid.listQuery.page = 1;
      this.getList();
    },
    handleInput() {
      this.dataGrid.listQuery.page = 1;
      this.getList();
    },
    handleSelect() {
      this.dataGrid.listQuery.page = 1;
      this.getList();
    },
    querySearchAsync(queryString, cb) {
    },
    getList() {

    }
  }
})
</script>
<style scoped>
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
  font-size:14px;
  font-weight:400;
  height: 40px;
  line-height: 40px;
  color:rgba(96,96,96,1);
}
.c-base-table,.c-base-table tr th, .c-base-table tr td {
  border:1px solid #D0CECE;
}
.c-th-header{
  background:#fcfcfc;
  text-align: left;
  font-size:14px;
  height: 40px;
  padding-left: 10px;
  font-weight:bolder;
  color:rgba(96,96,96,1);
  border:1px solid rgba(232, 241, 245, 1);
}
.c-base-table { border-collapse: collapse;}
.c-td-text{
  padding-left: 10px;
  text-align: left;
  height: 40px;
  font-size:14px;
  line-height: 40px;
  font-weight:400;
  color:rgba(96,96,96,1);
  background:white;
  border:1px solid #fcfcfc;
}
</style>
