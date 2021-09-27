import { saveAs } from 'file-saver'
import axios from 'axios'
import { getToken } from '@/utils/auth'

const baseURL = process.env.VUE_APP_BASE_API

export default {
  name(name, isDelete = true) {
    var url = baseURL + "/common/download?fileName=" + encodeURI(name) + "&delete=" + isDelete
    axios({
      method: 'get',
      url: url,
      responseType: 'blob',
      headers: { 'Authorization': 'Bearer ' + getToken() }
    }).then(res => {
      const blob = new Blob([res.data])
      this.saveAs(blob, decodeURI(res.headers['download-filename']))
    })
  },
  resource(resource) {
    var url = baseURL + "/common/download/resource?resource=" + encodeURI(resource);
    axios({
      method: 'get',
      url: url,
      responseType: 'blob',
      headers: { 'Authorization': 'Bearer ' + getToken() }
    }).then(res => {
      const blob = new Blob([res.data])
      this.saveAs(blob, decodeURI(res.headers['download-filename']))
    })
  },
  zip(url, name) {
    var url = baseURL + url
    axios({
      method: 'get',
      url: url,
      responseType: 'blob',
      headers: { 'Authorization': 'Bearer ' + getToken() }
    }).then(res => {
      const blob = new Blob([res.data], { type: 'application/zip' })
      this.saveAs(blob, name)
    })
  },
  saveAs(text, name, opts) {
    saveAs(text, name, opts);
  }
}

