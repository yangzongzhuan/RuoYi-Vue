<template>
  <div class="app-container" v-loading="loading">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
<!--      <el-form-item label="任务名称" prop="taskName">-->
<!--        <el-input-->
<!--          v-model="queryParams.taskName"-->
<!--          placeholder="请输入任务名称"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="作图账号名称" prop="accountName">
        <el-input
          v-model="queryParams.accountName"
          placeholder="请输入作图账号名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="模板名称" prop="templateName">
        <el-input
          v-model="queryParams.templateName"
          placeholder="请输入模板名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['psd:task:add']"
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
          v-hasPermi="['psd:task:edit']"
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
          v-hasPermi="['psd:task:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['psd:task:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="taskList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="任务名称" align="center" prop="taskName" />-->
      <el-table-column label="作图账号名称" align="center" prop="accountName" />
      <el-table-column label="模板名称" align="center" prop="templateName" />
      <el-table-column label="创建时间" align="center" prop="createDate" />
<!--      <el-table-column label="图片生产数量，[1,2,3] 表示图一1张，图二两张" align="center" prop="imageCount" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['psd:task:edit']"-->
<!--          >修改</el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['psd:task:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改任务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body v-loading="loading" :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
<!--        <el-form-item label="任务名称" prop="taskName">-->
<!--          <el-input v-model="form.taskName" placeholder="请输入任务名称" />-->
<!--        </el-form-item>-->
        <el-form-item label="作图账号名称" prop="accountName">
          <el-select
            v-model="form.accountName"
            placeholder="请选择账号"
            filterable
            clearable
            style="width: 100%"
            @change="filterTemplate"
            :popper-append-to-body="false"
          >
            <el-option
              v-for="item in accountOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="模板名称" prop="templateName">
          <el-select
            v-model="form.templateName"
            placeholder="请选择模板"
            filterable
            clearable
            style="width: 100%"
            :popper-append-to-body="false"
          >
            <el-option
              v-for="item in templateOptionsFilter"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-button @click="getTemplateInfo" :disabled="isEmpty(form.accountName) || isEmpty(form.templateName)">获取模板信息</el-button>
        <!-- 模板信息展示区 -->
        <div v-if="!isEmpty(templateInfo.imageConfigs)" class="template-container">
          <!-- 基础配置展示 -->
          <el-card class="config-section">
            <div class="section-header">
              <h3>基础配置</h3>
            </div>
            <div class="config-item">
              <span class="label">账号名称：</span>
              <el-tag type="info">{{ templateInfo.baseConfig.accountName }}</el-tag>
            </div>
            <div class="config-item">
              <span class="label">PSD路径：</span>
              <el-tooltip :content="templateInfo.baseConfig.psdLocalPath">
                <span class="path">{{ templateInfo.baseConfig.psdLocalPath }}</span>
              </el-tooltip>
            </div>
            <div class="config-item">
              <span class="label">输出路径：</span>
              <el-tooltip :content="templateInfo.baseConfig.imageSavePath">
                <span class="path">{{ templateInfo.baseConfig.imageSavePath }}</span>
              </el-tooltip>
            </div>
          </el-card>

          <!-- 图片配置展示 -->
          <el-card
            v-for="(imgConfig, index) in templateInfo.imageConfigs"
            :key="index"
            class="config-section image-config">
            <div class="section-header">
              <h3>图片配置 {{ index + 1 }}</h3>
              <el-input-number
                v-model="imgConfig.generateCount"
                :min="0"
                :max="Infinity"
                :precision="0"
                controls-position="right"
                class="generate-count"
                placeholder="生成数量">
              </el-input-number>
            </div>

            <!-- 文件夹配置 -->
            <div class="config-item">
              <span class="label">目标文件夹：</span>
              <el-tag>{{ imgConfig.folderName }}</el-tag>
              <span v-if="imgConfig.hasSubfolder" class="subfolder">
            / {{ imgConfig.subfolderName }}
          </span>
            </div>

            <!-- 文字图层配置 -->
            <div
              v-for="(layer, key) in imgConfig.textLayerConfigs"
              :key="key"
              class="text-layer">
              <div class="layer-header">
                <el-tag type="success">{{ key }}: {{layer.name}}</el-tag>
              </div>
              <div class="config-item">
                <span class="label">示例文本：</span>
                <el-input
                  :value="layer.sampleText"
                  readonly
                  class="sample-text">
                  <template slot="append">
                    <span class="char-limit">{{ layer.maxCharsPerLine }}字/行</span>
                  </template>
                </el-input>
              </div>
            </div>
            <div class="config-item">
              <span class="label">提示词：</span>
              <el-input
                type="textarea"
                :rows="3"
                :value="imgConfig.prompt"
                readonly
                class="sample-text">
              </el-input>
            </div>
          </el-card>
<!--          <el-button type="primary" @click="getCozeInfo">请求coze</el-button>-->
        </div>
        <!-- 在点击按钮后添加 JSON 展示区域 -->
        <div v-if="form.jsonInfo" class="json-preview-container">
          <el-card class="json-card">
            <div slot="header" class="json-header">
              <span>JSON 响应数据</span>
              <el-button
                type="text"
                @click="toggleJsonView"
                class="toggle-btn">
                {{ showRawJson ? '展开结构' : '原始数据' }}
              </el-button>
            </div>

            <!-- 结构化展示 -->
            <div v-if="!showRawJson" class="formatted-json">
              <div
                v-for="(value, key) in parsedJson"
                :key="key"
                class="json-item">
                <span class="json-key">{{ key }}:</span>
                <span class="json-value">{{ value }}</span>
              </div>
            </div>

            <!-- 原始 JSON 展示 -->
            <pre v-else class="raw-json">{{ formatJson(form.jsonInfo) }}</pre>
          </el-card>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" :disabled="this.loading">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listTask, getTask, delTask, addTask, updateTask, getCoze} from "@/api/custom/task";
import {getListPSDConfigAll, listPSDConfig} from "@/api/custom/psd";
import {isEmpty} from "@/utils/validate";

export default {
  name: "Task",
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
      // 任务表格数据
      taskList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        taskName: null,
        accountName: null,
        templateName: null,
        imageCount: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      templateInfo: {
        baseConfig: {
          accountName: '',
          psdLocalPath: '',
          imageSavePath: ''
        },
        imageConfigs: [] // 每个元素需要包含 generateCount 字段
      },
      showRawJson: false, // 切换显示模式
      psdList: {},
      templateOptions: [], // 模板下拉选项
      templateOptionsFilter: [], // 模板下拉选项过滤后的数据
      accountOptions: [],   // 账号下拉选项
    };
  },
  created() {
    this.getList();
  },
  computed: {
    // 自动解析 JSON 为对象
    parsedJson() {
      try {
        return typeof this.form.jsonInfo === 'string'
          ? JSON.parse(this.form.jsonInfo)
          : this.form.jsonInfo;
      } catch {
        return { error: 'Invalid JSON format' };
      }
    }
  },
  methods: {
    isEmpty,
    /** 查询任务列表 */
    getList() {
      this.loading = true;
      listTask(this.queryParams).then(response => {
        this.taskList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        taskName: null,
        accountName: null,
        templateName: null,
        imageCount: null,
        config: null,
        jsonInfo: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.templateInfo = {
        baseConfig: {
          accountName: '',
          psdLocalPath: '',
          imageSavePath: ''
        }
      }
      getListPSDConfigAll().then(response => {
        this.psdList = response.rows
        this.psdList.forEach(item => {
          try {
            const config = JSON.parse(item.config)
            const { templateName, accountName } = config.baseConfig

            // 去重后添加到选项
            if (!this.templateOptions.find(opt => opt.value === templateName)) {
              this.templateOptions.push({
                value: templateName,
                label: templateName
              })
            }

            // 初始化时显示全部模板
            this.templateOptionsFilter = [...this.templateOptions];

            if (!this.accountOptions.find(opt => opt.value === accountName)) {
              this.accountOptions.push({
                value: accountName,
                label: accountName
              })
            }
          } catch (e) {
            console.error('JSON 解析错误:', e)
          }
        })
        console.log(this.psdList)
        this.reset();
        this.open = true;
        this.title = "添加任务";
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTask(id).then(response => {
        this.templateInfo = {
          baseConfig: {
            accountName: '',
            psdLocalPath: '',
            imageSavePath: ''
          }
        }
        this.form = response.data;
        this.open = true;
        this.title = "修改任务";
      });
    },
    /** 提交按钮 */
    submitForm() {
      console.log(this.templateInfo)
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.loading = true;
          this.form.config = this.templateInfo;
          if (this.form.id != null) {
            updateTask(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.templateInfo = {
                baseConfig: {
                accountName: '',
                psdLocalPath: '',
                imageSavePath: ''
                },
                imageConfigs: [] // 每个元素需要包含 generateCount 字段
              }
            }).finally(() => {this.loading = false;});
          } else {
            addTask(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.templateInfo = {
                baseConfig: {
                  accountName: '',
                  psdLocalPath: '',
                  imageSavePath: ''
                },
                imageConfigs: [] // 每个元素需要包含 generateCount 字段
              }
            }).finally(() => {this.loading = false;});
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除任务编号为"' + ids + '"的数据项？').then(function() {
        return delTask(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('psd/task/export', {
        ...this.queryParams
      }, `task_${new Date().getTime()}.xlsx`)
    },
    // 修改后代码
    getTemplateInfo() {
      this.loading = true;
      listPSDConfig({
        accountName: this.form.accountName,
        templateName: this.form.templateName
      }).then(res => {
        if (res.rows[0]?.config) {
          const parsedConfig = JSON.parse(res.rows[0].config);
          // 合并基础配置（保留响应式）
          Object.assign(this.templateInfo.baseConfig, parsedConfig.baseConfig);
          // 替换图片配置数组（确保响应式更新）
          this.templateInfo.imageConfigs = parsedConfig.imageConfigs.map(config => ({
            ...config,
            generateCount: config.generateCount || 1 // 初始化生成数量
          }));
          this.$modal.msgSuccess("模板信息获取成功");
        } else {
          this.$modal.msgError("模板配置数据不存在");
        }
        this.loading = false;
      }).catch(() => {
        this.loading = false;
      });
    },
    getCozeInfo() {
      this.loading = true;
      this.form.config = this.templateInfo;
      getCoze(this.form).then(res => {
        this.form.jsonInfo = res.msg;
      }).finally(() => {this.loading = false;})
    },
    // 格式化 JSON 的函数
    formatJson(json) {
      if (!json) return '';
      try {
        return JSON.stringify(
          typeof json === 'string' ? JSON.parse(json) : json,
          null,
          2
        );
      } catch {
        return json;
      }
    },
    // 切换显示模式
    toggleJsonView() {
      this.showRawJson = !this.showRawJson;
    },
    // 修改过滤方法
    filterTemplate() {
      this.form.templateName = ''
      if (!this.form.accountName) {
        // 如果未选择账号，显示全部模板
        this.templateOptionsFilter = [...this.templateOptions];
        return;
      }

      // 根据账号名称过滤模板
      const relatedTemplates = this.psdList
        .filter(item => {
          try {
            const config = JSON.parse(item.config);
            return config.baseConfig?.accountName === this.form.accountName;
          } catch(e) {
            return false;
          }
        })
        .map(item => {
          try {
            const config = JSON.parse(item.config);
            return config.baseConfig?.templateName;
          } catch(e) {
            return null;
          }
        })
        .filter(Boolean); // 过滤空值

      // 去重并转换为选项格式
      this.templateOptionsFilter = [...new Set(relatedTemplates)].map(name => ({
        value: name,
        label: name
      }));
    }
  }
};
</script>

<style scoped>
.template-container {
  margin-top: 20px;
  background: #f5f7fa;
  padding: 15px;
  border-radius: 4px;
}

.config-section {
  margin-bottom: 20px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;

  h3 {
    color: #409EFF;
    margin: 0;
    font-size: 16px;
  }
}

.generate-count {
  width: 150px;

  >>> .el-input__inner {
    text-align: right;
  }
}

.config-item {
  display: flex;
  align-items: center;
  margin: 10px 0;

  .label {
    width: 80px;
    color: #666;
    font-size: 13px;
  }

  .path {
    color: #909399;
    cursor: pointer;
    &:hover {
      color: #409EFF;
    }
  }

  .subfolder {
    color: #E6A23C;
    margin-left: 5px;
  }
}

.text-layer {
  border-left: 3px solid #EBEEF5;
  padding-left: 10px;
  margin: 15px 0;

  .layer-header {
    margin-bottom: 8px;
  }

  .sample-text {
    >>> .el-input__inner {
      background: #f5f7fa;
      cursor: default;
    }

    .char-limit {
      color: #F56C6C;
      font-size: 12px;
    }
  }
}
/* 样式优化 */
.json-preview-container {
  margin-top: 20px;
}

.json-card {
  background: #f8f9fa;
}

.json-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.toggle-btn {
  padding: 0;
  font-size: 12px;
}

.formatted-json {
  padding: 10px;
  border-radius: 4px;
  background: white;
}

.json-item {
  margin: 6px 0;
  font-family: Monaco, Consolas, monospace;
}

.json-key {
  color: #007bff;
  margin-right: 8px;
}

.json-value {
  color: #28a745;
}

.raw-json {
  max-height: 400px;
  overflow: auto;
  padding: 12px;
  background: #f8f9fa;
  border-radius: 4px;
  white-space: pre-wrap;
  word-wrap: break-word;
}

::v-deep .el-dialog {
  height: 90%;
  overflow: auto;
}
</style>
