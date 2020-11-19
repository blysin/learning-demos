package com.blysin.demo.web.controller;

import com.blysin.demo.web.domian.NewPlatform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author daishaokun
 * @date 2020/7/28
 */
@RestController
@RequestMapping("test")
@Slf4j
public class TestController {

    @GetMapping("get")
    public NewPlatform getOne(){
        log.info("get请求\n\n");
        return new NewPlatform(123, new Date(), "hello world");
    }

    @PostMapping("post")
    public NewPlatform post(@RequestBody NewPlatform newPlatform) {
        log.info("post请求\n\n");
        return new NewPlatform(123, new Date(), "哈航昂昂");
    }
}