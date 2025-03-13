<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="模板名称" prop="templateName">
        <el-input
          v-model="queryParams.templateName"
          placeholder="请输入模板名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="账号名称" prop="accountName">
        <el-input
          v-model="queryParams.accountName"
          placeholder="请输入做图账号"
          clearable
          size="small"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="configList" style="width: 100%">
      <el-table-column label="模板名称" min-width="150" align="center">
        <template slot-scope="{row}">
          {{ getConfigValue(row.config, 'baseConfig.templateName') }}
        </template>
      </el-table-column>
      <el-table-column label="账号名称" min-width="120" align="center">
        <template slot-scope="{row}">
          {{ getConfigValue(row.config, 'baseConfig.accountName') }}
        </template>
      </el-table-column>
      <el-table-column label="PSD路径" min-width="200" show-overflow-tooltip>
        <template slot-scope="{row}">
          {{ getConfigValue(row.config, 'baseConfig.psdLocalPath') }}
        </template>
      </el-table-column>
      <el-table-column label="保存路径" min-width="200" show-overflow-tooltip>
        <template slot-scope="{row}">
          {{ getConfigValue(row.config, 'baseConfig.imageSavePath') }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['namegenerator:psd:edit']"
          >编辑</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['namegenerator:psd:remove']"
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

    <!-- 编辑对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px">
      <el-form ref="form" :model="form" :rules="rules" label-width="190px">
        <!-- 基础配置 -->
        <el-form-item label="模板名称" prop="templateName">
          <el-input v-model="form.templateName" />
        </el-form-item>
        <el-form-item label="做图账号" prop="accountName">
          <el-input v-model="form.accountName" />
        </el-form-item>
        <el-form-item label="PSD路径" prop="psdLocalPath">
          <el-input v-model="form.psdLocalPath" />
        </el-form-item>
        <el-form-item label="保存路径" prop="imageSavePath">
          <el-input v-model="form.imageSavePath" />
        </el-form-item>

        <el-divider>图片配置</el-divider>
        <div v-for="(imgCfg, index) in form.imageConfigs" :key="index" class="config-block">
          <div class="config-header">
            <h3>图片配置 {{ index + 1 }}</h3>
            <el-button type="danger" size="mini" @click="removeImgConfig(index)">删除</el-button>
          </div>
          <el-form-item label="文件夹名称">
            <el-input v-model="imgCfg.folderName" />
          </el-form-item>
          <el-form-item label="包含子文件夹">
            <el-switch v-model="imgCfg.hasSubfolder" />
          </el-form-item>
          <el-form-item label="子文件夹名称" v-if="imgCfg.hasSubfolder">
            <el-input v-model="imgCfg.subfolderName" />
          </el-form-item>

          <el-divider>文字图层</el-divider>
          <div v-for="(layer, key) in imgCfg.textLayerConfigs" :key="key">
            <el-form-item :label="`${key} - 名称`">
              <el-input v-model="layer.name" />
            </el-form-item>
            <el-form-item :label="`${key} - 示例`">
              <el-input v-model="layer.sampleText" />
            </el-form-item>
            <el-form-item :label="`${key} - 字符限制`">
              <el-input v-model.number="layer.maxCharsPerLine" />
            </el-form-item>
          </div>
          <el-form-item label="提示词">
            <el-input v-model="imgCfg.prompt" />
          </el-form-item>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPSDConfig, updatePSDConfig, delPSDConfig } from "@/api/custom/psd";

export default {
  name: 'PSDConfig',
  data() {
    return {
      loading: true,
      showSearch: true,
      total: 0,
      configList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        templateName: null,
        accountName: null
      },
      form: {
        templateName: '',
        accountName: '',
        psdLocalPath: '',
        imageSavePath: '',
        imageConfigs: []
      },
      rules: {
        templateName: [{ required: true, message: "必填项", trigger: "blur" }],
        accountName: [{ required: true, message: "必填项", trigger: "blur" }],
        psdLocalPath: [{ required: true, message: "必填项", trigger: "blur" }],
        imageSavePath: [{ required: true, message: "必填项", trigger: "blur" }]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 数据获取方法
    getList() {
      this.loading = true;
      listPSDConfig(this.queryParams).then(res => {
        this.configList = res.rows;
        this.total = res.total;
        this.loading = false;
      });
    },

    // 查询处理
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },

    // 重置查询
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        templateName: null,
        accountName: null
      };
      this.getList();
    },

    // 数据解析
    getConfigValue(config, path) {
      try {
        const configObj = JSON.parse(config);
        return path.split('.').reduce((obj, key) => obj?.[key] ?? '-', configObj);
      } catch {
        return '-';
      }
    },

    // 表单处理
    handleUpdate(row) {
      this.resetForm();
      const config = JSON.parse(row.config);
      this.form = {
        id: row.id,
        templateName: config.baseConfig?.templateName || '',
        accountName: config.baseConfig?.accountName || '',
        psdLocalPath: config.baseConfig?.psdLocalPath || '',
        imageSavePath: config.baseConfig?.imageSavePath || '',
        imageConfigs: config.imageConfigs?.map(cfg => ({
          folderName: cfg.folderName,
          hasSubfolder: cfg.hasSubfolder,
          subfolderName: cfg.subfolderName,
          textLayerConfigs: cfg.textLayerConfigs,
          prompt: cfg.prompt
        })) || []
      };
      this.open = true;
      this.title = "编辑配置";
    },

    // 提交表单
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const postData = {
            id: this.form.id,
            config: JSON.stringify({
              baseConfig: {
                templateName: this.form.templateName,
                accountName: this.form.accountName,
                psdLocalPath: this.form.psdLocalPath,
                imageSavePath: this.form.imageSavePath
              },
              imageConfigs: this.form.imageConfigs.map(cfg => ({
                folderName: cfg.folderName,
                hasSubfolder: cfg.hasSubfolder,
                subfolderName: cfg.subfolderName,
                textLayerConfigs: cfg.textLayerConfigs,
                prompt: cfg.prompt
              }))
            })
          };

          updatePSDConfig(postData).then(() => {
            this.$message.success("更新成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },

    // 删除配置
    handleDelete(row) {
      this.$confirm("确认删除该配置？", "警告", {
        confirmButtonText: "确认",
        cancelButtonText: "取消"
      }).then(() => {
        return delPSDConfig(row.id);
      }).then(() => {
        this.getList();
        this.$message.success("删除成功");
      });
    },

    // 辅助方法
    resetForm() {
      this.form = {
        templateName: '',
        accountName: '',
        psdLocalPath: '',
        imageSavePath: '',
        imageConfigs: []
      };
    },

    removeImgConfig(index) {
      this.form.imageConfigs.splice(index, 1);
    },

    cancel() {
      this.open = false;
    }
  }
};
</script>

<style scoped>
.config-block {
  margin: 15px 0;
  padding: 15px;
  border: 1px solid #ebeef5;
  border-radius: 4px;
}

.config-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.el-form-item {
  margin-bottom: 15px;
}
</style>
