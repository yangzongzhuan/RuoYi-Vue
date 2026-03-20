const LOCK_KEY = 'screen-lock'
const LOCK_PATH_KEY = 'screen-lock-path'

const lock = {
  namespaced: true,
  state: {
    isLock: JSON.parse(localStorage.getItem(LOCK_KEY) || 'false'),
    lockPath: localStorage.getItem(LOCK_PATH_KEY) || '/index'
  },
  mutations: {
    SET_LOCK(state, status) {
      state.isLock = status
      localStorage.setItem(LOCK_KEY, JSON.stringify(status))
    },
    SET_LOCK_PATH(state, path) {
      state.lockPath = path
      localStorage.setItem(LOCK_PATH_KEY, path)
    }
  },
  actions: {
    // 锁定屏幕，同时记录当前路径
    lockScreen({ commit }, currentPath) {
      commit('SET_LOCK_PATH', currentPath || '/index')
      commit('SET_LOCK', true)
    },
    // 解锁屏幕，清除路径
    unlockScreen({ commit }) {
      commit('SET_LOCK', false)
      commit('SET_LOCK_PATH', '/index')
    }
  }
}

export default lock
