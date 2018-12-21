package code_07_dp;

/**
 * 91. Decode Ways
 *
 * A message containing letters from A-Z is being encoded to numbers using the following mapping:

  'A' -> 1
  'B' -> 2
   ...
  'Z' -> 26

 * Given a non-empty string containing only digits, determine the total number of ways to decode it.

 Example 1:

 Input: "12"
 Output: 2
 Explanation: It could be decoded as "AB" (1 2) or "L" (12).
 */
public class Code_91_DecodeWays {
    /**
     * 思路：
     * 使用动态规划，当前点的编码方法有两种情况：
     * （1）当前数在0-9之间，这样可以从前一个数到达；
     * （2）如果当前数和前一个数能编码，即在1-26之间，那么从当前数的前两个数可以到达当前数。
     */
    public int numDecodings(String s) {
        int n=s.length();
        if(n==0){
            return 0;
        }
        //memo[i]记录从开始到i-1的有多少种方式
        int[] memo=new int[n+1];
        memo[0]=1;
        //如果第一位上是0，那么无法转码，返回0；
        if(s.charAt(0)>'0'){
            memo[1]=1;
        }

        for(int i=2;i<=n;i++) {
            if (s.charAt(i - 1) >= '1') {
                memo[i] = memo[i - 1];
            }
            int num = Integer.parseInt(s.substring(i - 2, i));
            if (num <= 26 && s.charAt(i - 2) != '0') {
                memo[i] = memo[i] + memo[i - 2];
            }
        }
        return memo[n];
    }
}
