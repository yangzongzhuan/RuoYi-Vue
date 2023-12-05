<template>
  <div id="app">
    <div style="height:calc(100vh - 60px);border: 1px solid gray;margin: 20px">
      <RelationGraph
        ref="graphRef"
        :on-node-click="onNodeClick"
        :on-line-click="onLineClick"
        :style="{ width: '100%', height: '100%' }">
      </RelationGraph>
    </div>

  </div>
</template>
<script>
import request from "@/utils/request";
import RelationGraph from "relation-graph";
export default ({
  name: "EquityGraphDetail",
  components: {
    RelationGraph
  },
  data() {
    return {
      uniscid: null,
      graphOptions: {
        'backgrounImageNoRepeat': true,
        'moveToCenterWhenRefresh': true,
        'zoomToFitWhenRefresh': true,
        useAnimationWhenRefresh: false,
        placeOtherGroup: true,
        defaultNodeWidth: 150,
        defaultNodeHeight: 30,
        defaultLineWidth: 2,
        defaultLineShape: 4,
        showLineLabel: false,
        lineUseTextPath: true,
        defaultLineTextOffset_x: 0,
        defaultLineTextOffset_y: 0,
        fontColor: '#333',
        'layouts': [
          {
            'label': '中心',
            'layoutName': 'tree',
            from: 'left',
            'layoutClassName': 'seeks-layout-center'
          }
        ]
      },
    }
  },
  mounted() {
    this.uniscid = this.$route.query.uniscid
    this.getGraphDetail()
  },
  methods: {
    getGraphDetail() {
      request({
        url: '/entInfo/getShareHolderAndInvestment/'+this.uniscid,
        method: 'get',
      }).then(res => {
        const nodes = []
        nodes.push({
          id: res.data.baseEnterpriseInfo.uniscid,
          text: res.data.baseEnterpriseInfo.entname,
          data: res.data.baseEnterpriseInfo,
          nodeShape: 1,
        })
        res.data.investmentList.forEach(item => {
          nodes.push({
            id: item.investCreditNo,
            text: item.investName,
            data: item,
            nodeShape: 1,
          })
        })
        res.data.stockholderList.forEach(item => {
          nodes.push({
            id: item.invid,
            text: item.invname,
            data: item,
            nodeShape: 1,
          })
        })
        const lines = []
        res.data.investmentList.forEach(item => {
          lines.push({
            from: res.data.baseEnterpriseInfo.uniscid,
            to: item.investCreditNo,
            text: (parseFloat(item.stockPercent)*100).toLocaleString() + '%',
            color: 'green'
          })
        })

        res.data.stockholderList.forEach(item => {
          lines.push({
            from: item.invid,
            to: res.data.baseEnterpriseInfo.uniscid,
            text: parseFloat(item.subconprop).toLocaleString() + '%',
            color: '#ff0000'
          })
        })

        this.showGraph(res.data.baseEnterpriseInfo.uniscid, nodes, lines)
      })
    },
    onNodeClick(nodeObject, $event) {
      console.log('onNodeClick:', nodeObject)
    },
    onLineClick(lineObject, $event) {
      console.log('onLineClick:', lineObject)
    },
    showGraph(rootId, nodes, lines) {
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
  }
})
</script>
<style scoped lang="scss">

</style>
