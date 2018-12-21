package code_07_dp;

import org.junit.Test;

/**
 * 583. Delete Operation for Two Strings
 *
 * Given two words word1 and word2,
 * find the minimum number of steps required to make word1 and word2 the same,
 * where in each step you can delete one character in either string.

 Example 1:
 Input: "sea", "eat"
 Output: 2
 Explanation: You need one step to make "sea" to "ea" and another step to make "eat" to "ea".

 Note:
 The length of given words won't exceed 500.
 Characters in given words can only be lower-case letters.
 */
public class Code_583_DeleteOperationForTwoStrings {
    /**
     * 思路：
     * 要想获得最少步骤,只需要将这两个字符串通过一定步骤转化他们的最长公共子序列即可。
     * 由于1次步骤只能删除一个字符，则最少步骤就是
     * 两字符串长度和-2*最长公共子序列长度
     */
    public int minDistance(String word1, String word2) {
        int m=word1.length();
        int n=word2.length();
        if(m==0){
            return n;
        }
        if(n==0){
            return m;
        }

        int[][] lcs=new int[m+1][n+1];
        for(int i=1;i<m+1;i++){
            for(int j=1;j<n+1;j++){
                if(word1.charAt(i-1)==word2.charAt(j-1)){
                    lcs[i][j]=1+lcs[i-1][j-1];
                }else{
                    lcs[i][j]=Math.max(lcs[i-1][j],lcs[i][j-1]);
                }
            }
        }
        return m+n-2*lcs[m][n];
    }

    @Test
    public void test(){
        String word1="a";
        String word2="ab";
        System.out.println(minDistance(word1,word2));
    }
}
