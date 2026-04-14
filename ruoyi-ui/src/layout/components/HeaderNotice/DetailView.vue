<template>
  <el-drawer title="公告详情" :visible.sync="visible" direction="rtl" size="50%" append-to-body :before-close="handleClose" custom-class="notice-detail-drawer">
    <div v-loading="loading" class="notice-detail-drawer__body">
      <div v-if="!detail" class="notice-empty">
        <i class="el-icon-document"></i>
        <span>暂无数据</span>
      </div>
      <div v-else class="notice-page">
        <div class="notice-type-wrap">
          <span v-if="detail.noticeType === '1'" class="notice-type-tag type-notify">
            <i class="el-icon-bell"></i> 通知
          </span>
          <span v-else-if="detail.noticeType === '2'" class="notice-type-tag type-announce">
            <i class="el-icon-message"></i> 公告
          </span>
          <span v-else class="notice-type-tag type-notify">
            <i class="el-icon-document"></i> 消息
          </span>
        </div>

        <h1 class="notice-title">{{ detail.noticeTitle }}</h1>

        <div class="notice-meta">
          <span class="meta-item">
            <i class="el-icon-user"></i>
            <span>{{ detail.createBy || '—' }}</span>
          </span>
          <span class="meta-item">
            <i class="el-icon-time"></i>
            <span>{{ detail.createTime || '—' }}</span>
          </span>
          <span class="meta-item">
            <span :class="['status-dot', isStatusNormal ? 'status-ok' : 'status-off']"></span>
            <span>{{ isStatusNormal ? '正常' : '已关闭' }}</span>
          </span>
        </div>

        <div class="notice-divider">
          <span class="notice-divider-dot"></span>
          <span class="notice-divider-dot"></span>
          <span class="notice-divider-dot"></span>
        </div>

        <div class="notice-body">
          <div v-if="hasContent" class="notice-content" v-html="detail.noticeContent" />
          <div v-else class="notice-empty notice-empty--inner">
            <i class="el-icon-document"></i> 暂无内容
          </div>
        </div>
      </div>
    </div>
  </el-drawer>
</template>

<script>
import { getNotice } from '@/api/system/notice'

export default {
  name: 'NoticeDetailView',
  data() {
    return {
      visible: false,
      loading: false,
      detail: null
    }
  },
  computed: {
    isStatusNormal() {
      const s = this.detail && this.detail.status
      return s === '0' || s === 0
    },
    hasContent() {
      const c = this.detail && this.detail.noticeContent
      return c != null && String(c).trim() !== ''
    }
  },
  methods: {
    open(payload) {
      let id = null
      let preset = null
      if (payload != null && typeof payload === 'object') {
        id = payload.noticeId
        if (payload.noticeContent != null) {
          preset = payload
        }
      } else {
        id = payload
      }
      this.visible = true
      if (preset) {
        this.detail = preset
        return
      }
      if (id == null || id === '') {
        this.detail = null
        return
      }
      this.loading = true
      this.detail = null
      getNotice(id).then(res => {
        this.detail = res.data
      }).catch(() => {
        this.detail = null
      }).finally(() => {
        this.loading = false
      })
    },
    handleClose() {
      this.visible = false
      this.detail = null
      this.loading = false
    }
  }
}
</script>

<style lang="scss" scoped>
.notice-page {
  max-width: 760px;
  margin: 0 auto;
  padding: 8px 8px 20px;
  animation: notice-fade-up 0.28s ease both;
}

@keyframes notice-fade-up {
  from {
    opacity: 0;
    transform: translateY(14px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.notice-type-tag {
  display: inline-flex;
  align-items: center;
  gap: 5px;
  padding: 3px 12px;
  border-radius: 2px;
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 1px;
  text-transform: uppercase;
  margin-bottom: 14px;
}

.type-notify {
  background: #fff8e6;
  color: #b7791f;
  border-left: 3px solid #d97706;
}

.type-announce {
  background: #e8f5e9;
  color: #276749;
  border-left: 3px solid #38a169;
}

.notice-title {
  font-size: 22px;
  font-weight: 700;
  color: #1a202c;
  line-height: 1.45;
  margin: 0 0 16px;
  letter-spacing: -0.2px;
}

.notice-meta {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 16px;
  padding: 12px 0;
  border-top: 1px solid #e9ecef;
  border-bottom: 1px solid #e9ecef;
  margin-bottom: 28px;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 5px;
  font-size: 12px;
  color: #718096;
}

.meta-item i {
  font-size: 12px;
  color: #a0aec0;
}

.status-dot {
  display: inline-block;
  width: 7px;
  height: 7px;
  border-radius: 50%;
  margin-right: 4px;
}

.status-ok {
  background: #38a169;
}

.status-off {
  background: #e53e3e;
}

.notice-divider {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 24px;
}

.notice-divider::before,
.notice-divider::after {
  content: '';
  flex: 1;
  height: 1px;
  background: linear-gradient(to right, transparent, #dee2e6, transparent);
}

.notice-divider-dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: #cbd5e0;
}

.notice-body {
  background: #fff;
  border-radius: 6px;
  padding: 28px 32px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.06), 0 0 0 1px rgba(0, 0, 0, 0.04);
  min-height: 120px;
}

.notice-content {
  font-size: 14px;
  line-height: 1.85;
  color: #2d3748;
  word-break: break-word;
}

.notice-content ::v-deep p {
  margin: 0 0 1em;
}

.notice-content ::v-deep h1,
.notice-content ::v-deep h2,
.notice-content ::v-deep h3 {
  font-weight: 700;
  color: #1a202c;
  margin: 1.4em 0 0.6em;
}

.notice-content ::v-deep h1 {
  font-size: 18px;
}

.notice-content ::v-deep h2 {
  font-size: 16px;
}

.notice-content ::v-deep h3 {
  font-size: 14px;
}

.notice-content ::v-deep a {
  color: #3182ce;
  text-decoration: underline;
}

.notice-content ::v-deep a:hover {
  color: #2b6cb0;
}

.notice-content ::v-deep img {
  max-width: 100%;
  border-radius: 4px;
  margin: 8px 0;
}

.notice-content ::v-deep ul,
.notice-content ::v-deep ol {
  padding-left: 20px;
  margin: 0 0 1em;
}

.notice-content ::v-deep li {
  margin-bottom: 4px;
}

.notice-content ::v-deep blockquote {
  border-left: 3px solid #cbd5e0;
  margin: 1em 0;
  padding: 6px 16px;
  color: #718096;
  background: #f7fafc;
}

.notice-content ::v-deep table {
  border-collapse: collapse;
  width: 100%;
  margin: 1em 0;
  font-size: 13px;
}

.notice-content ::v-deep table th,
.notice-content ::v-deep table td {
  border: 1px solid #e2e8f0;
  padding: 7px 12px;
}

.notice-content ::v-deep table th {
  background: #f7fafc;
  font-weight: 600;
}

.notice-empty {
  text-align: center;
  padding: 40px 0;
  color: #a0aec0;
  font-size: 13px;
}

.notice-empty i {
  font-size: 28px;
  display: block;
  margin-bottom: 10px;
}

.notice-empty--inner {
  padding: 32px 0;
}

.notice-empty--inner i {
  font-size: 28px;
}

::v-deep .notice-detail-drawer {
  .el-drawer__header {
    margin-bottom: 0;
    padding: 16px 20px;
    border-bottom: 1px solid #ebeef5;
    font-size: 16px;
    font-weight: 600;
    color: #303133;
  }
  .el-drawer__body {
    background: #f5f6f8;
  }
}

.notice-detail-drawer__body {
  height: 100%;
  overflow: auto;
  padding: 10px 16px 22px;
}
</style>
