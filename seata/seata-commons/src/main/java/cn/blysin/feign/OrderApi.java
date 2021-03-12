package cn.blysin.feign;

import cn.blysin.entity.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author daishaokun
 * @date 2021/3/11
 */
@FeignClient(value = "seata-service",path = "order")
public interface OrderApi {
    @GetMapping("test")
    OrderDTO test();
}
