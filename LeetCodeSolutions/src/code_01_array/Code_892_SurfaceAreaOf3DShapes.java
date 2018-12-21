package code_01_array;

/**
 * 892. Surface Area of 3D Shapes
 *
 * On a N * N grid, we place some 1 * 1 * 1 cubes.
 * Each value v = grid[i][j] represents a tower of v cubes placed on top of grid cell (i, j).
 * Return the total surface area of the resulting shapes.
 *
 * Example 1:
 Input: [[2]]
 Output: 10

 * Example 2:
 Input: [[1,2],[3,4]]
 Output: 34

 * Example 3:
 Input: [[1,0],[0,2]]
 Output: 16

 * Example 4:
 Input: [[1,1,1],[1,0,1],[1,1,1]]
 Output: 32

 * Example 5:
 Input: [[2,2,2],[2,1,2],[2,2,2]]
 Output: 46
 */
public class Code_892_SurfaceAreaOf3DShapes {
    /**
     * 计算每个cube的表面积，然后减去他们重合的部分
     */
    public int surfaceArea(int[][] grid) {
        int m=grid.length;
        if(m==0){
            return 0;
        }
        int n=grid[0].length;
        int res=0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j]==0){
                    continue;
                }
                //res+=(grid[i][j]*1+grid[i][j]*1+1*1)*2;
                res+=grid[i][j]*4+2;
                if(i<m-1){
                    res-=Math.min(grid[i][j],grid[i+1][j])*2;
                }
                if(j<n-1){
                    res-=Math.min(grid[i][j],grid[i][j+1])*2;
                }
            }
        }
        return res;
    }
}
