package code_07_dp;

/**
 * 213. House Robber II

 * You are a professional robber planning to rob houses along a street.
 * Each house has a certain amount of money stashed. All houses at this place are arranged in a circle.
 * That means the first house is the neighbor of the last one.
 * Meanwhile, adjacent houses have security system connected
 * and it will automatically contact the police if two adjacent houses were broken into on the same night.
 *
 * Given a list of non-negative integers representing the amount of money of each house,
 * determine the maximum amount of money you can rob tonight without alerting the police.
 *
 * Example 1:
 * Input: [2,3,2]
 * Output: 3
 * Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money = 2),because they are adjacent houses.
 *
 * Example 2:
 * Input: [1,2,3,1]
 * Output: 4
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).Total amount you can rob = 1 + 3 = 4.
 *
 */
public class Code_213_HouseRobberII {
    //偷在[start,end]之间的房子
    private int rob(int[] nums,int start,int end){
        //偷取start位置
        int preMax=nums[start];

        //偷取start+1位置
        int curMax=Math.max(preMax,nums[start+1]);
        //curMax表示偷取 start或者(start+1)的最大值

        for(int i=start+2;i<=end;i++){
            int tmp=curMax;
            //偷取编号为i的房子
            curMax=Math.max(curMax,nums[i]+preMax);
            preMax=tmp;
        }
        return curMax;
    }

    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        if(n==1){
            return nums[0];
        }
        if(n==2){
            return Math.max(nums[0],nums[1]);
        }
        return Math.max(rob(nums,0,nums.length-2),rob(nums,1,nums.length-1));
    }
}
