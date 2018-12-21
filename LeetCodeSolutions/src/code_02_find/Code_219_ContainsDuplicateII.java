package code_02_find;

import java.util.HashSet;
import java.util.Set;

/**
 * 219. Contains Duplicate II
 *
 * Given an array of integers and an integer k,
 * find out whether there are two distinct indices i and j in the array such that nums[i] = nums[j] and the absolute difference between i and j is at most k.
 *
 * Example 1:
 * Input: nums = [1,2,3,1], k = 3
 * Output: true
 *
 * Example 2:
 * Input: nums = [1,0,1,1], k = 1
 * Output: true
 *
 * Example 3:
 * Input: nums = [1,2,3,1,2,3], k = 2
 * Output: false
 */
public class Code_219_ContainsDuplicateII {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> set=new HashSet<>();
        //set存储的是滑动窗口中的元素
        for(int i=0;i<nums.length;i++){
            if(set.contains(nums[i])){
                return true;
            }
            set.add(nums[i]);
            //维持滑动窗口的大小为k
            if(set.size()==k+1){
                set.remove(nums[i-k]);
            }
        }
        return false;
    }
}
