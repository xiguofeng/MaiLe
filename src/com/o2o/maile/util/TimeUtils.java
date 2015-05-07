
package com.o2o.maile.util;

import java.util.Date;

public class TimeUtils {

    public static final String FORMAT_PATTERN_DATE = "yyyy-MM-dd HH:mm:ss";

    public static long dateToLong(Date date) {
        
        return date.getTime() / 1000; // 得到秒数，Date类型的getTime()返回毫秒数 
    }

    /**
     * 将Unix时间戳改成正常时间
     * 
     * @param timestampString Unix时间戳
     * @param pattern 要显示的日期的格式，例如:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String TimeStamp2Date(String timestampString, String pattern) {
        if (timestampString != null && !"".equals(timestampString)) {
            Long timestamp = Long.parseLong(timestampString) * 1000;
            String date = new java.text.SimpleDateFormat(pattern).format(new java.util.Date(
                    timestamp));
            return date;
        }
        return "";
    }
}
