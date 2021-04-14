package com.blysin.demo.common.future;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 异步响应处理
 *
 * @author daishaokun
 * @date 2020/12/3
 */
public class DefaultFuture {
    private static final Map<String, DefaultFuture> FUTURES = new ConcurrentHashMap<>();
    private static Thread overtimeScanner;
    private final static String OVERTIME_RESULT = "cancel";


    static {
        overtimeScanner = new Thread(new OvertimeScanner(), "超时扫描线程");
        //设置为守护线程，标识不重要
        overtimeScanner.setDaemon(true);
        overtimeScanner.start();
    }

    private long timeout;
    private long start = System.currentTimeMillis();
    private String id;

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    //注意有个volatile，这个对象会变动的
    private volatile Object result;

    public DefaultFuture(String id) {
        this.timeout = 8000;
        this.id = id;
        FUTURES.put(id, this);
    }

    public static DefaultFuture getFuture(String id) {
        return FUTURES.get(id);
    }

    public static int count(){
        return FUTURES.keySet().size();
    }

    /**
     * 消息分发器响应消息入口
     *
     * @param id
     * @param result
     */
    public static void doReceived(String id, Object result) {
        //TODO 只有这里移除，那如果没有收到回调，不是会导致数据堆积吗？
        DefaultFuture future = FUTURES.remove(id);
        if (future != null) {
            future.received(result);
        }
    }

    /**
     * 是否完成
     *
     * @return true 已经完成
     */
    public boolean isDone() {
        return result != null;
    }

    /**
     * 阻塞的获取结果
     *
     * @return
     */
    public Object get() {
        if (!isDone()) {
            lock.lock();
            try {
                while (true) {
                    condition.await(timeout, TimeUnit.MILLISECONDS);
                    //防止意外唤醒,所以要加上超时时间判断
                    if (isDone() || System.currentTimeMillis() - start > timeout) {
                        //一直阻塞，直到完成或者超时
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            // 这里可选，如果还没完成（!isDone()） ，可以抛个超时异常
            if (!isDone()) {
                throw new RuntimeException("超时啦");
            }
        }

        return result;
    }

    /**
     * 设置返回值，消息监听器调用
     *
     * @param result
     */
    public void received(Object result) {
        lock.lock();
        try {
            this.result = result;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 取消，这个不重要，可能是定期清理策略，移除超时的记录
     */
    public void cancel() {
        this.result = OVERTIME_RESULT;
        FUTURES.remove(this.id);
    }

    public static class OvertimeScanner implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    for (DefaultFuture future : FUTURES.values()) {
                        if (future == null || future.isDone()) {
                            // 跳过已经完成的，疑问，为什么不直接移除呢？
                            continue;
                        }

                        if (System.currentTimeMillis() - future.start > future.timeout) {
                            //已经超时
                            future.received(OVERTIME_RESULT);
                            //TODO 这里不是dubbo的逻辑，超时移除
                            FUTURES.remove(future.id);
                        }

                    }
                    TimeUnit.MILLISECONDS.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
