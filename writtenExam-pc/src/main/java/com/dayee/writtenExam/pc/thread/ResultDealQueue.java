
package com.dayee.writtenExam.pc.thread;

import com.dayee.writtenExam.framework.util.SpringContextUtils;
import com.dayee.writtenExam.pc.service.apiService.CallbackService;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author: dayee_yangkai
 * @Description: 专门开启一个线程，去做获取答题结果并做处理
 * @Date: Created in 15:15 2017/7/20
 * @Modified By:
 * @Version  
 */
public class ResultDealQueue {

    private Logger                                          logger = Logger
            .getLogger(getClass());

    private final LinkedBlockingQueue<Map<String, Integer>> queue  = new LinkedBlockingQueue<Map<String, Integer>>();

    private static ResultDealQueue                          work   = new ResultDealQueue();

    private CallbackService                                 callbackService;

    private ResultDealQueue() {

        ResultDealThread thread = new ResultDealThread();
        thread.start();
    }

    public void initBean() {

        callbackService = (CallbackService) SpringContextUtils
                .getBean("callbackService");
    }

    /* 单例 */
    public static ResultDealQueue getInstance() {

        return work;
    }

    private void putQueue(Map<String, Integer> workMap) {

        synchronized (queue) {
            queue.offer(workMap);
            queue.notify();
        }
    }

    /* 增加任务 */
    public void addTaskWithParam(Integer accountId, Integer paperId) {

        Map<String, Integer> workMap = new HashMap<String, Integer>();
        workMap.put("accountId", accountId);
        workMap.put("paperId", paperId);
        putQueue(workMap);
    }

    /* 结果处理 */
    public void threadWork(Map<String, Integer> workMap) {

        callbackService.getSyncResult(workMap.get("accountId"),
                                      workMap.get("paperId"));
    }

    public class ResultDealThread extends Thread {

        @Override
        public void run() {

            initBean();
            try {
                while (true) {
                    try {
                        Map<String, Integer> workMap = null;
                        synchronized (queue) {
                            if (queue.isEmpty()) {
                                // 队列为空时释放redis连接，避免长时间持有
                                queue.wait();
                            } else {
                                workMap = queue.poll();
                            }
                        }
                        if (workMap != null) {
                            threadWork(workMap);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }
    }
}
