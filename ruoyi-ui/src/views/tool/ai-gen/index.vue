<template>
  <div class="app-container ai-gen-container">
    <!-- 顶部配置栏 -->
    <div class="config-bar">
      <el-form :inline="true" size="small">
        <el-form-item label="BaseURL">
          <el-input v-model="config.baseUrl" placeholder="请输入BaseURL" style="width: 280px">
            <el-dropdown slot="append" @command="onBaseUrlPreset" trigger="click">
              <el-button icon="el-icon-arrow-down"></el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="https://api.xiaomimimo.com">MiMo</el-dropdown-item>
                <el-dropdown-item command="https://0101.run">0101</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </el-input>
        </el-form-item>
        <el-form-item label="APIKey">
          <el-input v-model="config.apiKey" placeholder="请输入APIKey" show-password style="width: 240px" />
        </el-form-item>
        <el-form-item label="接口协议">
          <el-select v-model="config.modelType" placeholder="请选择" style="width: 160px" @change="onModelTypeChange">
            <el-option v-for="item in modelTypes" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="大模型">
          <el-input v-model="config.modelName" placeholder="模型名称" style="width: 200px" />
        </el-form-item>
        <el-form-item>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="clearChat">清空对话</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 聊天区域 -->
    <div class="chat-area" ref="chatArea">
      <div v-if="messages.length === 0" class="empty-tip">
        <i class="el-icon-chat-dot-round" style="font-size: 48px; color: #c0c4cc"></i>
        <p style="color: #909399; margin-top: 12px">开始与 AI 对话，进行代码生成和开发辅助</p>
      </div>

      <div v-for="(msg, idx) in messages" :key="idx" class="chat-message" :class="'msg-' + msg.type">
        <!-- 用户消息 -->
        <div v-if="msg.type === 'user'" class="msg-user">
          <div class="msg-avatar">
            <i class="el-icon-user-solid"></i>
          </div>
          <div class="msg-bubble user-bubble">
            <div class="msg-content" v-html="renderMarkdown(msg.content)"></div>
          </div>
        </div>

        <!-- AI 回复 -->
        <div v-else-if="msg.type === 'assistant'" class="msg-assistant">
          <div class="msg-avatar ai-avatar">
            <i class="el-icon-magic-stick"></i>
          </div>
          <div class="msg-bubble ai-bubble">
            <!-- 思考过程 -->
            <div v-if="msg.thinking" class="thinking-block">
              <div class="thinking-header" @click="msg.thinkingExpanded = !msg.thinkingExpanded">
                <i class="el-icon-loading" v-if="msg.streaming"></i>
                <i :class="msg.thinkingExpanded ? 'el-icon-arrow-down' : 'el-icon-arrow-right'" v-else></i>
                <span>思考过程</span>
              </div>
              <div v-show="msg.thinkingExpanded" class="thinking-content">{{ msg.thinking }}</div>
            </div>
            <!-- 工具调用 -->
            <div v-if="msg.tools && msg.tools.length > 0" class="tools-block">
              <div v-for="(tool, tIdx) in msg.tools" :key="tIdx" class="tool-item">
                <div class="tool-header" @click="tool.expanded = !tool.expanded">
                  <i :class="toolStatusIcon(tool.status)"></i>
                  <span class="tool-name">{{ tool.name }}</span>
                  <span class="tool-desc">{{ tool.description }}</span>
                  <i :class="tool.expanded ? 'el-icon-arrow-down' : 'el-icon-arrow-right'" class="expand-icon"></i>
                </div>
                <div v-show="tool.expanded && tool.params" class="tool-params">
                  <pre><code>{{ formatJson(tool.params) }}</code></pre>
                </div>
              </div>
            </div>
            <!-- 计划 -->
            <div v-if="msg.plans && msg.plans.length > 0" class="plans-block">
              <div v-for="(plan, pIdx) in msg.plans" :key="pIdx" class="plan-item">
                <div class="plan-header" @click="plan.expanded = !plan.expanded">
                  <i class="el-icon-s-claim"></i>
                  <span class="plan-name">{{ plan.name }}</span>
                  <span v-if="plan.description" class="plan-desc">{{ plan.description }}</span>
                  <el-tag size="mini" :type="plan.completed ? 'success' : 'warning'" style="margin-left: 8px">
                    {{ plan.completed ? '已完成' : '执行中' }}
                  </el-tag>
                  <i :class="plan.expanded ? 'el-icon-arrow-down' : 'el-icon-arrow-right'" class="expand-icon"></i>
                </div>
                <div v-show="plan.expanded" class="plan-detail">
                  <!-- 计划步骤 -->
                  <div v-if="plan.steps && plan.steps.length > 0" class="plan-steps">
                    <div v-for="(step, sIdx) in plan.steps" :key="sIdx" class="plan-step" :class="stepStatusClass(step)">
                      <span class="step-num">{{ sIdx + 1 }}</span>
                      <span class="step-text">{{ step }}</span>
                      <span v-if="plan.stepResults && plan.stepResults[sIdx]" class="step-result">
                        {{ plan.stepResults[sIdx] }}
                      </span>
                      <i v-if="plan.currentStep > sIdx" class="el-icon-success step-done"></i>
                      <i v-else-if="plan.currentStep === sIdx" class="el-icon-loading step-running"></i>
                    </div>
                  </div>
                  <!-- 计划内的工具调用 -->
                  <div v-if="plan.tools && plan.tools.length > 0" class="plan-tools">
                    <div v-for="(tool, ptIdx) in plan.tools" :key="ptIdx" class="tool-item mini">
                      <i :class="toolStatusIcon(tool.status)"></i>
                      <span class="tool-name">{{ tool.toolName }}</span>
                      <span class="tool-desc">{{ tool.toolDesc }}</span>
                    </div>
                  </div>
                  <!-- 计划步骤错误 -->
                  <div v-if="plan.errors && plan.errors.length > 0" class="plan-errors">
                    <div v-for="(err, eIdx) in plan.errors" :key="eIdx" class="plan-error">
                      <i class="el-icon-error"></i>
                      <span>步骤 {{ err.current + 1 }}: {{ err.error }}</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- 子代理 -->
            <div v-if="msg.subAgents && msg.subAgents.length > 0" class="subagents-block">
              <div v-for="(agent, aIdx) in msg.subAgents" :key="aIdx" class="subagent-item">
                <div class="subagent-header" @click="agent.expanded = !agent.expanded">
                  <i class="el-icon-s-custom"></i>
                  <span class="agent-name">{{ agent.name }}</span>
                  <span v-if="agent.task" class="agent-task">{{ agent.task }}</span>
                  <el-tag size="mini" :type="agent.result ? 'success' : 'warning'" style="margin-left: 8px">
                    {{ agent.result ? '已完成' : '执行中' }}
                  </el-tag>
                  <i :class="agent.expanded ? 'el-icon-arrow-down' : 'el-icon-arrow-right'" class="expand-icon"></i>
                </div>
                <div v-show="agent.expanded" class="subagent-detail">
                  <div v-if="agent.description" class="agent-desc">{{ agent.description }}</div>
                  <div v-if="agent.message" class="agent-message">{{ agent.message }}</div>
                  <div v-if="agent.result" class="agent-result">
                    <pre><code>{{ agent.result }}</code></pre>
                  </div>
                </div>
              </div>
            </div>
            <!-- AI 文本内容 -->
            <div v-if="msg.content" class="msg-content markdown-body" v-html="renderMarkdown(msg.content)"></div>
            <!-- 流式光标 -->
            <span v-if="msg.streaming && msg.content" class="typing-cursor"></span>
          </div>
        </div>

        <!-- 错误消息 -->
        <div v-else-if="msg.type === 'error'" class="msg-error">
          <i class="el-icon-error"></i>
          <span>{{ msg.content }}</span>
        </div>
      </div>
    </div>

    <!-- 输入区域 -->
    <div class="input-area">
      <div class="input-wrapper">
        <el-input
          ref="inputBox"
          v-model="inputMessage"
          type="textarea"
          :autosize="{ minRows: 1, maxRows: 6 }"
          placeholder="输入消息... (Enter 发送, Shift+Enter 换行)"
          @keydown.native="handleKeydown"
          :disabled="loading"
        />
        <el-button
          class="send-btn"
          :type="loading ? 'danger' : 'primary'"
          :icon="loading ? 'el-icon-video-pause' : 'el-icon-s-promotion'"
          circle
          size="small"
          @click="loading ? stopChat() : sendMessage()"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { getToken } from '@/utils/auth'
import { getModelTypes } from '@/api/tool/aiGen'
import { fetchEventSource } from '@microsoft/fetch-event-source'
import { marked } from 'marked'
import hljs from 'highlight.js'

// 配置 marked 使用 highlight.js
marked.setOptions({
  highlight: function(code, lang) {
    if (lang && hljs.getLanguage(lang)) {
      try { return hljs.highlight(code, { language: lang }).value } catch (e) { /* ignore */ }
    }
    try { return hljs.highlightAuto(code).value } catch (e) { /* ignore */ }
    return code
  },
  breaks: true,
  gfm: true
})

const STORAGE_KEY_CONFIG = 'ai-gen-config'
const STORAGE_KEY_MESSAGES = 'ai-gen-messages'
const MAX_HISTORY = 5

export default {
  name: 'AiGen',
  data() {
    return {
      messages: [],
      inputMessage: '',
      loading: false,
      abortController: null,
      currentMsg: null,
      config: {
        baseUrl: 'https://api.xiaomimimo.com',
        apiKey: '',
        modelType: '',
        modelName: ''
      },
      modelTypes: []
    }
  },
  created() {
    this.loadConfig()
    this.loadModelTypes()
    this.loadMessages()
  },
  beforeDestroy() {
    this.stopChat()
  },
  methods: {
    /** 加载缓存的配置 */
    loadConfig() {
      const saved = localStorage.getItem(STORAGE_KEY_CONFIG)
      if (saved) {
        try { Object.assign(this.config, JSON.parse(saved)) } catch (e) { /* ignore */ }
      }
    },
    /** 保存配置到缓存 */
    saveConfig() {
      localStorage.setItem(STORAGE_KEY_CONFIG, JSON.stringify(this.config))
    },
    /** 加载缓存的聊天记录 */
    loadMessages() {
      const saved = localStorage.getItem(STORAGE_KEY_MESSAGES)
      if (saved) {
        try {
          const parsed = JSON.parse(saved)
          // Vue 2 需要声明式属性才能响应式, 从 localStorage 反序列化的对象缺少这些属性
          this.messages = parsed.map(m => this.normalizeMessage(m))
        } catch (e) { /* ignore */ }
      }
    },
    /** 规范化消息对象, 确保 Vue 2 响应式属性完整 */
    normalizeMessage(m) {
      if (m.type === 'assistant') {
        return {
          ...m,
          thinkingExpanded: m.thinkingExpanded || false,
          tools: (m.tools || []).map(t => ({ ...t, expanded: t.expanded || false })),
          plans: (m.plans || []).map(p => ({
            ...p,
            expanded: p.expanded !== undefined ? p.expanded : true,
            stepResults: p.stepResults || [],
            tools: (p.tools || []).map(t => ({ ...t })),
            errors: (p.errors || []).map(e => ({ ...e }))
          })),
          subAgents: (m.subAgents || []).map(a => ({ ...a, expanded: a.expanded || false }))
        }
      }
      return m
    },
    /** 保存聊天记录到缓存 */
    saveMessages() {
      localStorage.setItem(STORAGE_KEY_MESSAGES, JSON.stringify(this.messages))
    },
    /** 从后端获取模型类型列表 */
    loadModelTypes() {
      getModelTypes().then(res => {
        this.modelTypes = res.data || res
        if (!this.config.modelType && this.modelTypes.length > 0) {
          this.config.modelType = this.modelTypes[0].value
        }
      }).catch(() => {})
    },
    /** 模型类型变更 */
    onModelTypeChange() {
      this.saveConfig()
    },
    /** 选择 BaseURL 预设 */
    onBaseUrlPreset(url) {
      this.config.baseUrl = url
    },
    /** 清空对话 */
    clearChat() {
      this.$confirm('确认清空所有对话记录?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.messages = []
        this.saveMessages()
      }).catch(() => {})
    },
    /** 渲染 Markdown */
    renderMarkdown(text) {
      if (!text) return ''
      try { return marked(text) } catch (e) { return text }
    },
    /** 格式化 JSON */
    formatJson(obj) {
      if (!obj) return ''
      try { return JSON.stringify(obj, null, 2) } catch (e) { return String(obj) }
    },
    /** 工具状态图标 */
    toolStatusIcon(status) {
      if (status === 'SUCCESS' || status === 'COMPLETED') return 'el-icon-success'
      if (status === 'RUNNING' || status === 'EXECUTING') return 'el-icon-loading'
      if (status === 'ERROR' || status === 'FAILED') return 'el-icon-error'
      return 'el-icon-info'
    },
    /** 步骤状态样式 */
    stepStatusClass(step) {
      // step is just a string, status comes from plan state
      return ''
    },
    /** 键盘事件 */
    handleKeydown(e) {
      if (e.key === 'Enter' && !e.shiftKey) {
        e.preventDefault()
        if (!this.loading) this.sendMessage()
      }
    },
    /** 发送消息 */
    sendMessage() {
      const text = this.inputMessage.trim()
      if (!text) return
      if (!this.config.apiKey) {
        this.$message.warning('请先填写 APIKey')
        return
      }
      if (!this.config.modelType) {
        this.$message.warning('请先选择接口协议')
        return
      }

      this.saveConfig()

      // 添加用户消息
      this.messages.push({ type: 'user', content: text })
      this.inputMessage = ''
      this.loading = true

      // 构建最近 N 轮历史对话
      const history = []
      const recentMsgs = this.messages.slice(-(MAX_HISTORY * 2 + 1))
      for (let i = 0; i < recentMsgs.length - 1; i++) {
        const m = recentMsgs[i]
        if (m.type === 'user') {
          history.push({ role: 'user', content: m.content })
        } else if (m.type === 'assistant' && m.content) {
          history.push({ role: 'assistant', content: m.content })
        }
      }

      const body = {
        baseUrl: this.config.baseUrl,
        apiKey: this.config.apiKey,
        modelType: this.config.modelType,
        modelName: this.config.modelName,
        message: text,
        history: history
      }

      this.$nextTick(() => this.scrollToBottom())
      this.startSSE(body)
    },
    /** 启动 SSE 连接 */
    startSSE(body) {
      this.abortController = new AbortController()

      // 创建 AI 回复消息对象
      this.currentMsg = {
        type: 'assistant',
        content: '',
        thinking: '',
        thinkingExpanded: false,
        tools: [],
        plans: [],
        subAgents: [],
        streaming: true
      }
      this.messages.push(this.currentMsg)

      const url = process.env.VUE_APP_BASE_API + '/tool/ai-gen/chat'

      fetchEventSource(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + getToken()
        },
        body: JSON.stringify(body),
        signal: this.abortController.signal,
        openWhenHidden: true,
        onmessage: (event) => {
          this.handleSSEEvent(event.event, event.data)
        },
        onerror: (err) => {
          this.onStreamError(err.message || '连接异常')
          throw err // 抛出让库停止重试
        },
        onclose: () => {
          this.onStreamEnd()
        }
      })
    },
    /** 处理单个 SSE 事件 */
    handleSSEEvent(eventName, data) {
      if (!this.currentMsg) return

      try {
        switch (eventName) {
          case 'message': {
            const text = JSON.parse(data)
            this.currentMsg.content += text
            this.scrollToBottom()
            break
          }
          case 'think': {
            const text = JSON.parse(data)
            this.currentMsg.thinking += text
            this.currentMsg.thinkingExpanded = true
            break
          }
          case 'tool': {
            const info = JSON.parse(data)
            const lastTool = this.currentMsg.tools.length > 0
              ? this.currentMsg.tools[this.currentMsg.tools.length - 1] : null
            if (lastTool && lastTool.name === info.name && lastTool.status !== 'SUCCESS' && lastTool.status !== 'COMPLETED' && lastTool.status !== 'ERROR' && lastTool.status !== 'FAILED') {
              lastTool.status = info.status
              if (info.params) lastTool.params = info.params
            } else {
              this.currentMsg.tools.push({
                name: info.name,
                description: info.description,
                status: info.status,
                params: info.params,
                expanded: false
              })
            }
            this.scrollToBottom()
            break
          }
          case 'planCreated': {
            const info = JSON.parse(data)
            this.currentMsg.plans.push({
              name: info.name,
              description: info.description,
              steps: info.steps || [],
              stepResults: [],
              currentStep: -1,
              completed: false,
              tools: [],
              errors: [],
              expanded: true
            })
            this.scrollToBottom()
            break
          }
          case 'planExecuted': {
            const info = JSON.parse(data)
            const plan = this.findPlan(info.name)
            if (plan) plan.completed = true
            break
          }
          case 'planStepStart': {
            const info = JSON.parse(data)
            const plan = this.findPlan(info.planName)
            if (plan) plan.currentStep = info.current
            this.scrollToBottom()
            break
          }
          case 'planStepComplete': {
            const info = JSON.parse(data)
            const plan = this.findPlan(info.planName)
            if (plan) {
              if (!plan.stepResults) plan.stepResults = []
              plan.stepResults[info.current] = info.result
              plan.currentStep = info.current + 1
            }
            break
          }
          case 'planStepTool': {
            const info = JSON.parse(data)
            const plan = this.findPlan(info.planName)
            if (plan) {
              const lastPlanTool = plan.tools.length > 0
                ? plan.tools[plan.tools.length - 1] : null
              if (lastPlanTool && lastPlanTool.toolName === info.toolName && lastPlanTool.status !== 'SUCCESS' && lastPlanTool.status !== 'COMPLETED' && lastPlanTool.status !== 'ERROR' && lastPlanTool.status !== 'FAILED') {
                lastPlanTool.status = info.status
              } else {
                plan.tools.push({
                  toolName: info.toolName,
                  toolDesc: info.toolDesc,
                  status: info.status
                })
              }
            }
            break
          }
          case 'planStepError': {
            const info = JSON.parse(data)
            const plan = this.findPlan(info.planName)
            if (plan) {
              plan.errors.push({
                current: info.current,
                step: info.step,
                error: info.error
              })
            }
            break
          }
          case 'subAgent': {
            const info = JSON.parse(data)
            this.currentMsg.subAgents.push({
              name: info.name,
              description: info.description,
              message: info.message,
              task: '',
              result: '',
              expanded: false
            })
            break
          }
          case 'subAgentStart': {
            const info = JSON.parse(data)
            const agent = this.findSubAgent(info.name)
            if (agent) agent.task = info.task
            break
          }
          case 'subAgentResult': {
            const info = JSON.parse(data)
            const agent = this.findSubAgent(info.name)
            if (agent) agent.result = info.result
            break
          }
          case 'error': {
            this.messages.push({ type: 'error', content: data })
            break
          }
          case 'done': {
            // onStreamEnd 由 onclose 回调触发，此处不重复调用
            break
          }
        }
      } catch (e) {
        console.warn('SSE event parse error:', eventName, e)
      }
    },
    /** 查找计划 */
    findPlan(name) {
      if (!this.currentMsg || !this.currentMsg.plans) return null
      return this.currentMsg.plans.find(p => p.name === name)
    },
    /** 查找子代理 */
    findSubAgent(name) {
      if (!this.currentMsg || !this.currentMsg.subAgents) return null
      return this.currentMsg.subAgents.find(a => a.name === name)
    },
    /** 流结束 */
    onStreamEnd() {
      if (!this.loading) return // 防止重复调用
      if (this.currentMsg) {
        this.currentMsg.streaming = false
      }
      this.loading = false
      this.currentMsg = null
      this.abortController = null
      this.saveMessages()
      this.scrollToBottom()
      this.$nextTick(() => {
        if (this.$refs.inputBox) this.$refs.inputBox.focus()
      })
    },
    /** 流错误 */
    onStreamError(msg) {
      if (this.currentMsg) {
        this.currentMsg.streaming = false
      }
      this.messages.push({ type: 'error', content: msg })
      this.loading = false
      this.currentMsg = null
      this.abortController = null
      this.saveMessages()
    },
    /** 停止对话 */
    stopChat() {
      if (this.abortController) {
        this.abortController.abort()
        this.abortController = null
      }
      if (this.currentMsg) {
        this.currentMsg.streaming = false
        this.currentMsg = null
      }
      this.loading = false
      this.saveMessages()
    },
    /** 滚动到底部 */
    scrollToBottom() {
      this.$nextTick(() => {
        const el = this.$refs.chatArea
        if (el) el.scrollTop = el.scrollHeight
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.ai-gen-container {
  display: flex;
  flex-direction: column;
  height: calc(100vh - 84px);
  padding: 0;
  background: #f5f7fa;
}

/* 配置栏 */
.config-bar {
  background: #fff;
  padding: 10px 16px;
  border-bottom: 1px solid #e6e6e6;
  ::v-deep .el-form-item {
    margin-bottom: 0;
    margin-right: 12px;
  }
}

/* 聊天区域 */
.chat-area {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  scroll-behavior: smooth;
}

.empty-tip {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
}

/* 消息样式 */
.chat-message {
  margin-bottom: 20px;
}

.msg-user {
  display: flex;
  flex-direction: row-reverse;
  align-items: flex-start;
}

.msg-assistant {
  display: flex;
  align-items: flex-start;
}

.msg-avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background: #409eff;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  flex-shrink: 0;
}

.ai-avatar {
  background: #67c23a;
}

.msg-bubble {
  max-width: 78%;
  padding: 12px 16px;
  border-radius: 8px;
  line-height: 1.6;
  word-break: break-word;
}

.user-bubble {
  background: #409eff;
  color: #fff;
  margin-right: 12px;
  border-top-right-radius: 2px;
  ::v-deep {
    p { color: #fff; }
    code { background: rgba(255,255,255,0.2); color: #fff; }
    pre code { background: #282c34; color: #abb2bf; }
  }
}

.ai-bubble {
  background: #fff;
  color: #303133;
  margin-left: 12px;
  border: 1px solid #e4e7ed;
  border-top-left-radius: 2px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.06);
}

/* 消息内容 Markdown */
.msg-content {
  font-size: 14px;
  ::v-deep {
    p { margin: 6px 0; }
    pre {
      background: #282c34;
      color: #abb2bf;
      padding: 12px;
      border-radius: 6px;
      overflow-x: auto;
      margin: 8px 0;
      code { background: none; color: inherit; padding: 0; }
    }
    code {
      background: #f0f2f5;
      color: #e6a23c;
      padding: 2px 6px;
      border-radius: 3px;
      font-size: 13px;
    }
    blockquote {
      border-left: 4px solid #409eff;
      padding-left: 12px;
      margin: 8px 0;
      color: #909399;
    }
    table {
      border-collapse: collapse;
      margin: 8px 0;
      th, td {
        border: 1px solid #ebeef5;
        padding: 8px 12px;
        text-align: left;
      }
      th { background: #f5f7fa; }
    }
    ul, ol { padding-left: 20px; margin: 6px 0; }
    h1, h2, h3, h4, h5, h6 { margin: 12px 0 6px; }
    img { max-width: 100%; }
    a { color: #409eff; }
  }
}

/* 思考块 */
.thinking-block {
  margin-bottom: 10px;
  border: 1px solid #ebeef5;
  border-radius: 6px;
  overflow: hidden;
}

.thinking-header {
  padding: 8px 12px;
  background: #fdf6ec;
  color: #e6a23c;
  cursor: pointer;
  font-size: 13px;
  display: flex;
  align-items: center;
  gap: 6px;
  user-select: none;
  &:hover { background: #faecd8; }
}

.thinking-content {
  padding: 10px 12px;
  background: #fffbe6;
  font-size: 13px;
  color: #909399;
  white-space: pre-wrap;
  max-height: 300px;
  overflow-y: auto;
}

/* 工具调用 */
.tools-block {
  margin-bottom: 10px;
}

.tool-item {
  border: 1px solid #e4e7ed;
  border-radius: 6px;
  margin-bottom: 6px;
  overflow: hidden;
  &.mini {
    border: none;
    padding: 4px 0;
    font-size: 13px;
    display: flex;
    align-items: center;
    gap: 6px;
  }
}

.tool-header {
  padding: 8px 12px;
  background: #f0f9ff;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 13px;
  user-select: none;
  &:hover { background: #e1f3ff; }
}

.tool-name {
  font-weight: 600;
  color: #409eff;
}

.tool-desc {
  color: #909399;
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.expand-icon {
  color: #c0c4cc;
  margin-left: auto;
}

.tool-params {
  padding: 8px 12px;
  background: #fafafa;
  pre {
    margin: 0;
    font-size: 12px;
    color: #606266;
    white-space: pre-wrap;
    word-break: break-all;
  }
}

/* 计划 */
.plans-block {
  margin-bottom: 10px;
}

.plan-item {
  border: 1px solid #e4e7ed;
  border-radius: 6px;
  margin-bottom: 8px;
  overflow: hidden;
}

.plan-header {
  padding: 8px 12px;
  background: #f0f9eb;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 13px;
  user-select: none;
  &:hover { background: #e1f3d8; }
}

.plan-name {
  font-weight: 600;
  color: #67c23a;
}

.plan-desc {
  color: #909399;
}

.plan-detail {
  padding: 10px 12px;
}

.plan-steps {
  margin-bottom: 8px;
}

.plan-step {
  display: flex;
  align-items: flex-start;
  gap: 8px;
  padding: 6px 0;
  font-size: 13px;
  border-bottom: 1px dashed #ebeef5;
  &:last-child { border-bottom: none; }
}

.step-num {
  width: 22px;
  height: 22px;
  border-radius: 50%;
  background: #e4e7ed;
  color: #606266;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  flex-shrink: 0;
}

.step-text {
  flex: 1;
  color: #303133;
}

.step-result {
  color: #909399;
  font-size: 12px;
}

.step-done {
  color: #67c23a;
  margin-top: 3px;
}

.step-running {
  color: #409eff;
  margin-top: 3px;
}

.plan-tools {
  margin-top: 8px;
  padding-top: 8px;
  border-top: 1px solid #ebeef5;
}

.plan-errors {
  margin-top: 8px;
}

.plan-error {
  color: #f56c6c;
  font-size: 13px;
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 4px 0;
}

/* 子代理 */
.subagents-block {
  margin-bottom: 10px;
}

.subagent-item {
  border: 1px solid #e4e7ed;
  border-radius: 6px;
  margin-bottom: 6px;
  overflow: hidden;
}

.subagent-header {
  padding: 8px 12px;
  background: #f4f4f5;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 13px;
  user-select: none;
  &:hover { background: #e9e9eb; }
}

.agent-name {
  font-weight: 600;
  color: #909399;
}

.agent-task {
  color: #606266;
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.subagent-detail {
  padding: 10px 12px;
  font-size: 13px;
}

.agent-desc {
  color: #909399;
  margin-bottom: 6px;
}

.agent-message {
  color: #606266;
  margin-bottom: 6px;
}

.agent-result {
  pre {
    background: #fafafa;
    padding: 8px;
    border-radius: 4px;
    margin: 0;
    font-size: 12px;
    white-space: pre-wrap;
  }
}

/* 错误消息 */
.msg-error {
  background: #fef0f0;
  color: #f56c6c;
  padding: 10px 16px;
  border-radius: 6px;
  border: 1px solid #fbc4c4;
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 13px;
}

/* 打字光标 */
.typing-cursor {
  display: inline-block;
  width: 2px;
  height: 16px;
  background: #303133;
  margin-left: 2px;
  vertical-align: text-bottom;
  animation: blink 1s step-end infinite;
}

@keyframes blink {
  50% { opacity: 0; }
}

/* 输入区域 */
.input-area {
  background: #fff;
  padding: 12px 20px 16px;
  border-top: 1px solid #e6e6e6;
}

.input-wrapper {
  display: flex;
  align-items: flex-end;
  gap: 10px;
  ::v-deep .el-textarea__inner {
    border-radius: 8px;
    resize: none;
    padding: 10px 14px;
    font-size: 14px;
  }
}

.send-btn {
  flex-shrink: 0;
  width: 36px;
  height: 36px;
}
</style>
