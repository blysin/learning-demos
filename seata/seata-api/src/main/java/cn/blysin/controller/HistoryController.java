package cn.blysin.controller;

import cn.blysin.entity.History;
import cn.blysin.service.HistoryService;
import cn.hutool.core.date.DateUtil;
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
@RequestMapping("history")
public class HistoryController {
    @Autowired
    private HistoryService orderService;

    @GetMapping("test")
    public History test(boolean exception) {
        try {
            return orderService.doSave(exception);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
