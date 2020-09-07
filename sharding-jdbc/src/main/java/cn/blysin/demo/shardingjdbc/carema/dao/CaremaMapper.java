package cn.blysin.demo.shardingjdbc.carema.dao;

import cn.blysin.demo.shardingjdbc.carema.entity.Carema;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * (TCarema)表数据库访问层
 *
 * @author makejava
 * @since 2020-09-05 18:26:28
 */
@Mapper
public interface CaremaMapper extends BaseMapper<Carema> {

}