package code_07_dp;

/**
 * 198. House Robber
 *
 * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security system connected and it will automatically contact the police if two adjacent houses were broken into on the same night.
 * Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount of money you can rob tonight without alerting the police.
 *
 * Example 1:
 Input: [1,2,3,1]
 Output: 4
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
 * Total amount you can rob = 1 + 3 = 4.
 */
public class Code_198_HouseRobber {
    private int[] memo1;

    //考虑抢劫nums[index...nums.length-1]者个范围内的所有房子
    private int tryRob(int[] nums,int index){
        if(index>=nums.length){
            return 0;
        }
        if(memo1[index]!=-1){
            return memo1[index];
        }
        int res=-1;
        for(int i=index;i< nums.length;i++){
            res=Math.max(res,nums[i]+tryRob(nums,i+2));
        }
        memo1[index]=res;
        return res;
    }

    public int rob1(int[] nums) {
        int n=nums.length;
        //房子的编号在[0...nums.length-1]，所以memo长度是nums.length即可
        memo1=new int[n];
        for(int i=0;i<n;i++){
            memo1[i]=-1;
        }
        return tryRob(nums,0);
    }

    //使用动态规划，先求出最优子结构
    public int rob2(int[] nums) {
        int n= nums.length;
        if(n==0){
            return 0;
        }
        // memo[i]表示抢劫[i...n-1]的最大收益
        int[] memo=new int[n];
        for(int i=0;i<n;i++){
            memo[i]=-1;
        }

        /**
         * 状态转移方程：
         * f(0)=max{ v(0)+f(2), v(1)+f(3),v(2)+f(4),...，v(n-3)+f(n-1),v(n-2),v(n-1)}
         */
        memo[n-1]=nums[n-1]; //注意这里n>=1
        for(int i=n-2;i>=0;i--){
            // 抢劫[i...n]中的房子
            for(int j=i;j<n;j++){
                //nums[j] 编号为j的房子下标
                //memo[j+2]抢劫 [j+2...n-1]的最大收益
                memo[i]=Math.max(memo[i],nums[j]+(j+2<n? memo[j+2]:0));
            }
        }
        return memo[0];
    }

    public int rob(int[] nums) {
        int n= nums.length;
        if(n==0){
            return 0;
        }
        // memo[i]表示抢劫[i...n-1]的最大收益
        int[] memo=new int[n];
        for(int i=0;i<n;i++){
            memo[i]=-1;
        }

        /**
         * 状态转移方程：
         * f(n-1)=max{ v(n-1)+f(n-3), v(n-2)+f(n-4),...,v(3)+f(1),v(2),v(1)}
         */
        memo[0]=nums[0]; //注意这里n>=1
        for(int i=1;i<n;i++){
            // 抢劫[0...i]中的房子
            for(int j=i;j>=0;j--){
                //nums[j] 编号为j的房子下标
                //memo[j+2]抢劫 [j+2...n-1]的最大收益
                memo[i]=Math.max(memo[i],nums[j]+(j-2>=0? memo[j-2]:0));
            }
        }
        return memo[n-1];
    }
}
