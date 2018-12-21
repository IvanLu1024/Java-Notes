package code_07_dp;

/**
 * 300. Longest Increasing Subsequence
 *
 * Given an unsorted array of integers, find the length of longest increasing subsequence.
 *
 * Example:
 * Input: [10,9,2,5,3,7,101,18]
 * Output: 4
 * Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
 *
 * Note:
 * There may be more than one LIS combination, it is only necessary for you to return the length.
 * Your algorithm should run in O(n^2) complexity.
 */
public class Code_300_LongestIncreasingSubsequence {
    public int lengthOfLIS(int[] nums) {
        int n=nums.length;
        if(n==0){
            return 0;
        }
        int[] memo=new int[n];
        for(int i=0;i<n;i++){
            memo[i]=1;
        }

        for(int i=1;i<n;i++){
            for(int j=0;j<i;j++){
                if(nums[i]>nums[j]){
                    memo[i]=Math.max(memo[i],1+memo[j]);
                }
            }
        }

        int res=1;
        for(int i=0;i<n;i++){
            res=Math.max(res,memo[i]);
        }
        return res;
    }
}
