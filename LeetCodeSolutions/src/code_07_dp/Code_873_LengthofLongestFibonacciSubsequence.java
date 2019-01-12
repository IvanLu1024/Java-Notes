package code_07_dp;

import java.util.HashMap;
import java.util.Map;

/**
 * 873. Length of Longest Fibonacci Subsequence
 *
 * A sequence X_1, X_2, ..., X_n is fibonacci-like if:
 * n >= 3
 * X_i + X_{i+1} = X_{i+2} for all i + 2 <= n
 *
 * Given a strictly increasing array A of positive integers forming a sequence,
 * find the length of the longest fibonacci-like subsequence of A.
 * If one does not exist, return 0.
 * (Recall that a subsequence is derived from another sequence A
 * by deleting any number of elements (including none) from A,
 * without changing the order of the remaining elements.
 * For example, [3, 5, 8] is a subsequence of [3, 4, 5, 6, 7, 8].)
 *
 * Note:
 * 3 <= A.length <= 1000
 * 1 <= A[0] < A[1] < ... < A[A.length - 1] <= 10^9
 */
public class Code_873_LengthofLongestFibonacciSubsequence {
    /**
     * 思路：
     * 用一个二维数组memo来记录斐波那契序列的长度。
     * 二维数组中第i行第j列数字memo[i][j]的含义是以输入数组中A[i]结尾、并且前一个数字是A[j]的斐波那契序列的长度。
     * 如果存在一个数字k，满足A[k] + A[j] = A[i]，（符合斐波那契数列）
     * 那么memo[i][j] = memo[j][k] + 1。(A[i]就是A[j]的后面一个元素）
     * 如果不存在满足条件的k，那么意味这A[j]、A[i]不在任意一个斐波那契序列中,memo[i][j] = 2。
     */
    public int lenLongestFibSubseq(int[] A) {
        if(A==null || A.length==0){
            return 0;
        }

        int N=A.length;

        //使用map存储<数字,在A中的下标>，由于是递增的,不用担心数字重复问题
        Map<Integer,Integer> num_index=new HashMap<>();
        for(int i=0;i<N;i++){
            num_index.put(A[i],i);
        }

        int[][] memo=new int[N][N];

        //将memo中元素都初始化为2
        //memo[i][j]表示以A[i]结尾，前面一个值为A[j]的斐波那契数列长度-->最小为2
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                memo[i][j]=2;
            }
        }

        int res=0;

        for(int j=2;j<N;j++){
            if(A[0]+A[1]==A[j]){
                //以A[j]结尾的前面的元素为A[1]的斐波那契数列长度为3
                memo[1][j]=3;
                res=Math.max(res,3);
            }
        }

        for(int i=2;i<N;i++){
            for(int j=i+1;j<N;j++){
                if((A[j]-A[i]<A[i]) && num_index.containsKey(A[j]-A[i])){
                    // A[k]+A[i]==A[j] --> memo[i][j]=memo[k][i]+1
                    int k=num_index.get(A[j]-A[i]);
                    memo[i][j] = memo[k][i]+1;
                    res = Math.max(res,memo[i][j]);
                }
            }
        }

        return res;
    }
}
