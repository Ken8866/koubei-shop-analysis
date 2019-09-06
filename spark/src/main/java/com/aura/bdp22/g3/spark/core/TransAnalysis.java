package com.aura.bdp22.g3.spark.core;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.function.Consumer;

public class TransAnalysis {

    public void countTop10Trans(String filePath,SparkContext sparkContext){
        JavaSparkContext javaSparkContext = JavaSparkContext.fromSparkContext(sparkContext);
        JavaRDD<String> javaRDD = javaSparkContext.textFile(filePath);
        javaRDD.take(10).forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
        sparkContext.stop();
    }
}
