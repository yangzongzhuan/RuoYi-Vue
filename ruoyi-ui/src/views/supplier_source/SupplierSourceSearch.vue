<template>
  <div id="app" style="margin: auto;background-color: #f9f9f9;padding-top: 15px;padding-bottom: 30px;padding-right: 15px;padding-left: 15px;min-height: 700px">
    <div style="background-color: white;text-align: left;padding:15px;height: 150px">
      <el-autocomplete
        v-model="dataGrid.listQuery.keywords"
        placeholder="请输入关键字检索"
        clearable
        style="width: 610px;"
        class="filter-item"
      ></el-autocomplete>
      <el-button type="primary" style="margin-left: 10px">检索</el-button>
      <div style="margin-top: 10px">
        <div style="font-weight: bolder;color: black;float: left;height: 30px;line-height: 30px;">检索维度：</div>
        <div style="float: left;width: 92%;">
          <div class="c-div-search" v-for="item in searchScopeConditions" :key="item.value">
            <span>{{item.label}}</span>
          </div>
        </div>
      </div>
    </div>
    <div style="background-color: white;margin-top: 10px">
      <el-card class="box-card">
        <div slot="header">
          <el-row>
            <el-col :span="23">
              <el-checkbox v-model="checkedAll" size="mini">
                企业列表（共检索出22条结果）
              </el-checkbox>
            </el-col>
            <el-col :span="1">
              <el-button type="primary" style="text-align: right" size="small" @click="saveList">保存</el-button>
            </el-col>
          </el-row>
        </div>
        <div v-for="item in 10" :key="item" style="padding-bottom: 10px">
          <el-row >
            <el-col :span="1">
              <div style="line-height: 230px">
                <el-checkbox v-model="checkOne"></el-checkbox>
              </div>
            </el-col>
            <el-col :span="3">
              <div class="grid-content bg-purple" style="line-height: 300px;height: 200px;;float: left">
                <el-image
                  style="width: 100px; height: 100px;border: 1px solid gainsboro"
                  src="https://teamcom-dev-1251781880.cos.ap-beijing.myqcloud.com/alibaba.png"
                  fit="fill">
                </el-image>
              </div>
            </el-col>
            <el-col :span="16">
              <div class="grid-content bg-purple-light" style="height: 200px">
                <div style="font-size: 16px;font-weight: bolder;">
                  阿里巴巴（中国）网络技术有限公司
                  <el-tag>高新技术企业</el-tag><el-tag style="margin-left: 10px">国家重点企业</el-tag>
                </div>
                <div>
                  <el-descriptions :column="4" size="mini">
                    <el-descriptions-item label="法定代表人">张勇</el-descriptions-item>
                    <el-descriptions-item label="注册资本">1000万人民币</el-descriptions-item>
                    <el-descriptions-item label="成立日期">2007-10-09</el-descriptions-item>
                    <el-descriptions-item label="所属地区">浙江省杭州市西湖区</el-descriptions-item>
                  </el-descriptions>
                </div>
                <div class="c-suggestion-name" style="width: 700px">
                  <el-tag>高新技术企业</el-tag>
                  <el-tag style="margin-left: 10px">国家重点企业</el-tag>
                  <el-tag style="margin-left: 10px">高端人才产业</el-tag>
                  <el-tag style="margin-left: 10px">中国500强</el-tag>
                  <el-tag style="margin-left: 10px">数据挖掘</el-tag>
                  <el-tag style="margin-left: 10px">大数据</el-tag>
                  <el-tag style="margin-left: 10px">云计算</el-tag>
                  <el-tag style="margin-left: 10px">人工智能</el-tag>
                  <el-tag style="margin-left: 10px">大数据分析</el-tag>
                </div>
                <div style="margin-top: 10px">
                  <el-descriptions :column="1" size="small">
                    <el-descriptions-item>
                      <template slot="label">
                        <i class="el-icon-paperclip"></i>
                        <span class="c-span-text">专利摘要</span>
                      </template>
                      <span class="c-suggestion-name">本申请提供一种商品信息处理系统、方法、设备及存储介质。<span class="c-highlight-word">在本申请中</span>，依托于服务端和价格域系统组成的商品信息处理系统，
                      首先，由服务端响应物流服务提供方触发的物流调价请求执行针对包邮商品的物流调价操作</span>
                    </el-descriptions-item>
                  </el-descriptions>
                  <el-descriptions :column="1" size="small">
                    <el-descriptions-item>
                      <template slot="label">
                        <i class="el-icon-paperclip"></i>
                        <span class="c-span-text">软件名称</span>
                      </template>
                      <span class="c-suggestion-name">阿里巴巴手机客户端&nbsp;|&nbsp;人工智能客户端&nbsp;|&nbsp;人工智能客户端&nbsp;</span>
                    </el-descriptions-item>
                  </el-descriptions>
                  <el-descriptions :column="1" size="small">
                    <el-descriptions-item>
                      <template slot="label">
                        <i class="el-icon-paperclip"></i>
                        <span class="c-span-text">APP应用简介</span>
                      </template>
                      <span class="c-suggestion-name">使用最先进的自然语言处理技术,让App内置的聊天机器人像真人一样流利地与您交流。您可以与它轻松聊天,它还可以提供生活、学习等方面的帮助。</span>
                    </el-descriptions-item>
                  </el-descriptions>
                  <el-descriptions :column="1" size="small">
                    <el-descriptions-item>
                      <template slot="label">
                        <i class="el-icon-paperclip"></i>
                        <span class="c-span-text">企业简介</span>
                      </template>
                      <span class="c-suggestion-name">阿里巴巴（中国）网络技术有限公司，成立于1999年，位于浙江省杭州市，是一家以从事互联网和相关服务为主的企业。</span>
                    </el-descriptions-item>
                  </el-descriptions>
                  <el-descriptions :column="1" size="small">
                    <el-descriptions-item>
                      <template slot="label">
                        <i class="el-icon-paperclip"></i>
                        <span class="c-span-text">商标</span>
                      </template>
                      <span class="c-suggestion-name">生成式人工智能</span>
                    </el-descriptions-item>
                  </el-descriptions>
                  <el-descriptions :column="1" size="small">
                    <el-descriptions-item>
                      <template slot="label">
                        <i class="el-icon-paperclip"></i>
                        <span class="c-span-text">网址名称</span>
                      </template>
                      <span class="c-suggestion-name">1688.com-世界上最大的批发网址</span>
                    </el-descriptions-item>
                  </el-descriptions>
                </div>
              </div>
            </el-col>
            <el-col :span="4">
              <div style="text-align: center;padding-top: 100px;cursor: pointer;" @click="viewScoreDetail">
                <div style="font-weight: bolder;font-size: 30px;color: #1ab394">434分</div>
                <div>企业评分</div>
              </div>
            </el-col>
          </el-row>
          <el-divider></el-divider>
        </div>
      </el-card>
      <el-pagination
        style="margin-top: 20px;text-align: right"
        background
        layout="prev, pager, next"
        :total="dataGrid.total"
        :page-size="dataGrid.listQuery.limit"
        :current-page="dataGrid.listQuery.page"
        @current-change="getList"
      >
      </el-pagination>
    </div>


<!--    <el-dialog title="企业评分详情" :visible.sync="dialogVisible" width="500">-->
<!--      <ScoreDetail />-->
<!--    </el-dialog>-->

    <el-dialog title="保存结果集" :visible.sync="dialogVisible">
      <el-form label-width="130px" :model="formLabelAlign">
        <el-form-item label="目标集名称">
          <el-input v-model="formLabelAlign.resultName"></el-input>
        </el-form-item>
        <el-form-item label="目标集可见性">
          <el-radio-group v-model="formLabelAlign.resultVisible">
            <el-radio :label="1">仅自己可见</el-radio>
            <el-radio :label="2">公开数据</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">保存</el-button>
          <el-button @click="dialogVisible = false">取消</el-button>
        </el-form-item>
      </el-form>

    </el-dialog>
  </div>
</template>
<script>
import Pagination from '@/components/Pagination';
import ScoreDetail from "@/views/supplier_source/compents/ScoreDetail.vue";
export default ({
  name: "SupplierSourceSearch",
  components: { Pagination, ScoreDetail },
  data() {
    return {
      tableKey: 0,
      checkBoxSearchType: [],
      dialogVisible: false,
      checkedAll: false,
      checkOne: false,
      // 搜索范围
      searchScopeConditions: [
        {
          label: '行业', value: 'industry'
        },
        {
          label: '经营范围', value: 'entBusinessScope'
        },
        {
          label: '企业性质', value: 'entNature'
        },
        {
          label: '区域', value: 'area'
        },
        {
          label: '股东性质', value: 'shareholderNature'
        },
        {
          label: '规模', value: 'scale'
        },
        {
          label: '专利摘要', value: 'patentAbstract'
        },
        {
          label: '专利名称', value: 'patentName'
        },
        {
          label: '资质/能力', value: 'qualification'
        },
        {
          label: '经营年限', value: 'entBusinessYear'
        },
        {
          label: '企业简介', value: 'entIntroduction'
        },
        {
          label: '网站摘要', value: 'websiteAbstract'
        },
        {
          label: '网站名称', value: 'websiteName'
        },
        {
          label: '企业APP简介', value: 'entAppAbstract'
        },
        {
          label: '实力评价分', value: 'strengthScore'
        }
      ],
      formLabelAlign: {
        resultName: null,
        resultVisible: false
      },
      dataGrid: {
        list: [],
        total: 0,
        listLoading: false,
        sort_by: '',
        sort_type: '',
        listQuery: {
          area_code: '',
          entid: '',
          keywords: '',
          page: 1,
          limit: 10
        }
      }
    }
  },
  methods: {
    showPropertiesView(){

    },
    getList() {

    },
    saveList() {
      console.log('保存结果集')
      this.dialogVisible = true;
    },
    onSubmit() {
      // 提示保存成功
      this.dialogVisible = false;
      this.$message({
        message: '保存成功',
        type: 'success'
      });
    },
    viewScoreDetail() {
      this.dialogVisible = true;
    }
  }
})
</script>
<style scoped>
 .c-span-text{
    font-size: 12px;
    color: #999999;
 }
  .c-suggestion-name {
      color: #333333;
      white-space: nowrap;
      overflow: hidden;
      font-weight: bolder;
      text-overflow: ellipsis;
      width: 600px;
  }
  .c-highlight-word {
      color: red;
  }
  .c-div-search{
    width: 90px;
    float: left;
    margin-left: 10px;
    margin-top: 5px;
    text-align: center;
    height: 30px;
    line-height: 30px;
    border: 1px solid #F2F2F2;
    font-size: 13px;
    background-color: #F8F8F8;
    color: #AAAAAA;
    font-weight: bolder;
    cursor: pointer;
  }
  .c-div-search-active{
    border: 1px solid #5297F7;
    background-color: white;
    color: #5297F7;
  }
</style>
