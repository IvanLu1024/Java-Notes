package code_01_array;

import org.junit.Test;

/**
 * 121. Best Time to Buy and Sell Stock
 *
 * Say you have an array for which the ith element is the price of a given stock on day i.
 * If you were only permitted to complete at most one transaction
 * (i.e., buy one and sell one share of the stock), design an algorithm to find the maximum profit.
 *
 * Note that you cannot sell a stock before you buy one.
 *
 * Example 1:
 Input: [7,1,5,3,6,4]
 Output: 5
 Explanation:
 Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
 Not 7-1 = 6, as selling price needs to be larger than buying price.
 */
public class Code_121_BestTimetoBuyandSellStock {
    /**
     * 思路：至多只能一次交易，那就在某次交易中化最小钱买入，以最大钱卖出
     */
    public int maxProfit(int[] prices) {
        if(prices.length<=1){
            return 0;
        }
        int maxProfit=0;
        int minValue=prices[0];
        for(int i=0;i<prices.length;i++){
            maxProfit=Math.max(maxProfit,prices[i]-minValue);
            minValue=Math.min(minValue,prices[i]);
        }
        return maxProfit;
    }

    @Test
    public void test(){
        int[] nums={7,1,5,3,6,4};
        System.out.println(maxProfit(nums));
    }
}
