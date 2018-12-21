package code_07_dp;

/**
 * 494. Target Sum
 *
 * You are given a list of non-negative integers,
 * a1, a2, ..., an, and a target, S.
 * Now you have 2 symbols + and -. For each integer, you should choose one from + and - as its new symbol.
 *
 * Find out how many ways to assign symbols to make sum of integers equal to target S.
 *
 * Example:
 Input: nums is [1, 1, 1, 1, 1], S is 3.
 Output: 5
 Explanation:
 -1+1+1+1+1 = 3
 +1-1+1+1+1 = 3
 +1+1-1+1+1 = 3
 +1+1+1-1+1 = 3
 +1+1+1+1-1 = 3
 There are 5 ways to assign symbols to make the sum of nums be target 3.
 */
public class Code_494_TargetSum {
    /**
     * 思路：
     * 假设原数组为nums，目标值为S，那么原数组必然可以分成两个部分：
     * 一个部分里面的元素前面需要加-，即运算的时候应该是做减法，另一个部分里面的元素前面需要加+，即运算的时候应该是做加法；
     * 我们将做加法部分的数组记为P，做减法部分的数组记为N，
     * 举个例子，例如S = {1，2，3，4，5}，S = 3，那么有一种可以是1-2+3-4+5，即P = {1，3，5}，N = {2，4}；
     * 于是我们可以知道：S = sum(P) - sum(N)；
     * 那么sum(P) + sum(N) + sum(P) - sum(N) = sum(nums) + S = 2sum(P)；
     * 那么sum(P) = [S + sum(nums)] / 2； []表示向下取整
     * 也就是在nums数组中选择值为 (s+sum(nums))/2的动态规划问题。可以参考416题
     */
    public int findTargetSumWays(int[] nums, int S) {
        int n=nums.length;
        int sum=0;
        for(int i=0;i<n;i++){
            sum+=nums[i];
        }
        if (sum < S || (sum + S) % 2 != 0) {
            return 0;
        }
        int C=(S+sum)/2;
        int[] memo=new int[C+1];
        memo[0]=1;
        for(int i=0;i<n;i++){
            for(int j=C;j>=nums[i];j--){
                memo[j]=memo[j]+ memo[j-nums[i]];
            }
        }
        return memo[C];
    }
}
