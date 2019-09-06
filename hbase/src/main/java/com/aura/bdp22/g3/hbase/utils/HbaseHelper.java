package com.aura.bdp22.g3.hbase.utils;

import com.aura.bdp22.g3.hbase.dao.ShopLabelsDaoHbaseImpl;
import com.aura.bdp22.g3.hbase.dao.UserBillDaoHbaseImpl;
import com.aura.bdp22.g3.hbase.dao.UserLabelsDaoHbaseImpl;
import com.aura.bdp22.g3.spark.model.ShopLabels;
import com.aura.bdp22.g3.spark.model.UserBill;
import com.aura.bdp22.g3.spark.model.UserLabels;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

import java.io.Serializable;
import java.util.List;

public class HbaseHelper implements Serializable {

    public static void saveUserLabels(List<com.aura.bdp22.g3.spark.model.UserLabels> userLabelsList) throws Exception{
        if (!userLabelsList.isEmpty()){
            UserLabelsDaoHbaseImpl userLabelsDao = new UserLabelsDaoHbaseImpl();
            userLabelsDao.save(userLabelsList);
        }
    }

    public static List<UserLabels> findUserLabels(UserLabels userLabels) throws Exception{
        UserLabelsDaoHbaseImpl userLabelsDao = new UserLabelsDaoHbaseImpl();
        return userLabelsDao.findUserLabelsList(userLabels);
    }

    public static void saveShopLabels(List<ShopLabels> shopLabelsList) throws Exception{
        if (!shopLabelsList.isEmpty()){
            ShopLabelsDaoHbaseImpl shopLabelsDao = new ShopLabelsDaoHbaseImpl();
            shopLabelsDao.save(shopLabelsList);
        }
    }

    public static List<ShopLabels> findShopLabelsList(ShopLabels shopLabels) throws Exception{
        ShopLabelsDaoHbaseImpl shopLabelsDao = new ShopLabelsDaoHbaseImpl();
        return shopLabelsDao.findShopLabelsList(shopLabels);
    }

    public static void saveUserBills(List<UserBill> userBills) throws Exception{
        if (!userBills.isEmpty()){
            UserBillDaoHbaseImpl userBillDao = new UserBillDaoHbaseImpl();
            userBillDao.save(userBills);
        }
    }

    public static List<UserBill> findUserBills(UserBill userBill) throws Exception{
        UserBillDaoHbaseImpl userBillDao = new UserBillDaoHbaseImpl();
        return userBillDao.findUserBills(userBill);
    }

}
