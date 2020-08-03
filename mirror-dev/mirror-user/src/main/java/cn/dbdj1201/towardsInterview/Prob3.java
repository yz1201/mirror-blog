package cn.dbdj1201.towardsInterview;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-03 17:51
 */
@Slf4j
public class Prob3 {

    public static void main(String[] args) {
        //二维数组
//        show(generate2Arrays(3));
        TimeInterval timer = DateUtil.timer();
        log.info("answer is " + test(generate2Arrays(3), 25));
        System.out.println(timer.interval());
//        timer.intervalRestart();//返回花费时间，并重置开始时间
        System.out.println(timer.intervalMinute());
    }


    public static boolean test(int[][] numbers, int num) {
        int length = numbers.length;
        for (int[] nums : numbers) {
            for (int i = 0; i < length; i++) {
                if (nums[i] == num) {
                    return true;
                }
            }
        }

        return false;

    }

    public static int[][] generate2Arrays(int length) {
        int[][] nums = new int[length][length];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                nums[i][j] = i + j;
            }
        }

        return nums;
    }

    public static void show(int[][] nums) {
        int length = nums.length;
        for (int[] num : nums) {
            for (int i1 = 0; i1 < length; i1++) {
                log.info(String.valueOf(num[i1]));
            }
        }

    }
}
