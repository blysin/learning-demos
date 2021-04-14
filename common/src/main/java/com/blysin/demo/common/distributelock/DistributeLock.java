package com.blysin.demo.common.distributelock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author daishaokun
 * @date 2021/3/13
 */
@Service
@Slf4j
public class DistributeLock {
    @Autowired
    private RedissonClient redissonClient;

    public void lock() {
        //获取一个读写锁
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("rw-lock");
        try {
            //获取锁有等待时间
            readWriteLock.readLock().tryLock(1L, 1L, TimeUnit.MINUTES);
            //等到死为止
            readWriteLock.readLock().lock(1L, TimeUnit.SECONDS);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            readWriteLock.readLock().unlock();
        }

        RLock lock = redissonClient.getLock("d-lock");
    }

}
