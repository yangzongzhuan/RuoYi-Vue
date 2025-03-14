package com.ruoyi.system.coze.utils;

import com.ruoyi.system.coze.domain.CoZeCompletionRequest;
import com.ruoyi.system.coze.domain.CoZeCompletionResponse;
import com.ruoyi.system.coze.domain.Message;
import com.ruoyi.system.coze.session.CoZeConfiguration;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.system.coze.domain.CozeRequestVO;
import com.ruoyi.system.coze.session.CoZeSession;
import com.ruoyi.system.coze.session.defaults.DefaultCoZeSessionFactory;
import okhttp3.logging.HttpLoggingInterceptor;

public class CozeRequestUtils {

	private final static String DEFAULT_BOT_ID = "7403315104051888155";
	private final static String DEFAULT_API_KEY = "Bearer pat_nP89hKiK0SDSxfvk4gYZBSPDyQkEz2FMxGn91CvFpbG2fMWcMv05ie4osG3X1mZX";

	private final static String SIX_YAO_BOT_ID = "7413572901758353446";
	private final static String SIX_YAO_API_KEY = "Bearer pat_iUOxjFpOxT041wInXBKeVsc6t6LDFUxdUxHxZ8VrXc0c6sC422Vq2IkjmWrSNOFG";

	private final CoZeSession defaultSession;
	private final CoZeSession sixYaoSession;

	public CozeRequestUtils() {
		this.defaultSession = createSession(DEFAULT_API_KEY);
		this.sixYaoSession = createSession(SIX_YAO_API_KEY);
	}

	private CoZeSession createSession(String apiKey) {
		CoZeConfiguration configuration = new CoZeConfiguration();
		configuration.setApiHost("https://api.coze.cn/open_api/");
		configuration.setApiKey(apiKey);
		configuration.setLevel(HttpLoggingInterceptor.Level.HEADERS);
		DefaultCoZeSessionFactory factory = new DefaultCoZeSessionFactory(configuration);
		return factory.openSession();
	}

	public String test_chat_completions(String botId, CozeRequestVO cozeRequestVO) {
		CoZeCompletionRequest chatCompletion = new CoZeCompletionRequest();
		chatCompletion.setConversationId(UUID.fastUUID().toString());
		chatCompletion.setUser("cc");
		chatCompletion.setBotId(botId == null ? DEFAULT_BOT_ID : SIX_YAO_BOT_ID);
		chatCompletion.setQuery(cozeRequestVO.getName() + "," + cozeRequestVO.getSex() + "," + cozeRequestVO.getOtherQuestion());

		CoZeSession session = SIX_YAO_BOT_ID.equals(botId) ? sixYaoSession : defaultSession;
		CoZeCompletionResponse yuanQiCompletionResponse = session.completions(chatCompletion);

		for (Message message : yuanQiCompletionResponse.getMessages()) {
			if (message.getType().equals("answer")) {
				// 先替换 &emsp;，然后标准化换行符，再去掉多余换行符，最后去掉首尾空白
				String processedContent = message.getContent()
						.replace("&emsp;", " ")  // 替换特殊空格符号
						.replaceAll("\\r?\\n+", "\n")  // 将连续的换行符替换为单个换行符
						.trim();  // 去掉首尾多余的空白（包括换行符）
				return processedContent;
			}
		}
		return "";
	}

	public String test_chat_completions_two(String botId, CozeRequestVO cozeRequestVO) {
		CoZeCompletionRequest chatCompletion = new CoZeCompletionRequest();
		chatCompletion.setConversationId(UUID.fastUUID().toString());
		chatCompletion.setUser("cc");
		chatCompletion.setBotId(botId == null ? DEFAULT_BOT_ID : SIX_YAO_BOT_ID);
		chatCompletion.setQuery("第一个名字：" + cozeRequestVO.getName() + "," + cozeRequestVO.getSex() + ","
				+"第二个名字：" + cozeRequestVO.getName2() + "," + cozeRequestVO.getSex2() + ","
				+ cozeRequestVO.getOtherQuestion());

		CoZeSession session = SIX_YAO_BOT_ID.equals(botId) ? sixYaoSession : defaultSession;
		CoZeCompletionResponse yuanQiCompletionResponse = session.completions(chatCompletion);

		for (Message message : yuanQiCompletionResponse.getMessages()) {
			if (message.getType().equals("answer")) {
				// 先替换 &emsp;，然后标准化换行符，再去掉多余换行符，最后去掉首尾空白
				String processedContent = message.getContent()
						.replace("&emsp;", " ")  // 替换特殊空格符号
						.replaceAll("\\r?\\n+", "\n")  // 将连续的换行符替换为单个换行符
						.trim();  // 去掉首尾多余的空白（包括换行符）
				return processedContent;
			}
		}

		return "";
	}


	/**
	 * coze替换文心接口
	 *
	 * @param name, question
	 * @return
	 */
	public AjaxResult getAiAnswerByCoze(String name, String question) {
		CozeRequestVO cozeRequestVO = new CozeRequestVO();
		cozeRequestVO.setName(name);
		cozeRequestVO.setOtherQuestion(question);
		String answer = new CozeRequestUtils().test_chat_completions(null, cozeRequestVO);
		return AjaxResult.success("ok", answer);
	}

	/**
	 * 六爻获取卦象信息
	 * @param question 六爻问题
	 */
	public String getSixYaoInfo(String question) {
		CozeRequestVO cozeRequestVO = new CozeRequestVO();
		cozeRequestVO.setName("name");
		cozeRequestVO.setOtherQuestion(question);
		return test_chat_completions(SIX_YAO_BOT_ID, cozeRequestVO);
	}

}
