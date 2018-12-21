package code_02_find;

import java.util.HashMap;
import java.util.Map;

/**
 * 1. Two Sum
 *
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
 *
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 *
 * Example:
 *
 * Given nums = [2, 7, 11, 15], target = 9,
 *
 * Because nums[0] + nums[1] = 2 + 7 = 9.
 *
 * return [0, 1].
 */
public class Code_01_TwoSum {
    public int[] twoSum(int[] nums, int target) {
        if(nums.length<=1){
            return null;
        }
        Map<Integer,Integer> map=new HashMap<>();
        //HashMap<Intager,Integer>,键存储的是该元素的值，值存储的是该元素的下标.
        for(int i=0;i<nums.length;i++){
            int v=target-nums[i];
            //在看查找表中查找是否有 target-nums[i]元素
            if(map.containsKey(v)){
                return new int[]{i,map.get(v)};
            }
            map.put(nums[i],i);
        }
        return null;
    }
}
