package com.ruoyi.system.coze;


import cn.hutool.core.util.IdUtil;
import com.ruoyi.system.coze.domain.CoZeCompletionRequest;
import com.ruoyi.system.coze.domain.CoZeCompletionResponse;
import com.ruoyi.system.coze.session.CoZeConfiguration;
import com.ruoyi.system.coze.domain.Message;
import com.ruoyi.system.coze.session.CoZeSession;
import com.ruoyi.system.coze.session.defaults.DefaultCoZeSessionFactory;
import okhttp3.logging.HttpLoggingInterceptor;

import java.time.LocalDateTime;

/**
 * @author gcc
 * @date 2024/7/30 17:54
 * @description:
 */
public class CozeRequestJsonUtils {

	private final static String botId = "7481275183366946816";

	private final static String apiKey = "Bearer pat_nP89hKiK0SDSxfvk4gYZBSPDyQkEz2FMxGn91CvFpbG2fMWcMv05ie4osG3X1mZX";

	/**
	 * 测试聊天完成
	 */
	public static String test_chat_completions(String question) {
		while (true) {
			try {
				// 1. 配置文件
				CoZeConfiguration yuanQiConfiguration = new CoZeConfiguration();
				yuanQiConfiguration.setApiHost("https://api.coze.cn/open_api/");
				yuanQiConfiguration.setApiKey(apiKey);
				yuanQiConfiguration.setLevel(HttpLoggingInterceptor.Level.HEADERS);
				// 2. 会话工厂
				DefaultCoZeSessionFactory factory = new DefaultCoZeSessionFactory(yuanQiConfiguration);
				// 3. 开启会话
				CoZeSession coZeSession = factory.openSession();
				CoZeCompletionRequest chatCompletion = new CoZeCompletionRequest();
				chatCompletion.setConversationId(IdUtil.nanoId());
				chatCompletion.setUser("lxy");
				chatCompletion.setBotId(botId);
				chatCompletion.setQuery(question);

				// 2. 发起请求
				CoZeCompletionResponse yuanQiCompletionResponse = coZeSession.completions(chatCompletion);
				// 3. 解析结果
				for (Message message : yuanQiCompletionResponse.getMessages()) {
					// answer 是机器人回答的
					if (message.getType().equals("answer")) {
//				System.err.println("内容介绍："+message.getContent() + System.lineSeparator()); // 写入并换行
						return message.getContent();
					}
				}
				return "";
			}catch (Exception e) {
				System.err.println("[" + LocalDateTime.now() + "] 请求失败: " + e.getMessage());
				try {
					Thread.sleep(10000); // 间隔10秒[1](@ref)
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
					System.err.println("线程被中断，终止重试");
					return "";
				}
			}

		}

	}
}
