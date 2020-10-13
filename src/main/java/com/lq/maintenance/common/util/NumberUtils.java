package com.lq.maintenance.common.util;

/**
 * @program: maintenance
 * @description:
 * @author: lzx
 * @create: 2020-09-19 21:11
 **/
public class NumberUtils {
    /**
     * @description 获取两个数之间的随机数
     * @author lzx
     * @date 2020年09月19日 21:11
     */
    public static int getRandom(int start,int end) {
        int num=(int) (Math.random()*(end-start+1)+start);
        return num;
    }
    /**
     * 随机指定范围内N个不重复的数
     * 最简单最基本的方法
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n 随机数个数
     */
    public static int[] randomCommon(int min, int max, int n){
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while(count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if(num == result[j]){
                    flag = false;
                    break;
                }
            }
            if(flag){
                result[count] = num;
                count++;
            }
        }
        return result;
    }
}
