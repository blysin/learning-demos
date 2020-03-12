package com.blysin.demo.netty;

import com.blysin.demo.netty.framework.config.AppProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NettyApplicationTests {

    @Autowired
    private AppProperties appProperties;
    @Test
    public void contextLoads() {
        System.out.println(appProperties);

    }

}
