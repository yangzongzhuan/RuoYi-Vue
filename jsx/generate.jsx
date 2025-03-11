// Photoshop自动模板处理脚本 (ES3兼容版)
// 版本：4.2.0
// 最后更新：2025-03-11

app.displayDialogs = DialogModes.NO;

// =============== 配置信息（示例） ===============
var CONFIG = {};

try {
    // 从配置中获取基础路径配置
    var baseConfig = CONFIG["基础配置"];
    var psdPath = baseConfig.psd本地路径.replace(/\\/g, "/");
    var outputDir = new Folder(baseConfig.图片保存路径);
    if (!outputDir.exists) {
        outputDir.create();
    }

    // 1. 打开原始 PSD 文件（仅一次）
    var originalDoc = app.open(File(psdPath));

    // 2. 遍历每个图片配置，逐次处理
    var imgConfigs = CONFIG["图片配置"];
    for (var i = 0; i < imgConfigs.length; i++) {
        var cfg = imgConfigs[i];

        // 每次都从原始文档重新复制
        var workingDoc = originalDoc.duplicate("working_doc_" + i, false);

        // 查找目标文件夹时添加可见性控制
        var targetSet;
        if (cfg["是否有子文件夹"]) {
            var parentSet = findParentLayerSet(workingDoc, cfg["图片所在文件夹名称"]);
            hideOtherSubfolders(parentSet, cfg["子文件夹名称"]); // 新增关键函数
            targetSet = findExactLayerSet(workingDoc, cfg["图片所在文件夹名称"], cfg["子文件夹名称"]);
        } else {
            targetSet = findLayerSetByName(workingDoc, cfg["图片所在文件夹名称"]);
        }

        // 新增：确保目标图层组及其父级可见
        ensureLayerVisibility(targetSet);

        // 2.3 修改指定文本图层的内容
        var textConfig = cfg["文字图层配置"];
        for (var key in textConfig) {
            if (textConfig.hasOwnProperty(key)) {
                var layerName = textConfig[key]["名称"];
                var layer = findTextLayer(targetSet.layers, layerName);
                if (!layer) {
                    throw new Error("找不到文本图层: " + layerName);
                }

                // 更新文本内容并检查结果
                if (!smartTextUpdate(
                    layer,
                    textConfig[key]["原文示例"],
                    textConfig[key]["每行最大字符数"]
                )) {
                    throw new Error("文本更新失败: " + layerName);
                }
            }
        }

        // 2.4 隐藏其他文件夹（改进版）
        var currentFolderName = cfg["图片所在文件夹名称"];
        var allLayerSets = workingDoc.layerSets;
        for (var j = 0; j < allLayerSets.length; j++) {
            allLayerSets[j].visible = (allLayerSets[j].name === currentFolderName);
        }

        // 2.5 生成导出文件名
        var fileName = baseConfig["模板名称"] + "_" +
            cfg["图片所在文件夹名称"].replace(/ /g, "") + "_" +
            Date.now() + ".png";

        // 2.6 导出为 PNG
        var saveFile = new File(outputDir.fsName + "/" + fileName);
        var saveOptions = new PNGSaveOptions();
        saveOptions.compression = 9;
        workingDoc.saveAs(saveFile, saveOptions, true);

        // 2.7 关闭工作文档，不保存更改
        workingDoc.close(SaveOptions.DONOTSAVECHANGES);

    }

    // 3. 处理完毕后，关闭原始文档
    originalDoc.close(SaveOptions.DONOTSAVECHANGES);

    alert("处理完成！文件保存在:\n" + outputDir.fsName);


} catch (e) {
    alert("严重错误:\n" + e.message + "\n行号: " + e.line);
}

// ===================== 新增关键函数 =====================
function findParentLayerSet(doc, parentName) {
    for (var i = 0; i < doc.layerSets.length; i++) {
        if (doc.layerSets[i].name === parentName) {
            return doc.layerSets[i];
        }
    }
    throw new Error("找不到父文件夹: " + parentName);
}

function hideOtherSubfolders(parentSet, targetChildName) {
    // 先显示所有子文件夹
    for (var j = 0; j < parentSet.layerSets.length; j++) {
        parentSet.layerSets[j].visible = true;
    }

    // 然后只保留目标子文件夹可见
    for (var j = 0; j < parentSet.layerSets.length; j++) {
        if (parentSet.layerSets[j].name !== targetChildName) {
            parentSet.layerSets[j].visible = false;
        }
    }
}

// ===================== 修改后的findExactLayerSet =====================
function findExactLayerSet(doc, parentName, childName) {
    var parentSet = findParentLayerSet(doc, parentName);
    for (var j = 0; j < parentSet.layerSets.length; j++) {
        if (parentSet.layerSets[j].name === childName) {
            parentSet.layerSets[j].visible = true;
            return parentSet.layerSets[j];
        }
    }
    throw new Error("找不到子文件夹: " + childName);
}

// ===================== 新增工具函数 =====================
function ensureLayerVisibility(layer) {
    while (layer && layer.typename === "LayerSet") {
        layer.visible = true;
        layer = layer.parent;
    }
}

function findLayerSetByName(doc, setName) {
    for (var i = 0; i < doc.layerSets.length; i++) {
        if (doc.layerSets[i].name === setName) {
            doc.layerSets[i].visible = true; // 强制可见
            return doc.layerSets[i];
        }
    }
    throw new Error("找不到文件夹: " + setName);
}

function smartTextUpdate(layer, newText, maxChars) {
    try {
        var safeText = newText.substr(0, 32767);

        if (maxChars && maxChars > 0) {
            var wrapped = "";
            // 改用正确的换行符
            var lineBreak = "\r"; // Photoshop 使用\r作为换行符
            for (var i = 0; i < safeText.length; i += maxChars) {
                wrapped += safeText.substr(i, maxChars) + lineBreak;
            }
            safeText = wrapped.replace(new RegExp(lineBreak + "$"), "");
        }

        layer.textItem.contents = safeText;
        return true;
    } catch (e) {
        return false;
    }
}

// 递归查找文本图层
function findTextLayer(layers, name) {
    for (var i = 0; i < layers.length; i++) {
        var layer = layers[i];
        if (layer.typename === "ArtLayer" && layer.kind === LayerKind.TEXT && layer.name === name) {
            return layer;
        }
        if (layer.typename === "LayerSet") {
            var found = findTextLayer(layer.layers, name);
            if (found) return found;
        }
    }
    return null;
}
