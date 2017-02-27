package com.leiyun.appmarket.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程管理器
 * Created by LeiYun on 2017/2/25 0025.
 */

public class ThreadManager {

    private static ThreadPool mThreadPool;

    public static ThreadPool getThreadPool() {
        if (mThreadPool == null) {
            synchronized (ThreadPool.class) {
                if (mThreadPool == null) {
                    int threadCount = 10;
                    mThreadPool = new ThreadPool(threadCount, threadCount, 1L);
                }
            }
        }
        return mThreadPool;
    }

    /**
     * 线程池
     */
    public static class ThreadPool {

        private int corePoolSize; // 核心线程数
        private int maximumPoolSize; // 最大线程数
        private long keepAliveTime; // 线程休眠时间


        private ThreadPoolExecutor executor = null;


        private ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
            this.maximumPoolSize = maximumPoolSize;
            this.corePoolSize = corePoolSize;
            this.keepAliveTime = keepAliveTime;
        }

        // 线程池几个参数的理解:
        // 比如去火车站买票, 有10个售票窗口, 但只有5个窗口对外开放. 那么对外开放的5个窗口称为核心线程数,
        // 而最大线程数是10个窗口.
        // 如果5个窗口都被占用, 那么后来的人就必须在后面排队, 但后来售票厅人越来越多, 已经人满为患, 就类似于线程队列已满.
        // 这时候火车站站长下令, 把剩下的5个窗口也打开, 也就是目前已经有10个窗口同时运行. 后来又来了一批人,
        // 10个窗口也处理不过来了, 而且售票厅人已经满了, 这时候站长就下令封锁入口,不允许其他人再进来, 这就是线程异常处理策略.
        // 而线程休眠时间指的是, 允许售票员休息的最长时间, 以此限制售票员偷懒的行为.
        public void execute(Runnable r) {
            if (executor == null) {
                // 参1:核心线程数;参2:最大线程数;参3:线程休眠时间;参4:时间单位;参5:线程队列;参6:生产线程的工厂;参7:线程异常处理策略
                executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                        TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(),
                        Executors.defaultThreadFactory(),
                        new ThreadPoolExecutor.AbortPolicy());
            }

            // 执行一个Runnable对象，具体运行时机由线程池决定
            executor.execute(r);
        }

        /**
         * 取消任务
         */
        public void cancel(Runnable runnable) {
            if (executor != null) {
                // 从线程队列中移除对象
                executor.getQueue().remove(runnable);
            }
        }
    }

}
