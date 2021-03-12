package cn.blysin.service;


import cn.blysin.entity.History;
import cn.blysin.entity.OrderDTO;
import cn.blysin.feign.OrderApi;
import cn.blysin.mapper.HistoryMapper;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author daishaokun
 * @date 2021/3/11
 */
@Service
@Slf4j
public class HistoryService extends ServiceImpl<HistoryMapper, History> {
    @Autowired
    private OrderApi orderApi;

    @GlobalTransactional(name = "default", rollbackFor = Exception.class)
    public History doSave(boolean exception) {
        OrderDTO order = orderApi.test();
        log.info("获取到的订单为：" + order);


        History history = new History();
        history.setName("购买历史-" + DateUtil.formatDate(new Date()));
        history.setCreateTime(new Date());
        //super.save(history);

        super.remove(Wrappers.<History>lambdaQuery().le(History::getId, 100));

        if (exception) {
            throw new RuntimeException("测试");
        }

        return history;
    }
}
