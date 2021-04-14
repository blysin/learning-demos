package cn.blysin.controller;

import cn.blysin.entity.Order;
import cn.blysin.service.OrderService;
import cn.hutool.core.date.DateUtil;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author daishaokun
 * @date 2021/3/10
 */
@RestController
@RequestMapping("order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("test")
    public Order test() {
        log.info("事务id：{}", RootContext.getXID());

        Order order = new Order();
        order.setName("订单-"+DateUtil.formatDate(new Date()));
        order.setCreateTime(new Date());
        orderService.save(order);

        return order;
    }

}
