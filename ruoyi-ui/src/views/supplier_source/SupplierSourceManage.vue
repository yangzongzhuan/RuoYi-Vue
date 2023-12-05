<template>
  <div id="app" style="margin: auto;background-color: #f9f9f9;padding:15px;min-height: 670px">
    <div style="background-color: white;min-height: 600px;">
      <div class="c-header">
        <el-row>
          <el-col :span="5">
            <el-input v-model="datasetName" placeholder="请输入关键字搜索" style="width: 200px" size="small"></el-input>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" size="small">检索</el-button>
          </el-col>
          <el-col :span="17" style="text-align: right">
            <el-button type="primary" size="small">导出目标集</el-button>
          </el-col>
        </el-row>
      </div>
      <div style="padding: 15px">
        <el-table :data="tableData" border>
          <el-table-column type="selection" width="50"></el-table-column>
          <el-table-column type="index" label="序号" width="50"></el-table-column>
          <el-table-column prop="datasetName" label="目标集名称"></el-table-column>
          <el-table-column prop="visibleDataset" label="可见状态"></el-table-column>
          <el-table-column prop="createTime" label="创建时间"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
                <el-link type="primary" @click="viewDetail(scope.row.id)">查看</el-link>
                <el-link type="danger" style="margin-left: 5px" @click="deleteData(scope.row.id)">删除</el-link>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          style="margin-top: 15px;text-align: right"
          background
          layout="prev, pager, next"
          :page-size="10"
          :total="total"
          :current-page="currentPage"
          @current-change="handleCurrentChange"
        >
        </el-pagination>
      </div>
    </div>
    <el-dialog
      width="70%"
      title="目标集详情"
      :visible.sync="dialogVisibleDetail"
    >
      <el-table
        :data="detailData"
        border
        size="mini"
      >
        <el-table-column label="企业名称" prop="entname" width="200"></el-table-column>
        <el-table-column label="实力评价" prop="entScore"></el-table-column>
        <el-table-column label="关键字段" prop="entKeyword"></el-table-column>
        <el-table-column label="法人" prop="lerepname"></el-table-column>
        <el-table-column label="注册资本" prop="regCapital">
          <template slot-scope="scope">
            {{ scope.row.regcap }}{{ scope.row.regcapcurCn }}
          </template>
        </el-table-column>
        <el-table-column label="注册时间" prop="esdate"></el-table-column>
      </el-table>
    </el-dialog>
  </div>

</template>

<script>
import request from "@/utils/request";
export default ({
  name: "SupplierSourceManage",
  data() {
    return {
      tableData: [],
      detailData: [],
      total: 0,
      currentPage: 1,
      dialogVisibleDetail: false,
      datasetName: '',
    }
  },
  mounted() {
      this.getList();
  },
  methods:{
    handleCurrentChange(val) {
      this.currentPage = val;
      this.getList();
    },
    getList() {
      request({
        url: '/entInfo/getDataSetList',
        method: 'post',
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize
        },
        data: {
          datasetName: this.datasetName
        }
      }).then(res => {
        this.tableData = res.data.item;
        this.total = res.data.total;
      })
    },
    viewDetail(id) {
      request({
        url: '/entInfo/getDataSetDetail/'+id,
        method: 'get'
      }).then(res => {
        this.detailData = res.data;
        this.dialogVisibleDetail = true;
      })
    },
    deleteData(id) {
      // 删除数据，用户需要确认
      this.$confirm('此操作将删除该结果集, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request({
          url: '/entInfo/deleteDataSet/'+id,
          method: 'delete'
        }).then(res => {
          this.getList();
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        })
        // 删除数据
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    }
  }
})
</script>
<style scoped>
.c-header{
  height: 100px;
  padding: 15px;
}
</style>
