package cn.blysin.service;


import cn.blysin.entity.Order;
import cn.blysin.mapper.OrderMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author daishaokun
 * @date 2021/3/11
 */
@Service
@Slf4j
public class OrderService extends ServiceImpl<OrderMapper, Order> {

}
