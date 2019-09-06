package com.aura.bdp22.g3.spring.service;

import com.aura.bdp22.g3.spark.sql.MetricsAnalysis;
import org.springframework.stereotype.Service;

@Service
public class AnalysisResultQueryServiceImpl implements QueryService{


    @Override
    public String queryUserLabels(Object o) {
        return "{result:queryUserLabels}";
    }

    @Override
    public String queryShopLabels(Object o) {
        return "{result:queryShopLabels}";
    }

    @Override
    public String queryUserBills(Object o) {
        return "{result:queryUserBills}";
    }
}
