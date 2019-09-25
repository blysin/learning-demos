package com.blysin.demo.mybatis.dao;

import com.blysin.demo.mybatis.domian.NewPlatform;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Blysin
 * @since 2019-09-23
 */
@Repository
public interface NewPlatformDao {

    List<NewPlatform> findAll();

    NewPlatform getById(@Param("id") Integer id);

    NewPlatform getByParkName(@Param("parkName") String parkName);

}
