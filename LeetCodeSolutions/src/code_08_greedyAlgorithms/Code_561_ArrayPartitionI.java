package code_08_greedyAlgorithms;

import org.junit.Test;

import java.lang.annotation.Target;
import java.util.Arrays;

/**
 * 561. Array Partition I
 *
 * Given an array of 2*n integers, your task is to group these integers into n pairs of integer,
 * say (a1, b1), (a2, b2), ..., (an, bn) which makes sum of min(ai, bi) for all i from 1 to n as large as possible.
 *
 * Example 1:
 Input: [1,4,3,2]
 Output: 4
 Explanation: n is 2, and the maximum sum of pairs is 4 = min(1, 2) + min(3, 4).
 *
 * Note:
 n is a positive integer, which is in the range of [1, 10000].
 All the integers in the array will be in the range of [-10000, 10000].
 */
public class Code_561_ArrayPartitionI {
    public int arrayPairSum(int[] nums) {
        int n=nums.length/2;
        if(n==1){
            return Math.min(nums[0],nums[1]);
        }
        Arrays.sort(nums);
        int res=0;
        /*for(int i=0;i<=2*n-2;i+=2){
            res+=Math.min(nums[i],nums[i+1]);
        }*/
        //改进
        for(int i=0;i<=2*n-2;i+=2){
            res+=nums[i];
        }
        return res;
    }

    @Test
    public void test(){
        int[] nums={1,4,3,2};
        System.out.println(arrayPairSum(nums));
    }
}
