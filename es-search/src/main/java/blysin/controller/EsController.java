package blysin.controller;

import blysin.domain.CarCome;
import blysin.service.CarComeService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author daishaokun
 * @date 2021/4/7
 */
@RestController
@RequestMapping("es")
@Slf4j
public class EsController {
    private final String index = "carcome";
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private CarComeService carComeService;

    @GetMapping("exist")
    public String exist(@RequestParam String id) throws IOException {
        log.info("索引:{},id:{}", index, id);
        boolean exist = restHighLevelClient.exists(new GetRequest(index, id), RequestOptions.DEFAULT);
        return String.valueOf(exist);
    }

    @GetMapping("put")
    public String put() throws IOException {
        log.info("索引:{}", index);
        IndexRequest indexRequest = new IndexRequest(index);

        CarCome carCome = carComeService.getOne();

        indexRequest.source(JSON.toJSONString(carCome), XContentType.JSON);

        IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        return String.valueOf(response.toString());
    }

    @GetMapping("batchPut")
    public String batchPut() throws IOException {
        log.info("索引:{}", index);
        BulkRequest bulkRequest = new BulkRequest(index);

        final List<CarCome> list = carComeService.list();
        for (CarCome carCome : list) {
            bulkRequest.add(new IndexRequest().source(JSON.toJSONString(carCome), XContentType.JSON));
        }


        final BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        if (bulk.hasFailures()) {
            return String.valueOf(bulk.buildFailureMessage());
        }
        return Arrays.toString(bulk.getItems());
    }


    @GetMapping("query")
    public String query() throws IOException {
        log.info("索引:{}", index);

        //过滤字段
        //sourceBuilder.fetchSource(new String[]{"billId"}, new String[]{});

        //搜索条件
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("carNo", "黑CSK5GM");
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("lotCode", "2007");
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("carTime");
        rangeQueryBuilder.gte(new Date(1617789323281L));
        rangeQueryBuilder.lte(new Date(1617789323381L));

        //匹配方式
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(matchQueryBuilder);
        boolBuilder.must(termQueryBuilder);
        boolBuilder.must(rangeQueryBuilder);


        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //分页
        sourceBuilder.from(0);
        sourceBuilder.size(10);

        //组装提交搜索
        sourceBuilder.query(boolBuilder);
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);


        SearchHits hits = response.getHits();
        log.info("hits数量：{}", hits.getHits().length);
        for (SearchHit hit : hits.getHits()) {
            System.out.println(hit.toString());
        }

        return response.toString();
    }


}
