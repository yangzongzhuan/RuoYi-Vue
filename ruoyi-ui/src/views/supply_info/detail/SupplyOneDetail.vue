<template>
  <section>
    <div style="margin: auto;padding-left: 15px;padding-top: 15px;min-height: 780px;background-color: #F2F2F2;padding-bottom: 20px">
      <div id="s-ent-header" style="background-color: white;margin-right: 15px">
        <!--<img :src="background_image" style="width: 100%;height: 380px;">-->
        <div style="color: black;width: 100%;">
          <table style="width: 100%;font-family: 'Microsoft YaHei'">
            <tbody>
            <tr>
              <td style="width: 76%;">
                <div style="padding-top: 10px">
                    <span class="c-ent-title">
                      {{ entinfo.entname }}<span v-show="entinfo.uniscid !== undefined && entinfo.uniscid !== '' && entinfo.uniscid !== null">{{ '（'+ entinfo.uniscid +'）' }}</span>
                    </span>
                </div>
                <div style="margin-top: 5px;margin-left: 2px">
                  <div
                    class="c-ent-label"
                    style="border: 1px solid #8AD8A9;background-color: white;"
                  >
                    <span style="color: #8AD8A9;font-weight: bolder">{{ entinfo.entstatus_cn }}</span>
                  </div>
                  <div
                    class="c-ent-label"
                    style="border: 1px solid #FFC005;background-color: white;"
                  >
                    <el-popover
                      placement="bottom"
                      width="200"
                      trigger="hover"
                      :disabled="tels.length<=0"
                    >
                      <div>
                        <div v-for="item in tels" :key="item">{{ item }}</div>
                      </div>
                      <span slot="reference" class="c-title-text" style="color: #FFC005;font-weight: bolder">
                          联系方式<i class="el-icon-arrow-down el-icon--right" />
                        </span>
                    </el-popover>
                  </div>
                  <div
                    class="c-ent-label"
                    style="border: 1px solid #FFC005;background-color: white;"
                  >
                    <el-popover
                      placement="bottom"
                      width="200"
                      trigger="hover"
                      :disabled="websites.length<=0"
                    >
                      <div>
                        <div v-for="item in websites" :key="item">{{ item }}</div>
                      </div>
                      <span slot="reference" class="c-title-text" style="color: #FFC005;font-weight: bolder">
                          官方网址<i class="el-icon-arrow-down el-icon--right" />
                        </span>
                    </el-popover>
                  </div>
                  <div
                    class="c-ent-label"
                    style="border: 1px solid #3663CB;background-color: white;"
                  >
                    <el-popover
                      placement="bottom"
                      trigger="hover"
                      :disabled="used_name.length<=0"
                    >
                      <div>
                        <div v-for="item in used_name" :key="item">{{ item }}</div>
                      </div>
                      <span slot="reference" class="c-title-text" style="color: #3663CB;font-weight: bolder">
                          曾用名<i class="el-icon-arrow-down el-icon--right" />
                        </span>
                    </el-popover>
                  </div>
                  <div
                    class="c-ent-label"
                    style="border: 1px solid #3663CB;background-color: white;"
                  >
                    <el-popover
                      placement="bottom"
                      width="200"
                      trigger="hover"
                      :disabled="syncWords.length<=0"
                    >
                      <div>
                        <div v-for="item in syncWords" :key="item">{{ item }}</div>
                      </div>
                      <span slot="reference" class="c-title-text" style="color: #3663CB;font-weight: bolder">
                          同义词<i class="el-icon-arrow-down el-icon--right" />
                        </span>
                    </el-popover>
                  </div>
                </div>
                <div class="c-ent-basic" style="width: 100%;padding-left: 2px;padding-top: 15px;">
                  <div style="min-height: 119px;margin-bottom: 5px;background-color: #f7f7f7;margin-top: 10px;padding-top: 5px;padding-left: 10px">
                    <table style="width: 100%;padding-left: 5px;">
                      <tbody>
                      <tr>
                        <td class="c-table-basic-title">企业法人</td>
                        <td class="c-table-basic-text">{{ entinfo.legal_name }}</td>
                        <td class="c-table-basic-title">注册资金</td>
                        <td class="c-table-basic-text">
                          <span v-if="entinfo.regcap !== null && entinfo.regcap !== undefined">{{ entinfo.regcap.toLocaleString() }}&nbsp;万{{ entinfo.regcapcur_cn }}</span>
                          <span v-if="entinfo.regcap === null || entinfo.regcap === undefined">-</span>
                        </td>
                        <td class="c-table-basic-title">所属地区</td>
                        <td class="c-table-basic-text">{{ entinfo.province }}-{{ entinfo.city }}-{{ entinfo.district }}</td>
                      </tr>
                      <tr>
                        <td class="c-table-basic-title">经营年限</td>
                        <td class="c-table-basic-text">{{ tagInfo.enages }}&nbsp;年</td>
                        <td class="c-table-basic-title">是否上市</td>
                        <td class="c-table-basic-text">{{ tagInfo.listboard_cn }}</td>
                        <td class="c-table-basic-title">是否高新</td>
                        <td class="c-table-basic-text">{{ tagInfo.isinnocomp === '0'?'否':'是' }}</td>
                      </tr>
                      <tr>
                        <td class="c-table-basic-title">主营行业</td>
                        <td class="c-table-basic-text">{{ tagInfo.industry_cn }}</td>
                        <td class="c-table-basic-title">组织形式</td>
                        <td class="c-table-basic-text">{{ tagInfo.orgtype_cn }}</td>
                        <td class="c-table-basic-title">企业规模</td>
                        <td class="c-table-basic-text">{{ tagInfo.enscale_cn }}</td>
                      </tr>
                      <tr>
                        <td class="c-table-basic-title">企业简介</td>
                        <td colspan="5" class="c-table-basic-text">
                          <div style="line-height: 20px">
                            {{ entinfo.opscope.length>47?entinfo.opscope.substr(0,47)+'...':entinfo.opscope }}
                            <el-button v-show="entinfo.opscope.length>47" type="text" style="color: #0070C5" @click="viewIntro(entinfo.opscope)">更多</el-button>
                          </div>
                        </td>
                      </tr>
                      </tbody>
                    </table>
                  </div>

                </div>
              </td>
              <td></td>
            </tr>
            <tr>
              <td id="s-footer-td" colspan="3">
                <div v-if="ctrlInfo.ctrlname !== undefined && this.ctrlInfo.ctrlname !== null && ctrlInfo.ctrlname !== ''" class="c-other-info" style="margin-left: 11px;width: 170px;padding-right: 4px">
                  <div style="float: left;width: 30%;text-align: center;padding-top: 5px">
                    <svg-icon icon-class="shijikongzhiren" class="c-other-info-icon" /><br>
                    <div style="font-size: 7px;color: #0070C4;font-weight: bolder;margin-top: 1px">实控人</div>
                  </div>
                  <div style="float: left;width: 70%;">
                    <div style="height: 31px;">
                      <div
                        v-if="ctrlInfo.ctrlname !== undefined && this.ctrlInfo.ctrlname !== null && ctrlInfo.ctrlname !== ''"
                        class="c-ent-taginfo"
                        style="font-size: 12px;line-height: 15px;padding-top: 4px"
                        @click="mainBodyIsShow=false;graphView4Control=true;graphView4Control_is_show=true"
                      >
                        {{ ctrlInfo.ctrlname.length>17?ctrlInfo.ctrlname.substr(0,17)+'...':ctrlInfo.ctrlname }}
                      </div>
                    </div>
                    <div style="height: 30px;">
                      <span style="font-size: 7px;">类型：{{ ctrlInfo.isunictrl_cn }}</span>
                    </div>
                  </div>
                </div>
                <div v-if="ctrlInfo.ctrlname === undefined || this.ctrlInfo.ctrlname === null || ctrlInfo.ctrlname === '' " class="c-other-info-gray" style="width: 85px;margin-left: 11px;">
                  <div style="width: 100%;text-align: center;padding-top: 3px;color: #BFBFBF;">
                    <div style="height: 32px;">
                      <svg-icon icon-class="shijikongzhiren" class="c-other-info-icon" />
                    </div>
                    <div style="height: 30px;">
                      <div style="font-size: 7px;color: #BFBFBF;font-weight: bolder">实控人</div>
                    </div>
                  </div>
                </div>
                <div v-if="tagInfo.grpname !== undefined && tagInfo.grpname !== null && tagInfo.grpname !== ''" class="c-other-info" style="margin-left: 15px;padding-right: 4px">
                  <div style="float: left;width: 30%;text-align: center;padding-top: 7px">
                    <svg-icon icon-class="jituankehu_one" class="c-other-info-icon" style="font-size: 27px" /><br>
                    <div style="font-size: 7px;color: #0070C4;font-weight: bolder;margin-top: 1px">集团</div>
                  </div>

                  <div style="float: left;width: 70%;">
                    <div style="height: 31px;">
                      <div
                        v-if="tagInfo.grpname !== undefined && tagInfo.grpname !== null && tagInfo.grpname !== ''"
                        class="c-ent-taginfo"
                        style="font-size: 12px;line-height: 15px;padding-top: 4px"
                        @click="mainBodyIsShow=false;graphView4Group=true;graphView4Group_is_show=true"
                      >
                        {{ tagInfo.grpname.length>17?tagInfo.grpname.substr(0,17)+'...':tagInfo.grpname }}
                      </div>
                    </div>
                    <div style="height: 30px;">
                        <span
                          style="font-size: 7px;"
                        >成员：{{ mbrCount }}</span>
                    </div>
                  </div>
                </div>
                <div v-if="tagInfo.grpname === undefined || tagInfo.grpname === null" class="c-other-info-gray" style="width: 85px;margin-left: 15px;">
                  <div style="width: 100%;text-align: center;padding-top: 5px;color: #BFBFBF;">
                    <div style="height: 32px;">
                      <svg-icon icon-class="jituankehu_one" class="c-other-info-icon" style="font-size: 27px" />
                      <div style="font-size: 7px;font-weight: bolder;margin-top: 1px">集团</div>
                    </div>
                  </div>
                </div>
                <div class="c-other-info" style="width:85px;margin-left: 15px">
                  <div style="width: 100%;text-align: center;padding-top: 7px">
                    <div style="height: 32px;">
                      <svg-icon icon-class="guquanchuantou_one" class="c-other-info-icon" style="font-size: 25px" />
                      <div style="font-size: 7px;font-weight: bolder;margin-top: 1px" class="c-ent-taginfo" @click="mainBodyIsShow=false;graphView4Shareholding=true;graphView4Shareholding_is_show=true">股权穿透</div>
                    </div>
                  </div>
                </div>
                <div class="c-other-info" style="width:85px;margin-left: 15px;">
                  <div style="width: 100%;text-align: center;padding-top: 5px">
                    <div style="height: 32px;">
                      <svg-icon icon-class="guanlianfang_one" class="c-other-info-icon" />
                      <div style="font-size: 7px;color: #0070C4;font-weight: bolder;" class="c-ent-taginfo" @click="mainBodyIsShow=false;graphView4RelatedParty=true;graphView4RelatedParty_is_show=true">关联方</div>
                    </div>
                  </div>
                </div>
                <div class="c-other-info-gray" style="width: 85px;margin-left: 15px">
                  <div style="width: 100%;text-align: center;padding-top: 7px;color: #BFBFBF">
                    <div style="height: 32px;">
                      <svg-icon icon-class="yizhixingdongren" class="c-other-info-icon" style="font-size: 27px" />
                      <div style="font-size: 7px;font-weight: bolder">一致行动人</div>
                    </div>
                  </div>
                </div>
                <div class="c-other-info-gray" style="width: 85px;margin-left: 15px">
                  <div style="width: 100%;text-align: center;padding-top: 7px;color: #BFBFBF">
                    <div style="height: 32px;">
                      <svg-icon icon-class="gongyinglian" class="c-other-info-icon" style="font-size: 27px" />
                      <div style="font-size: 7px;font-weight: bolder">供应链</div>
                    </div>
                  </div>
                </div>
                <div class="c-other-info-gray" style="width: 85px;margin-left: 15px">
                  <div style="width: 100%;text-align: center;padding-top: 7px;color: #BFBFBF">
                    <div style="height: 32px;">
                      <svg-icon icon-class="chanyelian" class="c-other-info-icon" style="font-size: 27px" />
                      <div style="font-size: 7px;font-weight: bolder">产业链</div>
                    </div>
                  </div>
                </div>
                <div class="c-other-info-gray" style="width: 85px;margin-left: 15px">
                  <div style="width: 100%;text-align: center;padding-top: 7px;color: #BFBFBF">
                    <div style="height: 32px;">
                      <svg-icon icon-class="yichangdanbao" class="c-other-info-icon" style="font-size: 27px" />
                      <div style="font-size: 7px;font-weight: bolder">异常担保</div>
                    </div>
                  </div>
                </div>
                <div v-if="newsShow" style="height: 60px;float: left;margin-left: 15px;font-size: 12px" :style="newsWidth">
                  <div class="c-news-info" style="width: 100%;height: 25px;line-height: 27px;">
                    <div style="color: #0070C4 ">
                      <span style="font-weight: bolder">企业快讯</span> ：<span style="color: black">暂无</span>
                    </div>
                  </div>
                  <div class="c-news-info" style="width: 100%;height: 25px;margin-top: 3px;line-height: 27px;padding-bottom: 1px">
                    <div style="color: #0070C4 ">
                      <span style="font-weight: bolder">风险预警</span> ：<span style="color: black">暂无</span>
                    </div>
                  </div>
                </div>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  name: 'EntDetail',
  data() {
    return {
      entid: '',
      divHeight: '',
      tempRate: 3.6,
      entInfoTabId: 'jiben',
      evamkt: 0,
      evaviality: 0,
      activeName: 'jiben',
      newsShow: false,
      groupInfo: {},
      is_monitor: false,
      monitor_tab_names: [],
      tagInfo: {
        grpname: '',
        comevascore: ''
      },
      newsWidth: {
        width: '0px'
      },
      entinfo: {
        inv: [],
        per: [],
        regcap: 0,
        opscope: '',
        data_time: ''
      },
      entIntroInfo: '技术开发；货物进出口、技术进出口、代理进出口；销售通讯设备、厨房用品、卫生用品（含个人护理用品）、日用杂货、化妆品、医疗器械Ⅰ类、Ⅱ类、避孕器具、玩具、体育用品、文化用品、服装鞋帽、钟表眼镜、针纺织品、家用电器、家具（不从事实体店铺经营）、花、草及观赏植物、不再分装的包装种子、照相器材、工艺品、礼品、计算机、软件及辅助设备、珠宝首饰、食用农产品、宠物食品、电子产品、摩托车、电动车、自行车及零部件、智能卡、五金交电（不从事实体店铺经营）、建筑材料（不从事实体店铺经营）；维修仪器仪表；维修办公设备；承办展览展示活动；会议服务；筹备、策划、组织大型庆典；设计、制作、代理、发布广告；摄影扩印服务；文艺演出票务代理、体育赛事票务代理、展览会票务代理、博览会票务代理；手机技术开发；手机生产、手机服务（限海淀区永捷北路2号二层经营）；从事互联网文化活动；出版物零售；出版物批发；销售第三类医疗器械；销售食品；零售药品；广播电视节目制作；经营电信业务。（企业依法自主选择经营项目，开展经营活动；从事互联网文化活动、出版物批发、出版物零售、销售食品、经营电信业务、广播电视节目制作、零售药品、销售第三类医疗器械以及依法须经批准的项目，经相关部门批准后依批准的内容开展经营活动；不得从事本市产业政策禁止和限制类项目的经营活动。）',
      used_name: [],
      tels: [],
      websites: [],
      syncWords: [],
      entname: '',
      location: '',
      ctrlInfo: {
        ctrlname: ''
      },
      entIntro: '',
      dialogVisible: false,
      mbrCount: 0,
      groupStats: {},
      radar_options: {
        title: {
          text: ''
        },
        tooltip: {},
        radar: {
          radius: '50%',
          // shape: 'circle',
          center: ['50%', '50%'],
          name: {
            textStyle: {
              color: '#fff',
              backgroundColor: '#999',
              padding: [1, 2]
            }
          },
          indicator: [
            { name: '财务', max: 6500 },
            { name: '行业', max: 16000 },
            { name: '融资', max: 30000 },
            { name: '创新', max: 38000 }
          ]
        },
        series: [{
          name: '企业评分',
          type: 'radar',
          label: {
            show: false
          },
          // areaStyle: {normal: {}},
          data: [
            {
              value: [4300, 10000, 28000, 35000],
              name: '企业评分',
              label: {
                show: false
              }
            }
          ]
        }]
      },
      monitorDialogVisible: false,
      monitor_id_checked: [],
      activeNameMonitor: 'mfg_entbase',
      defaultProps: {
        children: 'children',
        label: 'label'
      }
    };
  },
  mounted() {
  },
  methods: {
  }
};
</script>
<style scoped>

</style>
