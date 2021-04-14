package com.blysin.demo.common;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;

/**
 * @author daishaokun
 * @date 2021/3/2
 */
public class SimpleTest {
    @Test
    public void extent() {
        ArrayBlockingQueue abq = new ArrayBlockingQueue(112);
        String str = "if (redis.call('hexists', KEYS[1], ARGV[3]) == 0) then " + "return nil;" + "end; " + "local counter = redis.call('hincrby', KEYS[1], ARGV[3], -1); " + "if (counter > 0) then " + "redis.call('pexpire', KEYS[1], ARGV[2]); " + "return 0; " + "else " + "redis.call('del', KEYS[1]); " + "redis.call('publish', KEYS[2], ARGV[1]); " + "return 1; " + "end; " + "return nil;";
        System.out.println(str);

    }
}
