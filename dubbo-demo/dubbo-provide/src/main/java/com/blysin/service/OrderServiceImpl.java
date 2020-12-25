package com.blysin.service;

import com.blysin.vo.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author daishaokun
 * @date 2020/12/25
 */
@DubboService(interfaceClass = OrderService.class)
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Override
    public Order getOne(Integer id) {
        log.info("收到请求：{}", id);
        return Order.builder().id(id).carNo("闽D12345").orderNo(RandomStringUtils.randomAlphabetic(32)).build();
    }

    @Override
    public List<Order> list() {
        return Arrays.asList(
                //
                Order.builder().id(1).carNo("闽D12345").orderNo(RandomStringUtils.randomAlphabetic(32)).build(),
                //
                Order.builder().id(2).carNo("闽D12344").orderNo(RandomStringUtils.randomAlphabetic(32)).build(),
                //
                Order.builder().id(3).carNo("闽D12343").orderNo(RandomStringUtils.randomAlphabetic(32)).build());
    }
}
