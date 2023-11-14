<template>
  <div id="app" style="margin: auto;padding-top: 15px;background-color: #F2F2F2;min-height: 1200px;padding-bottom: 15px">
    <div style="margin-left: 15px;background-color: white;padding-left: 10px;margin-right: 15px;padding-top: 3px;padding-right: 10px;padding-bottom: 10px;height: 300px">
      <div class="c-title-div">
        企业担保信息
      </div>
      <div style="height: 300px">
        <el-table
          height="200"
          :data = "entWarrantyList"
          border
        >
          <el-table-column label="企业名称" prop="entname"></el-table-column>
          <el-table-column label="担保金额" prop="waramount"></el-table-column>
          <el-table-column label="被担保金额" prop="b_waramount"></el-table-column>
          <el-table-column label="担保企业"></el-table-column>
          <el-table-column label="担保日期"></el-table-column>
          <el-table-column label="担保期限"></el-table-column>
          <el-table-column label="主债权人名称"></el-table-column>
          <el-table-column label="债务人名称"></el-table-column>
          <el-table-column label="主债权数额"></el-table-column>
          <el-table-column label="主债权到期日"></el-table-column>
          <el-table-column label="数据来源"></el-table-column>
          <el-table-column label="数据日期"></el-table-column>
        </el-table>
      </div>
    </div>
    <div style="margin-left: 15px;background-color: white;padding-left: 10px;margin-right: 15px;padding-bottom: 20px;padding-top: 3px;margin-top: 15px">
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
  name: "WarrantyGraphDetail",
  components: { RelationGraph },
  data(){
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
      entWarrantyList: [],
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
  }
})
</script>
<style scoped lang="scss">
.c-title-div {
  border-left: 10px solid #002060;
  padding-left: 10px;
  height: 30px;
  line-height: 30px;
  font-weight: bolder;
  font-size: 18px;
  color: #002060;
  margin-top: 10px;
  margin-bottom: 20px;
}
</style>
