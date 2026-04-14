<template>
  <div>
    <el-popover ref="noticePopover" placement="bottom-end" width="320" trigger="manual" :value="noticeVisible" popper-class="notice-popover">
      <div class="notice-header">
        <span class="notice-title">通知公告</span>
        <span class="notice-mark-all" @click="markAllRead">全部已读</span>
      </div>
      <div v-if="noticeLoading" class="notice-loading"><i class="el-icon-loading"></i> 加载中...</div>
      <div v-else-if="noticeList.length === 0" class="notice-empty"><i class="el-icon-inbox"></i><br>暂无公告</div>
      <div v-else>
        <div v-for="item in noticeList" :key="item.noticeId" class="notice-item" :class="{ 'is-read': item.isRead }" @click="previewNotice(item)">
          <el-tag size="mini" :type="item.noticeType === '1' ? 'warning' : 'success'" class="notice-tag">
            {{ item.noticeType === '1' ? '通知' : '公告' }}
          </el-tag>
          <span class="notice-item-title">{{ item.noticeTitle }}</span>
          <span class="notice-item-date">{{ item.createTime }}</span>
        </div>
      </div>
    </el-popover>

    <div v-popover:noticePopover class="right-menu-item hover-effect notice-trigger" @mouseenter="onNoticeEnter" @mouseleave="onNoticeLeave">
      <svg-icon icon-class="bell" />
      <span v-if="unreadCount > 0" class="notice-badge">{{ unreadCount }}</span>
    </div>

    <notice-detail-view ref="noticeViewRef" />
  </div>
</template>

<script>
import NoticeDetailView from './DetailView'
import { listNoticeTop, markNoticeRead, markNoticeReadAll } from '@/api/system/notice'

export default {
  name: 'HeaderNotice',
  components: { NoticeDetailView },
  data() {
    return {
      noticeList: [], // 通知列表
      unreadCount: 0, // 未读数量
      noticeLoading: false, // 加载状态
      noticeVisible: false, // 弹出层显示状态
      noticeLeaveTimer: null // 鼠标离开计时器
    }
  },
  mounted() {
    this.loadNoticeTop()
  },
  methods: {
    // 鼠标移入铃铛区域
    onNoticeEnter() {
      clearTimeout(this.noticeLeaveTimer)
      this.noticeVisible = true
      this.$nextTick(() => {
        const popper = this.$refs.noticePopover.$refs.popper
        if (popper && !popper._noticeBound) {
          popper._noticeBound = true
          popper.addEventListener('mouseenter', () => clearTimeout(this.noticeLeaveTimer))
          popper.addEventListener('mouseleave', () => {
            this.noticeLeaveTimer = setTimeout(() => { this.noticeVisible = false }, 100)
          })
        }
      })
    },
    // 鼠标离开铃铛区域
    onNoticeLeave() {
      this.noticeLeaveTimer = setTimeout(() => { this.noticeVisible = false }, 150)
    },
    // 加载顶部公告列表
    loadNoticeTop() {
      this.noticeLoading = true
      listNoticeTop().then(res => {
        this.noticeList = res.data || []
        this.unreadCount = res.unreadCount !== undefined ? res.unreadCount : this.noticeList.filter(n => !n.isRead).length
      }).finally(() => {
        this.noticeLoading = false
      })
    },
    // 预览公告详情
    previewNotice(item) {
      if (!item.isRead) {
        markNoticeRead(item.noticeId).catch(() => {})
        item.isRead = true
        const idx = this.noticeList.indexOf(item)
        if (idx !== -1) this.$set(this.noticeList, idx, { ...item, isRead: true })
        this.unreadCount = Math.max(0, this.unreadCount - 1)
      }
      this.$refs.noticeViewRef.open(item.noticeId)
    },
    // 全部已读
    markAllRead() {
      const ids = this.noticeList.map(n => n.noticeId).join(',')
      if (!ids) return
      markNoticeReadAll(ids).catch(() => {})
      this.noticeList = this.noticeList.map(n => ({ ...n, isRead: true }))
      this.unreadCount = 0
    }
  }
}
</script>

<style lang="scss" scoped>
.notice-trigger {
  position: relative;
  transform: translateX(-6px);
  .svg-icon { width: 1.2em; height: 1.2em; vertical-align: -0.2em; }
  .notice-badge {
    position: absolute;
    top: 7px;
    right: -3px;
    background: #f56c6c;
    color: #fff;
    border-radius: 10px;
    font-size: 10px;
    height: 16px;
    line-height: 16px;
    padding: 0 4px;
    min-width: 16px;
    text-align: center;
    white-space: nowrap;
    pointer-events: none;
  }
}
.notice-popover {
  padding: 0 !important;
}
.notice-popover .notice-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 14px;
  background: #f7f9fb;
  border-bottom: 1px solid #eee;
  font-size: 13px;
  font-weight: 600;
  color: #333;
}
.notice-popover .notice-mark-all {
  font-size: 12px;
  color: #409EFF;
  font-weight: normal;
  cursor: pointer;
}
.notice-popover .notice-mark-all:hover { color: #2b7cc1; }
.notice-popover .notice-loading,
.notice-popover .notice-empty {
  padding: 24px;
  text-align: center;
  color: #bbb;
  font-size: 12px;
  line-height: 1.8;
}
.notice-popover .notice-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 14px;
  border-bottom: 1px solid #f5f5f5;
  cursor: pointer;
  transition: background 0.15s;
}
.notice-popover .notice-item:last-child { border-bottom: none; }
.notice-popover .notice-item:hover { background: #f7f9fb; }
.notice-popover .notice-item.is-read .notice-tag,
.notice-popover .notice-item.is-read .notice-item-title,
.notice-popover .notice-item.is-read .notice-item-date { opacity: 0.45; filter: grayscale(1); color: #999; }
.notice-popover .notice-tag { flex-shrink: 0; }
.notice-popover .notice-item-title {
  flex: 1;
  font-size: 12px;
  color: #333;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.notice-popover .notice-item-date {
  flex-shrink: 0;
  font-size: 11px;
  color: #bbb;
}
</style>
