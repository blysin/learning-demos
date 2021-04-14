package com.blysin.demo.common.springsource.facotry;

import com.blysin.demo.common.CommonApplication;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author daishaokun
 * @date 2021/1/31
 */
public class SpringFactorySourceViewer {
    public void fun1() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(CommonApplication.class);

        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();

        //BeanPostProcessor
        //postProcessAfterInstantiation
        //postProcessAfterInitialization
    }

}
