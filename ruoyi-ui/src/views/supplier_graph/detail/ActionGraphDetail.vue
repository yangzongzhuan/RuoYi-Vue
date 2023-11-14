<template>
  <div id="app" style="margin: auto;padding-top: 15px;background-color: #F2F2F2;min-height: 1200px;padding-bottom: 15px">
    <div v-loading="baseInfoLoading" style="margin-left: 15px;background-color: white;padding-left: 10px;margin-right: 15px;padding-top: 3px;padding-right: 10px;padding-bottom: 10px">
      <div class="c-title-div">
        企业工商注册信息
      </div>
      <table v-show="baseInfoHidden" class="c-base-table" style="background-color: white;width:100%;">
        <tbody>
        <tr>
          <td class="c-td-title">企业名称：</td>
          <td class="c-td-text">{{ ent_baseinfo.entname }}</td>
          <td class="c-td-title">统一社会信用代码 ：</td>
          <td class="c-td-text">{{ act_detail_item.uniscid }}</td>
        </tr>
        <tr>
          <td class="c-td-title">经营状态：</td>
          <td class="c-td-text">
            <span :style="( statusStyle(act_detail_item.entstatus))">{{ act_detail_item.entstatus_cn }}</span>
          </td>
          <td class="c-td-title">注册资本：</td>
          <td class="c-td-text" style="width: 30%">{{ act_detail_item.regcap.toLocaleString()+act_detail_item.regcapcur_cn }}</td>
        </tr>
        <tr>
          <td class="c-td-title">注册地：</td>
          <td class="c-td-text">
            {{ act_detail_item.regorg_cn }}
          </td>
          <td class="c-td-title">股东总数：</td>
          <td class="c-td-text">
            {{ ent_baseinfo.invCount }}
          </td>
        </tr>
        <tr>
          <td class="c-td-title">⼀致⾏动⼈关系类型数量：</td>
          <td class="c-td-text">
            {{ ent_baseinfo.actrelCount }}
          </td>
          <td class="c-td-title">一致行动人组数量：</td>
          <td class="c-td-text">
            {{ ent_baseinfo.actgrpCount }}
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <div v-loading="detailInfoLoading" style="margin-left: 15px;background-color: white;padding-left: 10px;margin-right: 15px;padding-bottom: 20px;padding-top: 3px;margin-top: 15px">
      <div class="c-title-div">
        一致行动人组信息
      </div>
      <div>
        <el-tabs v-model="activeTabName">
          <el-tab-pane v-for="tabItem in tabsNames" :key="tabItem.text" :name="tabItem.text">
            <div slot="label" class="c-menu-nav" @click="onTabChange(tabItem)">
              <span :class="(activeTabName === tabItem.text ? 'c-menu-nav-active' : '')">{{ tabItem.text }}</span>
            </div>
            <div style="padding:15px 15px 15px 0px">
              <el-table
                border
                highlight-current-row
                :header-row-style="{height:'40px',fontSize: '13px'}"
                :header-cell-style="{padding:'0px'}"
                :row-style="{height:'40px',fontSize: '13px'}"
                :cell-style="{padding:'0px'}"
                style="width: 100%;background-color: white;"
                :data="tabItem.tableData"
              >
                <el-table-column
                  type="index"
                  label="序号"
                />
                <el-table-column
                  prop="partyid"
                  label="主体ID"
                />
                <el-table-column
                  prop="partyname"
                  label="一致行动人名称"
                />
                <el-table-column
                  prop="partytype"
                  label="一致行动人类型"
                >
                  <template slot-scope="scope">
                    {{ scope.row.partytype === 'C' ? '企业':'个人' }}
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-tab-pane>
        </el-tabs>
        <div class="c-graph-one" style="height: 420px;">
          <div style="height: 420px;width: 60%;border: 4px solid #F2F2F2;float: left">
          </div>
          <div style="height: 420px;width: 38%;border: 4px solid #F2F2F2;float: left;margin-left: 20px;line-height: 20px;padding-left: 5px;">
            说明：
            <p>1.一致行动人识别依据证监会颁布相关文件基于公开可信的数据设计的识别模型及算法。</p>
            <p>2.企业股东一致行动人设计两大类15种关系类型，例如：母子公司、联营公司、持股、密切家族成员等关系类型。</p>
            <p>3.您可以点击部分关系连接线了解具体关系构成。</p>
          </div>
        </div>
      </div>
    </div>
    <div style="margin-left: 15px;background-color: white;padding-left: 10px;margin-right: 15px;padding-top: 3px;margin-top: 15px;min-height: 580px">
      <div class="c-title-div">
        关系详情信息
      </div>
      <div class="c-my-graph1" style="height: 510px;border: 4px solid #F2F2F2;float: left;width: 60%;">
        <div style="height: 30px;width: 100%;line-height: 30px;padding-left: 5px;">
          <span>{{ detailTitle }}</span>
        </div>
        <div style="height: 50px;width: 100%;line-height: 25px;color: #7F7F7F;padding-left: 5px;padding-right: 5px">
          <span>{{ relaInfoText }}</span>
        </div>
      </div>
      <div style="height: 510px;width: 38%;float: left;margin-left: 15px;border: 4px solid #F2F2F2;">
        <el-select v-model="exampleGraphValue" placeholder="请选择" style="margin-top: 5px;margin-left: 5px" @change="onSelectChange">
          <el-option
            v-for="item in exampleOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <div style="height: 360px;width: 100%">
          <transition name="component-fade" mode="out-in">
            <component :is="view" />
          </transition>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import RelationGraph from "relation-graph";
import OneZeroOneGraph from './graphExample/OneZeroOneGraph'
import OneZeroTwoGraph from './graphExample/OneZeroTwoGraph'
import OneZeroThreeGraph from './graphExample/OneZeroThreeGraph'
import OneZeroFourGraph from './graphExample/OneZeroFourGraph'
import OneZeroFiveGraph from './graphExample/OneZeroFiveGraph'
import OneZeroSevenGraph from './graphExample/OneZeroSevenGraph'
import OneZeroEightGraph from './graphExample/OneZeroEightGraph'
import OneOneZeroGraph from './graphExample/OneOneZeroGraph'
import OneZeroNineGraph from './graphExample/OneZeroNineGraph'
import TwoZeroOneGraph from './graphExample/TwoZeroOneGraph'
import TwoZeroTwoGraph from './graphExample/TwoZeroTwoGraph'

export default {
  name: 'ActPersonDetail',
  components: {
    RelationGraph
  },
  data() {
    return {
      baseInfoLoading: false,
      baseInfoHidden: true,
      detailInfoLoading: false,
      detailHidden: true,
      activeTabName: null,
      currentId: '1',
      g_loading: false,
      exampleGraphId: '101',
      view: OneZeroOneGraph,
      relaInfoText: '',
      exampleOptions: [
        { value: '101', label: '101母子公司关系' },
        { value: '102', label: '102兄弟公司关系' },
        { value: '103', label: '103合营公司关系' },
        { value: '104', label: '104同一高管关系' },
        { value: '105', label: '105联营公司关系' },
        { value: '107', label: '107持股关系' },
        { value: '108', label: '108任职关系' },
        { value: '109', label: '109关系密切家族成员持股关系' },
        { value: '110', label: '110关系密切家族成员任职关系' },
        { value: '201', label: '201关系密切家族成员关系' },
        { value: '202', label: '202关系密切家族成员控制企业关系' }
      ],
      exampleGraphValue: '101',
      orgTypeColorMap: {
        'n-green': { color: '#67C23A', text: '被查询企业', checked: true, isNode: true },
        'n-blue': { color: '#409EFF', text: '关联方', checked: true, isNode: true },
        'n-yellow': { color: '#E6A23C', text: '疑似关联方', checked: true, isNode: true },
        '101': { color: '#EC6941', text: '母公司', checked: true, isNode: false },
        '102': { color: '#FF9E00', text: '兄弟公司', checked: true, isNode: false },
        '103': { color: '#4EA2F0', text: '合营公司', checked: true, isNode: false },
        '104': { color: '#D1C0A5', text: '联营公司', checked: true, isNode: false },
        '105': { color: 'red', text: '主要投资者个人', checked: true, isNode: false },
        '06': { color: '#3D6EBF', text: '关键管理人员', checked: true, isNode: false },
        '107': { color: '#5FB878', text: '关系密切家族成员', checked: true, isNode: false },
        '108': { color: '#efefef', color11: '#ffeecf', fontColor: '#D1C0A5', text: '重要自然人关联企业', prevDesc: '', suffixDesc: '', checked: true, isNode: false },
        '109': { color: '#efefef', color11: '#ffeecf', fontColor: '#D1C0A5', text: '其他', prevDesc: '', suffixDesc: '', checked: true, isNode: false }
      },
      relaInfoTextMap: {
        '100': '股东间关系',
        '101': '母子关系（投资者之间股权控制关系）',
        '102': '兄弟公司关系（投资者的母公司除投资者以外直接对外投资占比达到50%以上的其他投资者）',
        '103': '合营关系（投资者与合营企业作为共同控制投资方投资目标企业，即投资者直接持有目标企业股权比例20%~50%之间，且存在多个投资者投资比例相等，则多个投资者为对目标企业共同控制的投资方）',
        '104': '同一高管关系（投资者的高级管理人员中的主要成员，同时在另一个投资者担任高级管理人员）',
        '105': '联营关系（投资方对其具有重大影响，但不是投资者的子公司或合营企业的企业，即投资方直接持有目标企业股权比例为5%~20%以内，且不为最大股东或共同控制方的其他投资方企业）',
        '106': '股权融资关系（暂不处理）',
        '107': '持股关系（持有投资者30%以上股份的自然人，与投资者持有目标企业股份）',
        '108': '任职关系（在投资者任职的董事、监事及高级管理人员，与投资者持有目标企业股份）',
        '109': '关系密切家族成员持股关系（持有投资者30%以上股份的自然人和在投资者任职的董事、监事及高级管理人员，其父母、配偶、子女及其配偶、配偶的父母、兄弟姐妹及其配偶、配偶的兄弟姐妹及其配偶等亲属，与投资者持有目标企业股份）',
        '110': '关系密切家族成员任职关系（在投资者任职的董事、监事及高级管理人员，其父母、配偶、子女及其配偶、配偶的父母、兄弟姐妹及其配偶、配偶的兄弟姐妹及其配偶等亲属，与投资者持有目标企业股份；在上市公司任职的董事、监事、高级管理人员及其前项所述亲属同时持有目标企业股份的，或者与其自己或者其前项所述亲属直接或者间接控制的企业同时持有目标企业股份；）',
        '120': '其他关联方关系',
        '200': '高管股东关系',
        '201': '关系密切家族成员关系（目标企业主要投资者个人、关键管理人员及亲属之间的关系）',
        '202': '关系密切家族成员控制企业关系（目标企业主要投资者个人、关键管理人员及亲属之间对目标企业共同控制或施加重大影响）',
        '203': '其他关联方关系（投资者之间具有其他关联关系）'
      },
      userGraphSetting1: {
        heightByContent: false,
        fullTools: false,
        isShowMiniView: false,
        isAutoFixedTools: false,
        isMiniNameFilter: true,
        isMiniToolBar: true,
        expandPosition: 'bottom',
        hideTextByZoom: false,
        lineColor_default: '#dddddd',
        nodeColor_default: '',
        layoutName: 'tree-plus-b',
        layoutDirection: 'v',
        nodeShape: 0,
        lineShape: 1,
        isEndArrow: false,
        lineMarkerConfig: { // 另一种箭头样式
          markerWidth: 0,
          markerHeight: 0,
          refX: 0,
          refY: 0,
          color: '#128bed',
          data: '00000'
        }
      },
      userGraphSetting_two: {
        heightByContent: false,
        isShowMiniView: false,
        isAutoFixedTools: false,
        isMiniNameFilter: true,
        isMiniToolBar: true,
        fullTools: false,
        expandPosition: 'bottom',
        prettyLevelPosition: false,
        canvasZoom: 75,
        hideTextByZoom: false,
        lineColor_default: '#dddddd',
        nodeColor_default: '',
        layoutName: 'SeeksBidirectionalTreeLayouter',
        layoutDirection: 'v',
        nodeShape: 0,
        lineShape: 1
      },
      tabsNames: [],
      ent_baseinfo: {},
      act_detail_item: {
        regcap: 0
      },
      entid: '',
      detailTitle: ''
    }
  },
  mounted() {
    this.entid = this.$route.query.entid
    var ctrlItem = JSON.parse(sessionStorage.getItem('act_detail_item'))
    this.act_detail_item = ctrlItem || {}
    this.queryBasicDetail()
    this.queryActGraph()
  },
  methods: {
    queryBasicDetail() {
      this.baseInfoLoading = true
      const url = '/smart-api/aff-enactinf-ws/enactinf/getEntActBaseInfo?entid=' + this.entid
      this.SeeksHttp.get(url).then(response => {
        if (response.data.item !== null && response.data.item !== undefined) {
          this.ent_baseinfo = response.data.item
        }
        this.baseInfoLoading = false
      })
    },
    queryActGraph() {
      this.detailInfoLoading = true
      const url = '/smart-api/aff-enactinf-ws/enactinf/getEntActGraph?entid=' + this.entid
      const _this = this
      this.SeeksHttp.get(url).then(response => {
        if (response.data.item !== null && response.data.item !== undefined) {
          response.data.item.forEach(function(thisItem, index) {
            _this.tabsNames.push(
              {
                text: '一致行动人组' + (index + 1),
                key: index,
                tableData: thisItem.actlist,
                graphData: thisItem
              }
            )
          })
          _this.activeTabName = _this.tabsNames[0].text
          _this.loadGraphData(_this.convertGraphData(_this.tabsNames[0].graphData))
        }
        _this.detailInfoLoading = false
      })
    },
    convertGraphData(item) {
      const nodes = []
      item.nodes.forEach(function(thisItem, index) {
        if (index === 0) {
          nodes.push(
            {
              color: '#0070C4',
              fontColor: '#FFFFFF',
              nodeShape: 0,
              border: 0,
              text: thisItem.nodename,
              allowClick: false,
              id: thisItem.nodeid,
              x: 350,
              y: -80,
              fixed: true
            }
          )
        } else {
          nodes.push(
            {
              color: '#0070C4',
              fontColor: '#FFFFFF',
              nodeShape: 0,
              border: 0,
              text: thisItem.nodename,
              allowClick: false,
              id: thisItem.nodeid
            }
          )
        }
      })
      const links = []
      item.links.forEach(thisItem => {
        var __color
        var _fontColor
        if (thisItem.reltype_cn) {
          var colorConfig = this.orgTypeColorMap[thisItem.reltype]
          if (colorConfig) {
            __color = colorConfig.color
            _fontColor = colorConfig.fontColor
          }
        }
        links.push(
          {
            from: thisItem.fromid,
            to: thisItem.toid,
            color: __color,
            fontColor: _fontColor,
            text: thisItem.reltype_cn,
            reltype: thisItem.reltype,
            actrelid: thisItem.actrelid,
            isRerverse: true
          }
        )
      })
      return {
        nodes: nodes,
        links: links,
        rootId: ''
      }
    },
    onSelectChange(event) {
      console.log(event)
      this.exampleGraphId = event
      if (event === '101') {
        this.view = OneZeroOneGraph
      }
      if (event === '102') {
        this.view = OneZeroTwoGraph
      }
      if (event === '103') {
        this.view = OneZeroThreeGraph
      }
      if (event === '104') {
        this.view = OneZeroFourGraph
      }
      if (event === '105') {
        this.view = OneZeroFiveGraph
      }
      if (event === '107') {
        this.view = OneZeroSevenGraph
      }
      if (event === '108') {
        this.view = OneZeroEightGraph
      }
      if (event === '109') {
        this.view = OneZeroNineGraph
      }
      if (event === '110') {
        this.view = OneOneZeroGraph
      }
      if (event === '201') {
        this.view = TwoZeroOneGraph
      }
      if (event === '202') {
        this.view = TwoZeroTwoGraph
      }
    },
    onTabChange(tabItem) {
      console.log('switch to:', tabItem)
      this.loadGraphData(this.convertGraphData(tabItem.graphData))
    },
    loadGraphData(graphData) {
      this.$nextTick(() => {
        this.$refs.seeksRelationGraph.setJsonData(graphData, function(seeksRGGraph) {
        })
      })
    },
    onNodeClick(data) {
      console.log(data)
      if (data.spcType === 'source') {
        const text = data.text
        if (this.detailTitle !== '' && this.detailTitle.indexOf('<-') > 0) {
          this.detailTitle = text + this.detailTitle.substr(this.detailTitle.indexOf('<-'))
        }
      }
    },
    onLineClick(data) {
      const actrelid = data.actrelid
      const url = '/smart-api/aff-enactinf-ws/enactinf/getRelDetailsGraph?entid=' + this.entid + '&actrelid=' + actrelid
      const _this = this
      let actName = ''
      let targetName = ''
      this.SeeksHttp.get(url).then(response => {
        if (response.data.item !== null && response.data.item !== undefined) {
          const nodes = []
          response.data.item.nodes.forEach(function(thisItem, index) {
            if (thisItem.nodeid === _this.entid) {
              targetName = thisItem.nodename
              nodes.push(
                {
                  spcType: 'target',
                  styleClass: 'c-graph-node-person',
                  color: '#FFFFFF',
                  fontColor: '#0070C4',
                  nodeShape: 0,
                  border: 0,
                  text: thisItem.nodename,
                  allowClick: false,
                  x: 0,
                  y: 180,
                  fixed: true,
                  id: thisItem.nodeid
                }
              )
            } else {
              actName = thisItem.nodename
              if (index === 0) {
                nodes.push(
                  {
                    spcType: 'source',
                    styleClass: 'c-graph-node-person',
                    color: '#FFFFFF',
                    fontColor: '#0070C4',
                    nodeShape: 0,
                    border: 0,
                    text: thisItem.nodename,
                    allowClick: false,
                    id: thisItem.nodeid,
                    x: -150,
                    y: -50,
                    fixed: true
                  }
                )
              } else {
                nodes.push(
                  {
                    spcType: 'source',
                    styleClass: 'c-graph-node-person',
                    color: '#FFFFFF',
                    fontColor: '#0070C4',
                    nodeShape: 0,
                    border: 0,
                    text: thisItem.nodename,
                    allowClick: false,
                    id: thisItem.nodeid,
                    x: index * 80,
                    y: -50,
                    fixed: true
                  }
                )
              }
            }
          })
          const links = []

          response.data.item.links.forEach(thisItem => {
            var __color
            var _fontColor
            if (thisItem.reltype_cn) {
              var colorConfig = this.orgTypeColorMap[thisItem.reltype]
              if (colorConfig) {
                __color = colorConfig.color
                _fontColor = colorConfig.fontColor
              }
            }
            if (thisItem.reltype === '1') {
              links.push(
                {
                  from: thisItem.fromid,
                  to: thisItem.toid,
                  color: __color,
                  fontColor: _fontColor,
                  text: thisItem.reltype_cn + '：' + thisItem.property + '%'
                }
              )
            } else if (thisItem.reltype === '2') {
              links.push(
                {
                  from: thisItem.fromid,
                  to: thisItem.toid,
                  color: __color,
                  fontColor: _fontColor,
                  text: thisItem.reltype_cn + '：' + thisItem.property_cn
                }
              )
            } else {
              links.push(
                {
                  from: thisItem.fromid,
                  to: thisItem.toid,
                  color: __color,
                  fontColor: _fontColor,
                  text: thisItem.reltype_cn + '：' + thisItem.property_cn
                }
              )
            }
          })
          console.log('two graph links: ', links)
          const graphData = {
            rootId: this.currentEntId,
            nodes: nodes,
            links: links
          }
          _this.detailTitle = actName + '<-' + data.reltype + '-' + data.text + '->' + targetName
          _this.exampleGraphValue = data.reltype
          _this.relaInfoText = '说明：' + _this.relaInfoTextMap[data.reltype]
          _this.loadGraphTwoData(graphData)
          _this.onSelectChange(data.reltype)
        }
      })
    },
    loadGraphTwoData(graphData) {
      this.$nextTick(() => {
        this.$refs.seeksRelationGraph_two.setJsonData(graphData, function(seeksRGGraph) {
        })
      })
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
    }
  }
}
</script>
<style scoped>
/deep/ .c-mini-toolbar{
  margin-top: 100px;
}
/deep/ .el-tabs__nav-wrap{
  padding-left: 20px;
}
.c-graph-one /deep/ .rel-node-shape-0{
  border-radius: 3px;
  padding-left: 5px;
  padding-right: 5px;
  height: 52px;
  width: 120px;
  /*display: flex;*/
  /*align-items: center;*/
  /*justify-content: center;*/
}
.c-my-graph1 /deep/ .rel-node-shape-0{
  padding: 0px 0px 0px 0px;
  height:75px;
  width:200px;
  text-align: center;
  border-radius: 3px;
  border:#cccccc solid 1px !important;
  /*display: flex;*/
  /*align-items: center;*/
  /*justify-content: center;*/
}
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
.c-th-header{
  text-align: left;
  font-size:14px;
  height: 50px;
  padding-left: 10px;
  font-family:Microsoft YaHei;
  font-weight:bolder;
  color:rgba(96,96,96,1);
  border:1px solid rgba(232, 241, 245, 1);
}
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
.c-menu-nav{
  color: black;
  font-size: 14px;
  width: 100px;
  text-align: center;
}
.c-menu-nav:hover{
  color: #409EFF;
}
.c-menu-nav-active:after{
  content: '';
  left: 50%;
  margin-left: -6px;
  bottom: -6px;
  position: absolute;
  display: inline-block;
  width: 12px;
  height: 12px;
  background-color: #fff;
}
.component-fade-enter-active, .component-fade-leave-active {
  transition: opacity .3s ease;
}
.component-fade-enter, .component-fade-leave-to
  /* .component-fade-leave-active for below version 2.1.8 */ {
  opacity: 0;
}
/deep/ .rel-node-checked{
  box-shadow: none;
  border:#2E4E8F solid 1px;
  /*border-color: #BA7909;*/
  /*background-color: #FD8B37;*/
  /*color: #ffffff;*/
  /* firefox bug fix - won't rotate at 90deg angles */
  /*-moz-transform: rotate(-89deg) translateX(-190px);*/
  /*animation-timing-function:linear;*/
  animation: none;
}
</style>
