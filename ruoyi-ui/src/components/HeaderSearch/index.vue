<template>
  <div class="header-search">
    <svg-icon class-name="search-icon" icon-class="search" @click.stop="click" />
    <el-dialog
      :visible.sync="show"
      width="600px"
      @close="close"
      :show-close="false"
      append-to-body
    >
      <el-input
        v-model="search"
        ref="headerSearchSelectRef"
        size="large"
        @input="querySearch"
        prefix-icon="el-icon-search"
        placeholder="菜单搜索，支持标题、URL模糊查询"
        clearable
        @keyup.enter.native="selectActiveResult"
        @keydown.up.native="navigateResult('up')"
        @keydown.down.native="navigateResult('down')"
      >
      </el-input>
      <el-scrollbar wrap-class="right-scrollbar-wrapper">
        <div class="result-wrap">
          <div class="search-item" v-for="(item, index) in options" :key="item.path" :style="activeStyle(index)" @mouseenter="activeIndex = index" @mouseleave="activeIndex = -1">
            <div class="left">
              <svg-icon class="menu-icon" :icon-class="item.icon" />
            </div>
            <div class="search-info" @click="change(item)">
              <div class="menu-title">
                {{ item.title.join(" / ") }}
              </div>
              <div class="menu-path">
                {{ item.path }}
              </div>
            </div>
            <svg-icon icon-class="enter" v-show="index === activeIndex"/>
          </div>
       </div>
      </el-scrollbar>
    </el-dialog>
  </div>
</template>

<script>
import Fuse from 'fuse.js/dist/fuse.min.js'
import path from 'path'
import { isHttp } from '@/utils/validate'

export default {
  name: 'HeaderSearch',
  data() {
    return {
      search: '',
      options: [],
      searchPool: [],
      activeIndex: -1,
      show: false,
      fuse: undefined
    }
  },
  computed: {
    theme() {
      return this.$store.state.settings.theme
    },
    routes() {
      return this.$store.getters.defaultRoutes
    }
  },
  watch: {
    routes() {
      this.searchPool = this.generateRoutes(this.routes)
    },
    searchPool(list) {
      this.initFuse(list)
    }
  },
  mounted() {
    this.searchPool = this.generateRoutes(this.routes)
  },
  methods: {
    click() {
      this.show = !this.show
      if (this.show) {
        this.$refs.headerSearchSelect && this.$refs.headerSearchSelect.focus()
        this.options = this.searchPool
      }
    },
    close() {
      this.$refs.headerSearchSelect && this.$refs.headerSearchSelect.blur()
      this.search = ''
      this.options = []
      this.show = false
      this.activeIndex = -1
    },
    change(val) {
      const path = val.path
      const query = val.query
      if(isHttp(val.path)) {
        // http(s):// 路径新窗口打开
        const pindex = path.indexOf("http")
        window.open(path.substr(pindex, path.length), "_blank")
      } else {
        if (query) {
          this.$router.push({ path: path, query: JSON.parse(query) })
        } else {
          this.$router.push(path)
        }
      }
      this.search = ''
      this.options = []
      this.$nextTick(() => {
        this.show = false
      })
    },
    initFuse(list) {
      this.fuse = new Fuse(list, {
        shouldSort: true,
        threshold: 0.4,
        location: 0,
        distance: 100,
        minMatchCharLength: 1,
        keys: [{
          name: 'title',
          weight: 0.7
        }, {
          name: 'path',
          weight: 0.3
        }]
      })
    },
    // Filter out the routes that can be displayed in the sidebar
    // And generate the internationalized title
    generateRoutes(routes, basePath = '/', prefixTitle = []) {
      let res = []

      for (const router of routes) {
        // skip hidden router
        if (router.hidden) { continue }

        const data = {
          path: !isHttp(router.path) ? path.resolve(basePath, router.path) : router.path,
          title: [...prefixTitle],
          icon: ''
        }

        if (router.meta && router.meta.title) {
          data.title = [...data.title, router.meta.title]
          data.icon = router.meta.icon

          if (router.redirect !== 'noRedirect') {
            // only push the routes with title
            // special case: need to exclude parent router without redirect
            res.push(data)
          }
        }

        if (router.query) {
          data.query = router.query
        }

        // recursive child routes
        if (router.children) {
          const tempRoutes = this.generateRoutes(router.children, data.path, data.title)
          if (tempRoutes.length >= 1) {
            res = [...res, ...tempRoutes]
          }
        }
      }
      return res
    },
    querySearch(query) {
      this.activeIndex = -1
      if (query !== '') {
        this.options = this.fuse.search(query).map((item) => item.item) ?? this.searchPool
      } else {
        this.options = this.searchPool
      }
    },
    activeStyle(index) {
      if (index !== this.activeIndex) return {}
      return {
        "background-color": this.theme,
        "color": "#fff"
      }
    },
    navigateResult(direction) {
      if (direction === "up") {
        this.activeIndex = this.activeIndex <= 0 ? this.options.length - 1 : this.activeIndex - 1
      } else if (direction === "down") {
        this.activeIndex = this.activeIndex >= this.options.length - 1 ? 0 : this.activeIndex + 1
      }
    },
    selectActiveResult() {
      if (this.options.length > 0 && this.activeIndex >= 0) {
        this.change(this.options[this.activeIndex])
      }
    }
  }
}
</script>

<style lang='scss' scoped>
::v-deep {
  .el-dialog__header {
    padding: 0 !important;
  }
}

.header-search {
  .search-icon {
    cursor: pointer;
    font-size: 18px;
    vertical-align: middle;
  }
}

.result-wrap {
  height: 280px;
  margin: 6px 0;

  .search-item {
    display: flex;
    height: 48px;
    align-items: center;
    padding-right: 10px;

    .left {
      width: 60px;
      text-align: center;

      .menu-icon {
        width: 18px;
        height: 18px;
      }
    }

    .search-info {
      padding-left: 5px;
      margin-top: 10px;
      width: 100%;
      display: flex;
      flex-direction: column;
      justify-content: flex-start;
      flex: 1;

      .menu-title,
      .menu-path {
        height: 20px;
      }
      .menu-path {
        color: #ccc;
        font-size: 10px;
      }
    }
  }

  .search-item:hover {
    cursor: pointer;
  }
}
</style>

