<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="应用ID" prop="appId">
        <el-input
          v-model="queryParams.appId"
          placeholder="请输入应用标识"
          clearable
          style="width: 180px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="应用名称" prop="appName">
        <el-input
          v-model="queryParams.appName"
          placeholder="请输入应用名称"
          clearable
          style="width: 180px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="平台" prop="platform">
        <el-select v-model="queryParams.platform" placeholder="请选择平台" clearable style="width: 160px">
          <el-option
            v-for="dict in dict.type.sys_platform"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="更新类型" prop="updateType">
        <el-select v-model="queryParams.updateType" placeholder="请选择更新类型" clearable style="width: 160px">
          <el-option
            v-for="dict in dict.type.app_update_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 120px">
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="发布时间">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮区 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['app:version:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['app:version:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['app:version:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['app:version:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 数据表 -->
    <el-table v-loading="loading" :data="versionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="应用ID" align="center" prop="appId" width="120" />
      <el-table-column label="应用名称" align="center" prop="appName" :show-overflow-tooltip="true" />
      <el-table-column label="平台" align="center" prop="platform" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_platform" :value="scope.row.platform" />
        </template>
      </el-table-column>
      <el-table-column label="版本号" align="center" prop="version" width="120" />
      <el-table-column label="版本Code" align="center" prop="versionCode" width="100" />
      <el-table-column label="更新类型" align="center" prop="updateType" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.app_update_type" :value="scope.row.updateType" />
        </template>
      </el-table-column>
      <el-table-column label="包大小(MB)" align="center" prop="packageSize" width="110" />
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status === '0'"
            @change="(val) => handleStatusChange(scope.row, val ? '0' : '1')"
            active-color="#13ce66"
            inactive-color="#ff4949"
          />
        </template>
      </el-table-column>
      <el-table-column label="发布时间" align="center" prop="publishTime" width="170">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.publishTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="160">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['app:version:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['app:version:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 新增/编辑弹窗 -->
    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="应用ID" prop="appId">
              <el-input v-model="form.appId" placeholder="如 ruoyi-app" :disabled="isUpdate" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="应用名称" prop="appName">
              <el-input v-model="form.appName" placeholder="请输入应用名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="平台" prop="platform">
              <el-select v-model="form.platform" placeholder="请选择平台" :disabled="isUpdate" style="width:100%">
                <el-option
                  v-for="dict in dict.type.sys_platform"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="版本号" prop="version">
              <el-input v-model="form.version" placeholder="如 1.2.0" :disabled="isUpdate" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="版本Code" prop="versionCode">
              <el-input-number v-model="form.versionCode" :min="1" :max="999999999" controls-position="right" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="更新类型" prop="updateType">
              <el-select v-model="form.updateType" placeholder="请选择更新类型" style="width:100%">
                <el-option
                  v-for="dict in dict.type.app_update_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="包大小(MB)" prop="packageSize">
              <el-input-number v-model="form.packageSize" :min="0" :precision="2" controls-position="right" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最低支持版本" prop="minSupportVersion">
              <el-input v-model="form.minSupportVersion" placeholder="如 100(纯数字字符串)" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="MD5" prop="md5">
              <el-input v-model="form.md5" placeholder="安装包 MD5(可选)" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发布时间" prop="publishTime">
              <el-date-picker
                v-model="form.publishTime"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="选择发布时间"
                style="width:100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="下载地址" prop="downloadUrl">
              <el-input v-model="form.downloadUrl" placeholder="支持点击右侧按钮上传,也可粘贴外链" style="width: calc(100% - 280px)" />
              <!-- el-upload 用 default slot 触发,避免 display:none 隐藏导致 input 不渲染 -->
              <el-upload
                ref="uploadRef"
                :headers="uploadHeaders"
                :http-request="customUpload"
                :before-upload="beforeUpload"
                :show-file-list="false"
                accept=".apk,.ipa,.hap"
                style="display: inline-block; margin-left: 8px"
              >
                <el-button :loading="uploadLoading" icon="el-icon-upload2" size="small">上传 APK</el-button>
              </el-upload>
              <el-link v-if="form.md5" type="info" :underline="false" style="margin-left: 8px">MD5: {{ form.md5 }}</el-link>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="更新日志" prop="updateLog">
              <el-input v-model="form.updateLog" type="textarea" :rows="4" placeholder="支持多行,可用 \n 换行" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listVersion,
  getVersion,
  delVersion,
  addVersion,
  updateVersion,
  changeVersionStatus,
  exportVersion,
  uploadApk
} from '@/api/app/version'
import { getToken } from '@/utils/auth'

export default {
  name: 'AppVersion',
  dicts: ['sys_platform', 'app_update_type', 'sys_normal_disable'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // APP版本表格数据
      versionList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 是否为修改
      isUpdate: false,
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        appId: undefined,
        appName: undefined,
        platform: undefined,
        updateType: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        appId: [
          { required: true, message: '应用ID不能为空', trigger: 'blur' }
        ],
        appName: [
          { required: true, message: '应用名称不能为空', trigger: 'blur' }
        ],
        platform: [
          { required: true, message: '请选择平台', trigger: 'change' }
        ],
        version: [
          { required: true, message: '版本号不能为空', trigger: 'blur' }
        ],
        versionCode: [
          { required: true, message: '版本Code不能为空', trigger: 'blur' }
        ],
        updateType: [
          { required: true, message: '请选择更新类型', trigger: 'change' }
        ],
        downloadUrl: [
          { required: true, message: '下载地址不能为空', trigger: 'blur' }
        ]
      },
      // 上传相关
      uploadHeaders: { Authorization: 'Bearer ' + getToken() },
      uploadUrl: process.env.VUE_APP_BASE_API + '/app/version/upload',
      uploadLoading: false
    }
  },
  created() {
    this.uploadHeaders = { Authorization: 'Bearer ' + getToken() }
    this.getList()
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true
      listVersion(this.addDateRange(this.queryParams, this.dateRange)).then((response) => {
        this.versionList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        appId: undefined,
        appName: undefined,
        platform: undefined,
        version: undefined,
        versionCode: 1,
        updateType: '2',
        downloadUrl: undefined,
        updateLog: undefined,
        packageSize: 0,
        md5: undefined,
        minSupportVersion: undefined,
        status: '0',
        publishTime: undefined,
        remark: undefined
      }
      this.isUpdate = false
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加APP版本'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getVersion(id).then((response) => {
        this.form = response.data
        this.isUpdate = true
        this.open = true
        this.title = '修改APP版本'
      })
    },
    /** 状态切换 */
    handleStatusChange(row, status) {
      const text = status === '0' ? '启用' : '停用'
      this.$modal
        .confirm('确认要"' + text + '""' + row.appName + '"的版本 ' + row.version + ' 吗?')
        .then(() => {
          return changeVersionStatus({ id: row.id, status: status })
        })
        .then(() => {
          this.$modal.msgSuccess(text + '成功')
        })
        .catch(() => {
          // 失败回滚 UI 状态
          row.status = status === '0' ? '1' : '0'
        })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.id !== undefined) {
            updateVersion(this.form).then(() => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addVersion(this.form).then(() => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal
        .confirm('是否确认删除APP版本编号为"' + ids + '"的数据项?')
        .then(function () {
          return delVersion(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        'app/version/export',
        { ...this.queryParams },
        `appversion_${new Date().getTime()}.xlsx`
      )
    },
    /** 上传前校验:元数据 / 扩展名 / 大小 */
    beforeUpload(file) {
      // 1. 必填元数据
      if (!this.form.appId || !this.form.platform || this.form.versionCode == null) {
        this.$modal.msgError('请先填写应用ID/平台/版本Code')
        return false
      }
      // 2. 扩展名
      const allowExt = ['apk', 'ipa', 'hap']
      const ext = (file.name.split('.').pop() || '').toLowerCase()
      if (!allowExt.includes(ext)) {
        this.$modal.msgError('仅支持 apk/ipa/hap 格式')
        return false
      }
      // 3. 大小限制
      if (file.size / 1024 / 1024 >= 200) {
        this.$modal.msgError('文件大小不能超过 200MB')
        return false
      }
      this.uploadLoading = true
      return true
    },
    /** 自定义上传:覆盖 el-upload 默认行为,直接调 uploadApk */
    customUpload(option) {
      const formData = new FormData()
      formData.append('file', option.file)
      formData.append('appId', this.form.appId)
      formData.append('platform', this.form.platform)
      formData.append('versionCode', this.form.versionCode)
      uploadApk(formData)
        .then((res) => {
          const data = res && res.data
          if (data && data.code === 200) {
            this.form.downloadUrl = data.data.url
            this.form.packageSize = data.data.size
            this.form.md5 = data.data.md5
            this.$modal.msgSuccess('上传成功,字段已自动回填')
            option.onSuccess(data)
          } else {
            const msg = (data && data.msg) || '上传失败'
            this.$modal.msgError(msg)
            option.onError(new Error(msg))
          }
        })
        .catch((err) => {
          this.$modal.msgError('上传失败:' + (err.message || '网络错误'))
          option.onError(err)
        })
        .finally(() => {
          this.uploadLoading = false
        })
    }
  }
}
</script>
