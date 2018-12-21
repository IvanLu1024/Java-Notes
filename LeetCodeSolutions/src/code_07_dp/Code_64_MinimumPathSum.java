package code_07_dp;

/**
 * 64. Minimum Path Sum
 *
 * Given a m x n grid filled with non-negative numbers,
 * find a path from top left to bottom right which minimizes the sum of all numbers along its path.
 *
 * Note: You can only move either down or right at any point in time.

 * Example:
 * Input:
 [
 [1,3,1],
 [1,5,1],
 [4,2,1]
 ]
 * Output: 7
 * Explanation: Because the path 1→3→1→1→1 minimizes the sum.
 */
public class Code_64_MinimumPathSum {
    /**
     * 思路：
     * 对于其中一点grid[i][j](0<=i<m，0<=j<n)，（m,n只有是grid[][]的行数和列数）只有两种方法到达该点，所以到这点
     * 最短路径只能是上一点或者左边的点的最短路径加上当前点的值，即d(grid[i][j])=min{d(grid[i][j-1]),d(grid[i-1][j])} + grid[i][j]。
     * 但是对于第0行或者第0列来说都只有一种情况到达该点，所以需要直接**由前一个点的最短路径加上当前点值即可**。
     * 遍历整个网格，便可以得到最终值。
     */
    public int minPathSum1(int[][] grid) {
        int m=grid.length;
        if(m==0){
            return 0;
        }
        int n=grid[0].length;

        int[][] memo=new int[m][n];

        memo[0][0]=grid[0][0];

        //第0行或者第0列来说都只有一种情况到达该点
        //针对0行,涉及到上一行，所以j从1开始
        for(int j=1;j<n;j++){
            memo[0][j]=grid[0][j]+memo[0][j-1];
        }

        //针对0列
        for(int i=1;i<m;i++){
            memo[i][0]=grid[i][0]+memo[i-1][0];
        }

        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                memo[i][j]=grid[i][j]+Math.min(memo[i-1][j],memo[i][j-1]);
            }
        }
        return memo[m-1][n-1];
    }

    public int minPathSum(int[][] grid) {
        int m=grid.length;
        if(m==1){
            return getSum(grid[0]);
        }
        int n=grid[0].length;

        int[][] memo=new int[2][n];

        memo[0][0]=grid[0][0];

        //针对0行,涉及到上一行，所以j从1开始
        for(int j=1;j<n;j++){
            memo[0][j]=grid[0][j]+memo[0][j-1];
        }
        //与前面的memo[0][0]=grid[0][0]，一起组成第0列
        memo[1][0]=grid[1][0]+memo[0][0];

        for(int i=1;i<m;i++){
            memo[i%2][0]=grid[i][0]+memo[(i-1)%2][0];
            for(int j=1;j<n;j++){
                memo[i%2][j]=grid[i][j]+Math.min(memo[(i-1)%2][j],memo[i%2][j-1]);
            }
        }
        return memo[(m-1)%2][n-1];
    }

    int getSum(int[] nums){
        int sum=0;
        for(int i=0;i<nums.length;i++){
            sum+=nums[i];
        }
        return sum;
    }
}
