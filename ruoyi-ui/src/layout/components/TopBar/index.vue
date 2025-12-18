<template>
  <el-menu class="topbar-menu" :default-active="activeMenu" :active-text-color="theme" mode="horizontal">
    <sidebar-item :key="route.path + index" v-for="(route, index) in topMenus" :item="route" :base-path="route.path" />

    <el-submenu index="more" class="el-submenu__hide-arrow" v-if="moreRoutes.length > 0">
      <template slot="title">更多菜单</template>
      <sidebar-item :key="route.path + index" v-for="(route, index) in moreRoutes" :item="route" :base-path="route.path" />
    </el-submenu>
  </el-menu>
</template>

<script>
import SidebarItem from '../Sidebar/SidebarItem'

export default {
  components: { SidebarItem },
  data() {
    return {
      // 顶部栏初始数
      visibleNumber: 5
    }
  },
  computed: {
    theme() {
      return this.$store.state.settings.theme
    },
    topMenus() {
      return this.$store.state.permission.sidebarRouters.filter((f) => !f.hidden).slice(0, this.visibleNumber)
    },
    moreRoutes() {
      const sidebarRouters = this.$store.state.permission.sidebarRouters;
      return sidebarRouters.filter((f) => !f.hidden).slice(this.visibleNumber, sidebarRouters.length - this.visibleNumber)
    },
    // 默认激活的菜单
    activeMenu() {
      const { meta, path } = this.$route
      if (meta.activeMenu) {
        return meta.activeMenu
      }
      return path
    },
  },
  beforeMount() {
    window.addEventListener('resize', this.setVisibleNumber)
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.setVisibleNumber)
  },
  mounted() {
    this.setVisibleNumber()
  },
  methods: {
    // 根据宽度计算设置显示栏数
    setVisibleNumber() {
      const width = document.body.getBoundingClientRect().width / 3
      this.visibleNumber = parseInt(width / 85)
    }
  }
}
</script>

<style lang="scss">
/* menu item */
.topbar-menu.el-menu--horizontal .el-submenu__title, .topbar-menu.el-menu--horizontal .el-menu-item {
  padding: 0 10px !important;
}

.el-menu--horizontal .el-menu--popup .el-menu-item:hover {
  background-color: #f5f7fa !important;
}

/* submenu item */
.topbar-menu.el-menu--horizontal > .el-submenu .el-submenu__title {
  float: left;
  height: 47px !important;
  line-height: 50px !important;
  color: #303133;
  margin: 0 15px !important;
}

/* topbar more arrow */ 
.topbar-menu .el-submenu .el-submenu__icon-arrow {
  position: static;
  vertical-align: middle;
  margin-left: 8px;
  margin-top: 0px;
}

/* menu__title el-menu-item */
.topbar-menu.el-menu--horizontal .el-submenu__title, .topbar-menu.el-menu--horizontal .el-menu-item {
  height: 55px;
}

.el-menu--horizontal .el-menu .el-menu-item, .el-menu--horizontal .el-menu .el-submenu__title{
  color: #303133;
}
</style>

