package com.aura.bdp22.g3.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class UserPayProducer implements Serializable {

    public static final String TOPIC_USER_PAY = "user_pay";

    public void writeUserPay2Kafka(String filePath,JavaSparkContext javaSparkContext) throws Exception{
        JavaRDD<String> rdd = javaSparkContext.textFile(filePath);
        rdd.foreach(new VoidFunction<String>() {
            @Override
            public void call(String s) throws Exception {
                String[] split = s.split(",");
                sendKafkaRecord(TOPIC_USER_PAY,split[0],split[1]+","+split[2]);
                System.out.println("key: "+split[0]+"=> value: "+split[1]+","+split[2]);
                Thread.sleep(10);
            }
        });

        javaSparkContext.stop();
    }

    public void testKafkaProducer() throws Exception{
        List<String> data ;

    }

    public void sendKafkaRecord(String topic, String key ,String value) throws Exception{
        Properties properties = new Properties();
        InputStream inputStream = UserPayProducer.class.getClassLoader().getResourceAsStream("kafka_producer.properties");
        properties.load(inputStream);
        KafkaProducer producer = new KafkaProducer(properties);
        ProducerRecord record = new ProducerRecord(topic,key,value);
        producer.send(record);
    }
}
