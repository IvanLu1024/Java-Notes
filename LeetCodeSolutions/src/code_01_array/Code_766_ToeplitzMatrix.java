package code_01_array;

/**
 * 766. Toeplitz Matrix
 *
 * A matrix is Toeplitz if every diagonal from top-left to bottom-right has the same element.
 * Now given an M x N matrix, return True if and only if the matrix is Toeplitz.
 *
 * Example 1:
 Input:
 matrix = [
 [1,2,3,4],
 [5,1,2,3],
 [9,5,1,2]
 ]
 Output: True
 Explanation:
 In the above grid, the diagonals are:
 "[9]", "[5, 5]", "[1, 1, 1]", "[2, 2, 2]", "[3, 3]", "[4]".
 In each diagonal all elements are the same, so the answer is True.

* Example 2:
 Input:
 matrix = [
 [1,2],
 [2,2]
 ]
 Output: False
 Explanation:
 The diagonal "[1, 2]" has different elements.

* Note:
 matrix will be a 2D array of integers.
 matrix will have a number of rows and columns in range [1, 20].
 matrix[i][j] will be integers in range [0, 99].
 */
public class Code_766_ToeplitzMatrix {
    public boolean isToeplitzMatrix(int[][] matrix) {
       int m=matrix.length;
       int n=matrix[0].length;
       for(int i=1;i<m;i++){
           for(int j=1;j<n;j++){
               if(matrix[i-1][j-1]!=matrix[i][j]){
                   return false;
               }
           }
       }
       return true;
    }
}
