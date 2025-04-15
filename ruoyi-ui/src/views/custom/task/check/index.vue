<template>
  <div class="audit-wrapper">
    <div class="card">
      <h2>审核页面</h2>
      <div class="config-box">
        <!-- 使用 vue-json-editor 实现可编辑 JSON -->
        <vue-json-editor v-if="editableConfig" v-model="editableConfig" :options="editorOptions" />
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
import VueJsonEditor from 'vue-json-editor';
import {checkTask, getTaskByUuid} from "@/api/custom/task";

export default {
  name: 'AuditPage',
  components: {
    VueJsonEditor
  },
  data() {
    return {
      uuid: null,
      from: {},
      editableConfig: null,
      editorOptions: {
        mode: 'code',        // 使用代码模式，支持 JSON 格式高亮和编辑
        mainMenuBar: true,   // 显示主菜单栏
        statusBar: false,    // 隐藏状态栏（根据需求调整）
      }
    }
  },
  computed: {
    formattedConfig() {
      try {
        let configObj = typeof this.from.config === 'string' ? JSON.parse(this.from.config) : this.from.config;
        return JSON.stringify(configObj, null, 2);
      } catch (e) {
        return this.from.config;
      }
    }
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
      getTaskByUuid(this.uuid).then(response => {
        this.from = response.data;
        // 将返回的 config 赋值到 editableConfig，以便编辑
        try {
          this.editableConfig = typeof this.from.config === 'string' ? JSON.parse(this.from.config) : this.from.config;
        } catch (e) {
          this.editableConfig = this.from.config;
        }
      });
    },
    confirm() {
      // 确认时可以将 editableConfig 再转换为字符串或直接传 JSON 对象到后端
      this.from.config = JSON.stringify(this.editableConfig);
      checkTask(this.from).then(response => {
        if (response.code === 200) {
          this.fetchData()
          this.$message.success("审核成功！")
        }
      }).catch(() => {
        this.$message.error("审核失败！")
      })
    }
  },
  mounted() {
    this.getUuid();
    this.fetchData();
  }
}
</script>

<style scoped>
.audit-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 80vh;
  padding: 20px;
  box-sizing: border-box;
}

.card {
  width: 80%;
  max-width: 1200px;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  padding: 20px;
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
  max-height: 500px;
  overflow: auto;
}

.button-box {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}


.confirm-btn:hover {
  background-color: #369d75;
}
</style>
