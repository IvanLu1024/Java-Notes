package code_01_array;

import org.junit.Test;

/**
 * 807. Max Increase to Keep City Skyline
 *
 * In a 2 dimensional array grid, each value grid[i][j] represents the height of a building located there.
 * We are allowed to increase the height of any number of buildings,
 * by any amount (the amounts can be different for different buildings).
 * Height 0 is considered to be a building as well.
 *
 * At the end, the "skyline" when viewed from all four directions of the grid,
 * i.e. top, bottom, left, and right, must be the same as the skyline of the original grid.
 * A city's skyline is the outer contour of the rectangles formed by all the buildings when viewed from a distance.
 * (城市的天际线是从远处观看时由所有建筑物形成的矩形的外部轮廓)
 * See the following example.

 * What is the maximum total sum that the height of the buildings can be increased?
 *
 * Example:
 Input: grid = [[3,0,8,4],[2,4,5,7],[9,2,6,3],[0,3,1,0]]
 Output: 35
 Explanation:
 The grid is:
 [ [3, 0, 8, 4],
 [2, 4, 5, 7],
 [9, 2, 6, 3],
 [0, 3, 1, 0] ]

 The skyline viewed from top or bottom is: [9, 4, 8, 7]
 The skyline viewed from left or right is: [8, 7, 9, 3]

 The grid after increasing the height of buildings without affecting skylines is:
 gridNew = [
 [8, 4, 8, 7],
 [7, 4, 7, 7],
 [9, 4, 8, 7],
 [3, 3, 3, 3] ]

* Notes:
 1 < grid.length = grid[0].length <= 50.
 All heights grid[i][j] are in the range [0, 100].
 All buildings in grid[i][j] occupy the entire grid cell: that is, they are a 1 x 1 x grid[i][j] rectangular prism.
 */
public class Code_807_MaxIncreaseToKeepCitySkyline {
    /**
     * 思路：
     * 求出skyline viewed from top or bottom
     * 然后再求出skyline viewed from left or right
     */
    public int maxIncreaseKeepingSkyline(int[][] grid) {
        int n=grid.length;
        if(n==0){
            return 0;
        }
        int[] topOrButtom=new int[n];
        int[] leftOrRight=new int[n];
        //求出skyline viewed from top or bottom
        for(int j=0;j<n;j++){
            int max=0;
            for(int i=0;i<n;i++){
                max=Math.max(max,grid[i][j]);
            }
            topOrButtom[j]=max;
        }

        //求出skyline viewed from left or right
        for(int i=0;i<n;i++){
            int max=0;
            for(int j=0;j<n;j++){
                max=Math.max(max,grid[i][j]);
            }
            leftOrRight[i]=max;
        }

        /*int[][] topOrButtomGrid=new int[n][n];
        int[][] leftOrRightGrid=new int[n][n];
        for(int j=0;j<n;j++){
            for(int i=0;i<n;i++){
               topOrButtomGrid[i][j]=topOrButtom[j];
            }
        }
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                leftOrRightGrid[i][j]=leftOrRight[i];
            }
        }

        int res=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                res+=Math.min(topOrButtomGrid[i][j],leftOrRightGrid[i][j])-grid[i][j];
            }
        }*/
        //优化
        int res=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                res+=Math.min(topOrButtom[j],leftOrRight[i])-grid[i][j];
            }
        }
        return res;
    }

    @Test
    public void test(){
        int[][] grid={
                {3, 0, 8, 4},
                {2, 4, 5, 7},
                {9, 2, 6, 3},
                {0, 3, 1, 0}
        };
        System.out.println(maxIncreaseKeepingSkyline(grid));
    }
}
