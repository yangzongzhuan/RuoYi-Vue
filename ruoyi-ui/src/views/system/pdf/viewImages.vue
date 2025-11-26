<template>
  <div class="app-container">
    <div class="info-card">
      <el-descriptions :column="1" border>
        <el-descriptions-item label="PDF标题">{{ pdfFile.pdfTitle }}</el-descriptions-item>
        <el-descriptions-item label="PDF描述">{{ pdfFile.pdfDescription }}</el-descriptions-item>
        <el-descriptions-item label="处理状态">
          <el-tag v-if="pdfFile.status === '0'" type="info">待处理</el-tag>
          <el-tag v-else-if="pdfFile.status === '1'" type="warning">处理中</el-tag>
          <el-tag v-else-if="pdfFile.status === '2'" type="success">处理完成</el-tag>
          <el-tag v-else-if="pdfFile.status === '3'" type="danger">处理失败</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="总页数">{{ pdfFile.pageCount || 0 }}</el-descriptions-item>
      </el-descriptions>
    </div>

    <div v-if="pdfFile.status === '2' && images && images.length > 0" class="images-container">
      <el-divider>转换结果 (共 {{ images.length }} 页)</el-divider>
      <el-row :gutter="20">
        <el-col :xs="24" :sm="12" :md="8" :lg="6" v-for="image in images" :key="image.imageId" class="image-item">
          <el-card shadow="hover">
            <div slot="header" class="image-header">
              <span>第 {{ image.pageNum }} 页</span>
              <el-button type="text" size="small" @click="downloadImage(image)">
                <i class="el-icon-download"></i> 下载
              </el-button>
            </div>
            <div class="image-content">
              <el-image
                :src="getImageUrl(image.imagePath)"
                :preview-src-list="getPreviewList()"
                fit="contain"
                @click="previewImage(image)"
                :preview-teleported="true"
              >
                <div slot="error" class="image-error">
                  <i class="el-icon-picture-outline"></i>
                </div>
              </el-image>
            </div>
            <div class="image-footer">
              <span>{{ formatFileSize(image.imageSize) }}</span>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <div v-else-if="pdfFile.status === '0'" class="status-tip">
      <el-empty description="PDF文件尚未开始处理，请等待..." />
    </div>

    <div v-else-if="pdfFile.status === '1'" class="status-tip">
      <el-empty description="PDF文件正在处理中，请稍候..." />
    </div>

    <div v-else-if="pdfFile.status === '3'" class="status-tip">
      <el-empty description="PDF文件处理失败" />
      <el-button type="primary" @click="handleReprocess" style="margin-top: 20px;">
        重新处理
      </el-button>
    </div>

    <div v-else class="status-tip">
      <el-empty description="暂无图片数据" />
    </div>

    <!-- 全页预览 -->
    <el-dialog :visible.sync="fullPagePreview" :title="'第 ' + currentPageNum + ' 页'" width="90%" append-to-body>
      <el-image
        :src="currentImageUrl"
        fit="contain"
        style="width: 100%; height: 70vh;"
      />
      <div slot="footer" class="dialog-footer">
        <el-button @click="prevPage">上一页</el-button>
        <el-button type="primary" @click="downloadCurrentImage">下载</el-button>
        <el-button @click="nextPage">下一页</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getPdfFile, reprocessPdfFile } from "@/api/system/pdf";

export default {
  name: "PdfViewImages",
  data() {
    return {
      // PDF文件信息
      pdfFile: {
        pdfId: undefined,
        pdfTitle: undefined,
        pdfDescription: undefined,
        status: undefined,
        pageCount: 0
      },
      // 图片列表
      images: [],
      // 全页预览
      fullPagePreview: false,
      currentPageNum: 1,
      currentImageUrl: ""
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
        // 获取PDF文件信息
        getPdfFile(pdfId).then(response => {
          this.pdfFile = response;
          // 如果有图片信息，解析图片数据
          if (response.images && Array.isArray(response.images)) {
            this.images = response.images;
          }
        });
        // 单独获取图片列表（为了更好的性能和错误处理）
        this.getImagesList(pdfId);
      }
    },
    /** 获取图片列表 */
    getImagesList(pdfId) {
      // 从API获取图片列表
      // 这里假设后端API在getPdfFile中已经返回了图片列表
      // 如果没有，可以单独调用一个API
      // 实际项目中可能需要单独的API
    },
    /** 获取图片URL */
    getImageUrl(imagePath) {
      if (!imagePath) return '';
      // 确保路径以/开头
      const path = imagePath.startsWith('/') ? imagePath : `/${imagePath}`;
      return `${process.env.VUE_APP_BASE_API}${path}`;
    },
    /** 获取预览列表 */
    getPreviewList() {
      return this.images.map(image => this.getImageUrl(image.imagePath));
    },
    /** 预览图片（全页模式） */
    previewImage(image) {
      this.currentPageNum = image.pageNum;
      this.currentImageUrl = this.getImageUrl(image.imagePath);
      this.fullPagePreview = true;
    },
    /** 上一页 */
    prevPage() {
      if (this.currentPageNum > 1) {
        this.currentPageNum--;
        this.updateCurrentImageUrl();
      }
    },
    /** 下一页 */
    nextPage() {
      if (this.currentPageNum < this.images.length) {
        this.currentPageNum++;
        this.updateCurrentImageUrl();
      }
    },
    /** 更新当前图片URL */
    updateCurrentImageUrl() {
      const currentImage = this.images.find(img => img.pageNum === this.currentPageNum);
      if (currentImage) {
        this.currentImageUrl = this.getImageUrl(currentImage.imagePath);
      }
    },
    /** 下载单个图片 */
    downloadImage(image) {
      const url = this.getImageUrl(image.imagePath);
      const fileName = `${this.pdfFile.pdfTitle}_page${image.pageNum}.png`;
      this.downloadFile(url, fileName);
    },
    /** 下载当前图片 */
    downloadCurrentImage() {
      this.downloadFile(this.currentImageUrl, `${this.pdfFile.pdfTitle}_page${this.currentPageNum}.png`);
    },
    /** 下载文件 */
    downloadFile(url, fileName) {
      const link = document.createElement('a');
      link.href = url;
      link.download = fileName;
      link.click();
    },
    /** 重新处理PDF */
    handleReprocess() {
      this.$confirm("是否重新处理该PDF文件?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        return reprocessPdfFile(this.pdfFile.pdfId);
      }).then(() => {
        this.msgSuccess("重新处理任务已开始");
        // 更新状态为处理中
        this.pdfFile.status = '1';
        // 3秒后刷新页面
        setTimeout(() => {
          this.getInfo();
        }, 3000);
      }).catch(() => {});
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
.info-card {
  margin-bottom: 20px;
}

.images-container {
  margin-top: 20px;
}

.image-item {
  margin-bottom: 20px;
}

.image-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.image-content {
  height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.image-content img {
  max-height: 100%;
  cursor: pointer;
}

.image-error {
  width: 100%;
  height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f5f7fa;
}

.image-footer {
  text-align: center;
  margin-top: 10px;
  color: #909399;
  font-size: 12px;
}

.status-tip {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 300px;
}
</style>