package cn.blysin.demo.dcnacos.feign.impl;

import cn.blysin.demo.dcnacos.base.ResultInfo;
import cn.blysin.demo.dcnacos.feign.HelloFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 降级
 *
 * @author daishaokun
 * @date 2020/11/24
 */
@Component
@Slf4j
public class HelloFeignServiceImpl implements HelloFeignService {

    @Override
    public ResultInfo ping() {
        log.error("服务降级");
        return ResultInfo.ERROR();
    }

    @Override
    public ResultInfo post(String lotCode) {
        log.error("服务降级");
        return ResultInfo.ERROR();
    }

    @Override
    public ResultInfo get() {
        log.error("服务降级");
        return ResultInfo.ERROR();
    }
}
