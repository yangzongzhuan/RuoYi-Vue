<template>
  <div id="app" style="margin: auto;background-color: #f9f9f9;padding: 10px;min-height: 700px">
    <div style="background-color: white;line-height: 70px;height: 70px;text-align: center">
      <el-autocomplete
        v-model="keywords"
        class="input-with-select"
        style="width: 710px;margin-left: 48px;"
        placeholder="请输入企业名称、统⼀社会信⽤代码、⼯商注册号"
        :fetch-suggestions="querySearchAsync"
        :trigger-on-focus="false"
        @select="handleSelect"
        @input="handleInput"
        @keyup.enter.native="searchGroupList"
      >
        <el-button slot="append" icon="el-icon-search" @click="searchGroupList" />
      </el-autocomplete>
    </div>
    <div style="padding-left: 15px;line-height: 40px;height: 40px;background-color: white;font-size: 12px;margin-top: 15px">
      为您找到 <span style="color: #2E4E8F;font-weight: bolder">{{ totalSize }}</span> 家符合条件的企业，此处最多展示10000条。
    </div>
    <div style="margin-top: 15px;background-color: white;min-height: 450px;padding: 15px">
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
          prop="entname"
          label="企业名称"
          width="200"
        >
          <template slot-scope="scope">
            <div
              v-html="(scope.row.entname_hlf === undefined
                  || scope.row.entname_hlf === null
                  || scope.row.entname_hlf === '')?scope.row.entname: scope.row.entname_hlf"
            />
          </template>
        </el-table-column>
        <el-table-column
          prop="uniscid"
          label="统一社会信用代码"
        />
        <el-table-column
          label="企业经营状态"
        >
          <template slot-scope="scope">
            <span :style="( statusStyle(scope.row.entstatus))">{{ scope.row.entstatus_cn }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="注册资本"
        >
          <template slot-scope="scope">
            {{ scope.row.regcap+scope.row.regcapcur_cn }}
          </template>
        </el-table-column>
        <el-table-column
          prop="esdate"
          label="成立日期"
        />
        <el-table-column
          prop="regorg_cn"
          label="注册地"
        />
        <el-table-column
          prop="actgrpCount"
          label="一致行动人组（数量）"
        />
        <el-table-column
          label="⼀致⾏动⼈列表"
        >
          <template slot-scope="scope">
            <span v-html="convertPartyList(scope)" />
            <el-link v-show=" (scope.row.actlist !== undefined && scope.row.actlist.length>3)" type="primary" :underline="false" @click="showMoreEnt(scope)">更多</el-link>
          </template>
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="70"
        >
          <template slot-scope="scope">
            <el-link type="primary" :underline="false" @click="viewDetailInfo(scope.row)">详情</el-link>
          </template>
        </el-table-column>
      </el-table>
      <pagination :total="totalSize" :page.sync="searchListQuery.page" :limit.sync="searchListQuery.limit" @pagination="getList" />
    </div>
  </div>
</template>
<script>
import Pagination from "@/components/Pagination";
export default({
  name: "ActionGraphList",
  components: { Pagination },
  data() {
    return {
      keywords: "",
      searchType: "1",
      searchList: [],
      searchListLoading: false,
      totalSize: 0,
      tableData:  [
        {
          "esdate": "2017-01-03",
          "uniscid": "91340100MA2NAT6970",
          "entstatus_cn": "存续（在营、开业、在册）",
          "regcapcur_cn": "万人民币",
          "entid": "6F274D0BF2827C57E0539601A8C0ACD65",
          "entname": "安徽乐富强控股集团有限公司",
          "regcap": 100000.0,
          "regorg": "340191",
          "entstatus": "1",
          "regcapcur": "156",
          "regorg_cn": "安徽省-合肥市",
          "actgrpCount": 1,
          "actlist": [
            {
              "partytype_cn": "个人",
              "entid": "6F274D0BF2827C57E0539601A8C0ACD65",
              "partyname": "刘德文",
              "partytype": "P",
              "partyid": "557754E232F6573D3E5BC6E76D05A931",
              "actgid": "a4767d003308e37c5d05cd760261e2f7"
            },
            {
              "partytype_cn": "企业",
              "entid": "6F274D0BF2827C57E0539601A8C0ACD65",
              "partyname": "安徽金彩牛实业集团有限公司",
              "partytype": "C",
              "partyid": "6F274D0E43587C57E0539601A8C0ACD65",
              "actgid": "a4767d003308e37c5d05cd760261e2f7"
            },
            {
              "partytype_cn": "个人",
              "entid": "6F274D0BF2827C57E0539601A8C0ACD65",
              "partyname": "刘静雯",
              "partytype": "P",
              "partyid": "8384D7F423A8616C1E04C55691BEDB30",
              "actgid": "a4767d003308e37c5d05cd760261e2f7"
            }
          ]
        },
        {
          "esdate": "2013-02-27",
          "uniscid": "91500000060539403N",
          "entstatus_cn": "存续(在营、开业、在册)",
          "regcapcur_cn": "人民币",
          "entid": "6F274E5A1F657C57E0539601A8C0ACD65",
          "entname": "渝商投资集团股份有限公司",
          "regcap": 342066.6667,
          "regorg": "500000",
          "entstatus": "1",
          "regcapcur": "156",
          "regorg_cn": "重庆市-重庆市",
          "actgrpCount": 3,
          "actlist": [
            {
              "partytype_cn": "企业",
              "entid": "6F274E5A1F657C57E0539601A8C0ACD65",
              "partyname": "重庆市中科控股有限公司",
              "partytype": "C",
              "partyid": "6F274E5A074B7C57E0539601A8C0ACD65",
              "actgid": "284b040c84fda3052f10bed840475678"
            },
            {
              "partytype_cn": "企业",
              "entid": "6F274E5A1F657C57E0539601A8C0ACD65",
              "partyname": "重庆餐饮文化产业投资集团股份有限公司",
              "partytype": "C",
              "partyid": "6F274E5982C47C57E0539601A8C0ACD65",
              "actgid": "284b040c84fda3052f10bed840475678"
            },
            {
              "partytype_cn": "企业",
              "entid": "6F274E5A1F657C57E0539601A8C0ACD65",
              "partyname": "重庆嘉南控股（集团）有限公司",
              "partytype": "C",
              "partyid": "6F274E5C0B0F7C57E0539601A8C0ACD65",
              "actgid": "284b040c84fda3052f10bed840475678"
            },
            {
              "partytype_cn": "企业",
              "entid": "6F274E5A1F657C57E0539601A8C0ACD65",
              "partyname": "力帆科技（集团）股份有限公司",
              "partytype": "C",
              "partyid": "6F274E54F3DE7C57E0539601A8C0ACD65",
              "actgid": "f5a70777380220c16c0a865f9e11308c"
            },
            {
              "partytype_cn": "企业",
              "entid": "6F274E5A1F657C57E0539601A8C0ACD65",
              "partyname": "重庆涪商贸易有限公司",
              "partytype": "C",
              "partyid": "6F274E5BB0677C57E0539601A8C0ACD65",
              "actgid": "284b040c84fda3052f10bed840475678"
            },
            {
              "partytype_cn": "企业",
              "entid": "6F274E5A1F657C57E0539601A8C0ACD65",
              "partyname": "重庆小康控股有限公司",
              "partytype": "C",
              "partyid": "6F274E52816E7C57E0539601A8C0ACD65",
              "actgid": "f5a70777380220c16c0a865f9e11308c"
            },
            {
              "partytype_cn": "企业",
              "entid": "6F274E5A1F657C57E0539601A8C0ACD65",
              "partyname": "重庆金山科技（集团）有限公司",
              "partytype": "C",
              "partyid": "6F274E57B0C77C57E0539601A8C0ACD65",
              "actgid": "ebc98bbc70ae8358edce4a141233be8b"
            },
            {
              "partytype_cn": "企业",
              "entid": "6F274E5A1F657C57E0539601A8C0ACD65",
              "partyname": "重庆润邦置业有限公司",
              "partytype": "C",
              "partyid": "6F274E590A7F7C57E0539601A8C0ACD65",
              "actgid": "284b040c84fda3052f10bed840475678"
            },
            {
              "partytype_cn": "企业",
              "entid": "6F274E5A1F657C57E0539601A8C0ACD65",
              "partyname": "重庆陶然居饮食文化（集团）股份有限公司",
              "partytype": "C",
              "partyid": "6F274E559A027C57E0539601A8C0ACD65",
              "actgid": "284b040c84fda3052f10bed840475678"
            }
          ]
        },
        {
          "esdate": "2013-08-22",
          "uniscid": "91350200072829926E",
          "entstatus_cn": "存续（在营、开业、在册）",
          "regcapcur_cn": "万人民币",
          "entid": "6F274D2F5B787C57E0539601A8C0ACD65",
          "entname": "福建凤山集团厦门置业有限公司",
          "regcap": 2888.0,
          "regorg": "350200",
          "entstatus": "1",
          "regcapcur": "156",
          "regorg_cn": "福建省-厦门市",
          "actgrpCount": 1,
          "actlist": [
            {
              "partytype_cn": "企业",
              "entid": "6F274D2F5B787C57E0539601A8C0ACD65",
              "partyname": "福建凤山集团有限公司",
              "partytype": "C",
              "partyid": "6F274D2ADFC97C57E0539601A8C0ACD65",
              "actgid": "6775f15c9f9bf1e862a63109d8d92c8e"
            },
            {
              "partytype_cn": "企业",
              "entid": "6F274D2F5B787C57E0539601A8C0ACD65",
              "partyname": "福建凤山集团房地产开发有限公司",
              "partytype": "C",
              "partyid": "6F274D29D1147C57E0539601A8C0ACD65",
              "actgid": "6775f15c9f9bf1e862a63109d8d92c8e"
            },
            {
              "partytype_cn": "个人",
              "entid": "6F274D2F5B787C57E0539601A8C0ACD65",
              "partyname": "郑丽清",
              "partytype": "P",
              "partyid": "5C7596EA9298172273930ACDFAD5B3F3",
              "actgid": "6775f15c9f9bf1e862a63109d8d92c8e"
            }
          ]
        },
        {
          "esdate": "2016-01-28",
          "uniscid": "91320321MA1MEYYB4K",
          "entstatus_cn": "存续",
          "regcapcur_cn": "万人民币",
          "entid": "6F274C8EDA0E7C57E0539601A8C0ACD65",
          "entname": "万融时代资产管理（徐州）有限公司",
          "regcap": 1000.0,
          "regorg": "320321",
          "entstatus": "1",
          "regcapcur": "156",
          "regorg_cn": "江苏省-徐州市-丰县",
          "actgrpCount": 1,
          "actlist": [
            {
              "partytype_cn": "企业",
              "entid": "6F274C8EDA0E7C57E0539601A8C0ACD65",
              "partyname": "宁波万融鼎源投资管理有限公司",
              "partytype": "C",
              "partyid": "6F274CEC021A7C57E0539601A8C0ACD65",
              "actgid": "0a09f2707384a10e203042f859131870"
            },
            {
              "partytype_cn": "企业",
              "entid": "6F274C8EDA0E7C57E0539601A8C0ACD65",
              "partyname": "北京万融时代资本管理有限公司",
              "partytype": "C",
              "partyid": "6F274BB383AA7C57E0539601A8C0ACD65",
              "actgid": "0a09f2707384a10e203042f859131870"
            }
          ]
        },
        {
          "esdate": "2015-03-10",
          "uniscid": "91440500334802329U",
          "entstatus_cn": "在营（开业）企业",
          "regcapcur_cn": "万人民币",
          "entid": "6F274E1BF3AF7C57E0539601A8C0ACD65",
          "entname": "汕头市明道企业管理咨询有限公司",
          "regcap": 50.0,
          "regorg": "440500",
          "entstatus": "1",
          "regcapcur": "156",
          "regorg_cn": "广东省-汕头市",
          "actgrpCount": 1,
          "actlist": [
            {
              "partytype_cn": "个人",
              "entid": "6F274E1BF3AF7C57E0539601A8C0ACD65",
              "partyname": "温明",
              "partytype": "P",
              "partyid": "B080B702F667577285D9A81F3A824C92",
              "actgid": "c3e568698fe8964cf7b0fed095ab8cac"
            },
            {
              "partytype_cn": "企业",
              "entid": "6F274E1BF3AF7C57E0539601A8C0ACD65",
              "partyname": "深圳市前海宏程世景管理顾问有限公司",
              "partytype": "C",
              "partyid": "6F274DF6E1C77C57E0539601A8C0ACD65",
              "actgid": "c3e568698fe8964cf7b0fed095ab8cac"
            },
            {
              "partytype_cn": "个人",
              "entid": "6F274E1BF3AF7C57E0539601A8C0ACD65",
              "partyname": "张晓璇",
              "partytype": "P",
              "partyid": "6C86DBD366C0CB9C11790C003AD683CA",
              "actgid": "c3e568698fe8964cf7b0fed095ab8cac"
            }
          ]
        },
        {
          "esdate": "2015-08-19",
          "uniscid": "913302063405198531",
          "entstatus_cn": "存续",
          "regcapcur_cn": "人民币",
          "entid": "6F274CD062AC7C57E0539601A8C0ACD65",
          "entname": "宁波快塑投资合伙企业（有限合伙）",
          "regcap": 0.0,
          "regorg": "330206",
          "entstatus": "1",
          "regcapcur": "156",
          "regorg_cn": "浙江省-宁波市-北仑区",
          "actgrpCount": 1,
          "actlist": [
            {
              "partytype_cn": "个人",
              "entid": "6F274CD062AC7C57E0539601A8C0ACD65",
              "partyname": "刘育弘",
              "partytype": "P",
              "partyid": "3BE2420CADCAECDDF0C03E4828488F01",
              "actgid": "a238998a345e1188c3442893e20ba572"
            },
            {
              "partytype_cn": "个人",
              "entid": "6F274CD062AC7C57E0539601A8C0ACD65",
              "partyname": "郭坚晖",
              "partytype": "P",
              "partyid": "8522ED065920CE68F4DE69CBD7AF642C",
              "actgid": "a238998a345e1188c3442893e20ba572"
            },
            {
              "partytype_cn": "企业",
              "entid": "6F274CD062AC7C57E0539601A8C0ACD65",
              "partyname": "宁波赛聚投资管理有限公司",
              "partytype": "C",
              "partyid": "6F274CDA62C97C57E0539601A8C0ACD65",
              "actgid": "a238998a345e1188c3442893e20ba572"
            }
          ]
        },
        {
          "esdate": "2005-11-30",
          "uniscid": "911101027832455132",
          "entstatus_cn": "开业",
          "regcapcur_cn": "万人民币",
          "entid": "6F274BBA99A27C57E0539601A8C0ACD65",
          "entname": "北京民生投资有限公司",
          "regcap": 2980.0,
          "regorg": "110102",
          "entstatus": "1",
          "regcapcur": "156",
          "regorg_cn": "北京市-市辖区-西城区",
          "actgrpCount": 1,
          "actlist": [
            {
              "partytype_cn": "个人",
              "entid": "6F274BBA99A27C57E0539601A8C0ACD65",
              "partyname": "顾俊明",
              "partytype": "P",
              "partyid": "E76417EB8757468FDC9FFB96D5C42CDC",
              "actgid": "7b22157401bb68052db6265c154f7ec6"
            },
            {
              "partytype_cn": "企业",
              "entid": "6F274BBA99A27C57E0539601A8C0ACD65",
              "partyname": "北京汇通全程企业策划有限公司",
              "partytype": "C",
              "partyid": "6F274BB1AB957C57E0539601A8C0ACD65",
              "actgid": "7b22157401bb68052db6265c154f7ec6"
            }
          ]
        },
        {
          "esdate": "2018-12-03",
          "uniscid": "91341200MA2T9UNC2R",
          "entstatus_cn": "存续（在营、开业、在册）",
          "regcapcur_cn": "万",
          "entid": "7D24AA78B61AB147E0539601A8C07EFD0",
          "entname": "阜阳市金诺物业管理服务有限公司",
          "regcap": 100.0,
          "regorg": "",
          "entstatus": "1",
          "regcapcur": "156",
          "actgrpCount": 1,
          "actlist": [
            {
              "partytype_cn": "企业",
              "entid": "7D24AA78B61AB147E0539601A8C07EFD0",
              "partyname": "佛山市顺德区华天宝投资有限公司",
              "partytype": "C",
              "partyid": "6F274E0B73D47C57E0539601A8C0ACD65",
              "actgid": "874f534e18cf1b0ba0b1d96db1e4ed33"
            },
            {
              "partytype_cn": "企业",
              "entid": "7D24AA78B61AB147E0539601A8C07EFD0",
              "partyname": "佛山市顺德区海骏达企业资产管理有限公司",
              "partytype": "C",
              "partyid": "6F274E257E707C57E0539601A8C0ACD65",
              "actgid": "874f534e18cf1b0ba0b1d96db1e4ed33"
            }
          ]
        },
        {
          "esdate": "2004-04-24",
          "uniscid": "9131011776163944X4",
          "entstatus_cn": "存续（在营、开业、在册）",
          "regcapcur_cn": "万人民币",
          "entid": "6F274C650B0F7C57E0539601A8C0ACD65",
          "entname": "上海农乐生化制品销售有限公司",
          "regcap": 200.0,
          "regorg": "310117",
          "entstatus": "1",
          "regcapcur": "156",
          "regorg_cn": "上海市-市辖区-松江区",
          "actgrpCount": 1,
          "actlist": [
            {
              "partytype_cn": "企业",
              "entid": "6F274C650B0F7C57E0539601A8C0ACD65",
              "partyname": "上海农乐生物制品股份有限公司",
              "partytype": "C",
              "partyid": "6F274C69EF657C57E0539601A8C0ACD65",
              "actgid": "daea802db79feb27db40113a25b9e21e"
            },
            {
              "partytype_cn": "个人",
              "entid": "6F274C650B0F7C57E0539601A8C0ACD65",
              "partyname": "冯镇泰",
              "partytype": "P",
              "partyid": "FA937822813C0A9A813603B5DEBE89A3",
              "actgid": "daea802db79feb27db40113a25b9e21e"
            }
          ]
        },
        {
          "esdate": "2005-09-21",
          "uniscid": "9132031177969499XB",
          "entstatus_cn": "存续（在营、开业、在册）",
          "regcapcur_cn": "万人民币",
          "entid": "6F274CA294897C57E0539601A8C0ACD65",
          "entname": "徐州春宇煤炭洗选有限公司",
          "regcap": 200.0,
          "regorg": "320311",
          "entstatus": "1",
          "regcapcur": "156",
          "regorg_cn": "江苏省-徐州市-泉山区",
          "actgrpCount": 1,
          "actlist": [
            {
              "partytype_cn": "个人",
              "entid": "6F274CA294897C57E0539601A8C0ACD65",
              "partyname": "滕道春",
              "partytype": "P",
              "partyid": "F35DD6600792C40DA9ECAA9AF245F0C7",
              "actgid": "eec9c663bd64201713cfe962b77ed903"
            },
            {
              "partytype_cn": "企业",
              "entid": "6F274CA294897C57E0539601A8C0ACD65",
              "partyname": "江苏天裕能源化工集团有限公司",
              "partytype": "C",
              "partyid": "6F274CC139BB7C57E0539601A8C0ACD65",
              "actgid": "eec9c663bd64201713cfe962b77ed903"
            }
          ]
        }
      ],
      searchListQuery: {
        page: 1,
        limit: 10,
        keywords: ""
      }
    }
  },
  methods:{
    searchGroupList() {
      this.searchListQuery.keywords = this.keywords
      this.searchListQuery.page = 1
      this.searchListQuery.limit = 10
      this.searchListLoading = true
      this.$http({
        url: "/actionGraph/searchGroupList",
        method: "post",
        data: this.searchListQuery
      }).then(res => {
        this.searchListLoading = false
        this.searchList = res.data.rows
      })
    },
    handleSelect(item) {
      this.keywords = item.entname
      this.searchList = []
    },
    handleInput() {
      this.searchList = []
    },
    querySearchAsync(queryString, cb) {
    },
    getList() {

    },
    statusStyle(status) {
      if (status !== '1') {
        const style = {
          fontSize: '14px',
          fontFamily: 'Microsoft YaHei',
          fontWeight: '400',
          color: 'rgba(248,130,51,1)'
        }
        return style
      }
      const style = {
        fontSize: '14px',
        fontFamily: 'Microsoft YaHei',
        fontWeight: '400',
        color: '#19B94F'
      }
      return style
    },
    convertPartyList(scope) {
      if (scope.row.actlist !== null && scope.row.actlist !== undefined && scope.row.actlist !== '') {
        const partyname_list = []
        if (scope.row.actlist.length <= 3) {
          scope.row.actlist.forEach(thisItem => {
            if (thisItem.partyname_hlf !== null && thisItem.partyname_hlf !== undefined && thisItem.partyname_hlf !== '') {
              partyname_list.push(thisItem.partyname_hlf)
            } else {
              partyname_list.push(thisItem.partyname)
            }
          })
          return partyname_list.join(';').toLocaleString()
        } else {
          scope.row.actlist.forEach(function(thisItem, index) {
            if (index < 3) {
              if (thisItem.partyname_hlf !== null && thisItem.partyname_hlf !== undefined && thisItem.partyname_hlf !== '') {
                partyname_list.push(thisItem.partyname_hlf)
              } else {
                partyname_list.push(thisItem.partyname)
              }
            }
          })
          return partyname_list.join(';').toLocaleString() + '...'
        }
      }
      return ''
    },
    showMoreEnt(scope) {
      this.$router.push({
        path: '/actionGraph/moreEnt',
        query: {
          entname: scope.row.entname,
          uniscid: scope.row.uniscid,
          entid: scope.row.entid
        }
      })
    },
    viewDetailInfo(row) {
      this.$router.push({ path: "/supplier_graph/actionGraphDetail" });
    }
  }
})
</script>
<style scoped lang="scss">

</style>
