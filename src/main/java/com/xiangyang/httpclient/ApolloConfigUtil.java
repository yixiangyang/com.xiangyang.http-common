package com.xiangyang.httpclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApolloConfigUtil {
    public static String getStringConfig(String property, String defaultValue) {
//        Config config = ConfigFactory.getConfig("weimob.httpclient-enhance");
//        if (null == config)
//            return defaultValue;
        return null;
    }

    public static boolean getBooleanConfig(String property, boolean defaultValue) {
//        Config config = ConfigFactory.getConfig("weimob.httpclient-enhance");
//        if (null == config)
//            return defaultValue;
//        return config.getBooleanProperty(property, Boolean.valueOf(defaultValue)).booleanValue();
        return Boolean.FALSE;
    }


    public static List<String> getListStrConfig(String property, List<String> defaultList) {
//        String[] arr = new String[defaultList.size()];
//        defaultList.toArray(arr);
//        return Lists.newArrayList((Object[])getArrStrConfig(property, arr));
        return new ArrayList<>();
    }

    public static String[] getArrStrConfig(String property, String[] defaultArr) {
//        Config config = ConfigFactory.getConfig("weimob.httpclient-enhance");
//        if (null == config)
//            return defaultArr;
//        String value = config.getProperty(property, "");
//        if (value.trim().equals(""))
//            return defaultArr;
//        String[] arr = value.split(",");
//        return (null == arr || arr.length == 0) ? defaultArr : arr;
        return null;
    }

    public static Integer getIntegerConfig(String property, Integer defaultValue) {
//        Config config = ConfigFactory.getConfig("weimob.httpclient-enhance");
//        if (null == config)
//            return defaultValue;
//        return config.getIntProperty(property, defaultValue);
        return null;
    }

    public static HashMap<String, Integer> getMapConfig(String property) {
//        HashMap<String, Integer> map = new HashMap<>();
//        try {
//            Config config = ConfigFactory.getConfig("weimob.httpclient-enhance");
//            if (null == config)
//                return map;
//            JSONArray jsonArray = JSONArray.parseArray(config.getProperty(property, ""));
//            return map;
//        } finally {
//            Exception exception = null;
//        }
        return null;
    }
}
