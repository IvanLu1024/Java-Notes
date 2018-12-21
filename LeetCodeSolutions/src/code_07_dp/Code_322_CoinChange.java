package code_07_dp;

/**
 * 322. Coin Change
 * You are given coins of different denominations and a total amount of money amount.
 * Write a function to compute the fewest number of coins that you need to make up that amount.
 * If that amount of money cannot be made up by any combination of the coins, return -1.

 * Example 1:
 Input: coins = [1, 2, 5], amount = 11
 Output: 3
 Explanation: 11 = 5 + 5 + 1

 * Example 2:
 Input: coins = [2], amount = 3
 Output: -1
 */
public class Code_322_CoinChange {
    private int[] dp;
    private int max_amount;

    private int tryChange(int[] coins,int amount){
        if(amount==0){
            return 0;
        }
        if(dp[amount]!=-1){
            return dp[amount];
        }
        int res=max_amount;
        for(int coin:coins){
            if(amount - coin >= 0){
                res=Math.min(res,1+tryChange(coins,amount-coin));
            }
        }
        dp[amount]=res;
        return res;
    }

    public int coinChange(int[] coins, int amount) {
        max_amount=amount+1;
        dp=new int[amount+1];
        for(int i=0;i<amount+1;i++){
            dp[i]=-1;
        }
        int res=tryChange(coins,amount);
        return (res==max_amount)?-1:res;
    }

    /**
     * 思路：
     * 数组memo[i]存储凑齐钱数i时所需硬币的最少数量
     * memo[i]=min{memo[i],memo[i-coin]+1}
     */
    public int coinChange1(int[] coins, int amount) {
        int C = amount + 1;
        int[] memo = new int[C];
        memo[0] = 0;
        //初始化该数组，要求初始值大于amount即可
        for (int i = 1; i < C; i++) {
            memo[i] = amount + 1;
        }
        for (int coin : coins) {
            for (int i = coin; i < C; i++) {
                memo[i] = Math.min(memo[i], memo[i - coin] + 1);
            }
        }
        return memo[amount] == amount + 1 ? -1 : memo[amount];
    }
}
