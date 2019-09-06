package com.aura.bdp22.g3.spark.sql;


import com.aura.bdp22.g3.spark.model.ShopInfo;
import com.aura.bdp22.g3.spark.model.UserLabels;
import com.aura.bdp22.g3.spark.model.UserPay;
import com.aura.bdp22.g3.spark.model.UserView;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.StructType;
import scala.reflect.ClassTag;

import java.io.Serializable;
import java.util.StringTokenizer;

public class MetricsAnalysis implements Serializable {

    private Dataset<UserView> userView ;
    private Dataset<UserPay> userPay ;
    private Dataset<ShopInfo> shopInfo ;

    private Dataset<Row> userPayCount;
    private Dataset<Row> shopPayedCount;
    private Dataset<Row> userReviewCount;
    private Dataset<Row> shopReviewedCount;
    private Dataset<Row> userCity;
    private Dataset<Row> shopScore;

    public Dataset<UserPay> parseUserPay(String path,SparkSession sparkSession){
        Encoder<UserPay> encoder = Encoders.bean(UserPay.class);
        userPay = sparkSession.read().text(path).map(new MapFunction<org.apache.spark.sql.Row, UserPay>() {
            public UserPay call(org.apache.spark.sql.Row row) throws Exception {
                String[] cols = row.getString(0).split(",");
                UserPay userPay = new UserPay();
                userPay.setUser_id(cols[0]);
                userPay.setShop_id(cols[1]);
                userPay.setTime_stamp(cols[2]);
                return userPay;
            }},encoder);

        return userPay ;
    }

    public Dataset<UserView> parseUserView(String path,SparkSession sparkSession){
        Encoder<UserView> encoder = Encoders.bean(UserView.class);
        userView = sparkSession.read().text(path).map(new MapFunction<org.apache.spark.sql.Row, UserView>() {
            public UserView call(org.apache.spark.sql.Row row) throws Exception {
                String[] cols = row.getString(0).split(",");
                UserView userView = new UserView();
                userView.setUser_id(cols[0]);
                userView.setShop_id(cols[1]);
                userView.setTime_stamp(cols[2]);
                return userView;
            }},encoder);

        return userView ;
    }

    public Dataset<ShopInfo> parseShopInfo(String path,SparkSession sparkSession){
        Encoder<ShopInfo> encoder = Encoders.bean(ShopInfo.class);
        shopInfo = sparkSession.read().text(path).map(new MapFunction<org.apache.spark.sql.Row, ShopInfo>() {
            public ShopInfo call(org.apache.spark.sql.Row row) throws Exception {
                ShopInfo shopInfo = new ShopInfo();
                String[] data = new String[10];
                String[] cols = row.getString(0).split(",");
                for(int i=0 ;i<10 && i<cols.length;i++){
                    data[i] = cols[i];
                }
                shopInfo.setShop_id(data[0]);
                shopInfo.setCity_name(data[1]);
                shopInfo.setLocation_id(data[2]);
                if(data[3].isEmpty()){
                    shopInfo.setPer_pay(Double.valueOf(data[3])+0.00);
                }else{
                    shopInfo.setPer_pay(Double.valueOf(data[3]));
                }
                if(data[4].isEmpty()){
                    shopInfo.setScore(Integer.valueOf(data[4]+0));
                }else{
                    shopInfo.setScore(Integer.valueOf(data[4]));
                }
                if(data[5].isEmpty()){
                    shopInfo.setComment_cnt(Integer.valueOf(data[5]+0));
                }else{
                    shopInfo.setComment_cnt(Integer.valueOf(data[5]));
                }
                if(data[6].isEmpty()){
                    shopInfo.setShop_level(Integer.valueOf(data[6])+0);
                }else{
                    shopInfo.setShop_level(Integer.valueOf(data[6]));
                }
                shopInfo.setCate_1_name(data[7]);
                shopInfo.setCate_2_name(data[8]);
                shopInfo.setCate_3_name(data[9]);
                return shopInfo;
            }},encoder);

        return shopInfo ;
    }

    public Dataset<Row> countUserPay(String startDate, String endDate) {
        String sql = "select user_id, count(1) as count from userPay where to_date(time_stamp) between to_date('"+startDate+"') and to_date('"+endDate+"') group by user_id " ;
        userPay.createOrReplaceTempView("userPay");
        userPayCount = userPay.sqlContext().sql(sql);
        return userPayCount;
    }

    public Dataset<Row> countShopPayed(String startDate, String endDate) {
        String sql = "select shop_id, count(1) as count from userPay where to_date(time_stamp) between to_date('"+startDate+"') and to_date('"+endDate+"') group by shop_id " ;
        userPay.createOrReplaceTempView("userPay");
        shopPayedCount = userPay.sqlContext().sql(sql);
        return shopPayedCount;
    }

    public Dataset<Row> countUserReview(String startDate, String endDate) {
        String sql = "select user_id, count(1) as count from userView where to_date(time_stamp) between to_date('"+startDate+"') and to_date('"+endDate+"') group by user_id " ;
        userView.createOrReplaceTempView("userView");
        userReviewCount = userView.sqlContext().sql(sql);
        return userReviewCount;
    }

    public Dataset<Row> countShopReviewed(String startDate, String endDate) {
        String sql = "select shop_id, count(1) as count from userView where to_date(time_stamp) between to_date('"+startDate+"') and to_date('"+endDate+"') group by shop_id " ;
        userView.createOrReplaceTempView("userView");
        shopReviewedCount = userView.sqlContext().sql(sql);
        return shopReviewedCount;
    }

    public Dataset<Row> getUserCityName(){
        String sql = "select user_id, shop_id, count(1) as count from userView group by shop_id, user_id " ;
        userView.createOrReplaceTempView("userView");
        Dataset<Row> userShopCount = userView.sqlContext().sql(sql);

        userShopCount.createOrReplaceTempView("userShopCount");
        String sql2 = "select user_id, shop_id from userShopCount where (shop_id, count) in (select shop_id, max(count) from userShopCount group by shop_id)" ;
        Dataset<Row> maxShopByUser = userShopCount.sqlContext().sql(sql2);

        shopInfo.createOrReplaceTempView("shopInfo");
        Dataset<Row> shopInfoDs = shopInfo.select("shop_id","city_name");

        userCity = maxShopByUser.join(shopInfoDs, "shop_id").select("user_id","city_name");

        return userCity;
    }

    public Dataset<Row> getShopScore(){
        String sql = "select shop_id, score from shopInfo " ;
        shopInfo.createOrReplaceTempView("shopInfo");
        shopScore = shopInfo.select("shop_id","score");
        return  shopScore;
    }

}
