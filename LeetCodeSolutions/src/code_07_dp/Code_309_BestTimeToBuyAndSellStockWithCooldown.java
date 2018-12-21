package code_07_dp;

/**
 * 309. Best Time to Buy and Sell Stock with Cooldown
 *
 * Say you have an array for which the ith element is the price of a given stock on day i.
 * Design an algorithm to find the maximum profit.
 * You may complete as many transactions as you like
 * (ie, buy one and sell one share of the stock multiple times) with the following restrictions:

 You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
 After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)

 * Example:
 Input: [1,2,3,0,2]
 Output: 3
 Explanation: transactions = [buy, sell, cooldown, buy, sell]
 */
public class Code_309_BestTimeToBuyAndSellStockWithCooldown {
    /**
     * 思路：
     * 总共有三个状态：持有股票，卖掉股票，休息一天，后两种都可以归纳为未持有股票。
     * 状态有了，如何推断？
     * 第i天未持有股票的状态:最大利润有两种可能：
     * (1)和昨天一样保持未持有；(2)昨天持有股票今天卖掉。
     * unmemo[i]=max{unmemo[i-1],memo[i-1]+prices[i]}
     * 第i天持有股票的状态，最大利润也有两种可能：
     * (1)和昨天一样持有股票不卖；(2)两天前未持有（休息一天）今天购买。
     * memo[i]=max{memo[i-1],unmemo[i-2]-prices[i]}
     */
    public int maxProfit(int[] prices) {
        int n=prices.length;
        if (n == 0) {
            return 0;
        }
        int[] unmemo=new int[n];
        int[] memo=new int[n];
        unmemo[0]=0;
        //持有股票，也就是第一天必须要买入
        memo[0]=-prices[0];
        for(int i=1;i<n;i++){
            unmemo[i]=Math.max(unmemo[i-1],memo[i-1]+prices[i]);
            memo[i]=(i>2)?Math.max(memo[i-1],unmemo[i-2]-prices[i]):Math.max(memo[i-1],-prices[i]);
        }
        return unmemo[n-1];
    }
}
