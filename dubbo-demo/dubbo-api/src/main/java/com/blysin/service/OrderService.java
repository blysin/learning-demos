package com.blysin.service;

import com.blysin.vo.Order;

import java.util.List;

/**
 * @author daishaokun
 * @date 2020/12/25
 */
public interface OrderService {
    /**
     * 获取一个
     *
     * @param id
     * @return
     */
    Order getOne(Integer id);

    /**
     * 获取列表
     *
     * @return
     */
    List<Order> list();
}
