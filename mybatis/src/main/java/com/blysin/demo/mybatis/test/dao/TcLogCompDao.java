package com.blysin.demo.mybatis.test.dao;

import com.blysin.demo.mybatis.test.TcLogComp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 对账同步日志表(TcLogComp)表数据库访问层
 *
 * @author code generate
 * @since 2019-09-24 19:14:16
 */
 @Repository
public interface TcLogCompDao extends BaseMapper<TcLogComp> {

}