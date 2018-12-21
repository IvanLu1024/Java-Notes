package code_07_dp;

/**
 * 673. Number of Longest Increasing Subsequence
 *
 * Given an unsorted array of integers, find the number of longest increasing subsequence.
 *
 * Example 1:
 Input: [1,3,5,4,7]
 Output: 2
 Explanation: The two longest increasing subsequence are [1, 3, 4, 7] and [1, 3, 5, 7].

 * Example 2:
 Input: [2,2,2,2,2]
 Output: 5
 Explanation: The length of longest continuous increasing subsequence is 1, and there are 5 subsequences' length is 1, so output 5.
 */
public class Code_673_NumberOfLongestIncreasingSubsequence {
    /**
     * 思路：
     * LIS问题的变种：
     * LIS(i):表示以第i个数字为结尾的最长上升子序列的长度， 即在[0...i]范围内, 选择数字nums[i]可以获得的最长子序列的长度。
     * LIS(i)=max(j<i){1+LCS(j) if(nums[i]>nums[j])}
     * 这里要求统计 最长上升子序列的数目
     *
     * count[i]在[0...i]范围内的最长子序列的个数
     */
    public int findNumberOfLIS(int[] nums) {
        int n=nums.length;
        if(n==0){
            return 0;
        }
        int[] memo=new int[n];
        int[] count=new int[n];
        int maxlen=1;
        for(int i=0;i<n;i++){
            memo[i]=1;
            count[i]=1;
        }
        for(int i=1;i<n;i++){
            for(int j=0;j<i;j++){
                if(nums[i]>nums[j]){
                    if(memo[j]+1>memo[i]){
                        //说明这个长度的序列是新出现
                        memo[i]=1+memo[j];
                        count[i]=count[j];
                    }else if(memo[j]+1==memo[i]){
                        count[i]+=count[j];
                    }
                }
            }
            maxlen=Math.max(maxlen,memo[i]);
        }
        int res=0;
        for(int i=0;i<n;i++){
            if(memo[i]==maxlen){
                res+=count[i];
            }
        }
        return res;
    }
}
