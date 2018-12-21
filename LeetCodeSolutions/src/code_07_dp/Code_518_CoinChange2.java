package code_07_dp;

/**
 * 518. Coin Change 2
 *
 * You are given coins of different denominations and a total amount of money.
 * Write a function to compute the number of combinations that make up that amount.
 * You may assume that you have infinite number of each kind of coin.

 * Note: You can assume that
 0 <= amount <= 5000
 1 <= coin <= 5000
 the number of coins is less than 500
 the answer is guaranteed to fit into signed 32-bit integer

 * Example 1:
 Input: amount = 5, coins = [1, 2, 5]
 Output: 4
 Explanation: there are four ways to make up the amount:
 5=5
 5=2+2+1
 5=2+1+1+1
 5=1+1+1+1+1

 * Example 2:
 Input: amount = 3, coins = [2]
 Output: 0
 Explanation: the amount of 3 cannot be made up just with coins of 2.
 */
public class Code_518_CoinChange2 {
    /**
     * 思路：
     * memo[i]存储钱数为i时的组合数量
     * 则状态转移方程：memo[i]=memo[i]+memo[i-coin]
     */
    public int change(int amount, int[] coins) {
        int C=amount+1;

        //memo[i]存储钱数为i时的组合数量
        int[] memo=new int[C];
        //钱数为0时,只有一种组合
        memo[0]=1;
        for(int coin:coins){
            for(int j=coin;j<C;j++){
                memo[j]=memo[j]+memo[j-coin];
            }
        }
        return memo[amount];
    }
}