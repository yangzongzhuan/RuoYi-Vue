<template>
  <div style="margin: auto;background-color: #f9f9f9;padding-top: 15px;min-height: 700px">
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
        <el-table-column label="经营状态" prop="creditCode"></el-table-column>
        <el-table-column label="法人" prop="legalPerson"></el-table-column>
        <el-table-column label="注册资本" prop="regCapital"></el-table-column>
        <el-table-column label="注册时间" prop="regTime"></el-table-column>
        <el-table-column label="担保金额" prop="danbao_amount"></el-table-column>
        <el-table-column label="被担保金额" prop="beidanbao_amount"></el-table-column>
        <el-table-column label="详情">
          <template slot-scope="row">
            <el-link size="mini" type="primary" @click="viewDetail">详情</el-link>
          </template>
        </el-table-column>
      </el-table>
      <pagination :total="0" :page.sync="dataGrid.listQuery.page" :limit.sync="dataGrid.listQuery.limit" @pagination="getList" />
    </div>
  </div>
</template>
<script>
import Pagination from "@/components/Pagination";
export default({
  name: "WarrantyGraphList",
  components: { Pagination },
  data() {
    return {
      textCenter: "center",
      dialogTableVisible: false,
      gridMemberData: [],
      membersCurrentPage: 1,
      groupMembersTotal: 0,
      grpname: "",
      dataGrid:{
        total: 0,
        list: [
          {
            entname: '安徽乐富强控股集团有限公司',
            creditCode: '91340100MA2YXQ0X1E',
            legalPerson: '王建华',
            regCapital: '1000万人民币',
            regTime: '2018-01-01',
            danbao_amount: '1000万人民币',
            beidanbao_amount: '2000万人民币',
          },
          {
            entname: '渝商投资集团股份有限公司',
            creditCode: '91340100MA2YXQ0X1E',
            legalPerson: '王建华',
            regCapital: '1000万人民币',
            regTime: '2018-01-01',
            danbao_amount: '1000万人民币',
            beidanbao_amount: '2000万人民币',
          }
        ],
        listQuery: {
          page: 1,
          limit: 10,
          keywords: undefined
        }
      }
    }
  },
  methods: {
    getList() {
      this.dataGrid.listLoading = true;
      setTimeout(() => {
        this.dataGrid.listLoading = false;
      }, 2000);
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

    querySearchAsync(queryString, cb) {
      var restaurants = this.restaurants;
      var results = queryString
        ? restaurants.filter(this.createFilter(queryString))
        : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    viewDetail() {

      this.$router.push({
        path: "/supplier_graph/warrantyGraphDetail"
      });
    }
  }
})
</script>
<style scoped>

</style>
