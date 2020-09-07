package cn.blysin.demo.shardingjdbc.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author daishaokun
 * @date 2020/9/5
 */
@RestController
@RequestMapping("sharding")
public class TestController {

    @GetMapping("ping")
    public String ping(String lotCode) {
        return "hello " + lotCode;
    }
}