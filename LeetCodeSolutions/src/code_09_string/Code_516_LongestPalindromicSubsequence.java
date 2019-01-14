package code_09_string;

/**
 * 516. Longest Palindromic Subsequence
 *
 * Given a string s, find the longest palindromic subsequence's length in s.
 * You may assume that the maximum length of s is 1000.
 *
 * Example 1:
 Input:
 "bbbab"
 Output:
 4
 One possible longest palindromic subsequence is "bbbb".

 * Example 2:
 Input:
 "cbbd"
 Output:
 2
 One possible longest palindromic subsequence is "bb".
 */
public class Code_516_LongestPalindromicSubsequence {
    /**
     * 典型的的动态规划问题：
     * dp[i][j]表示字符串[i,j]下标所构成的子串中最长回文子串的长度
     * 当 s.charAt(i)==s.charAt(j)时
     * dp[i][j]=dp[i-1][j-1]+2 (加入了s.charAt(i)和s.charAt(j)两个字符)
     * 当s.charAt(i)!=s.charAt(j)时
     * dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1])(要么加入s.charAt(i)，要么加入s.charAt(j))
     */
    public int longestPalindromeSubseq(String s) {
        if(s.length()==0){
            return 0;
        }
        int len=s.length();

        //dp[i][j]表示字符串i-j下标所构成的子串中最长回文子串的长度
        int[][] dp=new int[len][len];

        for(int i=len-1;i>=0;i--){
            dp[i][i]=1;
            for(int j=i+1;j<len;j++){
                if(s.charAt(i)==s.charAt(j)){
                    dp[i][j]=dp[i+1][j-1]+2;
                }else{
                    dp[i][j]=Math.max(dp[i+1][j],dp[i][j-1]);
                }
            }
        }
        //最后返回的是[0,len-1]所构成的子串中最长回文子串的长度
        return dp[0][len-1];
    }
}
