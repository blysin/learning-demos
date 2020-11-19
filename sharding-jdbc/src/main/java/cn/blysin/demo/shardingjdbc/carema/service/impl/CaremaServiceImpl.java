package cn.blysin.demo.shardingjdbc.carema.service.impl;

import cn.blysin.demo.shardingjdbc.carema.dao.CaremaMapper;
import cn.blysin.demo.shardingjdbc.carema.entity.Carema;
import cn.blysin.demo.shardingjdbc.carema.service.CaremaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * (TCarema)表服务实现类
 *
 * @author makejava
 * @since 2020-09-05 18:26:29
 */
@Service("caremaService")
public class CaremaServiceImpl extends ServiceImpl<CaremaMapper, Carema> implements CaremaService {

}