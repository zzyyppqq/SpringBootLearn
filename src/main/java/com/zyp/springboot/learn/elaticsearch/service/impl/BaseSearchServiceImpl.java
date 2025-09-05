package com.zyp.springboot.learn.elaticsearch.service.impl;

import com.zyp.springboot.learn.elaticsearch.service.BaseSearchService;
import com.zyp.springboot.learn.elaticsearch.page.Page;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * elasticsearch 搜索引擎
 * @author zhoudong
 * @version 0.1
 * @date 2018/12/13 15:33
 */
@Service
public class BaseSearchServiceImpl<T> implements BaseSearchService<T> {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private ElasticsearchRestTemplate elasticsearchTemplate;


    @Override
    public List<T> query(String keyword, Class<T> clazz) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(new QueryStringQueryBuilder(keyword))
                .withSort(SortBuilders.scoreSort().order(SortOrder.DESC))
                // .withSort(new FieldSortBuilder("createTime").order(SortOrder.DESC))
                .build();

        SearchHits<T> searchHits = elasticsearchTemplate.search(searchQuery, clazz);
        return searchHits.getSearchHits().stream()
                .map(hit -> hit.getContent())
                .collect(Collectors.toList());
    }

    /**
     * 高亮显示
     * @auther: zhoudong
     * @date: 2018/12/13 21:22
     */
    @Override
    public  List<Map<String,Object>> queryHit(String keyword,String indexName,String ... fieldNames) {
        // 构造查询条件,使用标准分词器.
        QueryBuilder matchQuery = createQueryBuilder(keyword,fieldNames);

        // 设置高亮,使用默认的highlighter高亮器
        HighlightBuilder highlightBuilder = createHighlightBuilder(fieldNames);

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery)
                .withHighlightBuilder(highlightBuilder)
                .withPageable(PageRequest.of(0, 10000))
                .build();
        SearchHits<Object> hits = elasticsearchTemplate.search(searchQuery, Object.class ,IndexCoordinates.of(indexName));

        return getHitList(hits);
    }
    /**
     * 高亮显示，返回分页
     * @auther: zhoudong
     * @date: 2018/12/18 10:29
     */
    @Override
    public Page<Map<String, Object>> queryHitByPage(int pageNo,int pageSize, String keyword, String indexName, String... fieldNames) {
        // 构造查询条件,使用标准分词器.
        QueryBuilder matchQuery = createQueryBuilder(keyword,fieldNames);

        // 设置高亮,使用默认的highlighter高亮器
        HighlightBuilder highlightBuilder = createHighlightBuilder(fieldNames);

        // 设置查询字段
//        SearchResponse response = elasticsearchTemplate.getClient().prepareSearch(indexName)
//                .setQuery(matchQuery)
//                .highlighter(highlightBuilder)
//                .setFrom((pageNo-1) * pageSize)
//                .setSize(pageNo * pageSize)
//                .get();

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery)
                .withHighlightBuilder(highlightBuilder)
                .withPageable(PageRequest.of(pageNo - 1, pageSize))
                .build();

        SearchHits<Object> hits = elasticsearchTemplate.search(searchQuery, Object.class ,IndexCoordinates.of(indexName));


        Long totalCount = hits.getTotalHits();
        Page<Map<String, Object>> page = new Page<>(pageNo,pageSize,totalCount.intValue());
        page.setList(getHitList(hits));
        return page;
    }

    /**
     * 构造查询条件
     * @auther: zhoudong
     * @date: 2018/12/18 10:42
     */
    private QueryBuilder createQueryBuilder(String keyword, String... fieldNames){
        // 构造查询条件,使用标准分词器.
        return QueryBuilders.multiMatchQuery(keyword,fieldNames)   // matchQuery(),单字段搜索
                .analyzer("ik_max_word")
                .operator(Operator.OR);
    }
    /**
     * 构造高亮器
     * @auther: zhoudong
     * @date: 2018/12/18 10:44
     */
    private HighlightBuilder createHighlightBuilder(String... fieldNames){
        // 设置高亮,使用默认的highlighter高亮器
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                // .field("productName")
                .preTags("<span style='color:red'>")
                .postTags("</span>");

        // 设置高亮字段
        for (String fieldName: fieldNames) highlightBuilder.field(fieldName);

        return highlightBuilder;
    }


    /**
     * 处理搜索结果和高亮信息
     * @param searchHits 搜索结果
     * @return 包含源数据和高亮数据的Map列表
     */
    private List<Map<String, Object>> getHitList(SearchHits<?> searchHits) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map;

        for (SearchHit<?> searchHit : searchHits) {
            map = new HashMap<>();
            // 获取源数据
            map.put("source", searchHit.getContent());

            // 处理高亮数据
            Map<String, List<String>> highlightFields = searchHit.getHighlightFields();
            Map<String, String> highlightMap = new HashMap<>();

            for (Map.Entry<String, List<String>> entry : highlightFields.entrySet()) {
                String key = entry.getKey();
                List<String> fragments = entry.getValue();
                // 将所有高亮片段合并为一个字符串
                String combinedHighlight = String.join(" ", fragments);
                highlightMap.put(key, combinedHighlight);
            }

            map.put("highlight", highlightMap);
            list.add(map);
        }
        return list;
    }

    @Override
    public void deleteIndex(String indexName) {
        elasticsearchTemplate.indexOps(IndexCoordinates.of(indexName)).delete();
    }
}
