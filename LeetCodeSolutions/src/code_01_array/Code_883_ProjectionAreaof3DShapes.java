package code_01_array;

import org.junit.Test;

/**
 * 883. Projection Area(投影面积) of 3D Shapes
 *
 * On a N * N grid,
 * we place some 1 * 1 * 1 cubes（立方） that are axis-aligned with the x, y, and z axes.
 * Each value v = grid[i][j] represents a tower of v cubes placed on top of grid cell (i, j).
 * Now we view the projection of these cubes onto the xy, yz, and zx planes.
 * A projection is like a shadow, that maps our 3 dimensional figure to a 2 dimensional plane.
 * Here, we are viewing the "shadow" when looking at the cubes from the top, the front, and the side.
 * Return the total area of all three projections.
 *
 * Note:
 1 <= grid.length = grid[0].length <= 50
 0 <= grid[i][j] <= 50
 */
public class Code_883_ProjectionAreaof3DShapes {
    public int projectionArea(int[][] grid) {
        //s1表示俯视图的面积
        int s1=0;
        //s2表示正视图的面积
        int s2=0;
        //说表示左视图的面积
        int s3=0;

        int m=grid.length;
        int n=grid[0].length;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j]!=0){
                    s1++;
                }
            }
        }
        for(int i=0;i<m;i++){
            //记录每次遍历i位置的最大高度
            int max=grid[i][0];
            for(int j=0;j<n;j++){
                max=Math.max(max,grid[i][j]);
            }
            s2+=max;
        }

        for(int j=0;j<n;j++){
            //记录每次遍历i位置的最大高度
            int max=grid[0][j];
            for(int i=0;i<m;i++){
                max=Math.max(max,grid[i][j]);
            }
            s3+=max;
        }
        return (s1+s2+s3);
    }

    @Test
    public void test(){
       /* int[][] grid={
                {1,1,1},
                {1,0,1},
                {1,1,1}
        };*/

       int[][] grid={
               {2,2,2},
               {2,1,2},
               {2,2,2}
       };


        System.out.println(projectionArea(grid));
    }
}
