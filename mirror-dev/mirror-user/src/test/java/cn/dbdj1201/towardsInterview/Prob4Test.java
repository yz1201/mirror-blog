package cn.dbdj1201.towardsInterview;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-05 18:30
 */
public class Prob4Test {

    @Test
    public void replaceBlank() {
        StringBuilder sb = new StringBuilder("abcdefg");
        sb.replace(0,2,"z");
        System.out.println(sb);
    }
}