package code_01_array;

import org.junit.Test;

/**
 * 48. Rotate Image
 *
 * You are given an n x n 2D matrix representing an image.
 * Rotate the image by 90 degrees (clockwise).
 *
 * Note:
 You have to rotate the image in-place,
 which means you have to modify the input 2D matrix directly. DO NOT allocate another 2D matrix and do the rotation.

 * Example 1:

 Given input matrix =
 [
 [1,2,3],
 [4,5,6],
 [7,8,9]
 ],

 rotate the input matrix in-place such that it becomes:
 [
 [7,4,1],
 [8,5,2],
 [9,6,3]
 ]
 */
public class Code_48_RotateImage {
    /**
     * 思路一：
     * 进行两次操作
     * 第一次操作，将该矩阵进行转置
     * 第一次操作，将该矩阵每一行的一维数组进行逆序操作
     */
    public void rotate1(int[][] matrix) {
        if (matrix == null || matrix.length== 0) {
            return;
        }
        int n=matrix.length;

        //转置该数组
        for(int i=0;i<n;i++){
            for(int j=0;j<=i;j++){
                swap(matrix,i,j,j,i);
            }
        }
        //将每一行的一维数组逆序
        for(int i=0;i<n;i++){
            revers(matrix[i],0,n-1);
        }
    }

    /**
     * 思路二：
     * 仔细观察Example 1可以发现，一个矩阵里面，旋转会让4个元素互相到达对方的位置，可以把对应的4个元素归为一组。比如：
     [0][0] --> [0][3]
     [0][3] --> [3][3]
     [3][3] --> [3][0]
     [3][0] --> [0][0]
     这是矩阵四个角的4个元素归为了一组，旋转图像就是它们的位置互换。
     我们可以利用这个规律，让矩阵一次把一组4个元素的位置全部换完，然后再进行下一组4个元素。
     * 四个元素下标:
     * [n-1-j][i]
     * [j][n-1-i]
     * [n-1-i][n-1-j]
     * [i][j]
     */
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        if (n <= 1) {
            return;
        }
        for (int i = 0; i <= n / 2; i++){
            for (int j = i; j < n - 1 - i; j++) {
                int t = matrix[i][j];
                matrix[i][j] = matrix[n - 1 - j][i];
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
                matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
                matrix[j][n - 1 - i] = t;
            }
        }
    }

    private void swap(int[][] matrix,int i1,int j1,int i2,int j2){
        int tmp=matrix[i1][j1];
        matrix[i1][j1]=matrix[i2][j2];
        matrix[i2][j2]=tmp;
    }

    private void revers(int[] arr,int start,int end){
        while(start<=end){
            int tmp=arr[start];
            arr[start]=arr[end];
            arr[end]=tmp;
            start++;
            end--;
        }
    }

    @Test
    public void test(){
      /*  int[][] matrix={
                {1,2,3},
                {4,5,6},
                {7,8,9}
        };*/
        int[][] matrix={
                {5, 1, 9,11},
                {2, 4, 8,10},
                {13, 3, 6, 7},
                {15,14,12,16}
        };
        rotate(matrix);
    }
}
