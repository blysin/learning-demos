package com.blysin.demo.common.future;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 异步转同步实现，借鉴dubbo的实现逻辑
 *
 * @author daishaokun
 * @date 2020/12/3
 */
@RestController
@RequestMapping("future")
public class FutureController {
    @GetMapping("get")
    public Object get() {
        String id = System.currentTimeMillis() + "";
        System.out.println("记录id： " + id);
        try {
            Object result = new DefaultFuture(id).get();
            System.out.println("结果：" + result);
            return result;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("push")
    public void push(String id, String result) {
        //http://127.0.0.1:25001/future/push?result=哈哈哈哈&id=
        DefaultFuture.doReceived(id, result);
    }
}
