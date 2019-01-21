package code_11_logic;

import org.junit.Test;

import java.lang.annotation.Target;

/**
 * 877. Stone Game
 *
 * Alex and Lee play a game with piles of stones.
 * There are an even number of piles arranged in a row,
 * and each pile has a positive integer number of stones piles[i].
 *
 * The objective of the game is to end with the most stones.
 * The total number of stones is odd(奇数), so there are no ties(平局).
 *
 * Alex and Lee take turns, with Alex starting first.
 * Each turn, a player takes the entire pile of stones from either the beginning or the end of the row(从行的开始或结束处取走整堆石头).
 * This continues until there are no more piles left, at which point the person with the most stones wins.
 * Assuming Alex and Lee play optimally(最佳地), return True if and only if Alex wins the game.
 *
 *Example 1:
 Input: [5,3,4,5]
 Output: true
 Explanation:
 Alex starts first, and can only take the first 5 or the last 5.
 Say he takes the first 5, so that the row becomes [3, 4, 5].
 If Lee takes 3, then the board is [4, 5], and Alex takes 5 to win with 10 points.
 If Lee takes the last 5, then the board is [3, 4], and Alex takes 4 to win with 9 points.
 This demonstrated that taking the first 5 was a winning move for Alex, so we return true.

 Note:
 * 2 <= piles.length <= 500
 * piles.length is even. (偶数)
 * 1 <= piles[i] <= 500
 * sum(piles) is odd.（奇数）
 */
public class Code_877_StoneGame {
    /**
     * 思路：
     *
     * 石头的堆数是偶数，且石头的总数是奇数，因此Alex可以选择一种策略总是选偶数堆或者奇数堆的石头，则一定可以胜过Lee。
     *
     * 这里我们需要进行更一般化的分析，例如石头堆数不一定是偶数，石头总数也不一定是奇数，
     * 且不但要判断Alex是否能赢，还要判断最多赢多少分，如果输，能不能提供最少输多少分。这里的分数是指多拿的石头数量。
     *
     * 我们每次只能拿两端的石头堆的石头，但我们又不知道拿完后剩下的石头堆的情况，因此我们考虑先解决子问题。
     * 我们可以根据（n-1)个相邻石头堆的胜负情况，求出n个相邻石头堆的胜负情况，即我们的原问题。
     *
     * dp[i][j]为piles[i]到piles[j]Alex最多可以赢Lee的分数。
     * 每次取石头堆只能从两端取，因此:
     * dp[i][j] = max(piles[i] - dp[i+1][j], piles[j] - dp[i][j-1])。其中：
     * piles[i] - dp[i+1][j] 表示Alex取走编号为i的石头堆，
     * piles[j] - dp[i][j-1] 表示Alex取走编号为j的石头堆。
     *
     *
     * 注意，为什么dp[i+1][j]表示piles[i+1]到piles[j]之间Alex最多可以赢Lee的分数，而piles[i]要减去该值而不是加上该值呢？
     * 由于我们的要求是每一步Alex和Lee采取的都是最优策略，
     * 当取piles[i]时，piles[i+1]到piles[j]中Alex和Lee的走法会调换。意即Lee走Alex的走法，Alex走Lee的走法，因此这里要做减法。
     */
    public boolean stoneGame(int[] piles) {
        int n = piles.length;
        int[][] dp=new int[n][n];
        for(int i=0;i<n;i++){
            dp[i][i] = piles[i];
        }
        for(int dis = 1; dis < n; dis++) {
            //依次计算相邻2个石头堆到n个石头堆的情形
            for(int i = 0; i < n - dis; i++) {
                int j = i + dis;
                dp[i][j] = Math.max(piles[i]-dp[i+1][j], piles[j]-dp[i][j-1]);
            }
        }
        return dp[0][n-1] > 0;
    }

    @Test
    public void test(){
        int[] piles= {5,3,4,5};
        stoneGame(piles);
    }
}
