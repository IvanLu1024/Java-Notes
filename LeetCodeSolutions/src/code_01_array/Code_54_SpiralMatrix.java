package code_01_array;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 54. Spiral Matrix
 *
 * Given a matrix of m x n elements (m rows, n columns),
 * return all elements of the matrix in spiral order.
 *
 * Example 1:
 Input:
 [
 [ 1, 2, 3 ],
 [ 4, 5, 6 ],
 [ 7, 8, 9 ]
 ]
 Output: [1,2,3,6,9,8,7,4,5]


 * Example 2:
 Input:
 [
 [1, 2, 3, 4],
 [5, 6, 7, 8],
 [9,10,11,12]
 ]
 Output: [1,2,3,4,8,12,11,10,9,5,6,7]
 */
public class Code_54_SpiralMatrix {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res=new ArrayList<>();
        int m=matrix.length;
        if(m==0){
            return res;
        }
        int n=matrix[0].length;

        int top=0;
        int down=m-1;
        int left=0;
        int right=n-1;
        while (top <= down && left <= right) {
            //向从左向右遍历
            for(int j=left;j<=right;j++){
                res.add(matrix[top][j]);
            }
            top++;

            //从上向下遍历
            for(int i=top;i<=down;i++){
                res.add(matrix[i][right]);
            }
            right--;

            //从右向左遍历
            if(down-top>=0){
                //top此时已经是下一行了，down-top==0,说明top行和down重合了
                for(int j=right;j>=left;j--){
                    res.add(matrix[down][j]);
                }
            }
            down--;

            //从下往上遍历
            if(right-left>=0){
                for(int i=down;i>=top;i--){
                    res.add(matrix[i][left]);
                }
            }
            left++;
        }
        return res;
    }

    @Test
    public void test() {
        int[][] arr = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        };
        List<Integer> res=spiralOrder(arr);
        System.out.println(res);
    }
}
