package com.zyp.springboot.learn.elaticsearch.service.impl;

import com.alibaba.fastjson.JSON;
import com.zyp.springboot.learn.elaticsearch.document.ProductDocument;
import com.zyp.springboot.learn.elaticsearch.repository.ProductDocumentRepository;
import com.zyp.springboot.learn.elaticsearch.service.EsSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * elasticsearch 搜索引擎 service实现
 * @author zhoudong
 * @version 0.1
 * @date 2018/12/13 15:33
 */
@Service
public class EsSearchServiceImpl extends BaseSearchServiceImpl<ProductDocument> implements EsSearchService {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private ElasticsearchRestTemplate elasticsearchTemplate;
    @Resource
    private ProductDocumentRepository productDocumentRepository;

    @Override
    public void save(ProductDocument ... productDocuments) {
//        elasticsearchTemplate.putMapping(ProductDocument.class);
        IndexOperations indexOps = elasticsearchTemplate.indexOps(ProductDocument.class);
        if (!indexOps.exists()) {
            boolean indexCreated = indexOps.create();
            System.out.println("索引创建结果: " + indexCreated);
        }
        Document mapping = indexOps.createMapping();
        indexOps.putMapping(mapping);
        if(productDocuments.length > 0){
            /*Arrays.asList(productDocuments).parallelStream()
                    .map(productDocumentRepository::save)
                    .forEach(productDocument -> log.info("【保存数据】：{}", JSON.toJSONString(productDocument)));*/
            log.info("【保存索引】：{}",JSON.toJSONString(productDocumentRepository.saveAll(Arrays.asList(productDocuments))));
        }
    }

    @Override
    public void delete(String id) {
        productDocumentRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        productDocumentRepository.deleteAll();
    }

    @Override
    public ProductDocument getById(String id) {
        return productDocumentRepository.findById(id).get();
    }

    @Override
    public List<ProductDocument> getAll() {
        List<ProductDocument> list = new ArrayList<>();
        productDocumentRepository.findAll().forEach(list::add);
        return list;
    }

}
