package com.blysin.demo.mybatis.service;

import com.blysin.demo.mybatis.dao.NewPlatformDao;
import com.blysin.demo.mybatis.domian.NewPlatform;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author daishaokun
 * @date 2019-09-23
 */
@Service
@Slf4j
public class NewPlatformService {
    @Autowired
    private NewPlatformDao newPlatformDao;

    public List<NewPlatform> findAll() {
        log.info("获取所有数据");
        return newPlatformDao.findAll();
    }

    public NewPlatform getById(Integer id) {
        return newPlatformDao.getById(id);
    }

    public NewPlatform getByParkName(String name) {
        return newPlatformDao.getByParkName(name);
    }
}