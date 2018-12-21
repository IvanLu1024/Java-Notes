package code_07_dp;

import org.junit.Test;

/**
 * 714. Best Time to Buy and Sell Stock with Transaction Fee
 *
 * Your are given an array of integers prices,
 * for which the i-th element is the price of a given stock on day i;
 * and a non-negative integer fee representing a transaction fee.
 * You may complete as many transactions as you like,
 * but you need to pay the transaction fee for each transaction.
 * You may not buy more than 1 share of a stock at a time
 * (ie. you must sell the stock share before you buy again.)
 * Return the maximum profit you can make.

 * Example:
 Input: prices = [1, 3, 2, 8, 4, 9], fee = 2
 Output: 8
 Explanation: The maximum profit can be achieved by:
 Buying at prices[0] = 1
 Selling at prices[3] = 8
 Buying at prices[4] = 4
 Selling at prices[5] = 9
 The total profit is ((8 - 1) - 2) + ((9 - 4) - 2) = 8.
 */
public class Code_714_BestTimeToBuyAndSellStockWithTransactionFee {
    /**
     * 参考309题
     */
    public int maxProfit(int[] prices, int fee) {
        int n=prices.length;
        if (n == 0) {
            return 0;
        }
        int[] unmemo=new int[n];
        int[] memo=new int[n];
        unmemo[0]=0;
        //持有股票，也就是第一天必须要买入
        memo[0]=-prices[0];
        /**
         *  未持有股票的状态:最大利润有两种可能：
         * (1)和昨天一样保持未持有；(2)昨天持有股票，今天卖掉。
         * unmemo[i]=max{unmemo[i-1],memo[i-1]+prices[i]}
         * 持有股票的状态，最大利润也有两种可能。
         * (1)和昨天一样持有股票不卖；(2)昨天未持有，今天购买。
         * memo[i]=max{memo[i-1],unmemo[i-1]-prices[i]}
         */
        for(int i=1;i<n;i++){
            //每次交易后，都是买入的时候，交手续费
            unmemo[i]=Math.max(unmemo[i-1],memo[i-1]+prices[i]-fee);
            memo[i]=Math.max(memo[i-1],unmemo[i-1]-prices[i]);
        }
        return unmemo[n-1];
    }

    @Test
    public void test(){
        int[] prices={1, 3, 2, 8, 4, 9};
        int fee=2;
        System.out.println(maxProfit(prices,fee));
    }
}
