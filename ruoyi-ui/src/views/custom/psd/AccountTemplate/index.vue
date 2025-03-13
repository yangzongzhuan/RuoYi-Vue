<template>
  <div class="container">
    <h2>基础配置</h2>
    <div class="form-group">
      <label for="accountName">做图账号名称:</label>
      <input type="text" id="accountName" v-model="baseConfig.accountName" placeholder="请输入做图账号名称" />
    </div>
    <div class="form-group">
      <label for="templateName">本模板名称:</label>
      <input type="text" id="templateName" v-model="baseConfig.templateName" placeholder="请输入模板名称" />
    </div>
    <div class="form-group">
      <label for="psdPathInput">PSD路径:</label>
      <input type="text" id="psdPathInput" v-model="baseConfig.psdPath" placeholder="请复制粘贴PSD文件路径" />
    </div>
    <div class="form-group">
      <label for="imageSavePathInput">图片保存路径:</label>
      <input type="text" id="imageSavePathInput" v-model="baseConfig.imageSavePath" placeholder="请复制粘贴图片保存文件夹路径" />
    </div>

    <h2>图片配置</h2>
    <div v-for="(picCfg, index) in picConfigs" :key="index" class="layer-folder-group">
      <div class="header">
        <h3>图片配置 {{ index + 1 }}</h3>
        <button class="delete-btn" @click="removePicConfig(index)">删除配置</button>
      </div>
      <div class="form-group">
        <label>图片所在文件夹名称:</label>
        <input type="text" v-model="picCfg.folderName" placeholder="请输入图片所在文件夹名称" />
      </div>
      <div class="form-group inline-group">
        <label>有子文件夹:</label>
        <input type="checkbox" v-model="picCfg.hasSubfolder" />
        <input type="text" v-model="picCfg.subfolderName" :disabled="!picCfg.hasSubfolder" placeholder="请输入子文件夹名称" />
      </div>
      <div class="form-group">
        <label>要修改的文字图层数量:</label>
        <input type="number"
               v-model.number="picCfg.layersToModify"
               min="1"
               @change="updateTextLayerCount(picCfg)"/>
      </div>
      <div class="text-layer-group">
        <div
          v-for="(layer, idx) in picCfg.textLayers"
          :key="idx"
          class="text-layer-item"
        >
          <h4>文字图层 {{ idx + 1 }}</h4>
          <div class="form-group">
            <label>名称:</label>
            <input type="text" v-model="layer.name" placeholder="请输入图层名称" />
          </div>
          <div class="form-group">
            <label>原文示例:</label>
            <textarea v-model="layer.example" class="example-textarea" placeholder="请输入原文示例"></textarea>
          </div>
          <div class="form-group">
            <label>每行最大字符数:</label>
            <input type="number" v-model.number="layer.maxChars" placeholder="请输入每行最大字符数" />
          </div>
        </div>
      </div>
    </div>
    <button class="add-btn" @click="addPicConfig">添加图片配置</button>
    <button class="create-btn" @click="createAccountTemplate" v-hasPermi="['namegenerator:psd:add']">创建模板</button>
  </div>
</template>

<script>
import {saveTemplate} from "@/api/custom/psd";

export default {
  name: "AccountTemplate",
  data() {
    return {
      baseConfig: {
        accountName: "",
        templateName: "",
        psdPath: "", // 对应Java的psdLocalPath
        imageSavePath: ""
      },
      picConfigs: [{
        folderName: "",
        hasSubfolder: false,
        subfolderName: "",
        layersToModify: 1, // 原textLayerCount改为layersToModify
        prompt: "", // 新增图片配置级提示词
        textLayers: [{
          name: "",
          example: "",
          maxChars: 0,
        }]
      }]
    };
  },
  methods: {
    // 返回一个空的文字图层配置对象
    createBlankTextLayer() {
      return {
        name: "",
        example: "",
        maxChars: 0
      };
    },
    // 添加一条图片配置，默认初始文字图层数量为1
    addPicConfig() {
      const initialCount = 1;
      this.picConfigs.push({
        folderName: "",
        hasSubfolder: false,
        subfolderName: "",
        layersToModify: initialCount,
        textLayers: [this.createBlankTextLayer()]
      });
    },
    // 删除指定索引的图片配置
    removePicConfig(index) {
      this.picConfigs.splice(index, 1);
    },
    // 当文字图层数量发生变化时，根据新数量更新配置数组
    updateTextLayerCount(picCfg) {
      const newCount = picCfg.layersToModify;
      const currentCount = picCfg.textLayers.length;
      if (newCount > currentCount) {
        // 增加文字图层配置
        for (let i = currentCount; i < newCount; i++) {
          picCfg.textLayers.push(this.createBlankTextLayer());
        }
      } else if (newCount < currentCount) {
        // 截断多余的文字图层配置
        picCfg.textLayers.splice(newCount);
      }
    },
    // 构造模板数据后发送 AJAX 请求
    createAccountTemplate() {
      const template = {
        baseConfig: {
          accountName: this.baseConfig.accountName,
          templateName: this.baseConfig.templateName,
          psdLocalPath: this.baseConfig.psdPath, // 注意字段名映射
          imageSavePath: this.baseConfig.imageSavePath
        },
        imageConfigs: this.picConfigs.map(picCfg => {
          const textLayerConfigs = {};
          picCfg.textLayers.forEach((layer, index) => {
            textLayerConfigs[`textLayer${index + 1}`] = {
              name: layer.name,
              sampleText: layer.example,
              maxCharsPerLine: layer.maxChars,
            };
          });

          return {
            folderName: picCfg.folderName,
            hasSubfolder: picCfg.hasSubfolder,
            subfolderName: picCfg.subfolderName,
            layersToModify: picCfg.layersToModify, // 原textLayerCount改为layersToModify
            textLayerConfigs: textLayerConfigs,
            prompt: picCfg.prompt // 新增图片配置级提示词
          };
        })
      };

      saveTemplate(template).then(response => {
        if (response.code === 200) {
          this.$message.success("模板创建成功！");
        } else {
          this.$message.error("模板创建失败！");
        }
      })
    }
  }
};
</script>

<style scoped>
.container {
  font-family: Arial, sans-serif;
  padding: 20px;
}

.form-group {
  margin-bottom: 15px;
  display: flex;
  align-items: center;
}

.form-group label {
  width: 150px;
  margin-right: 10px;
}

.form-group input,
.form-group textarea {
  padding: 8px;
  width: 300px;
}

.layer-folder-group {
  margin-top: 20px;
  border: 1px solid #ccc;
  padding: 10px;
}

.text-layer-group {
  margin-left: 20px;
  margin-top: 10px;
}

.text-layer-item {
  border: 1px dashed #ddd;
  padding: 10px;
  margin-bottom: 10px;
}

.inline-group {
  display: flex;
  align-items: center;
}

.inline-group input[type="checkbox"] {
  width: auto;
  margin-right: 5px;
}

.example-textarea {
  width: 600px;
  height: 6em;
  padding: 8px;
  resize: vertical;
  box-sizing: border-box;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.delete-btn {
  background-color: #ff4d4f;
  color: #fff;
  border: none;
  padding: 5px 10px;
  cursor: pointer;
}

.add-btn,
.create-btn {
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #1890ff;
  color: #fff;
  border: none;
  cursor: pointer;
  margin-right: 10px;
}

.add-btn:hover,
.create-btn:hover,
.delete-btn:hover {
  opacity: 0.8;
}
</style>
