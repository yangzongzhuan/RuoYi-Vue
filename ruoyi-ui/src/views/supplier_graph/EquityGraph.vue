<template>
  <div id="app" style="margin: auto;background-color: #f9f9f9;padding-top: 15px;min-height: 700px">
    <div style="background-color: white;line-height: 70px;height: 70px;text-align: center">
      <el-autocomplete
        v-model="dataGrid.listQuery.keyword"
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
        <el-table-column label="企业名称" prop="entname" width="200"></el-table-column>
        <el-table-column label="统一社会信用代码" prop="uniscid"></el-table-column>
        <el-table-column label="法人" prop="lerepname"></el-table-column>
        <el-table-column label="注册资本" prop="regCapital" width="200">
          <template slot-scope="scope">
            {{ scope.row.regcap }}{{ scope.row.regcapcurCn }}
          </template>
        </el-table-column>
        <el-table-column label="注册时间" prop="esdate"></el-table-column>
        <el-table-column label="注册机关" prop="regorgCn" width="200"></el-table-column>
        <el-table-column label="所属行政区" prop="domdistrictCn"></el-table-column>
        <el-table-column label="详情">
          <template slot-scope="scope">
            <el-link size="mini" type="primary" @click="viewGraph(scope.row.uniscid)">详情</el-link>
          </template>
        </el-table-column>
      </el-table>
      <pagination style="text-align: right;" :total="dataGrid.total" :page.sync="dataGrid.listQuery.page" :limit.sync="dataGrid.listQuery.limit" @pagination="getList" />
    </div>
  </div>
</template>
<script>
import Pagination from "@/components/Pagination/index.vue";
import request from "@/utils/request";
export default ({
  name: "EquityGraph",
  components: { Pagination },
  data() {
    return {
      dataGrid: {
        listQuery: {
          page: 1,
          limit: 10,
          keyword: null
        },
        total: 0,
        list: [],
        loading: true
      },
    }
  },
  mounted() {
    this.getList();
  },
  methods: {
    viewGraph(uniscid) {
      this.$router.push({
        path: "/supplier_graph/equity_graph_detail",
        query: { uniscid: uniscid }
      });
    },
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
      request({
        url: "/entInfo/searchInfoByKeywordSimple",
        method: "post",
        params: { pageNum: this.dataGrid.listQuery.page,pageSize: this.dataGrid.listQuery.limit },
        data: { keyword: this.dataGrid.listQuery.keyword }
      }).then(res => {
        this.dataGrid.list = res.data.item;
        this.dataGrid.total = res.data.total;
        this.dataGrid.loading = false;
      });
    },
    querySearchAsync(queryString, cb) {
    },
  }
})
</script>
<style scoped lang="scss">

</style>
