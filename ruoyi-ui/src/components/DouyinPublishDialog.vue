<template>
  <el-dialog
    title="发布抖音"
    :visible.sync="dialogVisible"
    custom-class="publish-dialog"
    v-loading="loading"
    element-loading-text="正在加载账号列表..."
    width="500px"
    @close="handleClose">
    <el-form :model="form" :rules="rules" ref="douyinForm" label-width="120px">
      <el-form-item label="选择账号" prop="accountFile">
        <el-select v-model="form.accountFile" placeholder="请选择抖音账号" style="width: 100%">
          <el-option
            v-for="account in douyinAccounts"
            :key="account.filePath"
            :label="account.name"
            :value="account.filePath">
          </el-option>
        </el-select>
        <div v-if="accountNotFound" style="color: #F56C6C; font-size: 12px; margin-top: 5px;">
          未保存对应账号信息，请先保存
        </div>
      </el-form-item>

      <el-form-item label="文件夹路径">
        <el-input v-model="form.folderPath" placeholder="可选，留空则使用任务默认路径"></el-input>
        <div style="color: #909399; font-size: 12px; margin-top: 5px;">
          提示：如果任务已完成，留空即可；否则请输入完整的文件夹绝对路径（如：D:\images\task001）
        </div>
      </el-form-item>

      <el-form-item label="标题（可选）">
        <el-input
          v-model="form.title"
          placeholder="请输入标题，可选，留空则从文件夹读取title.txt"
          maxlength="20"
          show-word-limit>
        </el-input>
        <div style="color: #909399; font-size: 12px; margin-top: 5px;">
          提示：留空将从文件夹中读取title.txt文件
        </div>
      </el-form-item>

      <el-form-item label="文案（可选）">
        <el-input
          v-model="form.copywriter"
          type="textarea"
          :rows="4"
          placeholder="请输入文案描述，可选，留空则从文件夹读取copywriter.txt">
        </el-input>
        <div style="color: #909399; font-size: 12px; margin-top: 5px;">
          提示：留空将从文件夹中读取copywriter.txt文件
        </div>
      </el-form-item>

      <el-form-item label="背景音乐类型">
        <el-radio-group v-model="form.musicType">
          <el-radio label="search">搜索热门</el-radio>
          <el-radio label="fav">收藏音乐</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item :label="form.musicType === 'search' ? '音乐名称' : '收藏序号'">
        <el-input
          v-model="form.musicName"
          :placeholder="form.musicType === 'search' ? '请输入背景音乐名称（可选）' : '请输入收藏音乐序号（如：1）'">
        </el-input>
      </el-form-item>

      <el-form-item label="发布方式" prop="publishType">
        <el-radio-group v-model="form.publishType">
          <el-radio label="immediate">立即发布</el-radio>
          <el-radio label="scheduled">定时发布</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="发布时间" v-if="form.publishType === 'scheduled'" prop="publishTime">
        <el-date-picker
          v-model="form.publishTime"
          type="datetime"
          placeholder="选择发布时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          :picker-options="pickerOptions"
          style="width: 100%">
        </el-date-picker>
      </el-form-item>
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleConfirm">确 定</el-button>
      <el-button @click="handleClose">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getDouyinAccounts, publishToDouyin } from "@/api/custom/task";

export default {
  name: "DouyinPublishDialog",
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    taskRow: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      dialogVisible: false,
      loading: false,
      douyinAccounts: [],
      accountNotFound: false,
      form: {
        taskId: null,
        accountFile: '',
        folderPath: '',
        title: '',
        copywriter: '',
        musicName: '',
        musicType: 'search',
        publishType: 'immediate',
        publishTime: ''
      },
      rules: {
        accountFile: [
          { required: true, message: '请选择抖音账号', trigger: 'change' }
        ],
        publishType: [
          { required: true, message: '请选择发布方式', trigger: 'change' }
        ],
        publishTime: [
          { required: true, message: '请选择发布时间', trigger: 'change' }
        ]
      },
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;
        }
      }
    };
  },
  watch: {
    visible(val) {
      this.dialogVisible = val;
      if (val) {
        this.initDialog();
      }
    }
  },
  methods: {
    async initDialog() {
      this.accountNotFound = false;

      // 重置表单
      this.form = {
        taskId: this.taskRow.id,
        accountFile: '',
        folderPath: this.taskRow.realPath || '',
        title: '',
        copywriter: '',
        musicName: '',
        musicType: 'search',
        publishType: 'immediate',
        publishTime: ''
      };

      // 加载抖音账号列表
      this.loading = true;
      this.douyinAccounts = [];

      try {
        const response = await getDouyinAccounts();
        if (response.code === 200) {
          this.douyinAccounts = response.data;

          // 智能匹配账号
          const matchedAccount = this.douyinAccounts.find(
            account => account.name === this.taskRow.accountName
          );

          if (matchedAccount) {
            this.form.accountFile = matchedAccount.filePath;
          } else {
            this.accountNotFound = true;
            this.$message.warning('未找到对应账号信息，请先保存');
          }
        } else {
          this.$message.error('获取账号列表失败');
          this.handleClose();
        }

        // 从任务配置中解析标题和文案
        this.parseCopywriterFromConfig();
      } catch (error) {
        console.error('获取抖音账号列表失败:', error);
        this.$message.error('获取账号列表失败: ' + (error.message || ''));
        this.handleClose();
      } finally {
        this.loading = false;
      }
    },

    parseCopywriterFromConfig() {
      try {
        // 解析任务配置中的copywriter字段
        const config = JSON.parse(this.taskRow.config);
        const copywriterContent = config.copywriter;
        
        if (!copywriterContent) {
          console.log('任务配置中没有copywriter字段');
          return;
        }

        // 处理转义的换行符 \n 和实际的换行符
        const lines = copywriterContent
          .replace(/\\n/g, '\n')  // 将 \n 转换为实际换行符
          .split(/\r?\n/)
          .map(line => line.trim())
          .filter(line => line);
        
        console.log('解析内容，总行数:', lines.length);

        // 提取第一段：第一个#后的内容作为标题
        for (let i = 0; i < lines.length; i++) {
          const line = lines[i];
          if (line.startsWith('#')) {
            const title = line.replace(/^#+\s*/, '').trim();
            this.form.title = title;
            console.log('自动填充标题:', title);
            break;
          }
        }

        // 提取最后一段：最后一行包含多个#的内容作为文案
        for (let i = lines.length - 1; i >= 0; i--) {
          const line = lines[i];
          const hashCount = (line.match(/#/g) || []).length;
          if (hashCount >= 2) {
            this.form.copywriter = line;
            console.log('自动填充文案:', line);
            break;
          }
        }
      } catch (error) {
        console.error('解析copywriter失败:', error);
      }
    },

    handleConfirm() {
      this.$refs.douyinForm.validate(valid => {
        if (valid) {
          this.$modal.confirm('确认要发布到抖音吗？')
            .then(() => {
              this.loading = true;

              publishToDouyin(this.form)
                .then(response => {
                  if (response.code === 200) {
                    this.$modal.msgSuccess("发布任务已提交");
                    this.$emit('success');
                    this.handleClose();
                  } else {
                    this.$modal.msgError(response.msg || "发布失败");
                  }
                })
                .catch(error => {
                  console.error('发布失败:', error);
                  this.$modal.msgError("发布失败");
                })
                .finally(() => {
                  this.loading = false;
                });
            })
            .catch(() => {
              // 用户取消
            });
        }
      });
    },

    handleClose() {
      this.$emit('update:visible', false);
    }
  }
};
</script>

<style scoped>
/* 可以添加自定义样式 */
</style>
