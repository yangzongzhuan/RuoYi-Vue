<template>
  <div id="app" style="margin: auto;padding-top: 10px;background-color: #F2F2F2;min-height: 900px">
    <div v-loading="baseInfoLoading" style="margin-left: 20px;background-color: white;padding-left: 10px;margin-right: 20px;padding-top: 3px;padding-bottom: 20px;padding-right: 15px">
      <div class="c-title-div">
        实际控制人相关信息
      </div>
      <table class="c-base-table" style="background-color: white;" width="100%">
        <tbody>
        <tr>
          <td class="c-td-title">目标企业名称：</td>
          <td class="c-td-text">{{ ctrlers.length>0?ctrlers[0].entname: '' }}</td>
          <td class="c-td-title">统一社会信用代码/工商注册号 ：</td>
          <td class="c-td-text">{{ ctrlers.length>0?ctrlers[0].uniscidOrRegno: '' }}</td>
        </tr>
        <tr>
          <td class="c-td-title">目标企业经营状态：</td>
          <td class="c-td-text">
            <span v-if="ctrlers.length>0" :style="( statusStyle(ctrlers[0].entstatus))">{{ ctrlers.length>0?ctrlers[0].entstatus_cn: '' }}</span>
          </td>
          <td class="c-td-title">控制目标企业类型：</td>
          <td class="c-td-text" style="width: 30%">{{ ctrlers.length>0?ctrlers[0].isunictrl_cn: '' }}</td>
        </tr>
        <tr>
          <td class="c-td-title">是否为上市公司 ：</td>
          <td class="c-td-text">
            {{ ctrlers.length>0?ctrlers[0].islist_cn: '' }}
            <span v-if="ctrlers.length>0" v-show="ctrlers[0].islist_cn === '是'">({{ ctrlers.length>0?ctrlers[0].skcode: '' }})</span>
          </td>
          <td class="c-td-title">上市公司自行披露实际控制人 ：</td>
          <td class="c-td-text">
            <span v-if="ctrlers.length>0">{{ ctrlers[0].publishCtrler === null? '':ctrlers[0].publishCtrler }}</span>
          </td>
        </tr>
        </tbody>
      </table>
      <div id="s-info-ctrlList" style="width: 100%;height: 50px;padding-top: 10px">
        <div v-for="item in ctrlers" :key="item.ctrlid" class="c-ctrler-info">
          <div style="float: left">
            <svg-icon icon-class="shijikongzhiren" style="font-size: 28px;color: #0071C2" />
          </div>
          <div style="float: left;margin-left: 5px;">
            <div style="font-size: 12px;font-weight: bolder;color: black">
              {{ item.ctrlname }}
              <span style="color: red;font-weight: bolder">({{ item.shratio+'%' }})</span>
            </div>
            <div style="font-size: 10px;font-weight: bolder;color: #808080;margin-top: 3px;">
              <span>类型:{{ item.ctrltype_cn }}</span>
              <span>控制方式:{{ item.isidrctrl_cn }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div style="margin-left: 20px;background-color: white;padding-left: 10px;margin-right: 20px;padding-top: 3px;margin-top: 15px;padding-right: 15px">
      <div class="c-title-div">
        控制路径图谱
      </div>
      <div style="height:calc(100vh - 160px);">
        <RelationGraph ref="graphRef" :options="graphOptions" :on-node-click="onNodeClick" :on-line-click="onLineClick" />
      </div>
    </div>
  </div>
</template>
<script>
import RelationGraph from 'relation-graph'

export default({
  name: "SupplyActualController",
  components: { RelationGraph },
  data() {
    return {
      baseInfoLoading: false,
      baseInfoHidden: false,
      graphOptions: {
        allowSwitchLineShape: true,
        allowSwitchJunctionPoint: true,
        nodeShape: 1,
        defaultJunctionPoint: 'border',
        allowShowMiniToolBar: true
        // 这里可以参考"Graph 图谱"中的参数进行设置 https://seeksdream.github.io/#/docs/graph
      },
      ctrlers: [
        {
          "regno":"",
          "esdate":"2022-03-29",
          "ctrlid":"inv34e8922207c3324469c4bdf7f16f6419",
          "ctrlname":"程瑞雪",
          "entid":"PVILPOGVKE2MBKPLLTX0EPLFSQOUKGJT7",
          "ctrlpath":[
            "程瑞雪-&gt;95.0000:阿锐塔（北京）数字科技有限公司"
          ],
          "isunictrl":"0",
          "shratio":95,
          "uniscid":"91110112MA7MLU6H3F",
          "entstatus_cn":"在营（开业）",
          "entname":"阿锐塔（北京）数字科技有限公司",
          "isidrctrl":"0",
          "entstatus":"1",
          "lerepname":"程瑞雪",
          "ctrltype":"1",
          "isidrctrl_cn":"直接控制",
          "isunictrl_cn":"单一控制",
          "ctrltype_cn":"自然人",
          "ctrlCount":0,
          "uniscidOrRegno":"91110112MA7MLU6H3F",
          "ctrlerCreditOrId":"inv34e8922207c3324469c4bdf7f16f6419",
          "publishCtrler":null,
          "skcode":null,
          "islist":"0",
          "islist_cn":"否"
        }
      ]
    }
  },
  mounted() {
    this.showGraph()
  },
  methods:{
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
    },
    querySearchAsync(queryString, cb) {
    },
    handleSelect(item) {
    },
    handleInput(value) {
    },
    handleFilter() {
    },
    viewDetail() {
      this.$router.push({ path: "/supplier_graph/actualControllerGraph" });
    },
    statusStyle(status) {
      if (status !== '1') {
        const style = {
          fontSize: '14px',
          fontFamily: 'Microsoft YaHei',
          fontWeight: '400',
          color: 'rgba(248,130,51,1)'
        }
        return style
      }
      const style = {
        fontSize: '14px',
        fontFamily: 'Microsoft YaHei',
        fontWeight: '400',
        color: '#19B94F'
      }
      return style
    },
  }
})
</script>
<style scoped>
.c-title-div{
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
.c-td-title{
  background:#f9f9f9;
  border:1px solid #D0CECE;
  padding-left: 10px;
  font-size:13px;
  font-family:Microsoft YaHei;
  font-weight:400;
  height: 40px;
  color:rgba(96,96,96,1);
}
.c-base-table,.c-base-table tr th, .c-base-table tr td {
  border-bottom:1px solid #D0CECE;
  border-top:1px solid #D0CECE;
}
.c-base-table { border-collapse: collapse;}
.c-td-text{
  padding-left: 10px;
  text-align: left;
  height: 30px;
  font-size:13px;
  line-height: 25px;
  font-family:Microsoft YaHei;
  font-weight:400;
  color:rgba(96,96,96,1);
  background:white;
  border:1px solid #D0CECE;
}
.c-ctrler-info{
  margin-right: 5px;
  float: left;
  padding-top: 7px;
  padding-left: 5px;
  padding-right: 5px;
  min-width: 180px;
  height: 53px;
  font-size: 20px;
  font-family: "Microsoft YaHei";
  border-radius: 5px;
  border: 1px solid #BFBFBF;
}
</style>
