package code_01_array;

/**
 * 122. Best Time to Buy and Sell Stock II
 *
 * Say you have an array for which the ith element is the price of a given stock on day i.
 * Design an algorithm to find the maximum profit.
 * You may complete as many transactions as you like (i.e., buy one and sell one share of the stock multiple times).
 *
 * Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).
 *
 * Example 1:
 Input: [7,1,5,3,6,4]
 Output: 7
 Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
 Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
 */
public class Code_122_BestTimetoBuyandSellStockII {
    /**
     * 思路：
     * 贪心策略：每次只要当天股价比前一天股价高就卖出。
     */
    public int maxProfit(int[] prices) {
        if(prices.length<=1){
            return 0;
        }
        int maxProfit=0;
        for(int i=1;i<prices.length;i++){
            int profit=prices[i]-prices[i-1];
            if(profit>=0){
                maxProfit+=profit;
            }
        }
        return maxProfit;
    }
}
