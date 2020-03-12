package com.keytop.demo.client.config;

import com.keytop.demo.client.netty.HeartBeatsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 服务启动器
 *
 * @author daishaokun
 * @date 2019-09-10
 */
@Component
@Slf4j
public class ApplicationStarter implements ApplicationRunner {
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    private HeartBeatsClient heartBeatsClient;
    @Autowired
    private AppProperties appProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*
         * 每个车场创建一个客户端
         */
        threadPoolExecutor.execute(() -> {
            try {
                heartBeatsClient.connect(appProperties.getHost(), appProperties.getPort(), appProperties.getLotCode(), appProperties.getHost());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}