package com.blysin.demo.common.factorybean;

import lombok.Data;
import org.springframework.beans.factory.FactoryBean;

/**
 * 目标类的FactoryBean
 *
 * @author daishaokun
 * @date 2021/2/27
 */
@Data
public class MyTargetObjectFactoryBean implements FactoryBean<MyTargetObject> {
    private String name;


    @Override
    public MyTargetObject getObject() throws Exception {
        MyTargetObject myTargetObject = new MyTargetObject();
        myTargetObject.setName(name);
        return myTargetObject;
    }

    @Override
    public Class<?> getObjectType() {
        return MyTargetObject.class;
    }
}
