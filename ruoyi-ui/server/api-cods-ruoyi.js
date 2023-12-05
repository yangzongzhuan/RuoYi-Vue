var createError = require('http-errors')
var express = require('express')
var path = require('path')
var proxyMiddleware = require('http-proxy-middleware')
var app = express()
var proxyTable = {
  '/prod-api': {
    // target: 'http://entapi.cods.zhischina.com',
    target: 'http://localhost:13131',
    changeOrigin: true,
    pathRewrite: {
      ['^' + '/prod-api']: ''
    }
  }
}
function init() {
  // proxy api requests
  Object.keys(proxyTable).forEach(function(context) {
    var options = proxyTable[context]
    if (typeof options === 'string') {
      options = { target: options, changeOrigin: true }
    }
    app.use(proxyMiddleware(context, options))
  })
  // view engine setup
  app.use(express.json())
  app.use(express.urlencoded({ extended: false }))
  var _dist_path = app.get('dist_path')
  console.log('resources root', path.join(__dirname, _dist_path))
  app.use(express.static(path.join(__dirname, _dist_path)))
  // catch 404 and forward to error handler
  app.use(function(req, res, next) {
    next(createError(404))
  })
  // error handler
  app.use(function(err, req, res, next) {
    // set locals, only providing error in development
    console.log('throw error:', err)
    res.locals.message = err.message
    res.locals.error = req.app.get('env') === 'development' ? err : {}
    // render the error page
    res.status(err.status || 500)
    res.send(JSON.stringify({ 'code': 500, 'message': '系统异常:' + err.message }))
  })
}

module.exports = {
  app,
  init
}
