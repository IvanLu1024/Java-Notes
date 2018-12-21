package code_07_dp;

import org.junit.Test;

/**
 * 123. Best Time to Buy and Sell Stock III
 *
 * Say you have an array for which the ith element is the price of a given stock on day i.
 * Design an algorithm to find the maximum profit.
 * You may complete at most two transactions.
 *
 * Note: You may not engage in multiple transactions at the same time
 * (i.e., you must sell the stock before you buy again).
 *
 * Example 1:
 Input: [3,3,5,0,0,3,1,4]
 Output: 6
 Explanation: Buy on day 4 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
 Then buy on day 7 (price = 1) and sell on day 8 (price = 4), profit = 4-1 = 3.

 * Example 2:
 Input: [1,2,3,4,5]
 Output: 4
 Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
 Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are
 engaging multiple transactions at the same time. You must sell before buying again.

 * Example 3:
 Input: [7,6,4,3,1]
 Output: 0
 Explanation: In this case, no transaction is done, i.e. max profit = 0.
 */
public class Code_123_BestTimeToBuyAndSellStockIII {
    /**
     * 思路：
     * 参照121题
     */
    public int maxProfit(int[] prices) {
        int n=prices.length;
        if(n==0 || n==1){
            return 0;
        }

        //从左向右遍历 maxBackward[i]表示从0到i天的最高买卖值
        //状态转移方程 maxForward[i]=max{maxForward[i-1],price[i]-minValue};
        //price[i]-minValue 表示第i天卖出股票的收益
        int[] maxBackward=new int[n];
        //从右向左遍历 maxForward[i]表示从i到(n-1)天的最高买卖值
        //maxValue-price[i] 表示第i天买入股票后的损失
        int[] maxForward=new int[n];

        maxBackward[0]=0;
        int minValue=prices[0];
        for(int i=1;i<n;i++){
            maxForward[i]=Math.max(maxForward[i-1],prices[i]-minValue);
            minValue=Math.min(minValue,prices[i]);
        }

        maxForward[n-1]=0;
        int maxValue=prices[n-1];
        for(int i=n-2;i>=0;i--){
            maxForward[i]=Math.max(maxForward[i+1],maxValue-prices[i]);
            maxValue=Math.max(maxValue,prices[i]);
        }

        //枚举断点,maxBackward[cnt-1]是可以取到maxBackward[0]的
        int ret=maxForward[0];
        for(int cut=1;cut<n;cut++){
            ret=Math.max(ret,maxBackward[cut-1]+maxForward[cut]);
        }
        return ret;
    }

    @Test
    public void test(){
        int[] nums={1,2,3,4,5};
        System.out.println(maxProfit(nums));
    }
}
