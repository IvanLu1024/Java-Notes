package code_07_dp;

/**
 * 62. Unique Paths
 *
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
 * The robot can only move either down or right at any point in time.
 * The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
 * How many possible unique paths are there?
 */
public class Code_62_UniquePaths {
    //注意这里是 n行m列
    public int uniquePaths(int m, int n) {
        int[][] memo=new int[n][m];

        //初始化第0行数据
        memo[0][0]=1;
        for(int j=1;j<m;j++){
            memo[0][j]=memo[0][j-1];
        }

        //初始化第0列数据
        for(int i=1;i<n;i++){
            memo[i][0]=memo[i-1][0];
        }

        for(int i=1;i<n;i++){
            for(int j=1;j<m;j++){
                memo[i][j]=memo[i-1][j]+memo[i][j-1];
            }
        }
        return memo[n-1][m-1];
    }
}
