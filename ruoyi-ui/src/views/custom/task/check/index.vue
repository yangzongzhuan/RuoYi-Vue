<template>
  <div class="audit-wrapper">
    <div class="card">
      <h2>审核页面</h2>
      <div class="config-box">
        <pre class="json-preview" v-if="config">{{ formattedConfig }}</pre>
        <p v-else>加载中...</p>
      </div>
      <div class="button-box">
        <button class="confirm-btn" v-if="status != 3">已审核</button>
        <button @click="confirm" class="confirm-btn" v-else>确认</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { getTaskByUuid } from "@/api/custom/task";

export default {
  name: 'AuditPage',
  data() {
    return {
      uuid: null,
      config: null,
      status: null,
    }
  },
  computed: {
    formattedConfig() {
      try {
        let configObj = typeof this.config === 'string' ? JSON.parse(this.config) : this.config;
        return JSON.stringify(configObj, null, 2);
      } catch (e) {
        return this.config;
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
        this.config = response.data.config;
        this.status = response.data.status;
      });
    },
    confirm() {
      axios.post(`/api/confirm/${this.uuid}`)
        .then(() => alert('确认成功！'))
        .catch(err => {
          console.error('确认失败:', err);
          alert('确认失败：' + err);
        });
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
  position: relative;
  background: #f8f8f8;
  padding: 20px;
  border-radius: 6px;
  border: 1px solid #ddd;
  max-height: 500px;
  overflow: auto;
}

.json-preview {
  font-family: Menlo, Monaco, Consolas, monospace;
  font-size: 14px;
  white-space: pre-wrap;
  word-wrap: break-word;
  color: #333;
}

.button-box {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

.confirm-btn {
  background-color: #42b983;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
}

.confirm-btn:hover {
  background-color: #369d75;
}
</style>

