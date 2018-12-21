package code_07_dp;

/**
 * 474. Ones and Zeroes
 *
 * In the computer world, use restricted resource you have to generate maximum benefit is what we always want to pursue.
 * For now, suppose you are a dominator of m 0s and n 1s respectively.
 * On the other hand, there is an array with strings consisting of only 0s and 1s.
 * Now your task is to find the maximum number of strings that you can form with given m 0s and n 1s.
 * Each 0 and 1 can be used at most once.
 *
 * Note:
 * The given numbers of 0s and 1s will both not exceed 100
 * The size of given string array won't exceed 600.
 *
 * Example 1:
 * Input: Array = {"10", "0001", "111001", "1", "0"}, m = 5, n = 3
 * Output: 4
 * Explanation: This are totally 4 strings can be formed by the using of 5 0s and 3 1s, which are “10,”0001”,”1”,”0”

 * Example 2:
 * Input: Array = {"10", "0", "1"}, m = 1, n = 1
 * Output: 2
 * Explanation: You could form "10", but then you'd have nothing left. Better form "0" and "1".
 */
public class Code_474_OnesAndZeroes {
    //memo[i][j]表示0个数不超过i，1个数不超过j的最多能选取的字符串个数。
    public int findMaxForm(String[] strs, int m, int n) {
        int[][] memo=new int[m+1][n+1];
        for(String str:strs){
            //统计str中'0'和'1'的个数
            int cnt0=0;
            int cnt1=0;
            for(Character c:str.toCharArray()){
                if(c=='0'){
                    cnt0++;
                }else{
                    cnt1++;
                }
            }
            for(int i=m;i>=cnt0;i--){
                for(int j=n;j>=cnt1;j--){
                    memo[i][j]=Math.max(memo[i][j],memo[i-cnt0][j-cnt1]+1);
                }
            }
        }
        return memo[m][n];
    }
}
