package com.blysin.demo.mybatis.test.service.impl;

import com.blysin.demo.mybatis.test.TcLogComp;
import com.blysin.demo.mybatis.test.dao.TcLogCompDao;
import com.blysin.demo.mybatis.test.service.TcLogCompService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 对账同步日志表(TcLogComp)表服务实现类
 *
 * @author code generate
 * @since 2019-09-24 19:14:16
 */
@Service("tcLogCompService")
public class TcLogCompServiceImpl extends ServiceImpl<TcLogCompDao, TcLogComp> implements TcLogCompService {
    @Resource
    private TcLogCompDao tcLogCompDao;

    
}