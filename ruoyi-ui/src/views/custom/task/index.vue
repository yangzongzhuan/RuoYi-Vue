<template>
  <div class="app-container" v-loading="loading">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
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
      <el-form-item label="任务状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="任务状态" clearable>
          <el-option
            v-for="dict in dict.type.psd_task_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          type="info"
          plain
          icon="el-icon-edit-outline"
          size="mini"
          @click="handleManualAdd"
          v-hasPermi="['psd:task:add']"
        >手动创建</el-button>
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
      <el-table-column label="作图账号名称" align="center" prop="accountName" />
      <el-table-column label="模板名称" align="center" prop="templateName" />
      <el-table-column prop="status" label="任务状态" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.psd_task_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createDate" />
      <el-table-column label="发布公众号名称" align="center" prop="gzhName" />
      <el-table-column label="抖音发布状态" align="center" prop="dyStatus" width="120">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.dyStatus == '1'" type="success">发布成功</el-tag>
          <el-tag v-else-if="scope.row.dyStatus == '0'" type="warning">发布中</el-tag>
          <el-tag v-else-if="scope.row.dyStatus == '2'" type="danger">发布失败</el-tag>
          <el-tag v-else type="info">未发布</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleEditTask(scope.row)"
            v-hasPermi="['psd:task:edit']"
          >编辑</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['psd:task:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-promotion"
            disabled
            v-if="scope.row.gzhStatus == 1"
          >公众号发布成功</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-promotion"
            disabled
            v-else-if="scope.row.gzhStatus == 2"
          >公众号发布中</el-button>
          <el-button
            size="mini"
            v-else
            type="text"
            icon="el-icon-s-promotion"
            :disabled="scope.row.status !== '0'"
            @click="openOfficialAccount(scope.row)"
          >发布公众号</el-button>

          <!-- 抖音发布按钮 -->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-video-camera"
            @click="openDouyinPublish(scope.row)"
          >发布抖音</el-button>
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
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body :close-on-click-modal="false" class="auto-task-dialog">
      <div v-loading="loading" class="dialog-loading-wrapper">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <!-- 批量选择区域 -->
        <el-card class="batch-selection-card" shadow="never">
          <div slot="header" class="clearfix">
            <span>选择任务配置</span>
          </div>
          <el-row :gutter="20">
            <el-col :span="10">
              <el-form-item label="作图账号" label-width="80px">
                <el-select
                  v-model="batchSelection.accountName"
                  placeholder="请选择账号"
                  filterable
                  clearable
                  style="width: 100%"
                  @change="batchFilterTemplate"
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
            </el-col>
            <el-col :span="10">
              <el-form-item label="模板名称" label-width="80px">
                <el-select
                  v-model="batchSelection.templateName"
                  placeholder="请选择模板"
                  filterable
                  clearable
                  style="width: 100%"
                  :popper-append-to-body="false"
                >
                  <el-option
                    v-for="item in batchTemplateOptionsFilter"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-button 
                type="primary" 
                icon="el-icon-plus" 
                @click="addBatchTask"
                :disabled="!batchSelection.accountName || !batchSelection.templateName"
              >添加</el-button>
            </el-col>
          </el-row>
        </el-card>

        <!-- 已选择的任务列表 - 使用 el-tabs 展示 -->
        <el-card v-if="batchTasks.length > 0" class="batch-tasks-card" shadow="never">
          <div slot="header" class="clearfix">
            <span>已选择的任务 ({{ batchTasks.length }})</span>
          </div>
          <el-tabs v-model="activeTabName" type="card" closable @tab-remove="removeBatchTask">
            <el-tab-pane 
              v-for="(task, index) in batchTasks" 
              :key="task.id"
              :label="`${task.accountName} - ${task.templateName}`"
              :name="task.id">
              <div class="template-container">
                <div class="config-item">
                  <span class="label">文章提示词：</span>
                  <el-input type="textarea" :rows="7" v-model="task.templateInfo.copywriterPrompt" />
                </div>
                <div class="config-item">
                  <span class="label">提示词：</span>
                  <el-input type="textarea" :rows="7" v-model="task.templateInfo.prompt" />
                </div>
                <el-row>
                  <el-col :span="16">
                    <el-card class="config-section">
                      <div class="section-header">
                        <h3>基础配置</h3>
                      </div>
                      <div class="config-item">
                        <span class="label">账号名称：</span>
                        <el-tag type="info">{{ task.templateInfo.baseConfig.accountName }}</el-tag>
                      </div>
                      <div class="config-item">
                        <span class="label">PSD路径：</span>
                        <el-tooltip :content="task.templateInfo.baseConfig.psdLocalPath">
                          <span class="path">{{ task.templateInfo.baseConfig.psdLocalPath }}</span>
                        </el-tooltip>
                      </div>
                      <div class="config-item">
                        <span class="label">输出路径：</span>
                        <el-tooltip :content="task.templateInfo.baseConfig.imageSavePath">
                          <span class="path">{{ task.templateInfo.baseConfig.imageSavePath }}</span>
                        </el-tooltip>
                      </div>
                    </el-card>
                  </el-col>
                  <el-col :span="8">
                    <el-card class="config-section right-panel">
                      <div class="section-header">
                        <h3>模板预览</h3>
                        <el-tooltip content="点击可放大查看" placement="top">
                          <i class="el-icon-info"></i>
                        </el-tooltip>
                      </div>
                      <image-preview :src="task.imageList" :width="100" :height="100"></image-preview>
                    </el-card>
                  </el-col>
                </el-row>

                <el-card
                  v-for="(imgConfig, imgIndex) in task.templateInfo.imageConfigs"
                  :key="imgIndex"
                  class="config-section image-config">
                  <div class="section-header">
                    <h3>图片配置 {{ imgIndex + 1 }}</h3>
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
                  <div class="config-item">
                    <span class="label">目标文件夹：</span>
                    <el-tag>{{ imgConfig.folderName }}</el-tag>
                    <span v-if="imgConfig.hasSubfolder" class="subfolder">
                      / {{ imgConfig.subfolderName }}
                    </span>
                  </div>
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
                </el-card>
              </div>
            </el-tab-pane>
          </el-tabs>
        </el-card>
        <div v-if="form.jsonInfo" class="json-preview-container">
          <el-card class="json-card">
            <div slot="header" class="json-header">
              <span>JSON 响应数据</span>
              <el-button type="text" @click="toggleJsonView" class="toggle-btn">
                {{ showRawJson ? '展开结构' : '原始数据' }}
              </el-button>
            </div>
            <div v-if="!showRawJson" class="formatted-json">
              <div v-for="(value, key) in parsedJson" :key="key" class="json-item">
                <span class="json-key">{{ key }}:</span>
                <span class="json-value">{{ value }}</span>
              </div>
            </div>
            <pre v-else class="raw-json">{{ formatJson(form.jsonInfo) }}</pre>
          </el-card>
        </div>
      </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitBatchForm" :disabled="loading || batchTasks.length === 0">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="发布公众号"
      :visible.sync="accountOptionsVisible"
      custom-class="publish-dialog"
      v-loading="loading"
      width="30%">
      <el-input  v-model="gzhName" placeholder="请输入公众号名称" autocomplete="off"></el-input>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="pushConfirm">确 定</el-button>
        <el-button @click="accountOptionsVisible = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 抖音发布对话框 -->
    <douyin-publish-dialog
      :visible.sync="douyinDialogVisible"
      :task-row="currentRow"
      @success="handleDouyinPublishSuccess"
    />

    <!-- 手动创建任务弹窗 -->
    <el-dialog
      :title="isEditMode ? '编辑任务' : '手动创建任务'"
      :visible.sync="manualDialogVisible"
      width="80%"
      append-to-body
      :close-on-click-modal="false"
      class="manual-task-dialog">
      <div v-loading="loading" class="dialog-loading-wrapper">
      <el-form ref="manualForm" label-width="120px">
        <el-form-item label="作图账号名称" prop="accountName">
          <el-select
            v-model="manualForm.accountName"
            placeholder="请选择账号"
            filterable
            clearable
            style="width: 100%"
            @change="manualFilterTemplate"
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
            v-model="manualForm.templateName"
            placeholder="请选择模板"
            filterable
            clearable
            style="width: 100%"
            @change="manualTemplateNameChange"
            :popper-append-to-body="false"
          >
            <el-option
              v-for="item in manualTemplateOptionsFilter"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>

        <!-- 模板信息展示 -->
        <div v-if="manualTemplateInfo.baseConfig.psdLocalPath" class="manual-template-container">
          <el-card class="manual-config-section">
            <div class="section-header">
              <h3>基础配置</h3>
            </div>
            <div class="config-item">
              <span class="label">账号名称：</span>
              <el-tag type="info">{{ manualTemplateInfo.baseConfig.accountName }}</el-tag>
            </div>
            <div class="config-item">
              <span class="label">PSD路径：</span>
              <el-tooltip :content="manualTemplateInfo.baseConfig.psdLocalPath">
                <span class="path">{{ manualTemplateInfo.baseConfig.psdLocalPath }}</span>
              </el-tooltip>
            </div>
            <div class="config-item">
              <span class="label">输出路径：</span>
              <el-tooltip :content="manualTemplateInfo.baseConfig.imageSavePath">
                <span class="path">{{ manualTemplateInfo.baseConfig.imageSavePath }}</span>
              </el-tooltip>
            </div>
          </el-card>

          <div v-for="(templateImgConfig, templateIndex) in manualTemplateInfo.imageConfigs" :key="templateIndex" class="manual-image-config-wrapper">
            <el-card class="manual-image-config-card">
              <div class="section-header">
                <h3>{{ templateImgConfig.folderName }}</h3>
              </div>
              <div v-for="(manualConfig, configIndex) in manualImageConfigs[templateIndex]" :key="configIndex" class="manual-config-item">
                <el-card shadow="hover" class="config-item-card">
                  <div slot="header" class="config-item-header">
                    <span>配置项 {{ configIndex + 1 }}</span>
                    <el-button type="text" icon="el-icon-delete" @click="removeManualImageConfig(templateIndex, configIndex)">删除</el-button>
                  </div>
                  <div v-for="(layer, layerKey) in templateImgConfig.textLayerConfigs" :key="layerKey" class="text-layer-config">
                    <el-card shadow="never" class="layer-config-card">
                      <div slot="header" class="layer-header">
                        <span>{{ layerKey }}</span>
                      </div>
                      <el-form-item label="名称">
                        <el-input v-model="manualConfig.textLayerConfigs[layerKey].name" placeholder="图层名称" />
                      </el-form-item>
                      <el-form-item label="每行最大字符数">
                        <el-input-number
                          v-model="manualConfig.textLayerConfigs[layerKey].maxCharsPerLine"
                          :min="1"
                          :max="100"
                          controls-position="right"
                        />
                      </el-form-item>
                      <el-form-item label="示例文本">
                        <el-input
                          v-model="manualConfig.textLayerConfigs[layerKey].sampleText"
                          type="textarea"
                          :rows="3"
                          placeholder="请输入文本内容"
                        />
                      </el-form-item>
                    </el-card>
                  </div>
                  <!-- 在每个配置项底部添加"添加配置项"按钮 -->
                  <div class="add-config-btn-wrapper">
                    <el-button type="primary" size="small" icon="el-icon-plus" @click="addManualImageConfig(templateIndex)">
                      添加配置项
                    </el-button>
                  </div>
                </el-card>
              </div>
              <!-- 如果没有配置项，显示添加按钮 -->
              <div v-if="!manualImageConfigs[templateIndex] || manualImageConfigs[templateIndex].length === 0" class="empty-config-wrapper">
                <el-button type="primary" size="small" icon="el-icon-plus" @click="addManualImageConfig(templateIndex)">
                  添加配置项
                </el-button>
              </div>
            </el-card>
          </div>

          <el-card class="manual-config-section">
            <div class="section-header">
              <h3>标题（可选）</h3>
            </div>
            <el-form-item label="标题内容">
              <el-input
                v-model="manualForm.title"
                placeholder="请输入标题内容，可选，最多30字"
                maxlength="20"
                show-word-limit
              />
            </el-form-item>
          </el-card>

          <el-card class="manual-config-section">
            <div class="section-header">
              <h3>文案（可选）</h3>
            </div>
            <el-form-item label="文案内容">
              <el-input
                v-model="manualForm.copywriter"
                type="textarea"
                :rows="10"
                placeholder="请输入文案内容，可选"
              />
            </el-form-item>
          </el-card>
        </div>
      </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitManualForm" :disabled="loading || !canSubmitManual">
          {{ isEditMode ? '再次创建任务' : '确 定' }}
        </el-button>
        <el-button @click="cancelManual">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {listTask, getTask, delTask, addTask, updateTask, getCoze, pushOfficialAccount, addManualTask} from "@/api/custom/task";
import DouyinPublishDialog from '@/components/DouyinPublishDialog.vue';
 import {getImage, getListPSDConfigAll} from "@/api/custom/psd";
import {isEmpty} from "@/utils/validate";

export default {
  name: "Task",
  dicts: ['psd_task_status'],
  components: {
    DouyinPublishDialog
  },
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
        copywriterPrompt: '',
        prompt: '',
        baseConfig: {
          accountName: '',
          psdLocalPath: '',
          imageSavePath: '',
        },
        imageConfigs: [] // 每个元素需要包含 generateCount 字段
      },
      showRawJson: false, // 切换显示模式
      psdList: [],
      templateOptions: [], // 模板下拉选项
      templateOptionsFilter: [], // 模板下拉选项过滤后的数据
      accountOptions: [],   // 账号下拉选项
      imageList: '',
      gzhName: '',
      accountOptionsVisible: false,
      currentRow: {},
      // 抖音发布相关
      douyinDialogVisible: false,
      // 手动创建相关
      manualDialogVisible: false,
      manualForm: {
        accountName: '',
        templateName: '',
        title: '',
        copywriter: ''
      },
      manualTemplateInfo: {
        baseConfig: {
          accountName: '',
          psdLocalPath: '',
          imageSavePath: '',
        },
        imageConfigs: []
      },
      manualTemplateOptionsFilter: [],
      // manualImageConfigs 是一个对象，key是模板图片配置的索引，value是该配置的所有手动配置项数组
      // 例如: { 0: [{textLayerConfigs: {...}}, {textLayerConfigs: {...}}], 1: [...] }
      manualImageConfigs: {},
      // 编辑模式标识
      isEditMode: false,
      currentEditTask: null,
      // 批量任务创建相关
      batchSelection: {
        accountName: '',
        templateName: ''
      },
      batchTemplateOptionsFilter: [],
      batchTasks: [],
      activeTabName: '',
      batchTaskIdCounter: 0
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
    },
    // 是否可以提交手动创建表单
    canSubmitManual() {
      if (!this.manualTemplateInfo.baseConfig.psdLocalPath) {
        return false;
      }
      // 检查是否至少有一个图片配置有配置项
      const hasConfigs = Object.values(this.manualImageConfigs).some(configs =>
        Array.isArray(configs) && configs.length > 0
      );
      return hasConfigs;
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
      this.resetBatchTasks();
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
      this.resetTemplateInfo();
      this.resetBatchTasks();
      this.loadAccountAndTemplateOptions().then(() => {
        this.reset();
        this.batchTemplateOptionsFilter = [...this.templateOptions];
        this.open = true;
        this.title = "批量添加任务";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getTask(id).then(response => {
        this.resetTemplateInfo();
        this.form = response.data;
        this.open = true;
        this.title = "修改任务";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.loading = true;
          this.form.config = JSON.stringify(this.templateInfo);
          if (this.form.id != null) {
            updateTask(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.resetTemplateInfo();
            }).finally(() => {this.loading = false;});
          } else {
            addTask(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.resetTemplateInfo();
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
    // 修改后代码：从本地缓存 psdList 精确匹配获取模板信息
    getTemplateInfo() {
      this.loading = true;
      try {
        const match = this.psdList.find(item => {
          try {
            const cfg = JSON.parse(item.config);
            const bc = cfg && cfg.baseConfig ? cfg.baseConfig : {};
            return bc.accountName === this.form.accountName && bc.templateName === this.form.templateName;
          } catch (e) {
            return false;
          }
        });

        if (match) {
          if (match.images) {
            this.imageList = match.images;
          }
          const parsedConfig = JSON.parse(match.config);
          Object.assign(this.templateInfo.baseConfig, parsedConfig.baseConfig);
          this.templateInfo.copywriterPrompt = parsedConfig.copywriterPrompt;
          this.templateInfo.prompt = parsedConfig.prompt;
          this.templateInfo.imageConfigs = (parsedConfig.imageConfigs || []).map(config => ({
            ...config,
            generateCount: config.generateCount || 1
          }));
          this.$modal.msgSuccess("模板信息获取成功");
        } else {
          this.$modal.msgError("未找到匹配的模板配置");
          this.resetTemplateInfo();
        }
      } finally {
        this.loading = false;
      }
    },
    /** 自动创建 - 模板名称改变时自动获取模板信息 */
    templateNameChange() {
      if (!this.isEmpty(this.form.accountName) && !this.isEmpty(this.form.templateName)) {
        this.getTemplateInfo();
      } else {
        this.resetTemplateInfo();
      }
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
      this.form.templateName = '';
      if (!this.form.accountName) {
        this.templateOptionsFilter = [...this.templateOptions];
        this.resetTemplateInfo();
        return;
      }
      this.templateOptionsFilter = this.getFilteredTemplates(this.form.accountName);
    },

    /** 打开公众号发布对话框 */
    openOfficialAccount(row) {
      this.currentRow = row;
      this.gzhName = '';
      this.accountOptionsVisible = true;
    },
    /** 加载账号和模板选项 */
    loadAccountAndTemplateOptions() {
      return getListPSDConfigAll().then(response => {
        this.psdList = response.rows;
        this.psdList.forEach(item => {
          try {
            const config = JSON.parse(item.config);
            const { templateName, accountName } = config.baseConfig;

            if (!this.templateOptions.find(opt => opt.value === templateName)) {
              this.templateOptions.push({
                value: templateName,
                label: templateName
              });
            }

            if (!this.accountOptions.find(opt => opt.value === accountName)) {
              this.accountOptions.push({
                value: accountName,
                label: accountName
              });
            }
          } catch (e) {
            // JSON解析失败，跳过该项
          }
        });
        this.templateOptionsFilter = [...this.templateOptions];
      });
    },
    /** 根据账号名称过滤模板 */
    getFilteredTemplates(accountName) {
      const relatedTemplates = this.psdList
        .filter(item => {
          try {
            const config = JSON.parse(item.config);
            return config.baseConfig?.accountName === accountName;
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
        .filter(Boolean);

      return [...new Set(relatedTemplates)].map(name => ({
        value: name,
        label: name
      }));
    },
    /** 重置自动创建模板信息 */
    resetTemplateInfo() {
      this.templateInfo = {
        copywriterPrompt: '',
        prompt: '',
        baseConfig: {
          accountName: '',
          psdLocalPath: '',
          imageSavePath: '',
        },
        imageConfigs: []
      };
      this.imageList = '';
    },
    /** 重置手动创建模板信息 */
    resetManualTemplateInfo() {
      this.manualTemplateInfo = {
        baseConfig: {
          accountName: '',
          psdLocalPath: '',
          imageSavePath: '',
        },
        imageConfigs: []
      };
      this.manualImageConfigs = {};
    },
    /** 重置批量任务 */
    resetBatchTasks() {
      this.batchTasks = [];
      this.batchSelection = {
        accountName: '',
        templateName: ''
      };
      this.activeTabName = '';
      this.batchTaskIdCounter = 0;
    },
    /** 批量选择 - 过滤模板 */
    batchFilterTemplate() {
      this.batchSelection.templateName = '';
      if (!this.batchSelection.accountName) {
        this.batchTemplateOptionsFilter = [...this.templateOptions];
        return;
      }
      this.batchTemplateOptionsFilter = this.getFilteredTemplates(this.batchSelection.accountName);
    },
    /** 批量选择 - 添加任务 */
    async addBatchTask() {
      if (!this.batchSelection.accountName || !this.batchSelection.templateName) {
        this.$modal.msgWarning('请选择账号和模板');
        return;
      }

      // 检查是否已经添加过相同的账号-模板组合
      const exists = this.batchTasks.some(task => 
        task.accountName === this.batchSelection.accountName && 
        task.templateName === this.batchSelection.templateName
      );
      
      if (exists) {
        this.$modal.msgWarning('该账号-模板组合已添加');
        return;
      }

      this.loading = true;
      try {
        // 从 psdList 中获取模板信息
        const match = this.psdList.find(item => {
          try {
            const cfg = JSON.parse(item.config);
            const bc = cfg && cfg.baseConfig ? cfg.baseConfig : {};
            return bc.accountName === this.batchSelection.accountName && 
                   bc.templateName === this.batchSelection.templateName;
          } catch (e) {
            return false;
          }
        });

        if (match) {
          const parsedConfig = JSON.parse(match.config);
          const taskId = `task_${this.batchTaskIdCounter++}`;
          
          const newTask = {
            id: taskId,
            accountName: this.batchSelection.accountName,
            templateName: this.batchSelection.templateName,
            imageList: match.images || '',
            templateInfo: {
              copywriterPrompt: parsedConfig.copywriterPrompt || '',
              prompt: parsedConfig.prompt || '',
              baseConfig: { ...parsedConfig.baseConfig },
              imageConfigs: (parsedConfig.imageConfigs || []).map(config => ({
                ...config,
                generateCount: config.generateCount || 1
              }))
            }
          };

          this.batchTasks.push(newTask);
          this.activeTabName = taskId;
          
          // 清空选择
          this.batchSelection.accountName = '';
          this.batchSelection.templateName = '';
          this.batchTemplateOptionsFilter = [...this.templateOptions];
          
          this.$modal.msgSuccess('添加成功');
        } else {
          this.$modal.msgError('未找到匹配的模板配置');
        }
      } finally {
        this.loading = false;
      }
    },
    /** 批量选择 - 移除任务 */
    removeBatchTask(taskId) {
      const index = this.batchTasks.findIndex(task => task.id === taskId);
      if (index !== -1) {
        this.batchTasks.splice(index, 1);
        if (this.batchTasks.length > 0) {
          this.activeTabName = this.batchTasks[0].id;
        } else {
          this.activeTabName = '';
        }
      }
    },
    /** 批量提交表单 */
    async submitBatchForm() {
      if (this.batchTasks.length === 0) {
        this.$modal.msgWarning('请至少添加一个任务');
        return;
      }

      this.$modal.confirm(`确认要创建 ${this.batchTasks.length} 个任务吗？`)
        .then(async () => {
          this.loading = true;
          let successCount = 0;
          let failCount = 0;

          for (const task of this.batchTasks) {
            try {
              const formData = {
                accountName: task.accountName,
                templateName: task.templateName,
                config: JSON.stringify(task.templateInfo)
              };
              
              await addTask(formData);
              successCount++;
            } catch (error) {
              failCount++;
              console.error(`创建任务失败 [${task.accountName} - ${task.templateName}]:`, error);
            }
          }

          this.loading = false;
          
          if (failCount === 0) {
            this.$modal.msgSuccess(`成功创建 ${successCount} 个任务`);
          } else {
            this.$modal.msgWarning(`成功创建 ${successCount} 个任务，失败 ${failCount} 个`);
          }
          
          this.open = false;
          this.resetBatchTasks();
          this.getList();
        })
        .catch(() => {
          // 用户取消
        });
    },
    pushConfirm() {
      this.$modal.confirm('确认要发布吗？')
        .then(() => {
          this.loading = true;
          // 箭头函数内的 this 仍指向 Vue 组件
          this.currentRow.gzhName = this.gzhName
          pushOfficialAccount(this.currentRow)
            .then(res => {
              if (res.code === 200) {
                this.$modal.msgSuccess("发布成功")
              } else {
                this.$modal.msgError("发布失败")
              }
            })
            .catch(() => {
              this.$modal.msgError("发布失败")
            })
            .finally(() => {
              this.accountOptionsVisible = false
              this.loading = false;
              this.getList();
            })
        })
        .catch(() => {
          // 用户点了取消
        })
    },

    /** 打开抖音发布对话框 */
    openDouyinPublish(row) {
      this.currentRow = row;
      this.douyinDialogVisible = true;
    },

    /** 抖音发布成功回调 */
    handleDouyinPublishSuccess() {
      this.getList();
    },
    /** 手动创建按钮操作 */
    handleManualAdd() {
      this.isEditMode = false;
      this.currentEditTask = null;
      this.manualForm = {
        accountName: '',
        templateName: '',
        title: '',
        copywriter: ''
      };
      this.resetManualTemplateInfo();
      this.loadAccountAndTemplateOptions().then(() => {
        this.manualTemplateOptionsFilter = [...this.templateOptions];
        this.manualDialogVisible = true;
      });
    },
    /** 手动创建 - 过滤模板 */
    manualFilterTemplate() {
      this.manualForm.templateName = '';
      if (!this.manualForm.accountName) {
        this.manualTemplateOptionsFilter = [...this.templateOptions];
        this.resetManualTemplateInfo();
        return;
      }
      this.manualTemplateOptionsFilter = this.getFilteredTemplates(this.manualForm.accountName);
    },
    /** 手动创建 - 获取模板信息 */
    async manualGetTemplateInfo() {
      this.loading = true;
      try {
        const match = this.psdList.find(item => {
          try {
            const cfg = JSON.parse(item.config);
            const bc = cfg && cfg.baseConfig ? cfg.baseConfig : {};
            return bc.accountName === this.manualForm.accountName && bc.templateName === this.manualForm.templateName;
          } catch (e) {
            return false;
          }
        });

        if (match && match.config) {
          const parsedConfig = JSON.parse(match.config);
          // 合并基础配置
          Object.assign(this.manualTemplateInfo.baseConfig, parsedConfig.baseConfig);
          // 设置图片配置数组
          this.manualTemplateInfo.imageConfigs = parsedConfig.imageConfigs || [];
          // 初始化手动配置项对象
          this.manualImageConfigs = {};
          // 只有在非编辑模式下才为每个图片配置自动添加第一个配置项
          if (!this.isEditMode && this.manualTemplateInfo.imageConfigs.length > 0) {
            this.manualTemplateInfo.imageConfigs.forEach((templateImgConfig, index) => {
              this.addManualImageConfig(index);
            });
          }
          this.$modal.msgSuccess("模板信息获取成功");
        } else {
          this.$modal.msgError("未找到匹配的模板配置");
        }
      } finally {
        this.loading = false;
      }
    },
    /** 手动创建 - 模板名称改变时自动获取模板信息 */
    manualTemplateNameChange() {
      if (!this.isEmpty(this.manualForm.accountName) && !this.isEmpty(this.manualForm.templateName)) {
        this.manualGetTemplateInfo();
      } else {
        this.resetManualTemplateInfo();
      }
    },
    /** 手动创建 - 添加图片配置项 */
    addManualImageConfig(templateIndex) {
      if (!this.manualImageConfigs[templateIndex]) {
        this.$set(this.manualImageConfigs, templateIndex, []);
      }

      const templateImgConfig = this.manualTemplateInfo.imageConfigs[templateIndex];
      if (!templateImgConfig) {
        return;
      }

      // 创建一个新的配置项，基于模板的textLayerConfigs结构
      const newConfig = {
        textLayerConfigs: {}
      };

      // 复制模板的textLayerConfigs结构，默认使用模板的所有值
      Object.keys(templateImgConfig.textLayerConfigs).forEach(layerKey => {
        const layer = templateImgConfig.textLayerConfigs[layerKey];
        newConfig.textLayerConfigs[layerKey] = {
          maxCharsPerLine: layer.maxCharsPerLine || 10,
          name: layer.name || '',
          sampleText: layer.sampleText || ''
        };
      });

      this.manualImageConfigs[templateIndex].push(newConfig);
    },
    /** 手动创建 - 删除图片配置项 */
    removeManualImageConfig(templateIndex, configIndex) {
      if (this.manualImageConfigs[templateIndex] && this.manualImageConfigs[templateIndex][configIndex]) {
        this.manualImageConfigs[templateIndex].splice(configIndex, 1);
      }
    },
    /** 手动创建 - 取消 */
    cancelManual() {
      this.manualDialogVisible = false;
      this.manualForm = {
        accountName: '',
        templateName: '',
        title: '',
        copywriter: ''
      };
      this.manualTemplateInfo = {
        baseConfig: {
          accountName: '',
          psdLocalPath: '',
          imageSavePath: '',
        },
        imageConfigs: []
      };
      this.manualImageConfigs = {};
      // 重置编辑模式
      this.isEditMode = false;
      this.currentEditTask = null;
    },
    /** 手动创建 - 提交表单 */
    submitManualForm() {
      if (!this.canSubmitManual) {
        this.$modal.msgWarning("请至少为一个图片配置添加配置项");
        return;
      }

      // 构建最终的imageConfigs数组
      const finalImageConfigs = [];

      // 遍历每个模板图片配置
      this.manualTemplateInfo.imageConfigs.forEach((templateImgConfig, templateIndex) => {
        const manualConfigs = this.manualImageConfigs[templateIndex] || [];

        // 为每个手动配置项创建一个完整的imageConfig
        manualConfigs.forEach(manualConfig => {
          const imageConfig = {
            folderName: templateImgConfig.folderName,
            generateCount: String(templateImgConfig.generateCount || 1), // 使用模板保存的生成数量，默认为1
            hasSubfolder: templateImgConfig.hasSubfolder || false,
            subfolderName: templateImgConfig.subfolderName || "",
            textLayerConfigs: {}
          };

          // 复制textLayerConfigs，保留maxCharsPerLine和name，使用手动填写的sampleText
          Object.keys(manualConfig.textLayerConfigs).forEach(layerKey => {
            const manualLayer = manualConfig.textLayerConfigs[layerKey];
            const templateLayer = templateImgConfig.textLayerConfigs[layerKey];

            imageConfig.textLayerConfigs[layerKey] = {
              maxCharsPerLine: String(manualLayer.maxCharsPerLine || templateLayer.maxCharsPerLine || '10'),
              name: manualLayer.name || templateLayer.name || '',
              sampleText: manualLayer.sampleText || ''
            };
          });

          finalImageConfigs.push(imageConfig);
        });
      });

      if (finalImageConfigs.length === 0) {
        this.$modal.msgWarning("请至少添加一个配置项");
        return;
      }

      // 构建最终提交的数据
      const submitData = {
        accountName: this.manualTemplateInfo.baseConfig.accountName,
        templateName: this.manualTemplateInfo.baseConfig.templateName,
        config: JSON.stringify({
          baseConfig: this.manualTemplateInfo.baseConfig,
          imageConfigs: finalImageConfigs,
          title: this.manualForm.title || undefined,
          copywriter: this.manualForm.copywriter || undefined
        })
      };

      this.loading = true;
      addManualTask(submitData).then(response => {
        this.$modal.msgSuccess(this.isEditMode ? "任务重新创建成功" : "手动创建任务成功");
        this.manualDialogVisible = false;
        this.getList();
        this.cancelManual();
      }).catch(error => {
        this.$modal.msgError((this.isEditMode ? "任务重新创建失败：" : "手动创建任务失败：") + (error.msg || error.message || "未知错误"));
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 编辑任务按钮操作 */
    handleEditTask(row) {
      this.isEditMode = true;
      this.currentEditTask = row;
      this.manualForm = {
        accountName: row.accountName,
        templateName: row.templateName,
        copywriter: ''
      };
      this.resetManualTemplateInfo();
      this.loadAccountAndTemplateOptions().then(() => {
        this.manualTemplateOptionsFilter = this.getFilteredTemplates(row.accountName);
        // 先获取模板信息，然后在获取成功后填充配置
        this.manualGetTemplateInfo().then(() => {
          // 解析任务的config JSON并填充到手动创建表单
          this.populateManualFormFromConfig(row.config);
        });
        this.manualDialogVisible = true;
      });
    },
    /** 从config JSON填充手动创建表单 */
    populateManualFormFromConfig(configJson) {
      try {
        const config = JSON.parse(configJson);

        // 设置标题和文案
        this.manualForm.title = config.title || '';
        this.manualForm.copywriter = config.copywriter || '';

        // 重新初始化手动配置项对象
        this.manualImageConfigs = {};

        // 根据config中的imageConfigs创建手动配置项
        if (config.imageConfigs && config.imageConfigs.length > 0) {
          // 按照模板的imageConfigs顺序来映射-ui
          this.manualTemplateInfo.imageConfigs.forEach((templateImgConfig, templateIndex) => {
            this.$set(this.manualImageConfigs, templateIndex, []);

            // 找到与当前模板配置匹配的config配置项
            const matchingConfigs = config.imageConfigs.filter(imgConfig =>
              imgConfig.folderName === templateImgConfig.folderName &&
              imgConfig.subfolderName === templateImgConfig.subfolderName
            );

            // 为每个匹配的配置项创建手动配置
            matchingConfigs.forEach(imgConfig => {
              const manualConfig = {
                textLayerConfigs: {}
              };

              // 复制textLayerConfigs
              Object.keys(templateImgConfig.textLayerConfigs).forEach(layerKey => {
                const configLayer = imgConfig.textLayerConfigs[layerKey];
                const templateLayer = templateImgConfig.textLayerConfigs[layerKey];

                if (configLayer) {
                  manualConfig.textLayerConfigs[layerKey] = {
                    maxCharsPerLine: parseInt(configLayer.maxCharsPerLine) || parseInt(templateLayer.maxCharsPerLine) || 10,
                    name: configLayer.name || templateLayer.name || '',
                    sampleText: configLayer.sampleText || ''
                  };
                } else {
                  // 如果config中没有这个层，使用模板默认值
                  manualConfig.textLayerConfigs[layerKey] = {
                    maxCharsPerLine: parseInt(templateLayer.maxCharsPerLine) || 10,
                    name: templateLayer.name || '',
                    sampleText: templateLayer.sampleText || ''
                  };
                }
              });

              this.manualImageConfigs[templateIndex].push(manualConfig);
            });
          });
        }

        this.$modal.msgSuccess("任务配置加载成功");
      } catch (error) {
        console.error('解析config JSON失败:', error);
        this.$modal.msgError("解析任务配置失败");
      }
    }
  }
};
</script>

<style scoped>
.batch-selection-card {
  margin-bottom: 20px;
}

.batch-selection-card >>> .el-card__header {
  background: #f5f7fa;
  font-weight: 600;
}

.batch-tasks-card {
  margin-top: 20px;
}

.batch-tasks-card >>> .el-card__header {
  background: #ecf5ff;
  font-weight: 600;
  color: #409EFF;
}

.template-container {
  margin-top: 20px;
  background: #f5f7fa;
  padding: 15px;
  border-radius: 4px;
}

.config-section {
  margin-bottom: 1px;
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

/* 手动创建相关样式 */
.manual-template-container {
  margin-top: 20px;
}

.manual-config-section {
  margin-bottom: 20px;
}

.manual-image-config-wrapper {
  margin-bottom: 20px;
}

.manual-image-config-card {
  margin-bottom: 15px;
}

.config-item-card {
  margin-bottom: 15px;
  border: 1px solid #e4e7ed;
}

.config-item-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text-layer-config {
  margin-bottom: 15px;
}

.layer-config-card {
  margin-bottom: 10px;
  border: 1px solid #e4e7ed;
}

.layer-header {
  font-weight: bold;
  color: #409EFF;
}

.layer-info {
  margin-top: 5px;
}

/* 对话框loading优化 */
.dialog-loading-wrapper {
  min-height: 200px;
  position: relative;
}

.manual-task-dialog .el-loading-mask,
.auto-task-dialog .el-loading-mask {
  position: absolute !important;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  width: 100% !important;
  height: 100% !important;
  z-index: 2000;
}

/* 添加配置项按钮样式 */
.add-config-btn-wrapper {
  margin-top: 15px;
  padding-top: 15px;
  border-top: 1px dashed #e4e7ed;
  text-align: center;
}

.empty-config-wrapper {
  padding: 30px 0;
  text-align: center;
}


</style>
