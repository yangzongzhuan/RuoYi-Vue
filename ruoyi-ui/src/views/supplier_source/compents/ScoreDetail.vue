<template>
  <div id="app">
    <div style="margin-top: 10px;margin-left: 20px;margin-right: 20px">
      <div class="c-data-h1">企业综合评分</div>
    </div>
    <div style="margin-top: 10px;margin-left: 20px;margin-right: 20px">
      <div class="c-data-h1">企业分项评价</div>
      <div style="padding-right: 120px;padding-left: 30px">
        <el-row>
          <el-col :span="4" style="padding: 5px">
            <div style="text-align: center;font-size: 10px;border: 1px solid gainsboro;height: 50px;margin-top: 5px">
              <div style="font-weight: bolder;color: white;background-color: #3A71A8;height: 25px;line-height: 25px">身份特征</div>
              <div style="color: #1ab394;height: 25px;line-height: 25px">
                <i class="el-icon-star-off"></i>27
              </div>
            </div>
          </el-col>
          <el-col :span="4" style="padding: 5px">
            <div style="text-align: center;font-size: 10px;border: 1px solid gainsboro;height: 50px;margin-top: 5px">
              <div style="font-weight: bolder;color: white;background-color: #3A71A8;height: 25px;line-height: 25px">经营风险</div>
              <div style="color: #1ab394;height: 25px;line-height: 25px">
                <i class="el-icon-star-off"></i>37
              </div>
            </div>
          </el-col>
          <el-col :span="4" style="padding: 5px">
            <div style="text-align: center;font-size: 10px;border: 1px solid gainsboro;height: 50px;margin-top: 5px">
              <div style="font-weight: bolder;color: white;background-color: #3A71A8;height: 25px;line-height: 25px">行业实力</div>
              <div style="color: #1ab394;height: 25px;line-height: 25px">
                <i class="el-icon-star-off"></i>45
              </div>
            </div>
          </el-col>
          <el-col :span="4" style="padding: 5px">
            <div style="text-align: center;font-size: 10px;border: 1px solid gainsboro;height: 50px;margin-top: 5px">
              <div style="font-weight: bolder;color: white;background-color: #3A71A8;height: 25px;line-height: 25px">发展速度</div>
              <div style="color: #1ab394;height: 25px;line-height: 25px">
                <i class="el-icon-star-off"></i>56
              </div>
            </div>
          </el-col>
          <el-col :span="4" style="padding: 5px">
            <div style="text-align: center;font-size: 10px;border: 1px solid gainsboro;height: 50px;margin-top: 5px">
              <div style="font-weight: bolder;color: white;background-color: #3A71A8;height: 25px;line-height: 25px">活跃程度</div>
              <div style="color: #1ab394;height: 25px;line-height: 25px">
                <i class="el-icon-star-off"></i>66
              </div>
            </div>
          </el-col>
          <el-col :span="4" style="padding: 5px">
            <div style="text-align: center;font-size: 10px;border: 1px solid gainsboro;height: 50px;margin-top: 5px">
              <div style="font-weight: bolder;color: white;background-color: #3A71A8;height: 25px;line-height: 25px">创新能力</div>
              <div style="color: #1ab394;height: 25px;line-height: 25px">
                <i class="el-icon-star-off"></i>67
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
    <div style="margin-top: 10px;margin-left: 20px;margin-right: 20px">
      <div class="c-data-h1">企业分项排名</div>
      <div style="padding-right: 10px;padding-left: 30px">
        <el-row style="padding-top: 5px;">
          <el-col :span="2">
            <span>总体排名</span>
          </el-col>
          <el-col :span="17" style="padding: 5px">
            <el-progress :stroke-width="14" :percentage="99" :show-text="false"></el-progress>
          </el-col>
          <el-col :span="5">
            <span>领先99%企业</span>
          </el-col>
        </el-row>
        <el-row style="padding-top: 5px">
          <el-col :span="2">
            <span>总体排名</span>
          </el-col>
          <el-col :span="17" style="padding: 5px">
            <el-progress :stroke-width="10" :percentage="99" :show-text="false" status="success"></el-progress>
          </el-col>
          <el-col :span="5">
            <span>领先99%企业</span>
          </el-col>
        </el-row>
        <el-row style="padding-top: 5px">
          <el-col :span="2">
            <span>总体排名</span>
          </el-col>
          <el-col :span="17" style="padding: 5px">
            <el-progress :stroke-width="10" :percentage="99" :show-text="false" status="success"></el-progress>
          </el-col>
          <el-col :span="5">
            <span>领先99%企业</span>
          </el-col>
        </el-row>
        <el-row style="padding-top: 5px">
          <el-col :span="2">
            <span>总体排名</span>
          </el-col>
          <el-col :span="17" style="padding: 5px">
            <el-progress :stroke-width="10" :percentage="99" :show-text="false" status="success"></el-progress>
          </el-col>
          <el-col :span="5">
            <span>领先99%企业</span>
          </el-col>
        </el-row>
        <el-row style="padding-top: 5px">
          <el-col :span="2">
            <span>总体排名</span>
          </el-col>
          <el-col :span="17" style="padding: 5px">
            <el-progress :stroke-width="10" :percentage="99" :show-text="false" status="success"></el-progress>
          </el-col>
          <el-col :span="5">
            <span>领先99%企业</span>
          </el-col>
        </el-row>
        <el-row style="padding-top: 5px">
          <el-col :span="2">
            <span>总体排名</span>
          </el-col>
          <el-col :span="17" style="padding: 5px">
            <el-progress :stroke-width="10" :percentage="99" :show-text="false" status="success"></el-progress>
          </el-col>
          <el-col :span="5">
            <span>领先99%企业</span>
          </el-col>
        </el-row>
      </div>

    </div>
  </div>
</template>
<script>
import * as echarts from "echarts";
export default({
  name: "ScoreDetail",
  data() {
    return {
      radarStats: null,
    }
  },
  mounted() {
    this.initRadarStats();
  },
  methods: {
    initRadarStats() {
      this.radarStats = echarts.init(this.$refs.radarStats, "macarons");
      this.radarStats.setOption({
        title: {
          text: 'Basic Radar Chart',
          show: false
        },
        legend: {
          data: ['综合实力评分'],
          show: false
        },
        radar: {
          radius: '60%',
          // shape: 'circle',
          center: ['50%', '50%'],
          // shape: 'circle',
          indicator: [
            { name: '身份特征', max: 100 },
            { name: '经营风险', max: 100 },
            { name: '行业实力', max: 100 },
            { name: '发展速度', max: 100 },
            { name: '活跃程度', max: 100 },
            { name: '创新能力', max: 100 }
          ]
        },
        series: [
          {
            name: 'Budget vs spending',
            type: 'radar',
            data: [
              {
                value: [90, 80, 70, 60, 50, 40],
                name: '综合实力评分'
              }
            ]
          }
        ]
      });
    },

  }
})
</script>
<style scoped lang="scss">
.c-data-h1{
  height: 25px;
  line-height: 25px;
  font-size: 14px;
  color: #464c5b;
  font-weight: bolder;
}
</style>
