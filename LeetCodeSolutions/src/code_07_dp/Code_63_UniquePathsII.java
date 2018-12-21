package code_07_dp;

/**
 * 63. Unique Paths II
 *
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
 * The robot can only move either down or right at any point in time.
 * The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
 * Now consider if some obstacles are added to the grids. How many unique paths would there be?
 */
public class Code_63_UniquePathsII {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m=obstacleGrid.length;
        if(m==0){
            return 0;
        }
        int n=obstacleGrid[0].length;
        if(n==0 || obstacleGrid[0][0]==1){
            return 0;
        }
        int[][] memo=new int[m][n];

        //这里已经排除了obstacleGrid[0][0]==1的情况
        memo[0][0]=1;
        //初始化第0行
        for(int j=1;j<n;j++){
            memo[0][j]=(obstacleGrid[0][j]==1)?0:memo[0][j-1];
        }

        //初始化第0列
        for(int i=1;i<m;i++){
            memo[i][0]=(obstacleGrid[i][0]==1)?0:memo[i-1][0];
        }

        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                memo[i][j]=(obstacleGrid[i][j]==1)?0:(memo[i-1][j]+memo[i][j-1]);
            }
        }
        return memo[m-1][n-1];
    }
}
