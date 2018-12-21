package code_01_array;

import org.junit.Test;

/**
 * 598. Range Addition II
 *
 * Given an m * n matrix M initialized with all 0's and several update operations.
 * Operations are represented by a 2D array, and each operation is represented by an array with
 * two positive integers a and b, which means M[i][j] should be added by one for all 0 <= i < a and 0 <= j < b.
 * You need to count and return the number of maximum integers in the matrix after performing all the operations.
 *
 *
 */
public class Code_598_RangeAdditionII {
    /**
     * 思路一：
     * 暴力解法
     * 但是超出时间限制
     */
    public int maxCount1(int m, int n, int[][] ops) {
        if(m==0 || n==0){
            return 0;
        }
        int[][] matrix=new int[m][n];
        int operations=ops.length;
        for(int k=0;k<operations;k++){
            int a=ops[k][0];
            int b=ops[k][1];
            for(int i=0;i<a;i++){
                for(int j=0;j<b;j++){
                    matrix[i][j]++;
                }
            }
        }
        int matrixMax=matrix[0][0];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                matrixMax=Math.max(matrixMax,matrix[i][j]);
            }
        }
        int res=0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(matrix[i][j]==matrixMax){
                    res++;
                }
            }
        }
        return res;
    }

    /**
     * 思路二：
     * 只需要找出两个操作的重叠区域，由于每次操作都是+1
     * 所有最大值就是 重叠区域长*重叠区域宽
     */
    public int maxCount(int m, int n, int[][] ops) {
        if(m==0 || n==0){
            return 0;
        }
        //重叠区域的长度
        int row=m;
        //重叠区域的宽度
        int col=n;
        for(int i=0;i<ops.length;i++){
            row=Math.min(row,ops[i][0]);
            col=Math.min(col,ops[i][1]);
        }
        return row*col;
    }

    @Test
    public void test() {
        int m = 3;
        int n = 3;
        int[][] ops = {
                {2, 2},
                {3, 3}
        };
        System.out.println(maxCount(m, n, ops));
    }
}
