<template>
  <div id="app" style="margin: auto;background-color: #f9f9f9;padding-top: 15px;padding-bottom: 30px;padding-right: 15px;padding-left: 15px;min-height: 900px">
    <div style="background-color: white;line-height: 70px;height: 70px;text-align: center">
      <el-autocomplete
        v-model="dataGrid.listQuery.keywords"
        :fetch-suggestions="querySearchAsync"
        placeholder="请输入公司名称/统一社会信用代码"
        clearable
        style="width: 610px;"
        class="filter-item"
        @input="handleInput"
        @select="handleSelect"
        @keyup.enter.native="handleFilter"
      >
        <el-button slot="append" icon="el-icon-search" @click="handleFilter" />
      </el-autocomplete>
    </div>
    <div style="clear: both;background-color: white;margin-top: 15px;height: 350px">
      <div style="height: 40px;padding: 10px">
        <span style="font-weight: bolder;color: black;font-size: 15px">北京阿里巴巴科技有限公司</span>
      </div>
      <div style="height: 300px;padding: 15px">
        <div class="c-data-h1">企业综合评分</div>
        <el-row>
          <el-col :span="12">
            <div style="text-align: center;padding-top: 90px">
              <div style="font-weight: bolder;font-size: 30px;color: #1ab394">434分</div>
              <div>企业评分</div>
            </div>
          </el-col>
          <el-col :span="12">
            <div ref="radarStats" style="height: 300px;" />
          </el-col>
        </el-row>
      </div>
    </div>
    <div style="clear: both;background-color: white;padding: 15px;margin-top: 15px">
      <div class="c-data-h1">企业分项评价</div>
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
    <div style="clear: both;background-color: white;padding: 15px;margin-top: 15px;height: 250px;text-align: center">
      <div class="c-data-h1" style="text-align: left">企业分项排名</div>
      <el-row style="padding-top: 5px">
        <el-col :span="2">
          <span>经营风险</span>
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
          <span>身份特征</span>
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
          <span>创新能力</span>
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
          <span>活跃程度</span>
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
          <span>行业示例</span>
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
          <span>发展速度</span>
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
          <span>经营风险</span>
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
</template>
<script>
import * as echarts from "echarts";
export default({
  name: "StrengthEvaluation",
  data(){
    return{
      radarStats: null,
      dataGrid:{
        listQuery:{
          keywords: '',
        }
      },
      restaurants: ['麦当劳', '肯德基', '必胜客', '永和大王', '德克士', '星巴克', '吉野家', '哈根达斯']
    }
  },
  mounted() {
    this.initRadarStats()
  },
  methods:{
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
          center: ['50%', '40%'],
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

    handleFilter(){
      console.log(this.dataGrid.listQuery.keywords)
    },
    handleInput(){
      console.log(this.dataGrid.listQuery.keywords)
    },
    handleSelect(){
      console.log(this.dataGrid.listQuery.keywords)
    },
    querySearchAsync(queryString, cb) {
      var restaurants = this.restaurants;
      var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
  }
})
</script>
<style scoped>
.c-data-h1{
  border-left: 7px solid #009EFF;
  padding-left: 5px;
  height: 25px;
  line-height: 25px;
  font-size: 14px;
  color: #464c5b;
  font-weight: bolder;
}

</style>
