package code_01_array;

import org.junit.Test;

/**
 * 59. Spiral Matrix II
 * Given a positive integer n, generate a square matrix filled with elements from 1 to n2 in spiral order.
 *
 *  Example:
 Input: 3
 Output:
 [
 [ 1, 2, 3 ],
 [ 8, 9, 4 ],
 [ 7, 6, 5 ]
 ]
 */
public class Code_59_SpiralMatrixII {
    public int[][] generateMatrix(int n) {
        int[][] res=new int[n][n];

        int top=0;
        int down=n-1;
        int left=0;
        int right=n-1;
        int ele=1;
        while(top<=down && left<=right){
            //从左向右遍历，进行赋值
            for(int j=left;j<=right;j++){
                res[top][j]=ele;
                ele++;
            }
            top++;

            //从上向下遍历，进行赋值
            for(int i=top;i<=down;i++){
                res[i][right]=ele;
                ele++;
            }
            right--;

            //从右向左遍历，进行赋值
            if(down-top>=0){
                for(int j=right;j>=left;j--){
                    res[down][j]=ele;
                    ele++;
                }
            }
            down--;

            //从下向上遍历，进行赋值
            if(right-left>=0){
                for(int i=down;i>=top;i--){
                    res[i][left]=ele;
                    ele++;
                }
            }
            left++;
        }
        return res;
    }

    @Test
    public void test(){
        int n=3;
        int[][] arr=generateMatrix(n);
        ArrayUtils2.print2DimenArray(arr);
    }
}
