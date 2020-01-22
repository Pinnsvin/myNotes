/*
 * @Author: Pinnsvin
 * @Date: 2018-10-29 23:39:44 
 */
import java.util.Scanner;

public class TwoSum{
    public static void main(String[] args){
        int[] nums = new int[]{2,7,11,15};
        int[] result = method1(nums, 9);
        for(int num : result){
            System.out.print(num+"\t");
        }
    }
    /**
     * @msg: 暴力遍历。时间复杂度:O(n^2);空间复杂:O(1)
     * @param： 目标数组  目标元素
     * @return: 结果数组 
     */
    public static int[] method1(int[] arr, int target){
        for(int i=0;i<arr.length;i++){
            for(int j=i+1;i<arr.length;j++){
                if(arr[j] == target - arr[i]){
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("No two sum resultion.");
    }

    /**
     * @msg: 两遍
     * @param {type} 
     * @return: 
     */
    public static int[] method2(int[] arr, int target){

    }
}

                    