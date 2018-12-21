package code_01_array;

import org.junit.Test;

import java.util.Arrays;

/**
 * 747. Largest Number At Least Twice of Others
 *
 * In a given integer array nums, there is always exactly one largest element.
 * Find whether the largest element in the array is at least twice as much as every other number in the array.
 * If it is, return the index of the largest element, otherwise return -1.
 *
 * Example 1:
 Input: nums = [3, 6, 1, 0]
 Output: 1
 Explanation: 6 is the largest integer, and for every other number in the array x,
 6 is more than twice as big as x.  The index of value 6 is 1, so we return 1.

* Example 2:
 Input: nums = [1, 2, 3, 4]
 Output: -1
 Explanation: 4 isn't at least as big as twice the value of 3, so we return -1.


 * Note:
 nums will have a length in the range [1, 50].
 Every nums[i] will be an integer in the range [0, 99].
 */
public class Code_747_LargestNumberAtLeastTwiceofOthers {
    public int dominantIndex(int[] nums) {
        int n=nums.length;
        if(n==1){
            return 0;
        }
        int maxIndex=getMaxIndex(nums);
        Arrays.sort(nums);
        if(2*nums[n-2]>nums[n-1]){
            return -1;
        }
        return maxIndex;
    }

    private int getMaxIndex(int[] nums){
        int max=nums[0];
        int index=0;
        for(int i=1;i<nums.length;i++){
            if(nums[i]>max){
                max=nums[i];
                index=i;
            }
        }
        return index;
    }

    @Test
    public void test(){
        int[] nums={3, 6, 1, 0};
        System.out.println(getMaxIndex(nums));
    }
}
