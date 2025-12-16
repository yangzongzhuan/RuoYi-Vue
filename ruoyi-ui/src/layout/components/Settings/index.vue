<template>
  <el-drawer size="280px" :visible="showSettings" :with-header="false" :append-to-body="true" :before-close="closeSetting" :lock-scroll="false">
    <div class="drawer-container">
      <div>
        <div class="setting-drawer-content">
          <div class="setting-drawer-title">
            <h3 class="drawer-title">菜单导航设置</h3>
          </div>
          <div class="nav-wrap">
            <el-tooltip content="左侧菜单" placement="bottom">
              <div class="item left" @click="handleNavType(1)" :style="{'--theme': theme}" :class="{ activeItem: navType == 1 }">
                <b></b><b></b>
              </div>
            </el-tooltip>

            <el-tooltip content="混合菜单" placement="bottom">
              <div class="item mix" @click="handleNavType(2)" :style="{'--theme': theme}" :class="{ activeItem: navType == 2 }">
                <b></b><b></b>
              </div>
            </el-tooltip>
            <el-tooltip content="顶部菜单" placement="bottom">
              <div class="item top" @click="handleNavType(3)" :style="{'--theme': theme}" :class="{ activeItem: navType == 3 }">
                <b></b><b></b>
              </div>
            </el-tooltip>
          </div>
          <div class="setting-drawer-title">
            <h3 class="drawer-title">主题风格设置</h3>
          </div>
          <div class="setting-drawer-block-checbox">
            <div class="setting-drawer-block-checbox-item" @click="handleTheme('theme-dark')">
              <img src="@/assets/images/dark.svg" alt="dark">
              <div v-if="sideTheme === 'theme-dark'" class="setting-drawer-block-checbox-selectIcon" style="display: block;">
                <i aria-label="图标: check" class="anticon anticon-check">
                  <svg viewBox="64 64 896 896" data-icon="check" width="1em" height="1em" :fill="theme" aria-hidden="true" focusable="false" class="">
                    <path d="M912 190h-69.9c-9.8 0-19.1 4.5-25.1 12.2L404.7 724.5 207 474a32 32 0 0 0-25.1-12.2H112c-6.7 0-10.4 7.7-6.3 12.9l273.9 347c12.8 16.2 37.4 16.2 50.3 0l488.4-618.9c4.1-5.1.4-12.8-6.3-12.8z"/>
                  </svg>
                </i>
              </div>
            </div>
            <div class="setting-drawer-block-checbox-item" @click="handleTheme('theme-light')">
              <img src="@/assets/images/light.svg" alt="light">
              <div v-if="sideTheme === 'theme-light'" class="setting-drawer-block-checbox-selectIcon" style="display: block;">
                <i aria-label="图标: check" class="anticon anticon-check">
                  <svg viewBox="64 64 896 896" data-icon="check" width="1em" height="1em" :fill="theme" aria-hidden="true" focusable="false" class="">
                    <path d="M912 190h-69.9c-9.8 0-19.1 4.5-25.1 12.2L404.7 724.5 207 474a32 32 0 0 0-25.1-12.2H112c-6.7 0-10.4 7.7-6.3 12.9l273.9 347c12.8 16.2 37.4 16.2 50.3 0l488.4-618.9c4.1-5.1.4-12.8-6.3-12.8z"/>
                  </svg>
                </i>
              </div>
            </div>
          </div>

          <div class="drawer-item">
            <span>主题颜色</span>
            <theme-picker style="float: right;height: 26px;margin: -3px 8px 0 0;" @change="themeChange" />
          </div>
        </div>

        <el-divider/>

        <h3 class="drawer-title">系统布局配置</h3>

        <div class="drawer-item">
          <span>开启 Tags-Views</span>
          <el-switch v-model="tagsView" class="drawer-switch" />
        </div>

        <div class="drawer-item">
          <span>显示页签图标</span>
          <el-switch v-model="tagsIcon" :disabled="!tagsView" class="drawer-switch" />
        </div>

        <div class="drawer-item">
          <span>固定 Header</span>
          <el-switch v-model="fixedHeader" class="drawer-switch" />
        </div>

        <div class="drawer-item">
          <span>显示 Logo</span>
          <el-switch v-model="sidebarLogo" class="drawer-switch" />
        </div>

        <div class="drawer-item">
          <span>动态标题</span>
          <el-switch v-model="dynamicTitle" class="drawer-switch" />
        </div>

        <div class="drawer-item">
          <span>底部版权</span>
          <el-switch v-model="footerVisible" class="drawer-switch" />
        </div>

        <el-divider/>

        <el-button size="small" type="primary" plain icon="el-icon-document-add" @click="saveSetting">保存配置</el-button>
        <el-button size="small" plain icon="el-icon-refresh" @click="resetSetting">重置配置</el-button>
      </div>
    </div>
  </el-drawer>
</template>

<script>
import ThemePicker from '@/components/ThemePicker'

export default {
  components: { ThemePicker },
  expose: ['openSetting'],
  data() {
    return {
      theme: this.$store.state.settings.theme,
      sideTheme: this.$store.state.settings.sideTheme,
      navType: this.$store.state.settings.navType,
      showSettings: false
    }
  },
  computed: {
    fixedHeader: {
      get() {
        return this.$store.state.settings.fixedHeader
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'fixedHeader',
          value: val
        })
      }
    },
    tagsView: {
      get() {
        return this.$store.state.settings.tagsView
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'tagsView',
          value: val
        })
      }
    },
    tagsIcon: {
      get() {
        return this.$store.state.settings.tagsIcon
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'tagsIcon',
          value: val
        })
      }
    },
    sidebarLogo: {
      get() {
        return this.$store.state.settings.sidebarLogo
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'sidebarLogo',
          value: val
        })
      }
    },
    dynamicTitle: {
      get() {
        return this.$store.state.settings.dynamicTitle
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'dynamicTitle',
          value: val
        })
        this.$store.dispatch('settings/setTitle', this.$store.state.settings.title)
      }
    },
    footerVisible: {
      get() {
        return this.$store.state.settings.footerVisible
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'footerVisible',
          value: val
        })
      }
    }
  },
  watch: {
    navType: {
      handler(val) {
        if (val == 1) {
          this.$store.dispatch("app/toggleSideBarHide", false)
        }
        if (val == 2) {
        }
        if (val == 3) {
          this.$store.dispatch("app/toggleSideBarHide", true)
        }
        if ([1, 3].includes(val)) {
          this.$store.commit("SET_SIDEBAR_ROUTERS",this.$store.state.permission.defaultRoutes)
        }
      },
      immediate: true,
      deep: true
    }
  },
  methods: {
    themeChange(val) {
      this.$store.dispatch('settings/changeSetting', {
        key: 'theme',
        value: val
      })
      this.theme = val
    },
    handleTheme(val) {
      this.$store.dispatch('settings/changeSetting', {
        key: 'sideTheme',
        value: val
      })
      this.sideTheme = val
    },
    handleNavType(val) {
      this.$store.dispatch('settings/changeSetting', {
        key: 'navType',
        value: val
      })
      this.navType = val
    },
    openSetting() {
      this.showSettings = true
    },
    closeSetting(){
      this.showSettings = false
    },
    saveSetting() {
      this.$modal.loading("正在保存到本地，请稍候...")
      this.$cache.local.set(
        "layout-setting",
        `{
            "navType":${this.navType},
            "tagsView":${this.tagsView},
            "tagsIcon":${this.tagsIcon},
            "fixedHeader":${this.fixedHeader},
            "sidebarLogo":${this.sidebarLogo},
            "dynamicTitle":${this.dynamicTitle},
            "footerVisible":${this.footerVisible},
            "sideTheme":"${this.sideTheme}",
            "theme":"${this.theme}"
          }`
      )
      setTimeout(this.$modal.closeLoading(), 1000)
    },
    resetSetting() {
      this.$modal.loading("正在清除设置缓存并刷新，请稍候...")
      this.$cache.local.remove("layout-setting")
      setTimeout("window.location.reload()", 1000)
    }
  }
}
</script>

<style lang="scss" scoped>
.setting-drawer-content {
  .setting-drawer-title {
    margin-bottom: 12px;
    color: rgba(0, 0, 0, .85);
    font-size: 14px;
    line-height: 22px;
    font-weight: bold;
  }

  .setting-drawer-block-checbox {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    margin-top: 10px;
    margin-bottom: 20px;

    .setting-drawer-block-checbox-item {
      position: relative;
      margin-right: 16px;
      border-radius: 2px;
      cursor: pointer;

      img {
        width: 48px;
        height: 48px;
      }

      .setting-drawer-block-checbox-selectIcon {
        position: absolute;
        top: 0;
        right: 0;
        width: 100%;
        height: 100%;
        padding-top: 15px;
        padding-left: 24px;
        color: #1890ff;
        font-weight: 700;
        font-size: 14px;
      }
    }
  }
}

.drawer-container {
  padding: 20px;
  font-size: 14px;
  line-height: 1.5;
  word-wrap: break-word;

  .drawer-title {
    margin-bottom: 12px;
    color: rgba(0, 0, 0, .85);
    font-size: 14px;
    line-height: 22px;
  }

  .drawer-item {
    color: rgba(0, 0, 0, .65);
    font-size: 14px;
    padding: 12px 0;
  }

  .drawer-switch {
    float: right
  }
}

// 导航模式
.nav-wrap {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin-top: 10px;
  margin-bottom: 20px;

  .activeItem {
    border: 2px solid #{'var(--theme)'} !important;
  }

  .item {
    position: relative;
    margin-right: 16px;
    cursor: pointer;
    width: 56px;
    height: 48px;
    border-radius: 4px;
    background: #f0f2f5;
    border: 2px solid transparent;
  }

  .left {
    b:first-child {
      display: block;
      height: 30%;
      background: #fff;
    }
    b:last-child {
      width: 30%;
      background: #1b2a47;
      position: absolute;
      height: 100%;
      top: 0;
      border-radius: 4px 0 0 4px;
    }
  }
  .mix {
    b:first-child {
      border-radius: 4px 4px 0 0;
      display: block;
      height: 30%;
      background: #1b2a47;
    }
    b:last-child {
      width: 30%;
      background: #1b2a47;
      position: absolute;
      height: 70%;
      border-radius: 0 0 0 4px;
    }
  }
  .top {
    b:first-child {
      display: block;
      height: 30%;
      background: #1b2a47;
      border-radius: 4px 4px 0 0;
    }
  }
}
</style>
