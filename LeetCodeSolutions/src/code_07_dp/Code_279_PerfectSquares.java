package code_07_dp;

/**
 * 279. Perfect Squares
 * Given a positive integer n, find the least number of perfect square numbers (for example, 1, 4, 9, 16, ...) which sum to n.
 * Example 1:
 *
 * Input: n = 12
 * Output: 3
 * Explanation: 12 = 4 + 4 + 4.
 *
 * Example 2:
 * Input: n = 13
 * Output: 2
 * Explanation: 13 = 4 + 9.
 */
public class Code_279_PerfectSquares {
    private int[] memo;
    private int findNumSquare(int n){
        if(n==0){
            return 0;
        }
        if(memo[n]!=-1){
            return memo[n];
        }
        int min=Integer.MAX_VALUE;
        for(int j=1;n-j*j>=0;j++){
            min=Math.min(min,1+findNumSquare(n-j*j));
        }
        memo[n]=min;
        return memo[n];
    }

    public int numSquares(int n) {
        memo=new int[n+1];
        for(int i=0;i<n+1;i++){
            memo[i]=-1;
        }
        return findNumSquare(n);
    }

    //动态规划
    public int numSquares1(int n) {
        if(n==1){
            return 1;
        }
        int[] memo=new int[n+1];
        for(int i=0;i<memo.length;i++){
            memo[i]=Integer.MAX_VALUE;
        }
        memo[0]=0;
        //1--->n个数进行遍历
        for(int i=1;i<=n;i++){
            for(int j=1;i-j*j>=0;j++){
                memo[i]=Math.min(memo[i],1+memo[i-j*j]);
            }
        }
        return memo[n];
    }
}
