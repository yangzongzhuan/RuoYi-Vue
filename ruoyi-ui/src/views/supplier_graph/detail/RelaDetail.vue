<template>
  <div id="app" style="margin: auto;padding-bottom: 30px;background-color: #F2F2F2;padding-top: 10px;">
    <div style="padding: 15px 15px 15px 15px;background-color: white;margin-left: 15px;margin-right: 15px">
      <div class="c-title-div">
        企业基本信息
      </div>
      <table class="c-base-table" style="width: 100%;margin-bottom: 10px">
        <tbody>
        <tr>
          <td class="c-td-title" style="width: 12%">企业ID：</td>
          <td class="c-td-text" style="width: 20%">{{ baseinfo.entid }}</td>
          <td class="c-td-title" style="width: 12%">企业名称：</td>
          <td class="c-td-text" style="width: 20%">{{ baseinfo.entname }}</td>
          <td class="c-td-title" style="width: 12%">统一社会信用代码：</td>
          <td class="c-td-text" style="width: 20%">{{ baseinfo.uniscid }}</td>
        </tr>
        <tr>
          <td class="c-td-title">企业经营状态：</td>
          <td class="c-td-text">{{ baseinfo.entstatus_cn }}</td>
          <td class="c-td-title">是否为上市公司：</td>
          <td class="c-td-text">{{ baseinfo.islist_cn }}</td>
          <td class="c-td-title">股票代码：</td>
          <td class="c-td-text">{{ baseinfo.skcode }}</td>
        </tr>
        </tbody>
      </table>
    </div>
    <div style="padding: 15px 15px 15px 15px;background-color: white;margin-left: 15px;margin-right: 15px;margin-top: 15px">
      <div class="c-title-div">
        企业关联方信息
      </div>
      <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
        <el-tab-pane label="企业关联方列表" name="relaList">
          <el-table :data="enrelpar.slice((currentPage-1)*10,currentPage*10)" border style="width: 100%;margin-bottom: 10px" :header-cell-style="{backgroundColor:'#f9f9f9'}">
            <el-table-column
              type="index"
              width="50"
              label="序号"
            />
            <el-table-column
              prop="partyname"
              label="关联⽅名称"
            />
            <el-table-column
              prop="partytyp_cn"
              label="关联⽅类型"
            />
            <el-table-column
              prop="relpartyp_cn"
              label="关联关系类型"
            />
            <el-table-column
              prop="uniscid"
              label="统一社会信用代码"
            />
            <el-table-column
              prop="partystatus_cn"
              label="关联⽅经营状态"
            />
          </el-table>
          <div class="block" style="text-align: right;width: 100%;margin-top: 10px;">
            <el-pagination
              :current-page="currentPage"
              :page-size="10"
              :total="enrelpar.length"
              layout="total, prev,pager, next"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            />
          </div>
        </el-tab-pane>
        <el-tab-pane label="上市公司自行披露的关联方" name="stockRelaList">
          <el-table :data="listEnrelpar" border style="width: 100%;margin-bottom: 10px" :header-cell-style="{backgroundColor:'#f9f9f9'}">
            <el-table-column
              type="index"
              label="序号"
            />
            <el-table-column
              prop="partyname"
              label="关联⽅名称"
            />
            <el-table-column
              prop="partytyp_cn"
              label="关联⽅类型"
            />
            <el-table-column
              prop="relpartyp_cn"
              label="关联关系类型"
            />
            <el-table-column
              prop="uniscid"
              label="统一社会信用代码"
            />
            <el-table-column
              prop="partystatus_cn"
              label="关联⽅经营状态"
            />
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div style="padding: 15px;background-color: white;margin-left: 15px;margin-right: 15px;margin-top: 15px">
      <div class="c-title-div">
        企业关联方图谱
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
  name: "RelaDetail",
  components: { RelationGraph },
  data() {
    return {
      activeName: 'relaList',
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
      baseinfo: {
        "entid": "4A2345AB756F4CB1BBA1F74B784ACB814",
        "entname": "贵州读食食品有限公司",
        "entname_hlf": null,
        "uniscid": "91520102MAAJX8H613",
        "entstatus": "1",
        "entstatus_cn": "在营（开业）",
        "islist": "0",
        "islist_cn": "否",
        "skcode": null
      },
      listEnrelpar: [],
      enrelpar: [
        {
          "partyid": "inv72b7d1df6852f798fb93dcc35d2a4a8e",
          "partyname": "赵斌",
          "partytyp": "01",
          "partytyp_cn": "自然人",
          "relpartyp": "05",
          "relpartyp_cn": "主要投资者个人",
          "partystatus": null,
          "partystatus_cn": null,
          "uniscid": null,
          "subconprop": "10.0"
        },
        {
          "partyid": "inv68b44bc0bddd9257a7136f89ae492ca2",
          "partyname": "李莹",
          "partytyp": "01",
          "partytyp_cn": "自然人",
          "relpartyp": "05",
          "relpartyp_cn": "主要投资者个人",
          "partystatus": null,
          "partystatus_cn": null,
          "uniscid": null,
          "subconprop": "75.0"
        },
        {
          "partyid": "inv2721faa64604b44f2c256053102a9277",
          "partyname": "王辉",
          "partytyp": "01",
          "partytyp_cn": "自然人",
          "relpartyp": "05",
          "relpartyp_cn": "主要投资者个人",
          "partystatus": null,
          "partystatus_cn": null,
          "uniscid": null,
          "subconprop": "15.0"
        }
      ],
      currentPage: 1,
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
    handleClick(tab, event) {
      console.log(tab, event);
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    }
  }
})
</script>
<style scoped lang="scss">
.c-td-title{
  background:#f9f9f9;
  border:1px solid #D0CECE;
  padding-left: 10px;
  font-size:14px;
  font-family:Microsoft YaHei;
  font-weight:bolder;
  height: 40px;
  color:rgba(96,96,96,1);
}
.c-base-table,.c-base-table tr th, .c-base-table tr td {
  border-bottom:1px solid #D0CECE;
  border-top:1px solid #D0CECE;
}
.c-base-table { border-collapse: collapse;}
.c-title-div{
  border-left: 10px solid #002060;
  padding-left: 10px;
  height: 30px;
  line-height: 30px;
  font-weight: bolder;
  font-size: 18px;
  color: #002060;
  margin-bottom: 20px;
}
.c-td-text{
  padding-left: 10px;
  text-align: left;
  height: 40px;
  font-size:14px;
  line-height: 40px;
  font-family:Microsoft YaHei;
  font-weight:400;
  color:rgba(96,96,96,1);
  background:white;
  border:1px solid #D0CECE;
}
</style>
