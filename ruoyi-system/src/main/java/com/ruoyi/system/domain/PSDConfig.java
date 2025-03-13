package com.ruoyi.system.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PSDConfig {
    private BaseConfig baseConfig;
    // 列表配置使用Java集合
    private List<ImageConfig> imageConfigs;

    // Getters & Setters
}

@Data
class BaseConfig {
    private String templateName;
    private String accountName;
    private String psdLocalPath; // 包含Windows路径反斜杠
    private String imageSavePath;

    // Getters & Setters
}

@Data
class ImageConfig {
    private String folderName;
    private boolean hasSubfolder; // 对应"是否有子文件夹"
    private String subfolderName;
    // 动态图层配置使用Map结构
    private Map<String, TextLayerConfig> textLayerConfigs;
    private int layersToModify;
    private String prompt;

    // Getters & Setters
}

@Data
class TextLayerConfig {
    private String name;
    private String sampleText;
    private int maxCharsPerLine;

    // Getters & Setters
}
