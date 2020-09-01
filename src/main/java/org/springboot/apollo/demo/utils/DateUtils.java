package org.springboot.apollo.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author： WangQian
 * @date： 2020/8/27 下午 4:59
 */
public class DateUtils {
    public static String dateFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
