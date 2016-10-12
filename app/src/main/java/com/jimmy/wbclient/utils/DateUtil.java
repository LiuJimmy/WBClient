package com.jimmy.wbclient.utils;

import android.text.format.DateFormat;
import android.text.format.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * Created by Administrator on 2016/10/9.
 */
public class DateUtil {
    /**
     * 获取微博创建的时间
     *
     * @param dateStr
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String createDate(String dateStr) {
        Date curDate = new Date();  //当前时间
        Date pubDate = new Date(dateStr);
        long durDate = curDate.getTime() - pubDate.getTime();

        Calendar curCal = Calendar.getInstance();//代表当前时间的日历
        curCal.setTime(curDate);
        int curYear = curCal.get(Calendar.YEAR);
        int curDay = curCal.get(Calendar.DAY_OF_YEAR);

        Calendar pubCal = Calendar.getInstance();//代表微博发布时的日历
        pubCal.setTime(pubDate);
        int pubYear = pubCal.get(Calendar.YEAR);
        int pubDay = pubCal.get(Calendar.DAY_OF_YEAR);

        String result;
        if (DateUtils.isToday(pubDate.getTime())) {                     //是否当天
            if (durDate / DateUtils.MINUTE_IN_MILLIS < 1) {
                result = "刚刚";
            } else if (durDate / DateUtils.MINUTE_IN_MILLIS < 60) {
                result = durDate / DateUtils.MINUTE_IN_MILLIS + "分钟前";
            } else {
                result = durDate / DateUtils.HOUR_IN_MILLIS + "小时前";
            }
        } else if (curYear == pubYear) {                               //是否当年
            if (curDay - pubDay == 1) {
                result = "昨天" + DateFormat.format("kk:mm", pubDate).toString();
            } else {
                result = DateFormat.format("MM-dd", pubDate).toString();
            }
        } else {
            result = DateFormat.format("yyyy-MM-dd", pubDate).toString();//否则
        }
        return result;
    }
}
