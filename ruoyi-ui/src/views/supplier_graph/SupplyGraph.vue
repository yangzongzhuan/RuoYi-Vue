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
        <el-table-column label="当年采购总金额（人民币）" prop="totalAmountYear"></el-table-column>
        <el-table-column label="当年前五大供应商合作金额占比" prop="top5suppliers"></el-table-column>
        <el-table-column label="当年最大供应商名称" prop="theLargestSupplier"></el-table-column>
        <el-table-column label="详情">
          <template slot-scope="row">
            <el-link size="mini" type="primary" @click="viewDetail">详情</el-link>
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
            entname: "北京中科三环科技股份有限公司",
            creditCode: "9111010876584630XN",
            totalAmountYear: "1000000",
            top5suppliers: "50%",
            theLargestSupplier: "北京中科三环科技股份有限公司"
          },
          {
            entname: "北京中科三环科技股份有限公司",
            creditCode: "9111010876584630XN",
            totalAmountYear: "1000000",
            top5suppliers: "50%",
            theLargestSupplier: "北京中科三环科技股份有限公司"
          },
          {
            entname: "北京中科三环科技股份有限公司",
            creditCode: "9111010876584630XN",
            totalAmountYear: "1000000",
            top5suppliers: "50%",
            theLargestSupplier: "北京中科三环科技股份有限公司"
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
    viewDetail() {
      this.$router.push({ path: "/supplier_graph/supplyGraphDetail" });
    }
  }
})
</script>
<style scoped lang="scss">

</style>
