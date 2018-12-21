package code_02_find;

import org.junit.Test;

import java.util.*;

/**
 * 16.3Sum Closest
 * Given an array nums of n integers and an integer target,
 * find three integers in nums such that the sum is closest to target.
 * Return the sum of the three integers.
 * You may assume that each input would have exactly one solution.

 * Example:
 * Given array nums = [-1, 2, 1, -4], and target = 1.
 * The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
 */
public class Code_16_3SumClosest {
    //时间复杂度 O(n^2)
    public int threeSumClosest(int[] nums, int target) {
        if(nums.length<3){
            return 0;
        }
        Arrays.sort(nums);
        int ret=0;
        int closet=Integer.MAX_VALUE;
        for(int i=0;i<nums.length-2;i++){
           int begin=i+1;
           int end=nums.length-1;
           while(begin<end){
               int tmp=nums[i]+nums[begin]+nums[end];
               if(Math.abs(tmp-target)<closet){
                   //不断更新最接近的数值
                   closet=Math.abs(tmp-target);
                   ret=tmp;
               }
               if(tmp<target){
                   begin++;
               }else if(tmp>target){
                   end--;
               }else{
                   //tmp==target当然就是最接近的了
                   return target;
               }
           }
        }
        return ret;
    }

    @Test
    public void test(){
        int[] arr={-1, 2, 1, -4};
        int target = 1;
        System.out.println(threeSumClosest(arr,target));
    }
}
