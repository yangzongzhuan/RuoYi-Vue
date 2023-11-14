<template>
  <div id="app">
    <div style="float: right;margin-top: -30px">
      <el-pagination
        background
        :page-size="1"
        layout="pager"
        :current-page="currentPage"
        :total="2"
        @current-change="handleCurrentChange"
      />
    </div>
    <div v-show="currentPage === 1" id="s-container-one" />
    <div v-show="currentPage === 2" id="s-container-two" />
  </div>
</template>

<script>
import G6 from '@antv/g6'

export default {
  name: 'TwoZeroTwoGraph',
  data() {
    return {
      currentPage: 1
    }
  },
  mounted() {
    this.createGraphOne()
  },
  methods: {
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`)
      this.currentPage = val
      if (val === 1) {
        this.createGraphOne()
      }
      if (val === 2) {
        this.createGraphTwo()
      }
    },
    createGraphOne() {
      const data = {
        // 点集
        nodes: [
          {
            id: 'person_n', // String，该节点存在则必须，节点的唯一标识,
            label: '自然人N',
            style: {
              fill: '#FFE6CC'
            },
            x: 350,
            y: 80
          },
          {
            id: 'person_m', // String，该节点存在则必须，节点的唯一标识,
            label: '自然人M',
            style: {
              fill: '#FFE6CC'
            },
            x: 100,
            y: 80
          },
          {
            id: 'target', // String，该节点存在则必须，节点的唯一标识
            label: '目标企业',
            x: 225,
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
            source: 'person_n', // String，必须，起始点 id
            target: 'person_m', // String，必须，目标点 id
            label: 'N与M为目标企业的\n一致行动人',
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
            source: 'person_m', // String，必须，起始点 id
            target: 'target', // String，必须，目标点 id
            label: '持股5%~20%对目标企业有重大影响',
            shape: 'line',
            labelCfg: {
              refY: -10,
              autoRotate: true
            }
          },
          {
            source: 'person_n', // String，必须，起始点 id
            target: 'target', // String，必须，目标点 id
            label: '持股5%~20%对目标企业有重大影响',
            shape: 'line',
            labelCfg: {
              refY: -10,
              autoRotate: true
            }
          }
        ]
      }
      const graph = new G6.Graph({
        container: 's-container-one', // String | HTMLElement，必须，在 Step 1 中创建的容器 id 或容器本身
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
    },
    createGraphTwo() {
      const data = {
        // 点集
        nodes: [
          {
            id: 'person_n', // String，该节点存在则必须，节点的唯一标识,
            label: '自然人N',
            style: {
              fill: '#FFE6CC'
            },
            x: 350,
            y: 80
          },
          {
            id: 'person_m', // String，该节点存在则必须，节点的唯一标识,
            label: '自然人M',
            style: {
              fill: '#FFE6CC'
            },
            x: 100,
            y: 80
          },
          {
            id: 'target', // String，该节点存在则必须，节点的唯一标识
            label: '目标企业',
            x: 225,
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
            source: 'person_n', // String，必须，起始点 id
            target: 'person_m', // String，必须，目标点 id
            label: 'N与M为目标企业的\n一致行动人',
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
            source: 'person_m', // String，必须，起始点 id
            target: 'target', // String，必须，目标点 id
            label: '持股20%~50%并且与N投资比例相同',
            shape: 'line',
            labelCfg: {
              refY: -10,
              autoRotate: true
            }
          },
          {
            source: 'person_n', // String，必须，起始点 id
            target: 'target', // String，必须，目标点 id
            label: '持股20%~50%并且与M投资比例相同',
            shape: 'line',
            labelCfg: {
              refY: -10,
              autoRotate: true
            }
          }
        ]
      }
      const graph = new G6.Graph({
        container: 's-container-two', // String | HTMLElement，必须，在 Step 1 中创建的容器 id 或容器本身
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
