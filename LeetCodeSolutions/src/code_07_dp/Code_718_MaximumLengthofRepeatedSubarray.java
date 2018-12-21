package code_07_dp;

/**
 * 718. Maximum Length of Repeated Subarray
 *
 * Given two integer arrays A and B, return the maximum length of an subarray that appears in both arrays.
 *
 * Example 1:
 *Input:
 A: [1,2,3,2,1]
 B: [3,2,1,4,7]
 Output: 3
 Explanation:
 The repeated subarray with maximum length is [3, 2, 1].
 */
public class Code_718_MaximumLengthofRepeatedSubarray {
    /**
     * 思路：
     * lcs[i][j]表示数组
     * A[i-1]和B[j-1]结尾最长公共数组的长度
     *
     * 则递归方程：
     * A[i-1]==B[j-1]时A[i-1]就加入到原来的最长公共数组中
     * 对此有 lcs[i][j]=lcs[i-1][j-1]+1
     */
    public int findLength(int[] A, int[] B) {
        int m=A.length;
        int n=B.length;

        int[][] lcs=new int[m+1][n+1];
        int res=0;
        for(int i=1;i<=m;i++){
            for(int j=1;j<=n;j++){
                if(A[i-1]==B[j-1]){
                    lcs[i][j]=lcs[i-1][j-1]+1;
                    res=Math.max(lcs[i][j],res);
                }else{
                    lcs[i][j]=0;
                }
            }
        }
        return res;
    }
}
