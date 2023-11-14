<template>
  <div style="margin: auto;background-color: #f9f9f9;padding: 15px;min-height: 700px">
    <div v-loading="labelLoading" style="background-color: white;padding:15px">
      <table style="font-size: 15px;font-family: 'Microsoft YaHei';width: 100%">
        <tbody>
        <tr>
          <td style="padding-left: 10px;width: 130px;padding-right: 10px" rowspan="2">
            <i class="el-icon-office-building" style="font-size: 80px;color: gainsboro;border: 1px solid gainsboro" />
          </td>
          <td style="font-size: 24px;font-weight: 800;font-family: 'Microsoft YaHei';height: 60px;width: 83%;">
            <div style="float: left">集团名称：{{ grpInfo.grpname }}</div><br>
            <div style="margin-top: 14px">
              <div class="c-count-div">集团成员：{{ grpInfo.mbrCount }}</div>
              <div class="c-count-div">龙头企业：{{ grpInfo.leadingCount }}</div>
              <div class="c-count-div">核心企业：{{ grpInfo.coreCount }}</div>
              <div class="c-count-div">一般企业：{{ grpInfo.generalCount }}</div>
              <div class="c-count-div">上市公司：{{ grpInfo.labs.listedmbrs === undefined?'0':grpInfo.labs.listedmbrs }}</div>
            </div>
          </td>
          <td>
            <div
              style="border: 1px solid #01B0F0;
                          margin-left: 20px;
                          color: #01B0F0;font-size: 20px;
                          width: 140px;text-align: center;
                          height: 40px;line-height: 40px;border-radius: 10px;padding-bottom: 40px"
              @click="downLoadReport"
            >
              <svg-icon icon-class="cloud-download" style="color: #01B0F0" />
              <el-link type="primary" :underline="false" style="margin-bottom: 5px">集团报告</el-link>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <div
      style="background-color: white;
      margin-top: 10px;
      border-bottom: 1px solid #e3e8ee;
      padding-top: 2px;
      padding-left: 15px"
    >
      <div class="c-title-div">
        集团客户特征
      </div>
      <div v-loading="labelLoading" style="margin-top: 10px;;margin-right: 20px">
        <table class="c-base-table" style="width: 100%">
          <tbody>
          <tr>
            <td class="c-td-title" style="width: 25%;">集团内注册资本总额:</td>
            <td class="c-td-text c-td-text-padding" style="width: 25%;">{{ grpInfo.labs.grpregcaps === undefined?'-':grpInfo.labs.grpregcaps.toLocaleString()+'万元' }}</td>
            <td class="c-td-title" style="width: 25%;">集团内房地产⾏业企业数量:</td>
            <td class="c-td-text c-td-text-padding" style="width: 25%;">{{ grpInfo.labs.realtymbrs === undefined?'-':grpInfo.labs.realtymbrs }}</td>
          </tr>
          <tr>
            <td class="c-td-title">集团内国有企业数量:</td>
            <td class="c-td-text  c-td-text-padding">{{ grpInfo.labs.statembrs === undefined?'-':grpInfo.labs.statembrs }}</td>
            <td class="c-td-title">集团内国有企业占⽐:</td>
            <td class="c-td-text  c-td-text-padding">{{ grpInfo.labs.statesprop === undefined?'-':grpInfo.labs.statesprop }}</td>
          </tr>
          <tr>
            <td class="c-td-title">集团内⾏业数量:</td>
            <td class="c-td-text  c-td-text-padding">{{ grpInfo.labs.grpindustrs === undefined?'-':grpInfo.labs.grpindustrs }}</td>
            <td class="c-td-title">⾏业集中度:</td>
            <td class="c-td-text  c-td-text-padding">{{ grpInfo.labs.grpindusporp === undefined?'-':grpInfo.labs.grpindusporp+'%' }}</td>
          </tr>
          <tr>
            <td class="c-td-title">集团内成员注册省份数量:</td>
            <td class="c-td-text  c-td-text-padding">{{ grpInfo.labs.grpprovs === undefined?'-':grpInfo.labs.grpprovs }}</td>
            <td class="c-td-title">区域集中度:</td>
            <td class="c-td-text  c-td-text-padding">{{ grpInfo.labs.grpprovprop === undefined?'-':grpInfo.labs.grpprovprop+'%' }}</td>
          </tr>
          <tr>
            <td class="c-td-title">是否有银⾏牌照:</td>
            <td class="c-td-text  c-td-text-padding">{{ grpInfo.labs.islicbank_cn === undefined?'-':grpInfo.labs.islicbank_cn }}</td>
            <td class="c-td-title">是否有证券牌照:</td>
            <td class="c-td-text  c-td-text-padding">{{ grpInfo.labs.islicsec_cn === undefined?'-':grpInfo.labs.islicsec_cn }}</td>
          </tr>
          <tr>
            <td class="c-td-title">是否有保险牌照:</td>
            <td class="c-td-text  c-td-text-padding">{{ grpInfo.labs.islicisur_cn === undefined?'-':grpInfo.labs.islicisur_cn }}</td>
            <td class="c-td-title">是否有基⾦牌照:</td>
            <td class="c-td-text  c-td-text-padding">{{ grpInfo.labs.islicfund_cn === undefined?'-':grpInfo.labs.islicfund_cn }}</td>
          </tr>
          <tr>
            <td class="c-td-title">是否有信托牌照:</td>
            <td class="c-td-text  c-td-text-padding">{{ grpInfo.labs.islictrust_cn === undefined?'-':grpInfo.labs.islictrust_cn }}</td>
            <td class="c-td-title">是否有期货牌照:</td>
            <td class="c-td-text  c-td-text-padding">{{ grpInfo.labs.islicfutur_cn === undefined?'-':grpInfo.labs.islicfutur_cn }}</td>
          </tr>
          <tr>
            <td class="c-td-title">是否有租赁牌照:</td>
            <td class="c-td-text  c-td-text-padding">{{ grpInfo.labs.islicleas_cn === undefined?'-':grpInfo.labs.islicleas_cn }}</td>
            <td class="c-td-title">集团拥有⾦融牌照种类数量:</td>
            <td class="c-td-text  c-td-text-padding">{{ grpInfo.labs.licnums === undefined?'-':grpInfo.labs.licnums }}</td>
          </tr>
          <tr>
            <td class="c-td-title">集团内⾼新技术企业数量:</td>
            <td class="c-td-text  c-td-text-padding">{{ grpInfo.labs.innombrs === undefined?'-':grpInfo.labs.innombrs }}</td>
            <td class="c-td-title" />
            <td class="c-td-text  c-td-text-padding" />
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div
      style="background-color: white;
      margin-top: 10px;
      border-bottom: 1px solid #e3e8ee;
      padding-top: 2px;
      padding-left: 15px"
    >
      <div class="c-title-div">
        集团成员列表
      </div>
      <div v-loading="membersLoading" style="margin-top: 10px;background-color: white;padding-bottom: 20px;padding-right: 30px;">

        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="集团成员列表" name="second">
            <div class="demo-input-suffix" style="padding-bottom: 10px;padding-top: 10px">
              <el-input
                v-model="groupInput"
                placeholder="请输入集团成员名称"
                prefix-icon="el-icon-search"
                style="width: 270px"
                @input="handleGroup"
              />
            </div>
            <table class="c-base-table" style="width: 100%">
              <thead>
              <tr>
                <th class="c-th-header" style="width: 4%;">序号</th>
                <th class="c-th-header" style="width: 23%;">集团成员名称</th>
                <th class="c-th-header" style="width: 7%;">经营状态</th>
                <th class="c-th-header" style="width: 7%;">成员角色</th>
                <th class="c-th-header" style="width: 8%;">是否上市</th>
                <th class="c-th-header" style="width: 18%;">关联龙头</th>
                <th class="c-th-header" style="width: 7%;">穿透比例</th>
                <th class="c-th-header" style="width: 18%;">穿透详情</th>
                <th class="c-th-header" style="width: 10%;">操作</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="(item,index) in groupMembers " :key="index">
                <td class="c-td-text" style="text-align: center">{{ index+1 }}</td>
                <td class="c-td-text" style="padding-left: 10px">{{ item.mbrname }}</td>
                <td class="c-td-text" style="text-align: center">
                  <el-popover
                    placement="top-start"
                    trigger="hover"
                    :content="item.entstatus_cn"
                  >
                    <el-button slot="reference" type="text">
                      {{ item.entstatus_cn }}
                    </el-button>
                  </el-popover>
                </td>
                <td class="c-td-text" style="text-align: center">{{ item.mbrroles_cn }}</td>
                <td class="c-td-text" style="text-align: center">{{ item.islist_cn }}<span v-show="(item.islist_cn==='是')">({{ item.skcode }})</span></td>
                <td class="c-td-text" style="padding-left: 10px">{{ (item.leaderName !== undefined && item.leaderName !== '')?item.leaderName:'-' }}</td>
                <td class="c-td-text" style="text-align: center">
                  {{ item.leaderInvsRatio }}
                </td>
                <td class="c-td-text" style="font-size: 10px;padding-left: 10px">
                  {{ handleRelPath(item.relpath) }} <br>
                </td>
                <td class="c-td-text" style="text-align: center">
                  <el-link
                    v-show="(item.relpath !== null && item.relpath !== undefined && item.relpath.length>0)"
                    type="primary"
                    :underline="false"
                    style="font-size: 14px;margin-bottom: 2.5px"
                    @click="showMorePath(item)"
                  >
                    详情
                  </el-link>
                </td>
              </tr>
              </tbody>
            </table>
            <div class="block" style="text-align: right;width: 100%;">
              <el-pagination
                :current-page="currentPage"
                :page-size="10"
                :total="groupMembersTotal"
                layout="total, prev,pager, next"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
              />
            </div>
          </el-tab-pane>
          <el-tab-pane label="龙头企业的实际控制人" name="first">
            <table class="c-base-table" style="width: 98%">
              <thead>
              <tr>
                <th class="c-th-header">序号</th>
                <th class="c-th-header">龙头企业名称</th>
                <th class="c-th-header">实际控制人</th>
                <!--                  <th class="c-th-header">集团成员经营状态</th>-->
                <!--                  <th class="c-th-header">是否为上市公司</th>-->
              </tr>
              </thead>
              <tbody>
              <tr v-for="(item,index) in leadingMembers.slice((currentLeadPage-1)*10,currentLeadPage*10)" :key="index">
                <td class="c-td-text" style="text-align: center">{{ index+1 }}</td>
                <td class="c-td-text  c-td-text-padding">{{ item.memberName }}</td>
                <td class="c-td-text  c-td-text-padding">{{ item.controllers }}</td>
                <!--                  <td class="c-td-text"><span :style="( statusStyle(item.entstatus))">{{ item.entstatus_cn }}</span></td>-->
                <!--                  <td class="c-td-text">{{ item.islist_cn }}<span v-show="(item.islist_cn==='是')">({{ item.skcode }})</span></td>-->
              </tr>
              </tbody>
            </table>
            <div class="block" style="text-align: right;width: 100%;">
              <el-pagination
                :current-page="currentLeadPage"
                :page-size="10"
                :total="leadingMembers.length"
                layout="total, prev,pager, next"
                @current-change="handleCurrentLeadChange"
              />
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
    <div style="padding: 15px;background-color: white;margin-top: 15px">
      <div class="c-title-div">
        集团知识图谱
      </div>
      <div style="height:calc(100vh - 60px);">
        <RelationGraph
          ref="graphRef"
          :options="graphOptions"
          :on-node-click="onNodeClick"
          :on-line-click="onLineClick"
        />
      </div>
    </div>
  </div>
</template>
<script>
import RelationGraph from "relation-graph";
export default({
  name: "SupplyGroupDetail",
  components: {
    RelationGraph
  },
  data() {
    return {
      graphOptions: {
        defaultNodeBorderWidth: 0,
        defaultNodeColor: 'rgba(238, 178, 94, 1)',
        allowSwitchLineShape: true,
        allowSwitchJunctionPoint: true,
        defaultLineShape: 1,
        'layouts': [
          {
            'label': '中心',
            'layoutName': 'center',
            'layoutClassName': 'seeks-layout-center'
          }
        ],
        defaultJunctionPoint: 'border'
      },
      labelLoading: false,
      membersLoading: false,
      activeName: 'second',
      groupInput: '',
      currentPage: 1,
      currentLeadPage: 1,
      groupMembersTotal: 0,
      groupMembers: [
        {
          "grpid": "GR06F274C746D1E7C57E0539601A8C0ACD65",
          "entstatus_cn": "存续（在营、开业、在册）",
          "mbrname": "轲有（上海）汽车服务有限公司",
          "mbrroles": "01",
          "mbrid": "6F274C746D1E7C57E0539601A8C0ACD65",
          "grpname": "轲有（上海）汽车服务有限公司",
          "entstatus": "1",
          "mbrroles_cn": "龙头企业",
          "skcode": null,
          "islist": "0",
          "islist_cn": "否"
        },
        {
          "grpid": "GR06F274C746D1E7C57E0539601A8C0ACD65",
          "entstatus_cn": "存续",
          "mbrname": "三门成创空间科技服务有限公司",
          "mbrroles": "01",
          "mbrid": "6F274CFE667B7C57E0539601A8C0ACD65",
          "grpname": "轲有（上海）汽车服务有限公司",
          "entstatus": "1",
          "mbrroles_cn": "龙头企业",
          "skcode": null,
          "islist": "0",
          "islist_cn": "否"
        },
        {
          "grpid": "GR06F274C746D1E7C57E0539601A8C0ACD65",
          "entstatus_cn": "存续（在营、开业、在册）",
          "mbrname": "上海台海贸易有限公司",
          "mbrroles": "01",
          "mbrid": "6F274C4984D97C57E0539601A8C0ACD65",
          "grpname": "轲有（上海）汽车服务有限公司",
          "entstatus": "1",
          "mbrroles_cn": "龙头企业",
          "skcode": null,
          "islist": "0",
          "islist_cn": "否"
        },
        {
          "grpid": "GR06F274C746D1E7C57E0539601A8C0ACD65",
          "entstatus_cn": "存续",
          "mbrname": "台州万维文化传媒有限公司",
          "mbrroles": "02",
          "mbrid": "0463086E935548189F033B253D6CD9DD4",
          "grpname": "轲有（上海）汽车服务有限公司",
          "entstatus": "1",
          "mbrroles_cn": "核心企业",
          "skcode": null,
          "islist": "0",
          "islist_cn": "否",
          "leaderName": "三门成创空间科技服务有限公司",
          "leaderInvsRatio": "70",
          "relpath": [
            {
              "path": "三门成创空间科技服务有限公司->70.0000%:台州万维文化传媒有限公司",
              "pathRatio": "70"
            }
          ],
          "minLevel": 1
        }
      ],
      leadingMembers: [
        {
          "grpid": "GR06F274C746D1E7C57E0539601A8C0ACD65",
          "entstatus_cn": "存续（在营、开业、在册）",
          "mbrname": "轲有（上海）汽车服务有限公司",
          "mbrroles": "01",
          "mbrid": "6F274C746D1E7C57E0539601A8C0ACD65",
          "grpname": "轲有（上海）汽车服务有限公司",
          "entstatus": "1",
          "mbrroles_cn": "龙头企业",
          "skcode": null,
          "islist": "0",
          "islist_cn": "否"
        },
        {
          "grpid": "GR06F274C746D1E7C57E0539601A8C0ACD65",
          "entstatus_cn": "存续",
          "mbrname": "三门成创空间科技服务有限公司",
          "mbrroles": "01",
          "mbrid": "6F274CFE667B7C57E0539601A8C0ACD65",
          "grpname": "轲有（上海）汽车服务有限公司",
          "entstatus": "1",
          "mbrroles_cn": "龙头企业",
          "skcode": null,
          "islist": "0",
          "islist_cn": "否"
        },
        {
          "grpid": "GR06F274C746D1E7C57E0539601A8C0ACD65",
          "entstatus_cn": "存续（在营、开业、在册）",
          "mbrname": "上海台海贸易有限公司",
          "mbrroles": "01",
          "mbrid": "6F274C4984D97C57E0539601A8C0ACD65",
          "grpname": "轲有（上海）汽车服务有限公司",
          "entstatus": "1",
          "mbrroles_cn": "龙头企业",
          "skcode": null,
          "islist": "0",
          "islist_cn": "否"
        },
        {
          "grpid": "GR06F274C746D1E7C57E0539601A8C0ACD65",
          "entstatus_cn": "存续",
          "mbrname": "台州万维文化传媒有限公司",
          "mbrroles": "02",
          "mbrid": "0463086E935548189F033B253D6CD9DD4",
          "grpname": "轲有（上海）汽车服务有限公司",
          "entstatus": "1",
          "mbrroles_cn": "核心企业",
          "skcode": null,
          "islist": "0",
          "islist_cn": "否",
          "leaderName": "三门成创空间科技服务有限公司",
          "leaderInvsRatio": "70",
          "relpath": [
            {
              "path": "三门成创空间科技服务有限公司->70.0000%:台州万维文化传媒有限公司",
              "pathRatio": "70"
            }
          ],
          "minLevel": 1
        }
      ],
      relaInfoHidden: false,
      relaListDataLength: 0,
      grpInfo: {
        "grpid": "GR06F274C746D1E7C57E0539601A8C0ACD65",
        "grpname": "轲有（上海）汽车服务有限公司",
        "mbrCount": 4,
        "leadingCount": 3,
        "coreCount": 1,
        "generalCount": 0,
        "labs": {
          "grpindustrs": 3,
          "grpprovprop": 100.0,
          "statesprop": 0.0,
          "listedmbrs": 0,
          "licnums": 0,
          "islicisur": "0",
          "islicfund": "0",
          "islicfutur": "0",
          "islicleas": "0",
          "realtymbrs": 0,
          "islictrust": "0",
          "statembrs": 0,
          "innombrs": 0,
          "grpindusporp": 100.0,
          "grpregcaps": 11150.0,
          "grpembrs": 4,
          "grpprovs": 2,
          "islicsec": "0",
          "islicbank": "0",
          "islicbank_cn": "否",
          "islicsec_cn": "否",
          "islicisur_cn": "否",
          "islicfund_cn": "否",
          "islictrust_cn": "否",
          "islicfutur_cn": "否",
          "islicleas_cn": "否"
        }
      }
    }
  },
  mounted() {
    this.showSeeksGraph();
  },
  methods: {
    showSeeksGraph() {
      const __graph_json_data = {
        rootId: '2',
        nodes: [
          { id: '1', text: '节点-1', myicon: 'el-icon-star-on' },
          { id: '2', text: '节点-2', myicon: 'el-icon-setting' },
          { id: '4', text: '节点-4', myicon: 'el-icon-star-on' },
          { id: '6', text: '节点-6', myicon: 'el-icon-setting' },
          { id: '7', text: '节点-7', myicon: 'el-icon-setting' },
          { id: '8', text: '节点-8', myicon: 'el-icon-star-on' },
          { id: '9', text: '节点-9', myicon: 'el-icon-headset' }
        ],
        lines: [
          { from: '1', to: '2', text: '投资' },
          { from: '4', to: '2', text: '高管' },
          { from: '6', to: '2', text: '高管' },
          { from: '7', to: '2', text: '高管' },
          { from: '8', to: '2', text: '高管' },
          { from: '9', to: '2', text: '高管' }
        ]
      };
      this.$refs.graphRef.setJsonData(__graph_json_data, (graphInstance) => {
        // 这些写上当图谱初始化完成后需要执行的代码
      });
    },
    downLoadReport() {
      const url = '/smart-api/aff-enrelpar-ws/enrelpar/getEnrelpars?entid='
    },
    handleSizeChange(val) {
      this.currentPage = val
    },
    handleCurrentChange(val) {
      this.currentPage = val
    },
    handleCurrentLeadChange(val) {
      this.currentLeadPage = val
    },
    handleClick(tab, event) {
      console.log(tab, event)
    },
    handleGroup() {
      this.groupMembers = this.grpInfo.mbrs.filter(item => item.mbrname.indexOf(this.groupInput) !== -1)
      this.groupMembersTotal = this.groupMembers.length
    },
    handleRelPath(relPath) {
      if (relPath === null || relPath === undefined) return ''
      let relPathStr = ''
      for (let i = 0; i < relPath.length; i++) {
        if (i === relPath.length - 1) {
          relPathStr += relPath[i].mbrname
        } else {
          relPathStr += relPath[i].mbrname + '->'
        }
      }
      return relPathStr
    },
    showMorePath(item) {
      this.$router.push({
        path: '/nrzs/relationInfo',
        query: {
          queryId: item.mbrid
        }
      })
    }
  }
})
</script>
<style scoped lang="scss">
.c-td-title{
  background:#fcfcfc;
  border:1px solid #fcfcfc;
  padding-left: 10px;
  font-size:14px;
  font-family:Microsoft YaHei;
  font-weight:bolder;
  height: 40px;
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
  font-size:14px;
  height: 40px;
  padding-left: 10px;
  background:#f9f9f9;
  font-family:Microsoft YaHei;
  font-weight:bolder;
  color:rgba(96,96,96,1);
  border:1px solid #fcfcfc;
}
.c-td-text{
  text-align: left;
  height: 40px;
  line-height: 25px;
  font-size: 14px;
  font-family:Microsoft YaHei;
  font-weight:400;
  color:rgba(96,96,96,1);
  background:white;
  border:1px solid #fcfcfc;
}
.c-td-text-padding{
  padding-left: 10px;
}
.c-count-div{
  border-radius: 6px;
  border: 1px solid #01B050;
  color: #01B050;
  margin-right: 5px;
  height: 30px;
  min-width: 85px;
  padding-left: 5px;
  padding-right: 5px;
  float: left;
  font-size: 11px;
  line-height: 30px;
  text-align: center;
}
.c-title-div{
  border-left: 10px solid #002060;
  padding-left: 10px;
  height: 30px;
  line-height: 30px;
  font-weight: bolder;
  font-size: 18px;
  color: #002060;
  margin-top: 20px;
  margin-bottom: 20px;
}
</style>
