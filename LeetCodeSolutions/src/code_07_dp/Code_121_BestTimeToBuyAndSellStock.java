package code_07_dp;

import org.junit.Test;

/**
 * 121. Best Time to Buy and Sell Stock
 *
 * Say you have an array for which the ith element is the price of a given stock on day i.
 * If you were only permitted to complete at most one transaction
 * (i.e., buy one and sell one share of the stock), design an algorithm to find the maximum profit.
 * Note that you cannot sell a stock before you buy one.
 *
 * Example 1:
 Input: [7,1,5,3,6,4]
 Output: 5
 Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
 Not 7-1 = 6, as selling price needs to be larger than buying price.

 * Example 2:
 Input: [7,6,4,3,1]
 Output: 0
 Explanation: In this case, no transaction is done, i.e. max profit = 0.
 */
public class Code_121_BestTimeToBuyAndSellStock {
    /**
     * 思路一:
     * 遍历数组，找出（后面元素-当前元素）最大值
     * （因为要求先买入，然后再卖出，所以要求后面元素减去前面元素）
     */
    public int maxProfit1(int[] prices) {
        int n=prices.length;
        if(n==0 || n==1){
            return 0;
        }
        int res=0;
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                res=Math.max(res,prices[j]-prices[i]);
            }
        }
        return res;
    }

    /**
     * 思路二：
     * 使用minValue用来维护数组中的最小值，maxProfit来维护最大收益
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (n == 0 || n == 1) {
            return 0;
        }
        //minValue用来维护数组中的最小值,也就是买入的股票
        int minValue=prices[0];
        //为什么要取maxProfict为0，因为有可能没有交易
        int maxProfit=0;
        for(int i=1;i<n;i++){
            maxProfit=Math.max(maxProfit,prices[i]-minValue);
            minValue=Math.min(minValue,prices[i]);
        }
        return maxProfit;
    }

    @Test
    public void test(){
        int[] nums={7,1,5,3,6,4};
        //int[] nums={7,6,4,3,1};
        System.out.println(maxProfit(nums));
    }
}
