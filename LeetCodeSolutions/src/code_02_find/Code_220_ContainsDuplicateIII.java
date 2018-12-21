package code_02_find;

import org.junit.Test;

import java.util.*;

/**
 *
 * 220. Contains Duplicate III
 * Given an array of integers, find out whether there are two distinct indices i and j in the array such that
 * the absolute difference between nums[i] and nums[j] is at most t and the absolute difference between i and j is at most k.
 *
 * Example 1:
 * Input: nums = [1,2,3,1], k = 3, t = 0
 * Output: true
 *
 * Example 2:
 * Input: nums = [1,0,1,1], k = 1, t = 2
 * Output: true
 *
 * Example 3:
 * Input: nums = [1,5,9,1,5,9], k = 2, t = 3
 * Output: false
 */
public class Code_220_ContainsDuplicateIII {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (k < 1 || t < 0)
            return false;
        SortedSet<Long> set=new TreeSet<>();
        //采用Long类型是为了防止整型溢出
        for(int i=0;i<nums.length;i++){
            //判断是否存在元素在 [nums[i]-t,nums[i]+t]之间
            Set<Long> s = set.subSet((long) nums[i] - t, (long) nums[i] + t + 1);
            //左闭右开，所以 nums[i]+t要+1
            //s存储[nums[i]-t,nums[i]+t]之间的元素
            if(!s.isEmpty()){
                return true;
            }
            set.add((long)nums[i]);
            if(set.size()==k+1){
                set.remove((long)nums[i-k]);
            }
        }
        return false;
    }

    @Test
    public void test(){
        int[] nums = {1,5,9,1,5,9};
        int k = 2;
        int t = 3;
        System.out.println(containsNearbyAlmostDuplicate(nums,k,t));
    }
}
