package com.blysin.demo.common;

import org.junit.jupiter.api.Test;

/**
 * @author daishaokun
 * @date 2021/3/2
 */
public class SimpleTest {
    @Test
    public void extent() {
        //构造器不继承
        FatherClass fa = new SunClass(1);

        fa.say();
    }
}
