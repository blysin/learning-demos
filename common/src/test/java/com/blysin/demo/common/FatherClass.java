package com.blysin.demo.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author daishaokun
 * @date 2021/3/2
 */
@Data

public class FatherClass {
    private String name;
    public FatherClass(){
        System.out.print("FatherClass无参 ->");
    }

    public FatherClass(String name) {
        this.name = name;
        System.out.print("FatherClass有参 ->");
    }

    static {
        System.out.print("father static ->");
    }

    {
        System.out.print("father no static ->");
    }

    public void say() {
        System.out.print("father say ->");
    }

}
