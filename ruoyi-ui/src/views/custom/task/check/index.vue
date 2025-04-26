<template>
  <div class="audit-wrapper">
    <div class="card">
      <h2>审核页面</h2>
      <div class="config-box">
        <div v-if="editableConfig">
          <!-- 文案编辑卡片 -->
          <div class="config-card copywriter-card">
            <label class="field-label">文案：</label>
            <el-input
              type="textarea"
              :rows="6"
              v-model="editableConfig.copywriter"
            />
          </div>

          <!-- 图片配置卡片 -->
          <div
            class="config-card image-config-card"
            v-for="(imgCfg, idx) in editableConfig.imageConfigs"
            :key="idx"
          >
            <h3>配置 {{ idx + 1 }}：{{ imgCfg.folderName }}</h3>
            <div
              class="text-layer"
              v-for="(layerConfig, key) in imgCfg.textLayerConfigs"
              :key="key"
            >
              <label class="field-label">{{ layerConfig.name }}:</label>
              <el-input
                type="textarea"
                :autosize="{ minRows: 1, maxRows: 6 }"
                v-model="editableConfig.imageConfigs[idx].textLayerConfigs[key].sampleText"
              />
            </div>
          </div>
        </div>
        <p v-else>加载中...</p>
      </div>

      <div class="button-box">
        <el-button v-if="from.status == 2" type="primary" disabled>生成图片中</el-button>
        <el-button v-if="from.status == 0" type="success" disabled>已成功</el-button>
        <el-button v-if="from.status == 1" type="danger" disabled>生成失败</el-button>
        <el-button @click="confirm" type="primary" v-if="from.status == 3">确认</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { checkTask, getTaskByUuid } from '@/api/custom/task';

export default {
  name: 'AuditPage',
  data() {
    return {
      uuid: null,
      from: {},
      editableConfig: null,
    };
  },
  methods: {
    getUuid() {
      const urlParams = new URLSearchParams(window.location.search);
      this.uuid = urlParams.get('uuid');
    },
    fetchData() {
      if (!this.uuid) {
        alert('缺少 uuid 参数');
        return;
      }
      getTaskByUuid(this.uuid).then((response) => {
        this.from = response.data;
        try {
          this.editableConfig =
            typeof this.from.config === 'string'
              ? JSON.parse(this.from.config)
              : this.from.config;
        } catch (e) {
          this.editableConfig = this.from.config;
        }
      });
    },
    confirm() {
      this.$confirm('确认审核通过？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        this.from.config = JSON.stringify(this.editableConfig);
        checkTask(this.from)
          .then((response) => {
            if (response.code === 200) {
              this.fetchData();
              this.$message.success('审核成功！');
            }
          })
          .catch(() => {
            this.$message.error('审核失败！');
          });
      });
    },
  },
  mounted() {
    this.getUuid();
    this.fetchData();
  },
};
</script>

<style scoped>
.audit-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 100vh;
  padding: 0;
  margin: 0;
  box-sizing: border-box;
}

.card {
  width: 100%;
  height: 100%;
  max-width: none;
  background: #fff;
  box-shadow: none;
  border-radius: 0;
  padding: 20px;
  display: flex;
  flex-direction: column;
}

h2 {
  margin-bottom: 20px;
  font-weight: 600;
  font-size: 20px;
}

.config-box {
  background: #f8f8f8;
  padding: 20px;
  border-radius: 6px;
  border: 1px solid #ddd;
  flex: 1;
  overflow: auto;
  padding-bottom: 100px; /* 给底部按钮留出空间 */
}

.config-card {
  background: #ffffff;
  padding: 15px;
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  margin-bottom: 20px;
}

.copywriter-card {
  margin-bottom: 30px;
}

.field-label {
  display: block;
  font-weight: 500;
  margin-bottom: 6px;
}

.text-layer {
  margin-bottom: 12px;
}

.button-box {
  position: sticky;
  bottom: 0;
  display: flex;
  justify-content: flex-end;
  padding: 10px 20px;
  background: #fff;
  z-index: 10;
}
</style>
