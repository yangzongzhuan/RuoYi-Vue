<template>
  <div id="app" style="margin: auto;background-color: #f9f9f9;padding:15px;min-height: 670px">
    <div style="background-color: white;min-height: 600px;">
      <div class="c-header">
        <el-row>
          <el-col :span="5">
            <el-input placeholder="请输入关键字搜索" style="width: 200px" size="small"></el-input>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" size="small">检索</el-button>
          </el-col>
          <el-col :span="17" style="text-align: right">
            <el-button type="primary" size="small">导出目标集</el-button>
            <el-button type="primary" size="small">添加目标集</el-button>
          </el-col>
        </el-row>
      </div>
      <div style="padding: 15px">
        <el-table :data="tableData" border>
          <el-table-column type="selection" width="50"></el-table-column>
          <el-table-column prop="index" label="序号"></el-table-column>
          <el-table-column prop="name" label="目标集名称"></el-table-column>
          <el-table-column prop="visibleStatus" label="可见状态"></el-table-column>
          <el-table-column prop="createTime" label="创建时间"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="row">
                <el-link type="primary" @click="dialogVisibleDetail = true">查看</el-link>
                <el-link type="danger" style="margin-left: 5px" @click="detailResult">删除</el-link>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          style="margin-top: 15px;text-align: right"
          background
          layout="prev, pager, next"
          :page-size="10"
          :total="10"
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
      >
        <el-table-column label="企业名称" prop="entname" width="200"></el-table-column>
        <el-table-column label="实力评价" prop="entScore"></el-table-column>
        <el-table-column label="关键字段" prop="entKeyword"></el-table-column>
        <el-table-column label="法人" prop="legalPerson"></el-table-column>
        <el-table-column label="注册资本" prop="regCapital"></el-table-column>
        <el-table-column label="注册时间" prop="regTime"></el-table-column>
      </el-table>
    </el-dialog>
  </div>

</template>

<script>
export default ({
  name: "SupplierSourceManage",
  data() {
    return {
      tableData: [
        {
          index: 1,
          name: '目标集1',
          visibleStatus: '仅自己可见',
          createTime: '2020-01-01 12:00:00'
        },
        {
          index: 2,
          name: '目标集1',
          visibleStatus: '公开',
          createTime: '2020-01-01 12:00:00'
        },
        {
          index: 3,
          name: '目标集1',
          visibleStatus: '仅自己可见',
          createTime: '2020-01-01 12:00:00'
        },
        {
          index: 4,
          name: '目标集1',
          visibleStatus: '公开',
          createTime: '2020-01-01 12:00:00'
        },
        {
          index: 5,
          name: '目标集1',
          visibleStatus: '仅自己可见',
          createTime: '2020-01-01 12:00:00'
        },
        {
          index: 6,
          name: '目标集1',
          visibleStatus: '公开',
          createTime: '2020-01-01 12:00:00'
        },
        {
          index: 7,
          name: '目标集1',
          visibleStatus: '仅自己可见',
          createTime: '2020-01-01 12:00:00'
        },
        {
          index: 8,
          name: '目标集1',
          visibleStatus: '公开',
          createTime: '2020-01-01 12:00:00'
        },
        {
          index: 9,
          name: '目标集1',
          visibleStatus: '仅自己可见',
          createTime: '2020-01-01 12:00:00'
        },
        {
          index: 10,
          name: '目标集1',
          visibleStatus: '公开',
          createTime: '2020-01-01 12:00:00'
        }
      ],
      dialogVisibleDetail: false,
      detailData: [
        {
          entname: '北京阿里巴巴科技有限公司',
          entScore: 400,
          entKeyword: '软件名称',
          legalPerson: '马云',
          regCapital: '199万人民币',
          regTime: '1991-01-01'
        },
        {
          entname: '北京阿里巴巴科技有限公司',
          entScore: 400,
          entKeyword: '软件名称',
          legalPerson: '马云',
          regCapital: '199万人民币',
          regTime: '1991-01-01'
        },
        {
          entname: '北京阿里巴巴科技有限公司',
          entScore: 400,
          entKeyword: '软件名称',
          legalPerson: '马云',
          regCapital: '199万人民币',
          regTime: '1991-01-01'
        },
        {
          entname: '北京阿里巴巴科技有限公司',
          entScore: 400,
          entKeyword: '软件名称',
          legalPerson: '马云',
          regCapital: '199万人民币',
          regTime: '1991-01-01'
        },
        {
          entname: '北京阿里巴巴科技有限公司',
          entScore: 400,
          entKeyword: '软件名称',
          legalPerson: '马云',
          regCapital: '199万人民币',
          regTime: '1991-01-01'
        },
        {
          entname: '北京阿里巴巴科技有限公司',
          entScore: 400,
          entKeyword: '软件名称',
          legalPerson: '马云',
          regCapital: '199万人民币',
          regTime: '1991-01-01'
        }
      ]
    }
  },
  methods:{
    viewDetail() {

    },
    detailResult() {
      // 删除数据，用户需要确认
      this.$confirm('此操作将删除该结果集, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 删除数据
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
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
