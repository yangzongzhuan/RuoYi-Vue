<template>
  <div id="s-container" />
</template>

<script>
import G6 from '@antv/g6'
export default {
  name: 'OneZeroThreeGraph',
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
            y: 80
          },
          {
            id: 'ent_b', // String，该节点存在则必须，节点的唯一标识
            label: '企业B',
            style: {
              fill: '#CCFFCC'
            },
            x: 350,
            y: 80
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
            target: 'ent_b', // String，必须，目标点 id
            label: 'A与B为目标企业的\n一致行动人',
            labelCfg: {
              refY: 15
            },
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
            }
          },
          {
            source: 'ent_a', // String，必须，起始点 id
            target: 'target', // String，必须，目标点 id
            label: '持股20%~50%并且与B投资比例相同',
            shape: 'line',
            labelCfg: {
              autoRotate: true,
              refY: -15
            }
          },
          {
            source: 'ent_b', // String，必须，起始点 id
            target: 'target', // String，必须，目标点 id
            label: '持股20%~50%并且与A投资比例相同',
            shape: 'line',
            labelCfg: {
              autoRotate: true,
              refY: -15
            }
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
            lineWidth: 2,
            stroke: '#5B8FF9',
            fill: '#C6E5FF'
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
