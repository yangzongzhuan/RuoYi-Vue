<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="PDF标题" prop="pdfTitle">
        <el-input v-model="form.pdfTitle" placeholder="请输入PDF标题" />
      </el-form-item>
      <el-form-item label="PDF描述" prop="pdfDescription">
        <el-input v-model="form.pdfDescription" type="textarea" placeholder="请输入PDF描述" :rows="3" />
      </el-form-item>
      <el-form-item label="PDF文件">
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
      <el-form-item label="处理状态">
        <el-tag v-if="form.status === '0'" type="info">待处理</el-tag>
        <el-tag v-else-if="form.status === '1'" type="warning">处理中</el-tag>
        <el-tag v-else-if="form.status === '2'" type="success">处理完成</el-tag>
        <el-tag v-else-if="form.status === '3'" type="danger">处理失败</el-tag>
        <el-tag v-else>未知状态</el-tag>
      </el-form-item>
      <el-form-item label="页数">
        {{ form.pageCount || '-' }}
      </el-form-item>
      <el-form-item label="文件大小">
        {{ formatFileSize(form.pdfSize) }}
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="form.remark" type="textarea" placeholder="请输入备注信息" :rows="2" />
      </el-form-item>
    </el-form>
    <div class="footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { getPdfFile, updatePdfFile } from "@/api/system/pdf";
import FileUpload from "@/components/FileUpload";

export default {
  name: "PdfEdit",
  components: {
    FileUpload
  },
  data() {
    return {
      // 表单数据
      form: {
        pdfId: undefined,
        pdfTitle: undefined,
        pdfDescription: undefined,
        pdfPath: undefined,
        pdfSize: undefined,
        pageCount: undefined,
        status: undefined,
        remark: undefined
      },
      // 表单校验
      rules: {
        pdfTitle: [
          { required: true, message: "PDF标题不能为空", trigger: "blur" }
        ]
      },
      // 显示文件上传组件
      showFileUpload: false,
      // 上传的文件路径
      uploadFilePath: []
    };
  },
  created() {
    this.getInfo();
  },
  methods: {
    /** 获取详情 */
    getInfo() {
      const pdfId = this.$route.params && this.$route.params.pdfId;
      if (pdfId) {
        getPdfFile(pdfId).then(response => {
          this.form = response;
        });
      }
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updatePdfFile(this.form).then(response => {
            if (response.code === 200) {
              this.$message.success('修改成功');
    this.$emit('close');
    this.$parent.getList();
            }
          });
        }
      });
    },
    /** 取消按钮 */
    cancel() {
      this.$emit('close');
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
        this.form.pdfSize = file.size;
        // 重置处理状态，重新上传后需要重新处理
        this.form.status = '0';
        this.form.pageCount = undefined;
      }
    },
    /** 获取基础URL */
    getBaseUrl() {
      return process.env.VUE_APP_BASE_API;
    },
    /** 格式化文件大小 */
    formatFileSize(bytes) {
      if (!bytes) return '0 B';
      const k = 1024;
      const sizes = ['B', 'KB', 'MB', 'GB', 'TB'];
      const i = Math.floor(Math.log(bytes) / Math.log(k));
      return (bytes / Math.pow(k, i)).toFixed(2) + ' ' + sizes[i];
    }
  }
};
</script>

<style scoped>
.footer {
  text-align: center;
  margin-top: 20px;
}
.file-info {
  display: flex;
  align-items: center;
  gap: 10px;
}
</style>