package code_01_array;

import org.junit.Test;

/**
 * 867. Transpose Matrix
 *
 * Given a matrix A, return the transpose（专职） of A.
 * The transpose of a matrix is the matrix flipped over it's main diagonal(主对角线),
 * switching the row and column indices of the matrix.
 *
 * Example 1:
 Input: [[1,2,3],[4,5,6],[7,8,9]]
 Output: [[1,4,7],[2,5,8],[3,6,9]]

 *  Example 2:
 Input: [[1,2,3],[4,5,6]]
 Output: [[1,4],[2,5],[3,6]]
 */
public class Code_867_TransposeMatrix {
    public int[][] transpose(int[][] A) {
        int m=A.length;
        if(m==0){
            return new int[0][0];
        }
        int n=A[0].length;
        int[][] res=new int[n][m];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                res[i][j]=A[j][i];
            }
        }
        return res;
    }

    @Test
    public void test(){
        int[][] A={
                {1,2,3},
                {4,5,6}
        };
        int[][] AT=transpose(A);
        ArrayUtils2.print2DimenArray(AT);
    }
}
