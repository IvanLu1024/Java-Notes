package code_07_dp;

import org.junit.Test;

/**
 * 188. Best Time to Buy and Sell Stock IV
 *
 * Say you have an array for which the ith element is the price of a given stock on day i.
 * Design an algorithm to find the maximum profit. You may complete at most k transactions.
 *
 * Example 1:
 Input: [2,4,1], k = 2
 Output: 2
 Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.
 Example 2:

 Input: [3,2,6,5,0,3], k = 2
 Output: 7
 Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4.
 Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
 */
public class Code_188_BestTimeToBuyAndSellStockIV {
    public int maxProfit(int k, int[] prices) {
        int n=prices.length;
        if(k<=0){
            return 0;
        }
        if(k>=n/2){
            return maxProfit(prices);
        }
        //buy[i]表示完成第i笔交易的买入动作时的最大收益
        //sell[i]表示完成第i笔交易的卖出动作时的最大收益
        int[] buy=new int[k+1];
        int[] sell=new int[k+1];
        for(int i=0;i<k+1;i++){
            buy[i]=Integer.MIN_VALUE;
        }
        //buy[i] = max(buy[i], sell[i-1]-prices)[先要买入，然后才能卖出,i>=1]
        //第i次买入动作的最大收益=max{第i次买入动作是的最大收益,第(i-1)次卖出动作的最大收益-今天买入}
        //sell[i] = max(sell[i], buy[i]+prices)
        //第i次卖出动作的最大收益=max{第i次卖出动作的最大收益,第i次买入动作的最大收益+今天卖出}
        for(int price: prices){
            for(int i=k;i>=1;i--){
                buy[i]=Math.max(buy[i],sell[i-1]-price);
                sell[i]=Math.max(sell[i],buy[i]+price);
            }
        }
        return sell[k];
    }

    //k>=n/2，只要有利润，就可以买入
    private int maxProfit(int[] prices){
        int n=prices.length;
        if(n<=1){
            return 0;
        }
        int res=0;
        for(int i=1;i<n;i++){
            if(prices[i]>prices[i-1]){
                res+=(prices[i]-prices[i-1]);
            }
        }
        return res;
    }

    @Test
    public void test(){
        //int[] nums={3,2,6,5,0,3};
        //int[] nums={2,4,1};
        // int[] nums={3,3,5,0,0,3,1,4};
        int[] nums={6,1,6,4,3,0,2};
        int k = 1;
        System.out.println(maxProfit(k,nums));
    }
}
