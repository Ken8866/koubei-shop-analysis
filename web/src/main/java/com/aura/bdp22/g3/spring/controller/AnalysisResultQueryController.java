package com.aura.bdp22.g3.spring.controller;

import com.aura.bdp22.g3.spark.sql.MetricsAnalysis;
import com.aura.bdp22.g3.spring.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analysisResultQuery")
public class AnalysisResultQueryController {

    @Autowired
    QueryService queryService ;

    @GetMapping(path = "/queryUserLabels")
    public String queryUserLabels() throws Exception{
        return queryService.queryUserLabels(null);
    }

    @GetMapping(path = "/queryShopLabels")
    public String queryShopLabels() throws Exception{
        return queryService.queryShopLabels(null);
    }

    @GetMapping(path = "/queryUserBills")
    public String queryUserBills() throws Exception{
        return queryService.queryUserBills(null);
    }

}
