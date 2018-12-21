package code_01_array;

/**
 * 674. Longest Continuous Increasing Subsequence
 *
 * Given an unsorted array of integers, find the length of longest continuous increasing subsequence (subarray).
 *
 * Example 1:
 Input: [1,3,5,4,7]
 Output: 3
 Explanation: The longest continuous increasing subsequence is [1,3,5], its length is 3.
 Even though [1,3,5,7] is also an increasing subsequence, it's not a continuous one where 5 and 7 are separated by 4.

 * Example 2:
 Input: [2,2,2,2,2]
 Output: 1
 Explanation: The longest continuous increasing subsequence is [2], its length is 1.
 Note: Length of the array will not exceed 10,000.
 */
public class Code_674_LongestContinuousIncreasingSubsequence {
    /**
     * 思路：
     * 找递增的数列，遇到非递增的情况就把计数值重新值1，
     * 然后比较一下当前计数值和max的大小得到新的max
     */
    public int findLengthOfLCIS(int[] nums) {
        int n=nums.length;
        if(n<=1){
            return n;
        }
        int maxLen = 0;
        //连续递增区间的长度
        int continuousLen = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] >= nums[i]) {
                maxLen = Math.max(maxLen, continuousLen);
                continuousLen = 1;
            } else {
                continuousLen++;
            }
        }
        return Math.max(maxLen, continuousLen);
    }
}
