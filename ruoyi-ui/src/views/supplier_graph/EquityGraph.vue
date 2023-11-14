<template>
  <div id="app" style="margin: auto;background-color: #f9f9f9;padding-top: 15px;min-height: 700px">
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
    <div style="padding-left: 20px;line-height: 40px;height: 40px;background-color: white;font-size: 12px;margin-top: 15px">
      为您找到 <span style="color: #2E4E8F;font-weight: bolder">{{ dataGrid.total }}</span> 家符合条件的企业，此处最多展示10000条。
    </div>
    <div style="clear: both;background-color: white;margin-top: 15px;min-height: 300px;padding:15px">
      <el-table
        v-loading="dataGrid.listLoading"
        :data="dataGrid.list"
        border
        fit
        highlight-current-row
        :header-row-style="{height:'40px',fontSize: '13px'}"
        :header-cell-style="{padding:'0px'}"
        :row-style="{height:'40px',fontSize: '13px'}"
        :cell-style="{padding:'0px'}"
        style="width: 100%;"
      >
        <el-table-column label="序号" width="60" align="center">
          <template slot-scope="scope">{{ scope.$index+1 }}</template>
        </el-table-column>
        <el-table-column label="企业名称" prop="entname"></el-table-column>
        <el-table-column label="统一社会信用代码" prop="creditCode"></el-table-column>
        <el-table-column label="法人" prop="legalPerson"></el-table-column>
        <el-table-column label="注册资本" prop="regCapital"></el-table-column>
        <el-table-column label="注册时间" prop="regTime"></el-table-column>
        <el-table-column label="注册机关" prop="regOrg"></el-table-column>
        <el-table-column label="所属行政区" prop="regArea"></el-table-column>
        <el-table-column label="详情">
          <template slot-scope="row">
            <el-link size="mini" type="primary">详情</el-link>
          </template>
        </el-table-column>
      </el-table>
      <pagination style="text-align: right;" :total="dataGrid.total" :page.sync="dataGrid.listQuery.page" :limit.sync="dataGrid.listQuery.limit" @pagination="getList" />
    </div>
  </div>
</template>
<script>
import Pagination from "@/components/Pagination/index.vue";
export default ({
  name: "EquityGraph",
  components: { Pagination },
  data() {
    return {
      dataGrid: {
        listQuery: {
          page: 1,
          limit: 10,
          keywords: undefined
        },
        total: 0,
        list: [
          {
            entname: "北京阿里巴巴科技有限公司",
            creditCode: "91110108584400000A",
            legalPerson: "张勇",
            regCapital: "1000万人民币",
            regTime: "2018-01-01",
            regOrg: "北京市工商行政管理局",
            regArea: "北京市",
          },
          {
            entname: "北京阿里巴巴科技有限公司",
            creditCode: "91110108584400000A",
            legalPerson: "张勇",
            regCapital: "1000万人民币",
            regTime: "2018-01-01",
            regOrg: "北京市工商行政管理局",
            regArea: "北京市",
          },
          {
            entname: "北京阿里巴巴科技有限公司",
            creditCode: "91110108584400000A",
            legalPerson: "张勇",
            regCapital: "1000万人民币",
            regTime: "2018-01-01",
            regOrg: "北京市工商行政管理局",
            regArea: "北京市",
          }
        ],
        loading: true
      },
    }
  },
  methods: {

    handleFilter() {
      this.dataGrid.listQuery.page = 1;
      this.getList();
    },
    handleInput() {
      this.dataGrid.listQuery.page = 1;
      this.getList();
    },
    handleSelect() {
      this.dataGrid.listQuery.page = 1;
      this.getList();
    },
    getList() {
      this.dataGrid.loading = true;
    },
    querySearchAsync(queryString, cb) {
    },
  }
})
</script>
<style scoped lang="scss">

</style>
