package com.blysin.demo.common.factorybean;

import lombok.Data;

/**
 * 真正的目标对象
 *
 * @author daishaokun
 * @date 2021/2/27
 */
@Data
public class MyTargetObject {
    public String name;

    public void execute(){
        System.out.println(name);
    }
}
