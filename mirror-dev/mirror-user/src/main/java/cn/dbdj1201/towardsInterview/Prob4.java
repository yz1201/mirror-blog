package cn.dbdj1201.towardsInterview;

import cn.hutool.core.date.TimeInterval;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-05 18:13
 */
@Slf4j
public class Prob4 {
    /*请实现一个函数，把字符串中的每个空格替换成“%20”。*/

    public static void main(String[] args) {
        TimeInterval interval = new TimeInterval();
        String inputStr = " a  b c sd ";
//        System.out.println(inputStr);
        log.info("former str--->{}", inputStr);
        System.out.println(interval.interval());
        log.info("latter str--->{}", replaceBlank2(inputStr));
    }

    public static String replaceBlank(String text) {

        StringBuilder sb = new StringBuilder();
        for (byte b : text.getBytes()) {
            if (b != 32) {
                log.info("" + b);
                sb.append((char) b);
            } else {
                sb.append("%20");
            }
        }

        return sb.toString();
    }

    public static String replaceBlank2(String text) {
        StringBuilder sb = new StringBuilder(text);
        int length = sb.length();
        for (int i = 0; i < length; i++) {
            if (sb.charAt(i) == ' '){
                sb.replace(i,i+1,"%20");
            }
        }

        return sb.toString();
    }

}
