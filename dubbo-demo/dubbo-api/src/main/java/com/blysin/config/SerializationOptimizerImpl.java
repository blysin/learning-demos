package com.blysin.config;

import com.blysin.vo.Order;
import org.apache.dubbo.common.serialize.support.SerializationOptimizer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 标记需要序列化的类，提高性能
 *
 * @author daishaokun
 * @date 2020/12/25
 */
public class SerializationOptimizerImpl implements SerializationOptimizer {

    @Override
    public Collection<Class<?>> getSerializableClasses() {
        List<Class<?>> classes = new LinkedList<Class<?>>();

        classes.add(Order.class);

        return classes;
    }
}
