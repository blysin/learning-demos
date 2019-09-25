package com.blysin.demo.mybatis.test.dao;

import com.blysin.demo.mybatis.test.TcDict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * (TcDict)表数据库访问层
 *
 * @author code generate
 * @since 2019-09-24 19:14:13
 */
 @Repository
public interface TcDictDao extends BaseMapper<TcDict> {

}