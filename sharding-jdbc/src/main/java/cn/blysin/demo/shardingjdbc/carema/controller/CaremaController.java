package cn.blysin.demo.shardingjdbc.carema.controller;


import cn.blysin.demo.shardingjdbc.carema.entity.Carema;
import cn.blysin.demo.shardingjdbc.carema.service.CaremaService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (TCarema)表控制层
 *
 * @author makejava
 * @since 2020-09-05 18:26:29
 */
@RestController
@RequestMapping("carema")
public class CaremaController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private CaremaService caremaService;


    @GetMapping("listByLotCode")
    public R listByLotCode(Integer lotCode) {
        return success(caremaService.list(Wrappers.<Carema>lambdaQuery().eq(Carema::getLotCode, lotCode)));
    }

    @GetMapping("range")
    public R range(Integer min, Integer max) {
        return success(caremaService.list(Wrappers.<Carema>lambdaQuery().between(Carema::getLotCode, min, max)));
    }

    /**
     * 分页查询所有数据
     *
     * @param page    分页对象
     * @param tCarema 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<Carema> page, Carema tCarema) {
        return success(this.caremaService.page(page, new QueryWrapper<>(tCarema)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.caremaService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param tCarema 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody Carema tCarema) {
        return success(this.caremaService.save(tCarema));
    }

    /**
     * 修改数据
     *
     * @param tCarema 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody Carema tCarema) {
        return success(this.caremaService.updateById(tCarema));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.caremaService.removeByIds(idList));
    }
}