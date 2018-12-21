package code_01_array;

/**
 * 209 Minimum Size Subarray Sum

 * Given an array of n positive integers and a positive integer s, find the minimal length of a contiguous subarray of which the sum ≥ s. If there isn't one, return 0 instead.
 *
 * Example:
 *
 * Input: s = 7, nums = [2,3,1,2,4,3]
 * Output: 2
 * Explanation: the subarray [4,3] has the minimal length under the problem constraint.
 */
public class Code_209_MinimumSizeSubarraySum {
    public int minSubArrayLen(int s, int[] nums) {
        int n=nums.length;
        int l=0,r=-1;
        //[l,r]为滑动窗口，开始时不包含任何元素
        int sum=0;
        //记录滑动窗口中元素和
        int ret=n+1;
        //ret记录求解的长度
        while(l<n){
            if(r+1<n && sum<s){
                r++;
                sum+=nums[r];
            }else{
                sum-=nums[l];
                l++;
            }
            if(sum>=s){
                ret=Math.min(ret,(r-l+1));
            }
        }
        if(ret==n+1){
            //表示没有找到结果，使得 sum>=s
            ret=0;
        }
        return ret;
    }
}
