package cn.blysin.demo.shardingjdbc.sharding;

import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 车场路由规则
 *
 * @author daishaokun
 * @date 2020/9/6
 */
@Slf4j
public class LotCodeTableShardingRule implements PreciseShardingAlgorithm<Integer>, RangeShardingAlgorithm<Integer> {


    /**
     * 精准分片路由
     * 用于解析=和in的sql
     *
     * @param collection           可选的分库
     * @param preciseShardingValue 分片键值
     * @return
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Integer> preciseShardingValue) {
        log.info("可选的分库：{}，当前分片键：{}", collection, preciseShardingValue.getValue());
        return "park" + (preciseShardingValue.getValue() - 1);
    }

    /**
     * 范围分片路由
     * 用户解析between，<,<=,>,>=的sql语句
     *
     * @param collection
     * @param rangeShardingValue
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Integer> rangeShardingValue) {
        Range<Integer> range = rangeShardingValue.getValueRange();
        //最低值
        Integer low = range.lowerEndpoint();
        //最大值
        Integer upper = range.upperEndpoint();
        Collection<String> sharding = new ArrayList<>();
        for (int i = low - 1; i < upper - 1; i++) {
            sharding.add("park" + i);
        }

        log.info("范围路由，最小值：{}，最大值：{}，路由结果：{}", low, upper, sharding);

        return sharding;
    }

}