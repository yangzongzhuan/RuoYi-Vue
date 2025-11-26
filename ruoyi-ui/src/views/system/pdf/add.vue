<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="PDF标题" prop="pdfTitle">
        <el-input v-model="form.pdfTitle" placeholder="请输入PDF标题" />
      </el-form-item>
      <el-form-item label="PDF描述" prop="pdfDescription">
        <el-input v-model="form.pdfDescription" type="textarea" placeholder="请输入PDF描述" :rows="3" />
      </el-form-item>
      <el-form-item label="PDF文件" prop="pdfPath">
        <FileUpload
          v-model="form.pdfPath"
          :limit="1"
          :fileType="['pdf']"
          :fileSize="100"
          @change="handleFileChange"
        />
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
import { addPdfFile } from "@/api/system/pdf";
import FileUpload from "@/components/FileUpload";

export default {
  name: "PdfAdd",
  components: {
    FileUpload
  },
  data() {
    return {
      // 表单数据
      form: {
        pdfTitle: undefined,
        pdfDescription: undefined,
        pdfPath: undefined,
        pdfSize: undefined,
        remark: undefined
      },
      // 表单校验
      rules: {
        pdfTitle: [
          { required: true, message: "PDF标题不能为空", trigger: "blur" }
        ],
        pdfPath: [
          { required: true, message: "请上传PDF文件", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          addPdfFile(this.form).then(response => {
            if (response.code === 200) {
              this.$message.success('新增成功');
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
    /** 文件上传后处理 */
    handleFileChange(fileList) {
      if (fileList && fileList.length > 0) {
        const file = fileList[0];
        this.form.pdfPath = file.url;
        this.form.pdfSize = file.size;
      } else {
        this.form.pdfPath = undefined;
        this.form.pdfSize = undefined;
      }
    }
  }
};
</script>

<style scoped>
.footer {
  text-align: center;
  margin-top: 20px;
}
</style>