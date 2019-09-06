package com.aura.bdp22.g3.spark.utils;

import java.util.StringTokenizer;

public class DataFormatUtils {

    public static void main(String[] args) {
        String str1 = "2,哈尔滨,64,19,,,1,超市便利店,超市,";
        String str2 = "3,南昌,774,5,3,2,0,美食,休闲茶饮,奶茶";
        String str3 = "1970,大连,1139,17,0,02,,超市便利店,超市," ;
//        StringTokenizer tokenizer = new StringTokenizer(str1,",",true);
//        int i = 0 ;
//        while(tokenizer.hasMoreElements()){
//            System.out.println("next token: "+tokenizer.nextToken(","));;
//        }

        String[] split = str1.split(",");
        for (String s:split) {
            System.out.println(s);
        }

    }
}
