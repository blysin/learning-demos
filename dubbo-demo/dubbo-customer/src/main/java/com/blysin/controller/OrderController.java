package com.blysin.controller;

import com.blysin.service.OrderService;
import com.blysin.vo.Order;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author daishaokun
 * @date 2020/12/25
 */
@RestController
public class OrderController {
    @DubboReference
    private OrderService orderService;

    @GetMapping("getOne")
    public Order getOne(Integer id) {
        return orderService.getOne(id);
    }

    @GetMapping("list")
    public List<Order> list() {
        return orderService.list();
    }
}
