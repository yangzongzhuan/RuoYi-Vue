<template>
  <el-scrollbar ref="scrollContainer" :vertical="false" class="scroll-container" @wheel.native.prevent="handleScroll">
    <slot />
  </el-scrollbar>
</template>

<script>
const tagAndTagSpacing = 4

export default {
  name: 'ScrollPane',
  data() {
    return {
      left: 0
    }
  },
  computed: {
    scrollWrapper() {
      return this.$refs.scrollContainer.$refs.wrap
    }
  },
  mounted() {
    this.scrollWrapper.addEventListener('scroll', this.emitScroll, true)
  },
  beforeDestroy() {
    this.scrollWrapper.removeEventListener('scroll', this.emitScroll)
  },
  methods: {
    smoothScrollTo(target) {
      const $scrollWrapper = this.scrollWrapper
      const start = $scrollWrapper.scrollLeft
      const distance = target - start
      const duration = 300
      let startTime = null

      function ease(t, b, c, d) {
        t /= d / 2
        if (t < 1) return c / 2 * t * t + b
        t--
        return -c / 2 * (t * (t - 2) - 1) + b
      }

      const emit = this.$emit.bind(this)
      function step(timestamp) {
        if (!startTime) startTime = timestamp
        const elapsed = timestamp - startTime
        $scrollWrapper.scrollLeft = ease(elapsed, start, distance, duration)
        if (elapsed < duration) {
          requestAnimationFrame(step)
        } else {
          $scrollWrapper.scrollLeft = target
          emit('updateArrows')
        }
      }

      requestAnimationFrame(step)
    },
    handleScroll(e) {
      const eventDelta = e.wheelDelta || -e.deltaY * 40
      const $scrollWrapper = this.scrollWrapper
      $scrollWrapper.scrollLeft = $scrollWrapper.scrollLeft + eventDelta / 4
      this.$emit('updateArrows')
    },
    emitScroll() {
      this.$emit('scroll')
      this.$emit('updateArrows')
    },
    moveToTarget(currentTag) {
      const $container = this.$refs.scrollContainer.$el
      const $containerWidth = $container.offsetWidth
      const $scrollWrapper = this.scrollWrapper
      const tagList = this.$parent.$refs.tag

      let firstTag = null
      let lastTag = null

      if (tagList.length > 0) {
        firstTag = tagList[0]
        lastTag = tagList[tagList.length - 1]
      }

      if (firstTag === currentTag) {
        this.smoothScrollTo(0)
      } else if (lastTag === currentTag) {
        this.smoothScrollTo($scrollWrapper.scrollWidth - $containerWidth)
      } else {
        const currentIndex = tagList.findIndex(item => item === currentTag)
        const prevTag = tagList[currentIndex - 1]
        const nextTag = tagList[currentIndex + 1]
        const afterNextTagOffsetLeft = nextTag.$el.offsetLeft + nextTag.$el.offsetWidth + tagAndTagSpacing
        const beforePrevTagOffsetLeft = prevTag.$el.offsetLeft - tagAndTagSpacing

        if (afterNextTagOffsetLeft > $scrollWrapper.scrollLeft + $containerWidth) {
          this.smoothScrollTo(afterNextTagOffsetLeft - $containerWidth)
        } else if (beforePrevTagOffsetLeft < $scrollWrapper.scrollLeft) {
          this.smoothScrollTo(beforePrevTagOffsetLeft)
        }
      }
    },
    // 向左滚动固定距离
    scrollLeft() {
      const $scrollWrapper = this.scrollWrapper
      this.smoothScrollTo(Math.max(0, $scrollWrapper.scrollLeft - 200))
    },
    // 向右滚动固定距离
    scrollRight() {
      const $scrollWrapper = this.scrollWrapper
      const maxScroll = $scrollWrapper.scrollWidth - $scrollWrapper.clientWidth
      this.smoothScrollTo(Math.min(maxScroll, $scrollWrapper.scrollLeft + 200))
    },
    // 直接平滑滚动到最左端
    scrollToStart() {
      this.smoothScrollTo(0)
    },
    // 直接平滑滚动到最右端
    scrollToEnd() {
      const $scrollWrapper = this.scrollWrapper
      this.smoothScrollTo($scrollWrapper.scrollWidth - $scrollWrapper.clientWidth)
    },
    // 获取是否可以继续向左/右滚动
    getScrollState() {
      const $scrollWrapper = this.scrollWrapper
      return {
        canLeft: $scrollWrapper.scrollLeft > 0,
        canRight: $scrollWrapper.scrollLeft < $scrollWrapper.scrollWidth - $scrollWrapper.clientWidth - 1
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.scroll-container {
  white-space: nowrap;
  position: relative;
  overflow: hidden;
  width: 100%;
  ::v-deep {
    .el-scrollbar__bar {
      bottom: 0px;
    }
    .el-scrollbar__wrap {
      height: 34px;
      display: flex;
      align-items: center;
    }
  }
}
</style>
