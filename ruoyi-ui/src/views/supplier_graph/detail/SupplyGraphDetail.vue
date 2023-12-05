<template>
  <div>
    <div style="height:calc(100vh - 60px);">
      <RelationGraph ref="graphRef" :options="graphOptions" :on-node-click="onNodeClick" :on-line-click="onLineClick" />
    </div>
  </div>
</template>
<script>
import RelationGraph from 'relation-graph'
import request from "@/utils/request";
export default ({
  name: "SupplyGraphDetail",
  components: { RelationGraph },
  data() {
    return {
      graphOptions: {
        allowSwitchLineShape: true,
        allowSwitchJunctionPoint: true,
        nodeShape: 1,
        defaultJunctionPoint: 'border',
        allowShowMiniToolBar: true,
        layouts: [
          {
            label: '中心',
            layoutName: 'tree',
            from: 'left',
            layoutClassName: 'seeks-layout-center'
          }
        ]
        // 这里可以参考"Graph 图谱"中的参数进行设置 https://seeksdream.github.io/#/docs/graph
      },
      uniscid: null
    }
  },
  mounted() {
    this.uniscid = this.$route.query.uniscid
    this.getGraphData()
    this.showGraph()
  },
  methods: {
    getGraphData() {
      request({
        url: '/entInfo/getSupplierRelation/'+this.uniscid,
        method: 'get',
      }).then(res => {
        const nodes = []
        nodes.push({
          id: res.data.baseEnterpriseInfo.uniscid,
          text: res.data.baseEnterpriseInfo.entname,
          data: res.data.baseEnterpriseInfo,
          nodeShape: 1,
        })

        const lines = []

        res.data.supplierRelevanceList.forEach(thisItem => {
          nodes.push({
            id: thisItem.partyid,
            text: thisItem.partyname,
            data: thisItem,
            nodeShape: 1
          })

          lines.push({
            from: res.data.baseEnterpriseInfo.uniscid,
            to: thisItem.partyid,
            text: thisItem.relpartyp,
            color: 'red'
          })
        })

        this.showGraph(res.data.baseEnterpriseInfo.uniscid,nodes,lines)
      })
    },
    showGraph(rootId,nodes,lines) {
      // list [node1,node2,]
      const jsonData = {
        rootId: rootId,
        nodes: nodes,
        lines: lines
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

</style>
