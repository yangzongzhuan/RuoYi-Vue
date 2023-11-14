<template>
  <div style="margin: auto;background-color: #f9f9f9;padding-top: 15px;min-height: 700px">
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
    <div style="padding-left: 20px;line-height: 40px;height: 40px;background-color: white;font-size: 12px;margin-top: 15px">
      为您找到 <span style="color: #2E4E8F;font-weight: bolder">{{ dataGrid.total }}</span> 家符合条件的企业，此处最多展示10000条。
    </div>
    <div style="clear: both;background-color: white;margin-top: 15px;min-height: 300px;padding:15px">
      <el-table
        :data="tableData"
        border
        highlight-current-row
        :header-row-style="{height:'40px',fontSize: '13px'}"
        :header-cell-style="{padding:'0px'}"
        :row-style="{height:'40px',fontSize: '13px'}"
        :cell-style="{padding:'0px'}"
        style="width: 100%;background-color: white;"
      >
        <el-table-column
          prop="grpname"
          label="集团名称"
          align="left"
        >
          <template slot-scope="scope">
            <div v-html="grpnameHtml(scope.row)" />
          </template>
        </el-table-column>
        <el-table-column
          width="110"
          prop="mbrCount"
          :align="textCenter"
          label="集团成员总数"
        />
        <el-table-column
          width="110"
          prop="leadingCount"
          :align="textCenter"
          label="龙头企业数量"
        />
        <el-table-column
          width="110"
          prop="coreCount"
          :align="textCenter"
          label="核心成员数量"
        />
        <el-table-column
          width="110"
          prop="generalCount"
          :align="textCenter"
          label="一般成员数量"
        />
        <el-table-column
          prop="mbrlist"
          label="集团成员列表"
        >
          <template slot-scope="scope">
            <div style="word-break: break-all;white-space: normal;line-height: 17px">
              <span v-html="groupMemberHtml(scope.row)" />
              <el-link v-show=" (scope.row.mbrlist !== undefined && scope.row.mbrlist.length>3)" type="primary" :underline="false" @click="showMoreEnt(scope.row.grpid)">更多</el-link>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="150"
          :align="textCenter"
        >
          <template slot-scope="scope">
            <el-link type="primary" :underline="false" @click="viewDetailInfo(scope.row)" style="margin-right: 10px">详情</el-link>
            <el-link type="primary" :underline="false" @click="downLoadExcel(scope.row)">下载集团成员</el-link>
          </template>
        </el-table-column>
      </el-table>
      <pagination :total="100" :page.sync="dataGrid.listQuery.page" :limit.sync="dataGrid.listQuery.limit" @pagination="getList" />
    </div>
    <el-dialog title="集团企业列表（此处最多展示100条集团成员企业）" :visible.sync="dialogTableVisible" width="800px" style="height: 800px">
      <el-table :data="gridMemberData" style="font-size: 14px;">
        <el-table-column
          prop="mbrname_hlf"
          label="集团成员名称"
        >
          <template slot-scope="scope">
            <div v-html="scope.row.mbrname_hlf !== undefined? scope.row.mbrname_hlf:scope.row.mbrname" />
          </template>
        </el-table-column>
        <el-table-column
          prop="mbrroles_cn"
          label="集团成员角色"
        />
      </el-table>
      <div class="block" style="text-align: right;margin-top: 10px;">
        <el-pagination
          background
          :current-page="membersCurrentPage"
          :page-size="10"
          layout="total, prev, pager, next, jumper"
          :total="groupMembersTotal > 100?100:groupMembersTotal"
          @current-change="handleMembersChange"
        />
      </div>
    </el-dialog>
  </div>
</template>
<script>
import Pagination from "@/components/Pagination";
export default({
  name: "SupplyGroupList",
  components: { Pagination },
  data() {
    return {
      tableData: [
        {
          "grpid": "GR0EF2C7C390A3E4553A1C9A16BB11301414",
          "mbrlist": [
            {
              "grpid": "GR0EF2C7C390A3E4553A1C9A16BB11301414",
              "mbrname": "福建巴洛克克信息科技有限公司",
              "mbrroles": "01",
              "mbrid": "EF2C7C390A3E4553A1C9A16BB11301414"
            },
            {
              "grpid": "GR0EF2C7C390A3E4553A1C9A16BB11301414",
              "mbrname": "武夷山塔兰迪贸易有限公司",
              "mbrroles": "03",
              "mbrid": "0508799221A6410C8B66E599986AA2394"
            },
            {
              "grpid": "GR0EF2C7C390A3E4553A1C9A16BB11301414",
              "mbrname": "福建维格诺供应链有限公司",
              "mbrroles": "03",
              "mbrid": "577DFFE6D87C49D8A1E6FE221A35281A4"
            }
          ],
          "leadingCount": 1,
          "generalCount": 2,
          "grpname": "福建巴洛克克信息科技有限公司",
          "mbrCount": 3,
          "coreCount": 0
        },
        {
          "grpid": "GR06F274C746D1E7C57E0539601A8C0ACD65",
          "mbrlist": [
            {
              "grpid": "GR06F274C746D1E7C57E0539601A8C0ACD65",
              "mbrname": "轲有（上海）汽车服务有限公司",
              "mbrroles": "01",
              "mbrid": "6F274C746D1E7C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274C746D1E7C57E0539601A8C0ACD65",
              "mbrname": "三门成创空间科技服务有限公司",
              "mbrroles": "01",
              "mbrid": "6F274CFE667B7C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274C746D1E7C57E0539601A8C0ACD65",
              "mbrname": "上海台海贸易有限公司",
              "mbrroles": "01",
              "mbrid": "6F274C4984D97C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274C746D1E7C57E0539601A8C0ACD65",
              "mbrname": "台州万维文化传媒有限公司",
              "mbrroles": "02",
              "mbrid": "0463086E935548189F033B253D6CD9DD4"
            }
          ],
          "leadingCount": 3,
          "generalCount": 0,
          "grpname": "轲有（上海）汽车服务有限公司",
          "mbrCount": 4,
          "coreCount": 1
        },
        {
          "grpid": "GR06F274BE329627C57E0539601A8C0ACD65",
          "mbrlist": [
            {
              "grpid": "GR06F274BE329627C57E0539601A8C0ACD65",
              "mbrname": "唐山市路北区文化路天美健大药房＊",
              "mbrroles": "01",
              "mbrid": "6F274BEB1CE87C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274BE329627C57E0539601A8C0ACD65",
              "mbrname": "唐山市华佗药房有限责任公司",
              "mbrroles": "01",
              "mbrid": "6F274BEBA1FC7C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274BE329627C57E0539601A8C0ACD65",
              "mbrname": "北京市利君堂大药房连锁有限公司",
              "mbrroles": "01",
              "mbrid": "6F274BC5EA7E7C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274BE329627C57E0539601A8C0ACD65",
              "mbrname": "张家口华佗维君商贸有限公司",
              "mbrroles": "01",
              "mbrid": "6F274BF1FFEE7C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274BE329627C57E0539601A8C0ACD65",
              "mbrname": "乌兰察布市集宁区华佗益民堂大药房有限公司",
              "mbrroles": "01",
              "mbrid": "6F274C1203F17C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274BE329627C57E0539601A8C0ACD65",
              "mbrname": "河北华佗药房医药连锁有限公司",
              "mbrroles": "01",
              "mbrid": "6F274BE329627C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274BE329627C57E0539601A8C0ACD65",
              "mbrname": "大同市华佗医药亨泰大药房有限公司",
              "mbrroles": "02",
              "mbrid": "286A482153F74894853C9A63F66E23414"
            },
            {
              "grpid": "GR06F274BE329627C57E0539601A8C0ACD65",
              "mbrname": "怀来辛葆华佗药房医药有限公司",
              "mbrroles": "02",
              "mbrid": "D6B8B30927404761AB8E49CBB3963C564"
            },
            {
              "grpid": "GR06F274BE329627C57E0539601A8C0ACD65",
              "mbrname": "张家口姚方华佗药房医药有限公司",
              "mbrroles": "02",
              "mbrid": "682CC3B6777843D3A4B2C09B4056E72D4"
            }
          ],
          "leadingCount": 6,
          "generalCount": 514,
          "grpname": "河北华佗药房医药连锁有限公司",
          "mbrCount": 741,
          "coreCount": 221
        },
        {
          "grpid": "GR06F274D18F3297C57E0539601A8C0ACD65",
          "mbrlist": [
            {
              "grpid": "GR06F274D18F3297C57E0539601A8C0ACD65",
              "mbrname": "合肥裕万企业管理有限公司",
              "mbrroles": "01",
              "mbrid": "6F274D18F3297C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274D18F3297C57E0539601A8C0ACD65",
              "mbrname": "安徽融创房地产开发有限公司",
              "mbrroles": "02",
              "mbrid": "6F274D02F13B7C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274D18F3297C57E0539601A8C0ACD65",
              "mbrname": "杭州兆臻投资有限公司",
              "mbrroles": "02",
              "mbrid": "6F274CD598527C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274D18F3297C57E0539601A8C0ACD65",
              "mbrname": "蚌埠融创新领置业有限公司",
              "mbrroles": "02",
              "mbrid": "756D74E50190D27AE0539601A8C05DE96"
            },
            {
              "grpid": "GR06F274D18F3297C57E0539601A8C0ACD65",
              "mbrname": "南京鸿熙商业管理有限公司",
              "mbrroles": "02",
              "mbrid": "8B0BCC4CD00788B5E0539601A8C0B0E76"
            },
            {
              "grpid": "GR06F274D18F3297C57E0539601A8C0ACD65",
              "mbrname": "合肥融畔企业管理合伙企业（有限合伙）",
              "mbrroles": "02",
              "mbrid": "BAB3C922713344878D8EDFCDBC744B794"
            },
            {
              "grpid": "GR06F274D18F3297C57E0539601A8C0ACD65",
              "mbrname": "杭州裕万企业管理有限公司",
              "mbrroles": "02",
              "mbrid": "4A4BC5D7C6684CD08F2E7BEDCB00C1604"
            },
            {
              "grpid": "GR06F274D18F3297C57E0539601A8C0ACD65",
              "mbrname": "杭州昌茂置业有限公司",
              "mbrroles": "03",
              "mbrid": "0500CBC8B1C942A3A6338B3C373611BD4"
            },
            {
              "grpid": "GR06F274D18F3297C57E0539601A8C0ACD65",
              "mbrname": "浙江融创文旅投资有限公司",
              "mbrroles": "03",
              "mbrid": "6F274CF6EBBC7C57E0539601A8C0ACD65"
            }
          ],
          "leadingCount": 1,
          "generalCount": 14,
          "grpname": "合肥裕万企业管理有限公司",
          "mbrCount": 21,
          "coreCount": 6
        },
        {
          "grpid": "GR06F274CFE56937C57E0539601A8C0ACD65",
          "mbrlist": [
            {
              "grpid": "GR06F274CFE56937C57E0539601A8C0ACD65",
              "mbrname": "青岛大正创业投资企业（有限合伙）",
              "mbrroles": "01",
              "mbrid": "45731CC1CBF64254A2FD6286E488AE764"
            },
            {
              "grpid": "GR06F274CFE56937C57E0539601A8C0ACD65",
              "mbrname": "山东大有投资有限公司",
              "mbrroles": "01",
              "mbrid": "6F274D68FD7E7C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274CFE56937C57E0539601A8C0ACD65",
              "mbrname": "安吉大有投资合伙企业（有限合伙）",
              "mbrroles": "01",
              "mbrid": "6F274CFE56937C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274CFE56937C57E0539601A8C0ACD65",
              "mbrname": "青岛大有泽远创业投资企业（有限合伙）",
              "mbrroles": "02",
              "mbrid": "5741BDB1030549C08A954F8E1272DAF34"
            },
            {
              "grpid": "GR06F274CFE56937C57E0539601A8C0ACD65",
              "mbrname": "青岛大有私募基金管理有限公司",
              "mbrroles": "02",
              "mbrid": "6F274D7B9C957C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274CFE56937C57E0539601A8C0ACD65",
              "mbrname": "青岛大有明远创业投资企业（有限合伙）",
              "mbrroles": "03",
              "mbrid": "0878ACFDB37A4D23A47EEB320B7678084"
            },
            {
              "grpid": "GR06F274CFE56937C57E0539601A8C0ACD65",
              "mbrname": "青岛大来创业投资合伙企业（有限合伙）",
              "mbrroles": "03",
              "mbrid": "E360332857CE4CA49C185FF6E62A26604"
            }
          ],
          "leadingCount": 3,
          "generalCount": 2,
          "grpname": "安吉大有投资合伙企业（有限合伙）",
          "mbrCount": 7,
          "coreCount": 2
        },
        {
          "grpid": "GR08AF660A8B36ACA7FE0539601A8C0475E6",
          "mbrlist": [
            {
              "grpid": "GR08AF660A8B36ACA7FE0539601A8C0475E6",
              "mbrname": "广州广丰商业发展有限公司",
              "mbrroles": "01",
              "mbrid": "8AF660A8B36ACA7FE0539601A8C0475E6"
            },
            {
              "grpid": "GR08AF660A8B36ACA7FE0539601A8C0475E6",
              "mbrname": "广州美妍电子商务有限公司",
              "mbrroles": "02",
              "mbrid": "4E201564327A4D87B43C34D57A6EE2594"
            },
            {
              "grpid": "GR08AF660A8B36ACA7FE0539601A8C0475E6",
              "mbrname": "广州卓美电子商务有限公司",
              "mbrroles": "02",
              "mbrid": "DF6329DD60714845965692F407E7B48C4"
            },
            {
              "grpid": "GR08AF660A8B36ACA7FE0539601A8C0475E6",
              "mbrname": "广州信盛电子商务有限公司",
              "mbrroles": "02",
              "mbrid": "E317CC0FDC134C2E8C2FE77314CC58A64"
            },
            {
              "grpid": "GR08AF660A8B36ACA7FE0539601A8C0475E6",
              "mbrname": "广州湘木家具有限公司",
              "mbrroles": "02",
              "mbrid": "89A6AAAD686E4C0D9AFEDF6AD831CACA4"
            },
            {
              "grpid": "GR08AF660A8B36ACA7FE0539601A8C0475E6",
              "mbrname": "广州和骏电子商务有限公司",
              "mbrroles": "02",
              "mbrid": "59B9F4EAE2754173976C0F582F449AE84"
            },
            {
              "grpid": "GR08AF660A8B36ACA7FE0539601A8C0475E6",
              "mbrname": "广州英绅服装有限公司",
              "mbrroles": "02",
              "mbrid": "3AA9072E1A774DA09579353B6D80A1E74"
            },
            {
              "grpid": "GR08AF660A8B36ACA7FE0539601A8C0475E6",
              "mbrname": "广州莲爱生物科技有限公司",
              "mbrroles": "02",
              "mbrid": "75E298DB54CED545E0539601A8C01F496"
            },
            {
              "grpid": "GR08AF660A8B36ACA7FE0539601A8C0475E6",
              "mbrname": "广州领越电子商务有限公司",
              "mbrroles": "02",
              "mbrid": "656C61D0958145B6B12BAD47E085BEA74"
            }
          ],
          "leadingCount": 1,
          "generalCount": 21,
          "grpname": "广州广丰商业发展有限公司",
          "mbrCount": 30,
          "coreCount": 8
        },
        {
          "grpid": "GR06F274E301CA07C57E0539601A8C0ACD65",
          "mbrlist": [
            {
              "grpid": "GR06F274E301CA07C57E0539601A8C0ACD65",
              "mbrname": "广州世荣电子股份有限公司",
              "mbrroles": "01",
              "mbrid": "6F274E301CA07C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274E301CA07C57E0539601A8C0ACD65",
              "mbrname": "长沙爱瑟菲贸易有限公司",
              "mbrroles": "02",
              "mbrid": "0818007F7BE145D082218C047CFC8C304"
            },
            {
              "grpid": "GR06F274E301CA07C57E0539601A8C0ACD65",
              "mbrname": "重庆爱瑟菲智能科技有限公司",
              "mbrroles": "02",
              "mbrid": "6F274E58B5907C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274E301CA07C57E0539601A8C0ACD65",
              "mbrname": "爱瑟菲智能科技（天津）有限公司",
              "mbrroles": "03",
              "mbrid": "6F274BD9AE1F7C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274E301CA07C57E0539601A8C0ACD65",
              "mbrname": "广州好士福智能科技有限公司",
              "mbrroles": "03",
              "mbrid": "6F274DFEE3807C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274E301CA07C57E0539601A8C0ACD65",
              "mbrname": "成都大德汇通智能科技有限公司",
              "mbrroles": "03",
              "mbrid": "6F274E7793FA7C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274E301CA07C57E0539601A8C0ACD65",
              "mbrname": "广州爱瑟菲软件有限公司",
              "mbrroles": "03",
              "mbrid": "74B5F9120D19457CA7581235606C2D3F4"
            },
            {
              "grpid": "GR06F274E301CA07C57E0539601A8C0ACD65",
              "mbrname": "广州世荣软件有限公司",
              "mbrroles": "03",
              "mbrid": "6F274E16AD927C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274E301CA07C57E0539601A8C0ACD65",
              "mbrname": "南宁爱瑟菲智能家居有限公司",
              "mbrroles": "03",
              "mbrid": "8B1AF02DAFCF22DAE0539601A8C027D46"
            },
            {
              "grpid": "GR06F274E301CA07C57E0539601A8C0ACD65",
              "mbrname": "广州好士福软件有限公司",
              "mbrroles": "03",
              "mbrid": "6F274E06A68A7C57E0539601A8C0ACD65"
            }
          ],
          "leadingCount": 1,
          "generalCount": 7,
          "grpname": "广州世荣电子股份有限公司",
          "mbrCount": 10,
          "coreCount": 2
        },
        {
          "grpid": "GR06F274BB2CDD17C57E0539601A8C0ACD65",
          "mbrlist": [
            {
              "grpid": "GR06F274BB2CDD17C57E0539601A8C0ACD65",
              "mbrname": "万众竞彩传媒科技（北京）有限公司",
              "mbrroles": "01",
              "mbrid": "6F274BB2CDD17C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274BB2CDD17C57E0539601A8C0ACD65",
              "mbrname": "小木茶业（云南）集团有限公司",
              "mbrroles": "02",
              "mbrid": "1840B305F1794706BCE188ED41FA37C54"
            },
            {
              "grpid": "GR06F274BB2CDD17C57E0539601A8C0ACD65",
              "mbrname": "和众彩讯（北京）科技有限公司",
              "mbrroles": "02",
              "mbrid": "6F274BB800137C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274BB2CDD17C57E0539601A8C0ACD65",
              "mbrname": "南朵花业（云南）集团有限公司",
              "mbrroles": "03",
              "mbrid": "141AD0FC4B9B4F2B906FE131E02BF9C44"
            },
            {
              "grpid": "GR06F274BB2CDD17C57E0539601A8C0ACD65",
              "mbrname": "麻确工业大麻（云南）有限公司",
              "mbrroles": "03",
              "mbrid": "00145839812245BE9BF0431F805FAE0F4"
            },
            {
              "grpid": "GR06F274BB2CDD17C57E0539601A8C0ACD65",
              "mbrname": "敢为天下鲜调味品云南有限公司",
              "mbrroles": "03",
              "mbrid": "84BAF03BFDF8468A959B481913E2193A4"
            },
            {
              "grpid": "GR06F274BB2CDD17C57E0539601A8C0ACD65",
              "mbrname": "优尔大数据（云南）有限公司",
              "mbrroles": "03",
              "mbrid": "330E32AECA7A4EE7BF9E4EA4CD3385C04"
            },
            {
              "grpid": "GR06F274BB2CDD17C57E0539601A8C0ACD65",
              "mbrname": "彩富宝科技（北京）有限公司",
              "mbrroles": "03",
              "mbrid": "6F274BB8F5AC7C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274BB2CDD17C57E0539601A8C0ACD65",
              "mbrname": "芸楠药业（云南）集团有限公司",
              "mbrroles": "03",
              "mbrid": "9591979CA2DB40D9A687FCBC0644FDD84"
            }
          ],
          "leadingCount": 1,
          "generalCount": 6,
          "grpname": "万众竞彩传媒科技（北京）有限公司",
          "mbrCount": 9,
          "coreCount": 2
        },
        {
          "grpid": "GR047C3A16D42BA40B1810EC3D034C4E27E4",
          "mbrlist": [
            {
              "grpid": "GR047C3A16D42BA40B1810EC3D034C4E27E4",
              "mbrname": "上海斑马时代酒业有限公司",
              "mbrroles": "01",
              "mbrid": "47C3A16D42BA40B1810EC3D034C4E27E4"
            },
            {
              "grpid": "GR047C3A16D42BA40B1810EC3D034C4E27E4",
              "mbrname": "上海斑马有皮气餐饮管理有限公司",
              "mbrroles": "02",
              "mbrid": "2B143475E4B4426495258E5513E40DF94"
            },
            {
              "grpid": "GR047C3A16D42BA40B1810EC3D034C4E27E4",
              "mbrname": "安徽斑马有皮气餐饮有限公司",
              "mbrroles": "03",
              "mbrid": "0BB9CC1457E948BF8C0ECE7EB361E4414"
            }
          ],
          "leadingCount": 1,
          "generalCount": 1,
          "grpname": "上海斑马时代酒业有限公司",
          "mbrCount": 3,
          "coreCount": 1
        },
        {
          "grpid": "GR06F274CF4666D7C57E0539601A8C0ACD65",
          "mbrlist": [
            {
              "grpid": "GR06F274CF4666D7C57E0539601A8C0ACD65",
              "mbrname": "遂昌县老百姓大药房有限公司",
              "mbrroles": "01",
              "mbrid": "6F274CF4666D7C57E0539601A8C0ACD65"
            },
            {
              "grpid": "GR06F274CF4666D7C57E0539601A8C0ACD65",
              "mbrname": "丽水市康养百姓医药连锁有限公司",
              "mbrroles": "03",
              "mbrid": "1581E27AF2524B3E8DB2E92D2D9F778F4"
            },
            {
              "grpid": "GR06F274CF4666D7C57E0539601A8C0ACD65",
              "mbrname": "丽水维康康养百姓医药连锁有限公司",
              "mbrroles": "03",
              "mbrid": "D8FCB8EEB300495F9219FC74B336221A4"
            }
          ],
          "leadingCount": 1,
          "generalCount": 2,
          "grpname": "遂昌县老百姓大药房有限公司",
          "mbrCount": 3,
          "coreCount": 0
        }
      ],
      textCenter: "center",
      dialogTableVisible: false,
      gridMemberData: [],
      membersCurrentPage: 1,
      groupMembersTotal: 0,
      grpname: "",
      dataGrid:{
        total: 0,
        listQuery: {
          page: 1,
          limit: 10,
          keywords: undefined
        }
      }
    }
  },
  methods: {
    groupMemberHtml(row) {
      const memberHtml = []

      if (row.mbrlist === null || row.mbrlist === undefined || row.mbrlist.length <= 0) {
        return ''
      }

      if (row.mbrlist.length <= 3) {
        row.mbrlist.forEach(function(thisItem, index) {
          if (thisItem.mbrname_hlf !== undefined) {
            memberHtml.push(thisItem.mbrname_hlf)
          } else {
            memberHtml.push(thisItem.mbrname)
          }
        })
        return memberHtml.join(';').toLocaleString()
      }
      row.mbrlist.forEach(function(thisItem, index) {
        if (index < 3) {
          if (thisItem.mbrname_hlf !== undefined) {
            memberHtml.push(thisItem.mbrname_hlf)
          } else {
            memberHtml.push(thisItem.mbrname)
          }
        }
      })
      return memberHtml.join(';').toLocaleString() + '...'
    },
    handleSizeChange(val) {
      this.dataGrid.listQuery.limit = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.dataGrid.listQuery.page = val
      this.getList()
    },
    getList() {
    },
    grpnameHtml(row) {
      return `<span>${row.grpname}</span>`
    },
    handleMembersChange(val) {
      this.membersCurrentPage = val
    },
    showMoreEnt(grpid) {
      this.dialogTableVisible = true
    },
    viewDetailInfo(row) {
      this.$router.push({ path: "/supplier_graph/supplyGroupDetail" });
    },
    downLoadExcel(row) {
      this.$router.push({
        path: '/supplyGroupDetail',
        query: {
          grpid: row.grpid
        }
      })
    },
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

  }
})
</script>
<style scoped>

</style>
