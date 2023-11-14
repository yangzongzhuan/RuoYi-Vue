<template>
  <div id="s-container" />

</template>

<script>
import G6 from '@antv/g6'
export default {
  name: 'OneZeroSevenGraph',
  mounted() {
    this.createGraph()
  },
  methods: {
    createGraph() {
      const data = {
        // 点集
        nodes: [
          {
            id: 'ent_a', // String，该节点存在则必须，节点的唯一标识,
            label: '企业A',
            style: {
              fill: '#CCFFCC'
            },
            x: 100,
            y: 60
          },
          {
            id: 'person', // String，该节点存在则必须，节点的唯一标识
            label: '自然人M',
            style: {
              fill: '#FFE6CC'
            },
            x: 350,
            y: 60
          },
          {
            id: 'target', // String，该节点存在则必须，节点的唯一标识
            label: '目标企业',
            x: 250,
            y: 330,
            style: {
              fill: '#CCFFFF',
              radius: 20
            }
          }
        ],
        // 边集
        edges: [
          {
            source: 'ent_a', // String，必须，起始点 id
            target: 'person', // String，必须，目标点 id
            label: 'A与M为目标企业的\n一致行动人',
            shape: 'quadratic',
            style: {
              stroke: '#F6BD16',
              lineDash: [4, 4, 4, 4],
              startArrow: {
                path: 'M 3,0 L -3,-3 L -3,3 Z', // 自定义箭头路径
                d: 3 // 偏移量
              },
              endArrow: {
                path: 'M 3,0 L -3,-3 L -3,3 Z', // 自定义箭头路径
                d: 3 // 偏移量
              }
            },
            labelCfg: {
              refY: 15
            }
          },
          {
            source: 'person', // String，必须，起始点 id
            target: 'ent_a', // String，必须，目标点 id
            shape: 'quadratic',
            label: '持股30%以上',
            labelCfg: {
              refY: -10
            }
          },
          {
            source: 'ent_a', // String，必须，起始点 id
            target: 'target', // String，必须，目标点 id
            label: '持股',
            shape: 'line'
          },
          {
            source: 'person', // String，必须，起始点 id
            target: 'target', // String，必须，目标点 id
            label: '持股',
            shape: 'line'
          }
        ]
      }
      const graph = new G6.Graph({
        container: 's-container', // String | HTMLElement，必须，在 Step 1 中创建的容器 id 或容器本身
        width: 800, // Number，必须，图的宽度
        height: 500, // Number，必须，图的高度
        defaultNode: {
          size: [100, 40],
          shape: 'rect',
          style: {
            lineWidth: 2
          }
        },
        defaultEdge: {
          shape: 'line',
          style: {
            stroke: '#F6BD16',
            refY: 10,
            endArrow: {
              path: 'M 3,0 L -3,-3 L -3,3 Z', // 自定义箭头路径
              d: 3 // 偏移量
            }
          }
        },
        modes: {
          default: ['drag-canvas'] // 允许拖拽画布、放缩画布、拖拽节点
        }
      })
      graph.data(data)
      graph.render() // 渲染图
    }
  }
}
</script>

<style scoped>

</style>
