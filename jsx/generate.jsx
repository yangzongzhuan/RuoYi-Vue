// Photoshop自动模板处理脚本 (ES3兼容版)
// 版本：4.2.0
// 最后更新：2025-03-11

app.displayDialogs = DialogModes.NO;

// =============== 配置信息（示例） ===============
var CONFIG = {};
var foldersName = "";

try {
    // 从配置中获取基础路径配置
    var baseConfig = CONFIG.baseConfig; // 原"基础配置"
    var psdPath = baseConfig.psdLocalPath.replace(/\\/g, "/"); // 原"psd本地路径"
    // 创建基础路径
    var outputBase = new Folder(baseConfig.imageSavePath);
    if (!outputBase.exists) outputBase.create();

    // 生成日期路径 格式如：2025-03-16
    function pad(n) { return n < 10 ? '0' + n : n.toString(); }
    var now = new Date();
    var datePath = now.getFullYear() + '-' + pad(now.getMonth()+1) + '-' + pad(now.getDate());

    // 创建日期文件夹
    var dateDir = new Folder(outputBase.fsName + "/" + datePath);
    if (!dateDir.exists) dateDir.create();

    // 创建任务名称文件夹
    var taskDir = new Folder(dateDir.fsName + "/" + foldersName);
    if (!taskDir.exists) taskDir.create();

    // 1. 打开原始 PSD 文件（仅一次）
    var originalDoc = app.open(File(psdPath));

    // 2. 遍历每个图片配置，逐次处理
    var imgConfigs = CONFIG.imageConfigs; // 原"图片配置"
    for (var i = 0; i < imgConfigs.length; i++) {
        var cfg = imgConfigs[i];

        // 每次都从原始文档重新复制
        var workingDoc = originalDoc.duplicate("working_doc_" + i, false);

        // 查找目标文件夹时添加可见性控制
        var targetSet;
        if (cfg.hasSubfolder) { // 原"是否有子文件夹"
            var parentSet = findParentLayerSet(workingDoc, cfg.folderName); // 原"图片所在文件夹名称"
            hideOtherSubfolders(parentSet, cfg.subfolderName); // 原"子文件夹名称"
            targetSet = findExactLayerSet(workingDoc, cfg.folderName, cfg.subfolderName);
        } else {
            targetSet = findLayerSetByName(workingDoc, cfg.folderName);
        }

        // 新增：确保目标图层组及其父级可见
        ensureLayerVisibility(targetSet);

        // 2.3 修改指定文本图层的内容
        var textConfig = cfg.textLayerConfigs; // 原"文字图层配置"
        for (var key in textConfig) {
            if (textConfig.hasOwnProperty(key)) {
                var layerName = textConfig[key].name; // 原"名称"
                var layer = findTextLayer(targetSet.layers, layerName);
                if (!layer) {
                    throw new Error("找不到文本图层: " + layerName);
                }

                // 更新文本内容并检查结果
                if (!smartTextUpdate(
                    layer,
                    textConfig[key].sampleText, // 原"原文示例"
                    parseInt(textConfig[key].maxCharsPerLine, 10) // 转换为数字
                )) {
                    throw new Error("文本更新失败: " + layerName);
                }
            }
        }

        // 2.4 隐藏其他文件夹（改进版）
        var currentFolderName = cfg.folderName;
        var allLayerSets = workingDoc.layerSets;
        for (var j = 0; j < allLayerSets.length; j++) {
            allLayerSets[j].visible = (allLayerSets[j].name === currentFolderName);
        }

        // 2.5 生成JPG文件名（注意扩展名变更）
        var fileName = baseConfig.templateName + "_" + cfg.folderName.replace(/ /g, "") + "_" + Date.now() + ".jpg";

        // 2.6 创建JPG保存选项
        var saveOptions = new JPEGSaveOptions();
        saveOptions.quality = 12;  // 质量等级1-12（12为最高质量）[6](@ref)
        saveOptions.formatOptions = FormatOptions.OPTIMIZEDBASELINE; // 基线优化[6](@ref)

        // 2.6 导出为JPG
        var saveFile = new File(taskDir.fsName + "/" + fileName);
        workingDoc.saveAs(saveFile, saveOptions, true);

        // 2.7 关闭工作文档，不保存更改
        workingDoc.close(SaveOptions.DONOTSAVECHANGES);
    }

    // 3. 处理完毕后，关闭原始文档
    originalDoc.close(SaveOptions.DONOTSAVECHANGES);

    // alert("处理完成！文件保存在:\n" + outputDir.fsName);
    // 执行退出
    // forceQuitPhotoshop();
} catch (e) {
    // alert("严重错误:\n" + e.message + "\n行号: " + e.line);
    // 执行退出
    // forceQuitPhotoshop();
    throw new Error("严重错误:\n" + e.message + "\n行号: " + e.line);
}

// =============== 修复后的退出逻辑 ===============
function forceQuitPhotoshop() {
    try {
        // 方法1: 官方推荐退出方式 (兼容所有版本)
        var quitAction = stringIDToTypeID("quit");
        app.executeAction(quitAction, undefined, DialogModes.NO);
    } catch(e1) {
        try {
            // 方法2: 基础退出方式
            if (app.documents.length === 0) {
                app.quit();
            }
        } catch(e2) {
            // 方法3: 强制解除进程引用 (仅限Windows)
            if (Folder.fs === "Windows") {
                var photoshopProcess = "Photoshop.exe";
                var cmd = 'wscript.exe "C:\\close_photoshop.vbs"'; // 使用外部脚本
                writeVBSScript(); // 生成VB脚本
                $.sleep(2000);
                app.system(cmd); // 使用app.system替代system.callSystem
            }
        }
    }
}

// =============== 辅助函数：生成VB脚本 ===============
function writeVBSScript() {
    var vbsCode = [
        'Set WshShell = WScript.CreateObject("WScript.Shell")',
        'WshShell.Run "taskkill /f /im Photoshop.exe", 0, True',
        'Set WshShell = Nothing'
    ].join('\n');

    var vbsFile = new File("C:\\close_photoshop.vbs");
    vbsFile.open("w");
    vbsFile.writeln(vbsCode);
    vbsFile.close();
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
            var chars = safeText.split('');
            var lineBreak = "\r";
            var wrapped = '';

            // 按字符数分割
            for (var i = 0; i < chars.length; i += maxChars) {
                var line = chars.slice(i, i + maxChars).join('');
                wrapped += line + (i + maxChars < chars.length ? lineBreak : '');
            }

            safeText = wrapped.replace(/\r$/, '');
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
