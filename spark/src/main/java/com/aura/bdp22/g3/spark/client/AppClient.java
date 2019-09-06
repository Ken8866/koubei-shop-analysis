package com.aura.bdp22.g3.spark.client;

import com.alibaba.fastjson.JSON;
import com.aura.bdp22.g3.spark.core.TransAnalysis;
import com.aura.bdp22.g3.spark.model.ShopInfo;
import com.aura.bdp22.g3.spark.model.UserLabels;
import com.aura.bdp22.g3.spark.model.UserPay;
import com.aura.bdp22.g3.spark.model.UserView;
import com.aura.bdp22.g3.spark.sql.MetricsAnalysis;
import com.aura.bdp22.g3.spark.utils.DatasetCovertUtils;
import com.sun.rowset.internal.Row;
import org.apache.spark.api.java.function.ForeachPartitionFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class AppClient {

    public static final String USER_PAY = "data/user_pay.txt" ;
    public static final String USER_VIEW = "data/user_view.txt" ;
    public static final String SHOP_INFO = "data/shop_info.txt" ;

    public static void main(String[] args) {

        SparkSession sparkSession = SparkSession.builder().master("local[*]").appName("User Labels Analysis").getOrCreate();
        sparkSession.sparkContext().setLogLevel("WARN");
        MetricsAnalysis metricsAnalysis = new MetricsAnalysis();
        Dataset<UserPay> userPayDataset = metricsAnalysis.parseUserPay(USER_PAY,sparkSession);
        Dataset<UserView> userViewDataset = metricsAnalysis.parseUserView(USER_VIEW,sparkSession);
        Dataset<ShopInfo> shopInfoDataset = metricsAnalysis.parseShopInfo(SHOP_INFO, sparkSession);

//        metricsAnalysis.countUserPay("2016-01-01","2016-10-31").show();
//        metricsAnalysis.countShopPayed("2016-01-01","2016-10-31").show();
//        metricsAnalysis.countShopReviewed("2016-01-01","2016-10-31").show();
//        metricsAnalysis.countUserReview("2016-01-01","2016-10-31").show();

        TransAnalysis transAnalysis = new TransAnalysis();
        transAnalysis.countTop10Trans(USER_PAY,sparkSession.sparkContext());


//           userViewDataset.foreachPartition(new ForeachPartitionFunction<UserView>() {
//               @Override
//               public void call(Iterator<UserView> iterator) throws Exception {
//                   ArrayList<UserView> userViews = new ArrayList<>();
//                   iterator.forEachRemaining(new Consumer<UserView>() {
//                       @Override
//                       public void accept(UserView userView) {
//                           userViews.add(userView);
//                       }
//                   });
//
//                   userViews.stream().forEach(o-> System.out.println(JSON.toJSONString(o)));
//               }
//           });
//
//           userPayDataset.foreachPartition(new ForeachPartitionFunction<UserPay>() {
//               @Override
//               public void call(Iterator<UserPay> iterator) throws Exception {
//                   ArrayList<UserPay> userPays = new ArrayList<>();
//                   iterator.forEachRemaining(new Consumer<UserPay>() {
//                       @Override
//                       public void accept(UserPay userPay) {
//                           userPays.add(userPay);
//                       }
//                   });
//                   userPays.stream().forEach(o-> System.out.println(JSON.toJSONString(o)));
//               }
//           });

        sparkSession.stop();
    }
}
