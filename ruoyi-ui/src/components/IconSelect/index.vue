<!-- @author zhengjie -->
<template>
  <div class="icon-body">
    <el-input v-model="name" class="icon-search" clearable placeholder="请输入图标名称" @clear="filterIcons" @input="filterIcons">
      <i slot="suffix" class="el-icon-search el-input__icon" />
    </el-input>
    <div class="icon-list">
      <el-scrollbar>
        <div class="list-container">
          <div v-for="(item, index) in iconList" class="icon-item-wrapper" :key="index" @click="selectedIcon(item)">
            <div :class="['icon-item', { active: activeIcon === item }]">
              <svg-icon :icon-class="item" class-name="icon" style="height: 30px;width: 16px;" />
              <span :title="item">{{ item }}</span>
            </div>
          </div>
        </div>
      </el-scrollbar>
    </div>
  </div>
</template>

<script>
import icons from './requireIcons'
export default {
  name: 'IconSelect',
  props: {
    activeIcon: {
      type: String
    }
  },
  data() {
    return {
      name: '',
      iconList: icons
    }
  },
  methods: {
    filterIcons() {
      this.iconList = icons
      if (this.name) {
        this.iconList = this.iconList.filter(item => item.includes(this.name))
      }
    },
    selectedIcon(name) {
      this.$emit('selected', name)
      document.body.click()
    },
    reset() {
      this.name = ''
      this.iconList = icons
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .icon-body {
    width: 100%;
    padding: 10px;
    .icon-search {
      position: relative;
      margin-bottom: 5px;
    }
    .icon-list {
      height: 200px;
      ::v-deep .el-scrollbar {
        height: 100%;
        .el-scrollbar__wrap {
          overflow-x: hidden;
        }
      }
      .list-container {
        display: flex;
        flex-wrap: wrap;
        .icon-item-wrapper {
          width: calc(100% / 3);
          height: 30px;
          line-height: 30px;
          margin-bottom: -5px;
          cursor: pointer;
          display: flex;
          .icon-item {
            display: flex;
            max-width: 100%;
            height: 100%;
            padding: 0 2px;
            &:hover {
              background: #ececec;
              border-radius: 5px;
            }
            .icon {
              flex-shrink: 0;
            }
            span {
              display: inline-block;
              vertical-align: -0.15em;
              fill: currentColor;
              padding-left: 2px;
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
            }
          }
          .icon-item.active {
            background: #ececec;
            border-radius: 5px;
          }
        }
      }
    }
  }
</style>
