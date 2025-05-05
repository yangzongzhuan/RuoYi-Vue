package com.ruoyi.web.controller.Queue;

import com.fasterxml.jackson.databind.JsonNode;
import com.ruoyi.system.coze.utils.CozeWorkflowClient;
import com.ruoyi.system.domain.AutoCheck;
import com.ruoyi.system.domain.PsdTask;
import com.ruoyi.system.mapper.PSDMapper;
import com.ruoyi.system.service.IPsdTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 并行异步执行公众号推送任务队列示例
 */
@Component
public class PushGZHTaskQueue {

    // 并行处理任务的线程池
    private final ExecutorService executor;

    @Autowired
    private PSDMapper psdMapper;

    @Autowired
    private IPsdTaskService psdTaskService;

    // 并行度，根据业务需求调整
    private static final int THREAD_POOL_SIZE = 10;

    public PushGZHTaskQueue() {
        this.executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    /**
     * 将 executeId 加入队列并异步轮询结果，返回 Future 以获取 JsonNode
     * @param executeId 执行 ID
     * @return Future<JsonNode> 返回轮询结果，成功时为 JsonNode，异常时抛出 RuntimeException
     */
    public void addTask(String executeId, PsdTask task) {
        executor.submit(() -> {
            try {
                // 调用带超时的轮询方法，内部已每隔 POLL_INTERVAL_MS 轮询一次
                JsonNode result = CozeWorkflowClient.pollResultWithTimeout(
                        executeId,
                        psdMapper.getCheckInfo().getToken()
                );
                if (result != null) {
                    System.out.println("轮询成功，executeId=" + executeId + ", 结果=" + result.toString());
                } else {
                    System.err.println("轮询返回空结果，executeId=" + executeId);
                }
                task.setGzhStatus("1");
                psdTaskService.updatePsdTask(task);
            } catch (Exception e) {
                throw new RuntimeException("轮询任务异常, executeId=" + executeId, e);
            }
        });
    }

    /**
     * 关闭线程池，应用退出时调用
     */
    public void shutdown() {
        executor.shutdown();
    }
}

