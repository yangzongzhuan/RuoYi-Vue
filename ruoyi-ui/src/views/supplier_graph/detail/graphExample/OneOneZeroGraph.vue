<template>
  <div id="app">
    <div style="float: right;margin-top: -30px">
      <el-pagination
        background
        :page-size="1"
        layout="pager"
        :current-page="currentPage"
        :total="3"
        @current-change="handleCurrentChange"
      />
    </div>
    <div v-show="currentPage === 1" id="s-container-one" />
    <div v-show="currentPage === 2" id="s-container-two" />
    <div v-show="currentPage === 3" id="s-container-three" />
  </div>

</template>

<script>
import G6 from '@antv/g6'
export default {
  name: 'OneOneZeroGraph',
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
      console.log('------------')
      console.log(`当前页: ${val}`)
      this.currentPage = val
      if (val === 1) {
        this.createGraphOne()
      }
      if (val === 2) {
        this.createGraphTwo()
      }
      if (val === 3) {
        this.createGraphThree()
      }
    },
    createGraphThree() {
      const data = {
        // 点集
        nodes: [
          {
            id: 'person_n', // String，该节点存在则必须，节点的唯一标识,
            label: '自然人N',
            style: {
              fill: '#FFE6CC'
            },
            x: 180,
            y: 60
          },
          {
            id: 'person_m', // String，该节点存在则必须，节点的唯一标识,
            label: '自然人M',
            style: {
              fill: '#FFE6CC'
            },
            x: 70,
            y: 230
          },
          {
            id: 'ent_a', // String，该节点存在则必须，节点的唯一标识
            label: '企业A',
            style: {
              fill: '#CCFFCC'
            },
            x: 300,
            y: 230
          },
          {
            id: 'target', // String，该节点存在则必须，节点的唯一标识
            label: '目标企业',
            x: 180,
            y: 400,
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
            label: '亲属',
            labelCfg: {
              refY: 10
            }
          },
          {
            source: 'person_m', // String，必须，起始点 id
            target: 'person_n' // String，必须，目标点 id
          },
          {
            source: 'person_n', // String，必须，起始点 id
            target: 'ent_a', // String，必须，目标点 id
            label: '直接或间接控制\n(持股比例50%)',
            labelCfg: {
              refY: 15,
              autoRotate: true
            }
          },
          {
            source: 'person_m', // String，必须，起始点 id
            target: 'ent_a', // String，必须，目标点 id
            label: 'A与M为目标企业的\n一致行动人',
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
            label: '既持股又任职',
            shape: 'line'
          },
          {
            source: 'ent_a', // String，必须，起始点 id
            target: 'target', // String，必须，目标点 id
            label: '持股',
            shape: 'line'
          }
        ]
      }
      const graph = new G6.Graph({
        container: 's-container-three', // String | HTMLElement，必须，在 Step 1 中创建的容器 id 或容器本身
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
            id: 'person_m', // String，该节点存在则必须，节点的唯一标识,
            label: '自然人M',
            style: {
              fill: '#FFE6CC'
            },
            x: 70,
            y: 120
          },
          {
            id: 'ent_a', // String，该节点存在则必须，节点的唯一标识
            label: '企业A',
            style: {
              fill: '#CCFFCC'
            },
            x: 330,
            y: 120
          },
          {
            id: 'target', // String，该节点存在则必须，节点的唯一标识
            label: '目标企业',
            x: 200,
            y: 350,
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
            target: 'person_m', // String，必须，目标点 id
            shape: 'quadratic',
            label: 'A与M为目标企业的\n一致行动人',
            labelCfg: {
              refY: -15
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
            target: 'ent_a', // String，必须，目标点 id
            label: '直接或间接控制\n(持股比例50%以上)',
            shape: 'quadratic',
            labelCfg: {
              refY: 15
            }
          },
          {
            source: 'person_m', // String，必须，起始点 id
            target: 'target', // String，必须，目标点 id
            label: '既持股又任职',
            shape: 'line'
          },
          {
            source: 'ent_a', // String，必须，起始点 id
            target: 'target', // String，必须，目标点 id
            label: '持股',
            shape: 'line'
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
            x: 200,
            y: 80
          },
          {
            id: 'person_m', // String，该节点存在则必须，节点的唯一标识,
            label: '自然人M',
            style: {
              fill: '#FFE6CC'
            },
            x: 75,
            y: 230
          },
          {
            id: 'ent_a', // String，该节点存在则必须，节点的唯一标识
            label: '企业A',
            style: {
              fill: '#CCFFCC'
            },
            x: 325,
            y: 230
          },
          {
            id: 'target', // String，该节点存在则必须，节点的唯一标识
            label: '目标企业',
            x: 200,
            y: 380,
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
            label: '亲属'
          },
          {
            source: 'person_m', // String，必须，起始点 id
            target: 'person_n' // String，必须，目标点 id
          },
          {
            source: 'person_n', // String，必须，起始点 id
            target: 'ent_a', // String，必须，目标点 id
            label: '任职'
          },
          {
            source: 'person_m', // String，必须，起始点 id
            target: 'ent_a', // String，必须，目标点 id
            label: 'A与M为目标企业的\n一致行动人',
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
            label: '持股',
            shape: 'line'
          },
          {
            source: 'ent_a', // String，必须，起始点 id
            target: 'target', // String，必须，目标点 id
            label: '持股',
            shape: 'line'
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
    }
  }
}
</script>

<style scoped>

</style>
