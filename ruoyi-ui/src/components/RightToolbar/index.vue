<template>
  <div class="top-right-btn" :style="style">
    <el-row>
      <el-tooltip class="item" effect="dark" :content="showSearch ? '隐藏搜索' : '显示搜索'" placement="top" v-if="search">
        <el-button size="mini" circle icon="el-icon-search" @click="toggleSearch()" />
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="刷新" placement="top">
        <el-button size="mini" circle icon="el-icon-refresh" @click="refresh()" />
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="显隐列" placement="top" v-if="Object.keys(columns).length > 0">
        <el-button size="mini" circle icon="el-icon-menu" @click="showColumn()" v-if="showColumnsType == 'transfer'"/>
        <el-dropdown trigger="click" :hide-on-click="false" style="padding-left: 12px" v-if="showColumnsType == 'checkbox'">
          <el-button size="mini" circle icon="el-icon-menu" />
          <el-dropdown-menu slot="dropdown">
            <!-- 全选/反选 按钮 -->
            <el-dropdown-item>
              <el-checkbox :indeterminate="isIndeterminate" v-model="isChecked" @change="toggleCheckAll"> 列展示 </el-checkbox>
            </el-dropdown-item>
            <div class="check-line"></div>
            <template v-for="(item, key) in columns">
              <el-dropdown-item :key="key">
                <el-checkbox v-model="item.visible" @change="checkboxChange($event, key)" :label="item.label" />
              </el-dropdown-item>
            </template>
          </el-dropdown-menu>
        </el-dropdown>
      </el-tooltip>
    </el-row>
    <el-dialog :title="title" :visible.sync="open" append-to-body>
      <el-transfer
        :titles="['显示', '隐藏']"
        v-model="value"
        :data="transferData"
        @change="dataChange"
      ></el-transfer>
    </el-dialog>
  </div>
</template>

<script>
import cache from '@/plugins/cache'

export default {
  name: "RightToolbar",
  data() {
    return {
      // 显隐数据
      value: [],
      // 弹出层标题
      title: "显示/隐藏",
      // 是否显示弹出层
      open: false
    }
  },
  props: {
    /* 是否显示检索条件 */
    showSearch: {
      type: Boolean,
      default: true
    },
    /* 显隐列信息（数组格式、对象格式） */
    columns: {
      type: [Array, Object],
      default: () => ({})
    },
    /* 是否显示检索图标 */
    search: {
      type: Boolean,
      default: true
    },
    /* 显隐列类型（transfer穿梭框、checkbox复选框） */
    showColumnsType: {
      type: String,
      default: "checkbox"
    },
    /* 右外边距 */
    gutter: {
      type: Number,
      default: 10
    },
    /* 列显隐状态记忆的 localStorage key（传入则启用记忆，不传则不记忆） */
    storageKey: {
      type: String,
      default: ""
    }
  },
  computed: {
    style() {
      const ret = {}
      if (this.gutter) {
        ret.marginRight = `${this.gutter / 2}px`
      }
      return ret
    },
    isChecked: {
      get() {
        return Array.isArray(this.columns) ? this.columns.every((col) => col.visible) : Object.values(this.columns).every((col) => col.visible)
      },
      set() {}
    },
    isIndeterminate() {
      return Array.isArray(this.columns) ? this.columns.some((col) => col.visible) && !this.isChecked : Object.values(this.columns).some((col) => col.visible) && !this.isChecked
    },
    transferData() {
      if (Array.isArray(this.columns)) {
        return this.columns.map((item, index) => ({ key: index, label: item.label }))
      } else {
        return Object.keys(this.columns).map((key, index) => ({ key: index, label: this.columns[key].label }))
      }
    }
  },
  created() {
    // 如果传入了 storageKey，从 localStorage 恢复列显隐状态
    if (this.storageKey) {
      try {
        const saved = cache.local.getJSON(this.storageKey)
        if (saved && typeof saved === 'object') {
          if (Array.isArray(this.columns)) {
            this.columns.forEach((col, index) => {
              if (saved[index] !== undefined) col.visible = saved[index]
            })
          } else {
            Object.keys(this.columns).forEach(key => {
              if (saved[key] !== undefined) this.columns[key].visible = saved[key]
            })
          }
        }
      } catch (e) {}
    }
    if (this.showColumnsType == 'transfer') {
      // transfer穿梭显隐列初始默认隐藏列
      if (Array.isArray(this.columns)) {
        for (let item in this.columns) {
          if (this.columns[item].visible === false) {
            this.value.push(parseInt(item))
          }
        }
      } else {
        Object.keys(this.columns).forEach((key, index) => {
          if (this.columns[key].visible === false) {
            this.value.push(index)
          }
        })
      }
    }
  },
  methods: {
    // 搜索
    toggleSearch() {
      let el = this.$el
      let formEl = null
      while ((el = el.parentElement) && el !== document.body) {
        if ((formEl = el.querySelector('.el-form'))) break
      }
      if (!formEl) return this.$emit('update:showSearch', !this.showSearch)
      this._animateSearch(formEl, this.showSearch)
    },
    // 搜索栏动画
    _animateSearch(el, isHide) {
      const DURATION = 260
      const TRANSITION = 'max-height 0.25s ease, opacity 0.2s ease'
      const clear = () => Object.assign(el.style, { transition: '', maxHeight: '', opacity: '', overflow: '' })
      Object.assign(el.style, { overflow: 'hidden', transition: '' })
      if (isHide) {
        Object.assign(el.style, { maxHeight: el.scrollHeight + 'px', opacity: '1', transition: TRANSITION })
        requestAnimationFrame(() => Object.assign(el.style, { maxHeight: '0', opacity: '0' }))
        setTimeout(() => { this.$emit('update:showSearch', false); clear() }, DURATION)
      } else {
        this.$emit('update:showSearch', true)
        this.$nextTick(() => {
          Object.assign(el.style, { maxHeight: '0', opacity: '0' })
          requestAnimationFrame(() => requestAnimationFrame(() => {
            Object.assign(el.style, { transition: TRANSITION, maxHeight: el.scrollHeight + 'px', opacity: '1' })
          }))
          setTimeout(clear, DURATION)
        })
      }
    },
    // 刷新
    refresh() {
      this.$emit("queryTable")
    },
    // 右侧列表元素变化
    dataChange(data) {
      if (Array.isArray(this.columns)) {
        for (let item in this.columns) {
          const key = this.columns[item].key
          this.columns[item].visible = !data.includes(key)
        }
      } else {
        Object.keys(this.columns).forEach((key, index) => {
          this.columns[key].visible = !data.includes(index)
        })
      }
      this.saveStorage()
    },
    // 打开显隐列dialog
    showColumn() {
      this.open = true
    },
    // 单勾选
    checkboxChange(event, key) {
      if (Array.isArray(this.columns)) {
        this.columns.filter(item => item.key == key)[0].visible = event
      } else {
        this.columns[key].visible = event
      }
      this.saveStorage()
    },
    // 切换全选/反选
    toggleCheckAll() {
      const newValue = !this.isChecked
      if (Array.isArray(this.columns)) {
        this.columns.forEach((col) => (col.visible = newValue))
      } else {
        Object.values(this.columns).forEach((col) => (col.visible = newValue))
      }
      this.saveStorage()
    },
    // 将当前列显隐状态持久化到 localStorage
    saveStorage() {
      if (!this.storageKey) return
      try {
        let state = {}
        if (Array.isArray(this.columns)) {
          this.columns.forEach((col, index) => { state[index] = col.visible })
        } else {
          Object.keys(this.columns).forEach(key => { state[key] = this.columns[key].visible })
        }
        cache.local.setJSON(this.storageKey, state)
      } catch (e) {}
    }
  },
}
</script>

<style lang="scss" scoped>
::v-deep .el-transfer__button {
  border-radius: 50%;
  padding: 12px;
  display: block;
  margin-left: 0px;
}
::v-deep .el-transfer__button:first-child {
  margin-bottom: 10px;
}
.check-line {
  width: 90%;
  height: 1px;
  background-color: #ccc;
  margin: 3px auto;
}
</style>
