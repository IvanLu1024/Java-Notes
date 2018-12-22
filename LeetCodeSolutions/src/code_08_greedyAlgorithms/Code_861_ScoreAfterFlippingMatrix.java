package code_08_greedyAlgorithms;

/**
 * 861. Score After Flipping Matrix
 *
 * We have a two dimensional matrix A where each value is 0 or 1.
 * A move consists of choosing any row or column, and toggling each value in that row or column:
 * changing all 0s to 1s, and all 1s to 0s.
 * After making any number of moves, every row of this matrix is
 * interpreted as a binary number, and the score of the matrix is the sum of these numbers.
 * Return the highest possible score.
 *
 * Example 1:
 Input: [[0,0,1,1],[1,0,1,0],[1,1,0,0]]
 Output: 39
 Explanation:
 Toggled to [[1,1,1,1],[1,0,0,1],[1,1,1,1]].
 0b1111 + 0b1001 + 0b1111 = 15 + 9 + 15 = 39
 */
public class Code_861_ScoreAfterFlippingMatrix {
    /**
     * 思路:
     * 返回尽可能高分这个要求，理解为对同一组数，高位尽可能置1，对不同组的相同位尽可能多的置1。
     * (1)判断最高位是否为1，如果不是1，移动当前行。
     * (2)判断每列的的0的个数，如果0较多，移动当前列。
     * @param A
     * @return
     */
    public int matrixScore(int[][] A) {
        int R=A.length;
        int C=A[0].length;
        for(int i=0;i<R;i++){
            if(A[i][0]==0){ //最高位如果不是1，移动当前行
                for(int j=0;j<C;j++){
                    A[i][j]=1-A[i][j];
                }
            }
        }
        for(int j=0;j<C;j++){
            //统计每列的0和1
            int zero=0;
            int one=0;
            for(int i=0;i<R;i++){
                if(A[i][j]==0){
                    zero++;
                }
                if(A[i][j]==1){
                    one++;
                }
            }
            if(zero>one){
                //反转当前列
                for(int i=0;i<R;i++){
                    A[i][j]=1-A[i][j];
                }
            }
        }
        int res=0;
        for(int i=0;i<R;i++){
            for(int j=0;j<C;j++){
                res+=A[i][j]*Math.pow(2,(int)(C-j-1));
            }
        }
        return res;
    }
}
