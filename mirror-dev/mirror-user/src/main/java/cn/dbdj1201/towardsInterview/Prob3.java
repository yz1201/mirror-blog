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

    /*
    在二维数组中，每一行都冲左到右递增。每一列都从上到下递增。输出一个二维数组和一个整数。判断数组中是否含有这个整数
    */
    public static void main(String[] args) {
        //二维数组
//        show(generate2Arrays(3));
        TimeInterval timer = DateUtil.timer();
        int[][] numbers = generate2Arrays(3);
//        log.info("answer is " + test(numbers, 222));
//        System.out.println(timer.interval());
//        System.out.println(timer.intervalRestart());

        System.out.println(Find(4, numbers));


        System.out.println(timer.interval());
    }


    public static boolean test(int[][] nums, int num) {
        int rowLen = nums.length;
        int columnLen = nums[0].length;

        int currentRow = 0;
        int currentCol = columnLen - 1;

        while (currentRow < rowLen && currentCol >= 0) {
            if (nums[currentRow][currentCol] == num) {
                return true;
            } else {
                if (nums[currentRow][currentCol] < num) {
                    currentRow++;
                } else {
                    currentCol--;
                }
            }
        }
        return false;

    }

    /**
     * @param target
     * @param array
     * @return
     */
    public static boolean Find(int target, int[][] array) {
        if (array == null || array[0] == null) {
            return false;
        }
        int rows = array.length;//长
        int columns = array[0].length;//高
        //从第一行最后一列开始比较
        int currentRow = 0;
        int currentColumn = columns - 1;

        while (currentRow < rows && currentColumn >= 0) {
            if (array[currentRow][currentColumn] == target) {
                return true;
            }
            if (array[currentRow][currentColumn] < target) {
                ++currentRow;
            } else {
                --currentColumn;
            }
        }
        return false;
    }

//    public static boolean find(int[][] nums, int number) {
//        int length = nums.length;
//        int target = nums[0][0];
//
//        if (target > number) {
//            return false;
//        } else if (target < number) {
//            target = nums[0][length - 1];
////            if (target )
//
//        }
//
//        return true;
//    }

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
