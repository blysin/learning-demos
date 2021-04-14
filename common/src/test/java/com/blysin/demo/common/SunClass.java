package com.blysin.demo.common;

import lombok.Data;

/**
 * @author daishaokun
 * @date 2021/3/2
 */
@Data
public class SunClass extends FatherClass{
    private Integer age;

    public SunClass(){
        System.out.print("SunClass 无参 ->");
    }

    public SunClass(Integer age) {
        //super("测试");
        this.age = age;
        System.out.print("SunClass 有参 ->");
    }

    static {
        System.out.print("SunClass static ->");
    }

    {
        System.out.print("SunClass no static ->");
    }

    public void say() {
        System.out.print("sun say ->");
    }
}
