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
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          icon="el-icon-plus"-->
<!--          size="mini"-->
<!--          @click="handleAdd"-->
<!--        >新增配置</el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="configList" style="width: 100%">
      <el-table-column label="模板名称" min-width="150" align="center">
        <template slot-scope="{row}">
          {{ getConfigValue(row.config, '基础配置.模板名称') }}
        </template>
      </el-table-column>
      <el-table-column label="账号名称" min-width="120" align="center">
        <template slot-scope="{row}">
          {{ getConfigValue(row.config, '基础配置.做图账号名称') }}
        </template>
      </el-table-column>
      <el-table-column label="PSD路径" min-width="200" show-overflow-tooltip>
        <template slot-scope="{row}">
          {{ getConfigValue(row.config, '基础配置.psd本地路径') }}
        </template>
      </el-table-column>
      <el-table-column label="保存路径" min-width="200" show-overflow-tooltip>
        <template slot-scope="{row}">
          {{ getConfigValue(row.config, '基础配置.图片保存路径') }}
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

    <!-- 新增/编辑对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px">
      <el-form ref="form" :model="form" :rules="rules" label-width="190px">
        <!-- 基础配置 -->
        <el-form-item label="模板名称" prop="templateName">
          <el-input v-model="form.templateName" placeholder="请输入模板名称" />
        </el-form-item>
        <el-form-item label="做图账号名称" prop="accountName">
          <el-input v-model="form.accountName" placeholder="请输入账号名称" />
        </el-form-item>
        <el-form-item label="PSD路径" prop="psdPath">
          <el-input v-model="form.psdPath" placeholder="请输入PSD完整路径" />
        </el-form-item>
        <el-form-item label="图片保存路径" prop="savePath">
          <el-input v-model="form.savePath" placeholder="请输入图片保存路径" />
        </el-form-item>

        <el-divider>图片配置</el-divider>
        <!-- 图片配置：支持多条配置 -->
        <div v-for="(picCfg, index) in form.图片配置" :key="index" style="border: 1px solid #ebeef5; padding: 15px; margin-bottom: 15px;">
          <div style="display: flex; justify-content: space-between; align-items: center;">
            <h3>图片配置 {{ index + 1 }}</h3>
            <el-button type="danger" size="mini" @click="removePicConfig(index)">删除配置</el-button>
          </div>
          <el-form-item label="图片所在文件夹名称">
            <el-input v-model="picCfg.图片所在文件夹名称" placeholder="请输入图片所在文件夹名称" />
          </el-form-item>
          <el-form-item label="是否有子文件夹">
            <el-switch v-model="picCfg.是否有子文件夹" :active-value="true" :inactive-value="false"></el-switch>
          </el-form-item>
          <el-form-item label="子文件夹名称" v-if="picCfg.是否有子文件夹">
            <el-input v-model="picCfg.子文件夹名称" placeholder="请输入子文件夹名称" />
          </el-form-item>

          <el-divider>文字图层配置</el-divider>
          <!-- 文字图层配置：遍历对象中每个图层 -->
          <div v-for="(layerCfg, key) in picCfg.文字图层配置" :key="key" style="margin-bottom: 10px;">
            <el-form-item :label="key + ' - 名称'">
              <el-input v-model="layerCfg.名称" placeholder="请输入图层名称" />
            </el-form-item>
            <el-form-item :label="key + ' - 原文示例'">
              <el-input v-model="layerCfg.原文示例" placeholder="请输入原文示例" />
            </el-form-item>
            <el-form-item :label="key + ' - 每行最大字符数'">
              <el-input v-model="layerCfg.每行最大字符数" placeholder="请输入每行最大字符数" />
            </el-form-item>
          </div>
          <el-form-item label="提示词">
            <el-input v-model="picCfg.提示词" placeholder="请输入提示词" />
          </el-form-item>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
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
      ids: [],
      showSearch: true,
      total: 0,
      configList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        templateName: undefined,
        accountName: undefined
      },
      // form中包含基础配置和图片配置
      form: {
        templateName: '',
        accountName: '',
        psdPath: '',
        savePath: '',
        图片配置: []
      },
      rules: {
        templateName: [
          { required: true, message: "模板名称不能为空", trigger: "blur" }
        ],
        accountName: [
          { required: true, message: "账号名称不能为空", trigger: "blur" }
        ],
        psdPath: [
          { required: true, message: "PSD路径不能为空", trigger: "blur" }
        ],
        savePath: [
          { required: true, message: "图片保存路径不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /**
     * 将返回的非标准 JSON 格式字符串转换为合法 JSON 对象
     * 例如：
     * "{基础配置={做图账号名称=2, psd本地路径=2, 图片保存路径=2, 模板名称=2}, 图片配置=[{...}]}"
     * 转换为合法 JSON 对象
     */
    parseConfig(configStr) {
      configStr = configStr.trim();
      if ((configStr.startsWith('"') && configStr.endsWith('"')) ||
        (configStr.startsWith("'") && configStr.endsWith("'"))) {
        configStr = configStr.substring(1, configStr.length - 1);
      }
      configStr = configStr.replace(/([{,]\s*)([^:{}\[\],]+)(?=\s*=)/g, '$1"$2"');
      configStr = configStr.replace(/=/g, ':');
      return JSON.parse(configStr);
    },
    // 根据路径获取配置中的值，路径用点分隔
    getConfigValue(config, path) {
      try {
        const configObj = this.parseConfig(config);
        return path.split('.').reduce((obj, key) => (obj && obj[key]) || '', configObj);
      } catch (e) {
        console.error(e);
        return '-';
      }
    },
    // 获取配置列表
    getList() {
      this.loading = true;
      listPSDConfig(this.queryParams).then(response => {
        this.configList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    reset() {
      this.form = {
        templateName: '',
        accountName: '',
        psdPath: '',
        savePath: '',
        图片配置: []
      };
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        templateName: undefined,
        accountName: undefined
      };
      this.handleQuery();
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加PSD配置";
    },
    handleUpdate(row) {
      this.reset();
      const config = this.parseConfig(row.config);
      this.form = {
        id: row.id,
        templateName: config["基础配置"]["模板名称"],
        accountName: config["基础配置"]["做图账号名称"],
        psdPath: config["基础配置"]["psd本地路径"],
        savePath: config["基础配置"]["图片保存路径"],
        图片配置: config["图片配置"] || []
      };
      this.open = true;
      this.title = "修改PSD配置";
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          const configData = {
            "基础配置": {
              "模板名称": this.form.templateName,
              "做图账号名称": this.form.accountName,
              "psd本地路径": this.form.psdPath,
              "图片保存路径": this.form.savePath
            },
            "图片配置": this.form.图片配置
          };
          const postData = {
            id: this.form.id,
            config: JSON.stringify(configData)
          };
          if (this.form.id) {
            updatePSDConfig(postData).then(response => {
              this.$message.success("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            // addPSDConfig(postData).then(response => {
            //   this.$message.success("新增成功");
            //   this.open = false;
            //   this.getList();
            // });
          }
        }
      });
    },
    cancel() {
      this.open = false;
    },
    handleDelete(row) {
      this.$confirm('确认删除该PSD配置?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        return delPSDConfig(row.id);
      }).then(() => {
        this.getList();
        this.$message.success("删除成功");
      });
    },
    // 删除指定索引的图片配置
    removePicConfig(index) {
      this.form.图片配置.splice(index, 1);
    },
  },
};
</script>

<style scoped>
.app-container {
  padding: 20px;
}
.el-form-item {
  margin-bottom: 15px;
}
.el-form--inline .el-form-item {
  width: 300px;
}
</style>
