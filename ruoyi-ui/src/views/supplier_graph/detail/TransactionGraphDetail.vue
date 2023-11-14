<template>
  <div id="app" style="margin: auto;padding-top: 15px;background-color: #F2F2F2;min-height: 1200px;padding-bottom: 15px">
    <div style="margin-left: 15px;background-color: white;padding-left: 10px;margin-right: 15px;padding-top: 3px;padding-right: 10px;padding-bottom: 10px;height: 300px">
      <div class="c-title-div">
        企业业务往来信息
      </div>
      <div style="height: 300px">
        <el-table
          height="200"
          :data = "entWarrantyList"
          border
        >
          <el-table-column label="企业ID" prop="entname"></el-table-column>
          <el-table-column label="企业名称" prop="waramount"></el-table-column>
          <el-table-column label="交易金额" prop="b_waramount"></el-table-column>
          <el-table-column label="交易类型"></el-table-column>
          <el-table-column label="交易币种"></el-table-column>
          <el-table-column label="交易对手名称"></el-table-column>
          <el-table-column label="交易对手ID"></el-table-column>
          <el-table-column label="交易日期"></el-table-column>
        </el-table>
      </div>
    </div>
    <div style="margin-left: 15px;background-color: white;padding-left: 10px;margin-right: 15px;padding-bottom: 20px;padding-top: 3px;margin-top: 15px">
      <div class="c-title-div">
        资金往来图谱
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
import RelationGraph from 'relation-graph'

export default ({
  name: "SupplyGraphDetail",
  components: { RelationGraph },
  data() {
    return {
      entWarrantyList: [],
      graphOptions: {
        allowSwitchLineShape: true,
        allowSwitchJunctionPoint: true,
        nodeShape: 1,
        defaultJunctionPoint: 'border',
        allowShowMiniToolBar: true
        // 这里可以参考"Graph 图谱"中的参数进行设置 https://seeksdream.github.io/#/docs/graph
      }
    }
  },
  mounted() {
    this.showGraph()
  },
  methods: {
    showGraph() {
      const jsonData = {
        rootId: 'a',
        nodes: [
          { id: 'a', text: '供应商1', nodeShape: 1,borderColor: 'yellow',width: 100,height: 40 },
          { id: 'b', text: '供应商2', nodeShape: 1,width: 100,height: 40 },
          { id: 'c', text: '供应商3', nodeShape: 1,width: 100,height: 40 },
          { id: 'd', text: '供应商4', nodeShape: 1,width: 100,height: 40 },
          { id: 'e', text: '供应商5', nodeShape: 1,width: 100,height: 40 },
          { id: 'f', text: '北京阿里巴巴企业有限公司', nodeShape: 1,width: 100,height: 40 }
        ],
        lines: [
          { from: 'a', to: 'b', text: '关系1' },
          { from: 'a', to: 'c', text: '关系2' },
          { from: 'a', to: 'd', text: '关系3' },
          { from: 'a', to: 'e', text: '关系4' },
          { from: 'a', to: 'f', text: '关系5' }
        ]
      }
      // 以上数据中的node和link可以参考"Node节点"和"Link关系"中的参数进行配置
      this.$refs.graphRef.setJsonData(jsonData, (graphInstance) => {
        // Called when the relation-graph is completed
      })
    },
    onNodeClick(nodeObject, $event) {
      console.log('onNodeClick:', nodeObject)
    },
    onLineClick(lineObject, $event) {
      console.log('onLineClick:', lineObject)
    }
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
