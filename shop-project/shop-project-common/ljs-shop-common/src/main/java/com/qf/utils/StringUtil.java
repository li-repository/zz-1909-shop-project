package com.qf.utils;

/**
 * 封装项目中需要的字符串拼接静态方法
 * @author li
 * @date 2020/3/10 0010 20:51
 */

public class StringUtil {

    //字符串拼接
    public static String format(String pre,String key){
        return new StringBuilder().append(pre).append(key).toString();
    }

    public static String format(String pre,String key1,String key2){
        return new StringBuilder().append(pre).append(key1).append(key2).toString();
    }

}
