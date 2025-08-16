package com.zyp.springboot.learn.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class DruidStatController {
    @GetMapping("/druid/stat")
    public Object druidStat() {
        List<Map<String, Object>> dataSourceStatDataList = DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
        log.info("dataSourceStatDataList: {}", dataSourceStatDataList.size());
        return dataSourceStatDataList;
    }
}