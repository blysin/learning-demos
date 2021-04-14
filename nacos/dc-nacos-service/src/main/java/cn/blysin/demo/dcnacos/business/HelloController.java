package cn.blysin.demo.dcnacos.business;

import cn.blysin.demo.dcnacos.base.ResultInfo;
import cn.blysin.demo.dcnacos.feign.HelloFeignService;
import cn.blysin.demo.dcnacos.properties.ServiceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author daishaokun
 * @date 2020/11/24
 */
@RestController
@RequestMapping("hello")
@Slf4j
public class HelloController implements HelloFeignService {
    @Autowired
    private ServiceProperties serviceProperties;


    @Override
    public ResultInfo ping() {
        log.info("pong");
        return ResultInfo.SUCCESS();
    }

    @Override
    public ResultInfo post(String lotCode) {
        return ResultInfo.SUCCESS();
    }

    private AtomicInteger count = new AtomicInteger();
    @Override
    public ResultInfo get() {
        int c = count.getAndIncrement();
        if (c % 3 == 0) {
            throw new RuntimeException("test");
        }
        return ResultInfo.SUCCESS(serviceProperties.toString());
    }
}
