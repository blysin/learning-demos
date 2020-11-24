package cn.blysin.demo.dcnacos.feign;

import cn.blysin.demo.dcnacos.base.ResultInfo;
import cn.blysin.demo.dcnacos.feign.impl.HelloFeignServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author daishaokun
 * @date 2020/11/24
 */
@Component
@FeignClient(value = "dc-nacos-service", path = "/hello", fallback = HelloFeignServiceImpl.class)
public interface HelloFeignService {
    @GetMapping("ping")
    ResultInfo ping();

    @PostMapping("post")
    ResultInfo post(String lotCode);

    @GetMapping("get")
    ResultInfo get();
}
