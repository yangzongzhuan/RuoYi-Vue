package com.ruoyi.web.controller.custom;

import cn.hutool.core.net.URLDecoder;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.system.domain.PSDConfig;
import com.ruoyi.system.domain.PSDTemplate;
import com.ruoyi.system.mapper.PSDMapper;
import com.ruoyi.system.service.impl.DeepSeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.*;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

@RestController
@RequestMapping("/psd")
public class PSDTemplateController extends BaseController
{
	@Autowired
	private PSDMapper psdMapper;

	@Autowired
	private DeepSeekService deepSeekService;


	@Autowired
	private ServerConfig serverConfig;


	@PostMapping("/saveTemplate")
   @PreAuthorize("@ss.hasPermi('namegenerator:psd:add')")
	public AjaxResult saveTemplate(@RequestBody PSDConfig config) throws JsonProcessingException {
		System.out.println(config);
		PSDTemplate psdTemplate = new PSDTemplate();
		// 将 map 序列化为合法 JSON 字符串
	    ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(config);
		psdTemplate.setConfig(json);
		psdMapper.insert(psdTemplate);
		return AjaxResult.success();
	}

	@GetMapping("/list")
	@PreAuthorize("@ss.hasPermi('namegenerator:psd:list')")
	public TableDataInfo list(
			@RequestParam(required = false) String templateName,
			@RequestParam(required = false) String accountName)
	{
		startPage();
		List<PSDTemplate> list = psdMapper.selectByCondition(templateName, accountName);
		return getDataTable(list);
	}

	@GetMapping("/listAll")
	@PreAuthorize("@ss.hasPermi('namegenerator:psd:listAll')")
	public TableDataInfo listAll()
	{
		List<PSDTemplate> list = psdMapper.selectAll();
		return getDataTable(list);
	}

	/**
	 * 修改 PSD 配置
	 * 要求传入的数据包含 id 和其它配置数据
	 */
	@PutMapping("/updateTemplate")
	@PreAuthorize("@ss.hasPermi('namegenerator:psd:edit')")
	public AjaxResult updateTemplate(@RequestBody Map<String, Object> map) throws JsonProcessingException {
		// 获取 id
		Integer id = (Integer) map.get("id");
		// 假设前端传递的 map 中 config 字段已经是合法的 JSON 字符串
		String config = (String) map.get("config");

		PSDTemplate psdTemplate = new PSDTemplate();
		psdTemplate.setId(id);
		psdTemplate.setConfig(config);
		psdTemplate.setImages((String) map.get("images"));
		psdMapper.updateById(psdTemplate);
		return AjaxResult.success();
	}
	public static void main(String[] args) {
		try {
			// 获取 JSX 模板路径
			String basePath = System.getProperty("user.dir");
			String jsxTemplatePath = basePath + "/jsx/generate.jsx"; // JSX 模板路径

			// 读取 JSX 模板内容
			String jsxTemplate = new String(Files.readAllBytes(Paths.get(jsxTemplatePath)), StandardCharsets.UTF_8);

			// **1. 生成动态 CONFIG 配置**
			JSONObject config = generateDynamicConfig();
			System.out.println(config);
			// **2. 替换 JSX 代码中的 CONFIG 变量**
			String modifiedJsx = jsxTemplate.replaceFirst("var CONFIG = .*?;", "var CONFIG = " + config + ";");
			System.out.println(modifiedJsx);
			// **3. 调用 Photoshop 执行 JSX**
			ActiveXComponent ps = new ActiveXComponent("Photoshop.Application");
			Dispatch.invoke(ps, "DoJavaScript", Dispatch.Method, new Object[]{modifiedJsx}, new int[1]);

			System.out.println("JSX 执行成功！");
		} catch (IOException e) {
			System.out.println("JSX 读取失败：" + e.getMessage());
		}
	}

	/**
	 * 生成动态的 CONFIG 配置
	 */
	private static JSONObject generateDynamicConfig() {
		// 基础配置
		JSONObject baseConfig = new JSONObject();
		baseConfig.put("做图账号名称", "test");
		baseConfig.put("psd本地路径", "C:\\\\Users\\\\liuxianying\\\\Desktop\\\\qm\\\\A4xin.psd"); // 四斜杠
		baseConfig.put("图片保存路径", "C:\\\\output");
		baseConfig.put("模板名称", "动态模板");

		// 图片配置数组
		JSONArray imageConfigs = new JSONArray();

		// 动态生成三个图片配置
		JSONObject imgConfig = new JSONObject();
		imgConfig.put("图片所在文件夹名称", "正文靠左 三个字 无姓氏");
		imgConfig.put("是否有子文件夹", true);
		imgConfig.put("子文件夹名称", "4");

		// 创建文字图层配置对象
		JSONObject textLayers = new JSONObject();

		// 文字图层1
		JSONObject textLayer1 = new JSONObject();
		textLayer1.put("名称", "远之");
		textLayer1.put("原文示例", "测试1");
		textLayer1.put("每行最大字符数", 3);
		textLayers.put("文字图层1", textLayer1);

		// 文字图层2
		JSONObject textLayer2 = new JSONObject();
		textLayer2.put("名称", "杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 ");
		textLayer2.put("原文示例", "杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。666");
		textLayer2.put("每行最大字符数", 15);
		textLayers.put("文字图层2", textLayer2);

		// 将文字图层配置加入图片配置中
		imgConfig.put("文字图层配置", textLayers);

		// 将图片配置加入配置数组
		imageConfigs.add(imgConfig);

		// 最终组装 CONFIG
		JSONObject config = new JSONObject();
		config.put("基础配置", baseConfig);
		config.put("图片配置", imageConfigs);

		return config;
	}


	/**
	 * 删除 PSD 配置
	 */
	@DeleteMapping("/deleteTemplate/{id}")
	@PreAuthorize("@ss.hasPermi('namegenerator:psd:remove')")
	public AjaxResult deleteTemplate(@PathVariable Integer id) {
		psdMapper.deleteById(id);
		return AjaxResult.success();
	}

//	@PostMapping("/psd-to-jpg")
//	public ResponseEntity<Resource> convertPsdLayersToJpg(@RequestBody String psdPath) {
//		try {
//			// 1. 路径清洗与解码
//			String sanitizedPath = psdPath
//					.replaceAll("^\"|\"$", "") // 去除首尾双引号
//					.replace("\\", "/");       // 统一路径分隔符
//			String decodedPath = URLDecoder.decode(sanitizedPath, StandardCharsets.UTF_8);
//
//			// 2. 获取文件路径
//			Path filePath = Paths.get(decodedPath).normalize();
//
//			// 3. 打开PSD文件
//			try (InputStream psdStream = Files.newInputStream(filePath);
//				 ImageInputStream imageStream = ImageIO.createImageInputStream(psdStream)) {
//
//				ImageReader reader = ImageIO.getImageReadersByFormatName("psd").next();
//				reader.setInput(imageStream);
//
//				// 获取PSD中图像数量（包含合并图像及各图层）
//				int numImages = reader.getNumImages(true);
//
//				// 4. 使用Zip打包所有转换后的JPG图层
//				ByteArrayOutputStream zipBytes = new ByteArrayOutputStream();
//				try (ZipOutputStream zos = new ZipOutputStream(zipBytes)) {
//					for (int i = 0; i < numImages; i++) {
//						// 读取每个图层
//						BufferedImage layerImage = reader.read(i);
//						if (layerImage == null) {
//							System.out.println("Layer " + i + " is null, skipping.");
//							continue;
//						}
//						ByteArrayOutputStream jpgBytes = new ByteArrayOutputStream();
//
//						// 转换为JPG
//						ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
//						try (ImageOutputStream outStream = ImageIO.createImageOutputStream(jpgBytes)) {
//							writer.setOutput(outStream);
//							JPEGImageWriteParam params = new JPEGImageWriteParam(null);
//							params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//							params.setCompressionQuality(0.9f); // 质量参数，可根据需求调整
//							writer.write(null, new IIOImage(layerImage, null, null), params);
//						}
//
//						// 将每个JPG作为ZIP中的一个条目
//						String baseName = filePath.getFileName().toString().replaceAll("(?i)\\.psd$", "");
//						String entryName = baseName + "_layer" + i + ".jpg";
//						ZipEntry entry = new ZipEntry(entryName);
//						zos.putNextEntry(entry);
//						zos.write(jpgBytes.toByteArray());
//						zos.closeEntry();
//					}
//				}
//
//				// 5. 返回ZIP文件（设置响应头，前端可以下载或直接解压使用）
//				return ResponseEntity.ok()
//						.header("Content-Type", "application/zip")
//						.header("Content-Disposition", "attachment; filename=\"converted_layers.zip\"")
//						.body(new ByteArrayResource(zipBytes.toByteArray()));
//			}
//		} catch (Exception e) {
//			System.out.println("转换失败：" + e.getMessage());
//			// 异常统一处理（可以根据实际需要记录日志）
//			return ResponseEntity.noContent().build();
//		}
//	}

}
