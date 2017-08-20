package com.wheat7.cashew.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wheat7 on 2017/8/20.
 */

public class TimeUtil {
    public static String getFormatTime(String time){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        long diff = 0;
        try {
            Date publish_date = df.parse(time);
            Date now_date = new Date();
            diff = now_date.getTime() - publish_date.getTime();
            if (diff < 0) {
                return "来自虫洞";
            } else {
                diff /= (1000 * 60);
                if (diff <= 60) {
                    return diff + "分钟前";
                }
                diff /= 60;
                if (diff <= 24) {
                    return diff + "小时前";
                }
                diff /= 24;
                if (diff < 30) {
                    if (diff == 1) return "昨天";
                    return diff + "天前";
                }
                DateFormat df_date = new SimpleDateFormat("yyyy-MM-dd");
                return df_date.format(publish_date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}