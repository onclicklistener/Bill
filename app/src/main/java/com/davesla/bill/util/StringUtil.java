package com.davesla.bill.util;

/**
 * Created by apple on 15-3-19.
 */
public class StringUtil {

    /**
     * 获取字符串字符长度,一个汉字等于2个英文字符
     * @param str
     * @return
     */
    public static int getFullLength(String str) {
        int len = str.length();
        str = str.replaceAll("[ 0-9a-zA-Z\\.,\\<\\>\\[\\]\\-\\+\\=\\_\\{\\}\\:\\;\\'\\\"]", "");
        return len + str.length();
    }
}
