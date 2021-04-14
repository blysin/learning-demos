package com.blysin.demo.common.factorybean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author daishaokun
 * @date 2021/2/27
 */
@RestController
@RequestMapping("test")
public class FactoryBeanTestController {
    @Resource
    private MyTargetObject helloTarget;

    @Resource
    private MyTargetObject worldTarget;

    @GetMapping("hello")
    public void hello() {
        helloTarget.execute();
    }

    @GetMapping("world")
    public void world() {
        worldTarget.execute();
    }
}
