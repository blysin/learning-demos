package com.blysin.demo.mybatis.test.service.impl;

import com.blysin.demo.mybatis.test.TcDict;
import com.blysin.demo.mybatis.test.dao.TcDictDao;
import com.blysin.demo.mybatis.test.service.TcDictService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * (TcDict)表服务实现类
 *
 * @author code generate
 * @since 2019-09-24 19:14:15
 */
@Service("tcDictService")
public class TcDictServiceImpl extends ServiceImpl<TcDictDao, TcDict> implements TcDictService {
    @Resource
    private TcDictDao tcDictDao;

    
}