<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="queryParams.pdfTitle" placeholder="请输入PDF标题" clearable class="filter-item w-200" />
      <el-select v-model="queryParams.status" placeholder="请选择状态" clearable class="filter-item w-200">
        <el-option label="待处理" value="0" />
        <el-option label="处理中" value="1" />
        <el-option label="处理完成" value="2" />
        <el-option label="处理失败" value="3" />
      </el-select>
      <el-date-picker
        v-model="dateRange"
        type="daterange"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        value-format="yyyy-MM-dd"
        class="filter-item"
      />
      <el-button type="primary" icon="el-icon-search" @click="handleQuery" size="mini">搜索</el-button>
      <el-button icon="el-icon-refresh" @click="resetQuery" size="mini">重置</el-button>
    </div>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:pdf:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:pdf:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:pdf:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="pdfFileList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="PDF标题" align="center" prop="pdfTitle" :show-overflow-tooltip="true" />
      <el-table-column label="页数" align="center" prop="pageCount" width="80">
        <template slot-scope="scope">
          {{ scope.row.pageCount || '-' }}
        </template>
      </el-table-column>
      <el-table-column label="文件大小" align="center" prop="pdfSize" width="100">
        <template slot-scope="scope">
          {{ formatFileSize(scope.row.pdfSize) }}
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === '0'" type="info">待处理</el-tag>
          <el-tag v-else-if="scope.row.status === '1'" type="warning">处理中</el-tag>
          <el-tag v-else-if="scope.row.status === '2'" type="success">处理完成</el-tag>
          <el-tag v-else-if="scope.row.status === '3'" type="danger">处理失败</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleViewImages(scope.row.pdfId)"
            v-hasPermi="['system:pdf:viewImage']"
          >查看图片</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleEdit(scope.row.pdfId)"
            v-hasPermi="['system:pdf:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-refresh"
            @click="handleReprocess(scope.row.pdfId)"
            v-hasPermi="['system:pdf:edit']"
          >重新处理</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row.pdfId)"
            v-hasPermi="['system:pdf:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改PDF文件对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="PDF标题" prop="pdfTitle">
          <el-input v-model="form.pdfTitle" placeholder="请输入PDF标题" />
        </el-form-item>
        <el-form-item label="PDF描述" prop="pdfDescription">
          <el-input v-model="form.pdfDescription" type="textarea" placeholder="请输入PDF描述" :rows="3" />
        </el-form-item>
        <el-form-item v-if="!form.pdfId" label="PDF文件" prop="pdfPath">
          <FileUpload
            v-model="form.pdfPath"
            :limit="1"
            :fileType="['pdf']"
            :fileSize="100"
            @change="handleFileChange"
          />
        </el-form-item>
        <el-form-item v-else label="PDF文件">
          <div v-if="form.pdfPath" class="file-info">
            <el-link :href="getBaseUrl() + form.pdfPath" target="_blank" type="primary">
              查看已上传文件
            </el-link>
            <el-button type="text" size="small" @click="handleReupload">重新上传</el-button>
          </div>
          <FileUpload
            v-if="showFileUpload"
            v-model="uploadFilePath"
            :limit="1"
            :fileType="['pdf']"
            :fileSize="100"
            @change="handleFileChange"
          />
        </el-form-item>
        <el-form-item v-if="form.pdfId" label="处理状态">
          <el-tag v-if="form.status === '0'" type="info">待处理</el-tag>
          <el-tag v-else-if="form.status === '1'" type="warning">处理中</el-tag>
          <el-tag v-else-if="form.status === '2'" type="success">处理完成</el-tag>
          <el-tag v-else-if="form.status === '3'" type="danger">处理失败</el-tag>
        </el-form-item>
        <el-form-item v-if="form.pdfId" label="页数">
          {{ form.pageCount || '-' }}
        </el-form-item>
        <el-form-item v-if="form.pdfId" label="文件大小">
          {{ formatFileSize(form.pdfSize) }}
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注信息" :rows="2" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style scoped>
.file-info {
  display: flex;
  align-items: center;
  gap: 10px;
}
.dialog-footer {
  text-align: center;
}
</style>

<script>
import { listPdfFile, getPdfFile, delPdfFile, addPdfFile, updatePdfFile } from "@/api/system/pdf";
import FileUpload from "@/components/FileUpload";

export default {
  components: {
    FileUpload
  },
  name: "PdfFile",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      multiple: true,
      // 显示状态
      open: false,
      // 总条数
      total: 0,
      // PDF文件表格数据
      pdfFileList: [],
      // 弹出层标题
      title: "",
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        pdfTitle: undefined,
        status: undefined,
        beginTime: undefined,
        endTime: undefined
      },
      // 表单参数
      form: {
        pdfId: undefined,
        pdfTitle: undefined,
        pdfDescription: undefined,
        pdfPath: undefined,
        originalFilename: undefined,
        pdfSize: undefined,
        pageCount: undefined,
        status: undefined,
        remark: undefined
      },
      // 表单校验
      rules: {
        pdfTitle: [
          { required: true, message: "PDF标题不能为空", trigger: "blur" }
        ],
        pdfPath: [
          { required: function() { return this.form.pdfId === undefined; }, message: "请上传PDF文件", trigger: "blur" }
        ]
      },
      // 显示文件上传组件
      showFileUpload: false,
      // 上传的文件路径
      uploadFilePath: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询PDF文件列表 */
    getList() {
      this.loading = true;
      if (this.dateRange && this.dateRange.length > 0) {
        this.queryParams.beginTime = this.dateRange[0];
        this.queryParams.endTime = this.dateRange[1];
      } else {
        this.queryParams.beginTime = undefined;
        this.queryParams.endTime = undefined;
      }
      listPdfFile(this.queryParams).then(response => {
        this.pdfFileList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 表格选中行变更 */
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.pdfId);
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加PDF文件";
    },
    /** 修改按钮操作 */
    handleEdit(row) {
      this.reset();
      const pdfId = typeof row === 'string' ? row : row.pdfId;
      getPdfFile(pdfId).then(response => {
        this.form = response.data || response;
        this.open = true;
        this.title = "修改PDF文件";
      });
    },
    /** 查看PDF图片 */
    handleViewImages(pdfId) {
      this.$router.push({ path: "/system/pdf/viewImages/" + pdfId });
    },
    /** 重新处理PDF */
    handleReprocess(pdfId) {
      this.$confirm("是否重新处理该PDF文件?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return reprocessPdfFile(pdfId);
      }).then(() => {
        this.msgSuccess("重新处理任务已开始");
        this.getList();
      }).catch(() => {});
    },
    /** 删除按钮操作 */
    handleDelete(pdfId) {
      const pdfIds = pdfId || this.ids;
      this.$confirm("是否确认删除PDF文件?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delPdfFile(pdfIds);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.downloadFile("/system/pdf/export", { ...this.queryParams }, `pdf_file_${new Date().getTime()}.xlsx`);
    },
    /** 提交表单 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.pdfId != undefined) {
            updatePdfFile(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addPdfFile(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 重置表单 */
    reset() {
      this.form = {
        pdfId: undefined,
        pdfTitle: undefined,
        pdfDescription: undefined,
        pdfPath: undefined,
        originalFilename: undefined,
        pdfSize: undefined,
        pageCount: undefined,
        status: '0', // 设置默认状态为待处理
        remark: undefined
      };
      this.showFileUpload = false;
      this.uploadFilePath = [];
      this.resetForm("form");
    },
    /** 获取基础URL */
    getBaseUrl() {
      return process.env.VUE_APP_BASE_API;
    },
    /** 重新上传文件 */
    handleReupload() {
      this.showFileUpload = true;
      this.uploadFilePath = [];
    },
    /** 文件上传后处理 */
    handleFileChange(fileList) {
      if (fileList && fileList.length > 0) {
        const file = fileList[0];
        this.form.pdfPath = file.url;
        this.form.originalFilename = file.name;
        this.form.pdfSize = file.size;
        // 重置处理状态，重新上传后需要重新处理
        this.form.status = '0';
        this.form.pageCount = undefined;
      }
    },
    /** 取消按钮操作 */
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 格式化文件大小 */
    formatFileSize(bytes) {
      if (bytes === 0) return '0 B';
      const k = 1024;
      const sizes = ['B', 'KB', 'MB', 'GB', 'TB'];
      const i = Math.floor(Math.log(bytes) / Math.log(k));
      return (bytes / Math.pow(k, i)).toFixed(2) + ' ' + sizes[i];
    }
  }
};
</script>