package com.aura.bdp22.g3.kafka.client;

import com.aura.bdp22.g3.kafka.producer.UserPayProducer;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;

public class KafkaClient {

    public static final String USER_PAY = "data/user_pay.txt" ;

    public static void main(String[] args) throws Exception{
        UserPayProducer userPayProducer = new UserPayProducer();
        SparkConf sparkConf = new SparkConf();
        sparkConf.setMaster("local[2]");
        sparkConf.setAppName("kafka producer");
        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);
        userPayProducer.writeUserPay2Kafka(USER_PAY,sparkContext);

        sparkContext.stop();
    }
}
