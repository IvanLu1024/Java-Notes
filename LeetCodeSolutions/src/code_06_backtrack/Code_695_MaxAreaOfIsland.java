package code_06_backtrack;

import org.junit.Test;

/**
 *
 * 695. Max Area of Island
 *
 * Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land)
 * connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.
 * Find the maximum area of an island in the given 2D array. (If there is no island, the maximum area is 0.)

 Example 1:
 [[0,0,1,0,0,0,0,1,0,0,0,0,0],
 [0,0,0,0,0,0,0,1,1,1,0,0,0],
 [0,1,1,0,1,0,0,0,0,0,0,0,0],
 [0,1,0,0,1,1,0,0,1,0,1,0,0],
 [0,1,0,0,1,1,0,0,1,1,1,0,0],
 [0,0,0,0,0,0,0,0,0,0,1,0,0],
 [0,0,0,0,0,0,0,1,1,1,0,0,0],
 [0,0,0,0,0,0,0,1,1,0,0,0,0]]
 Given the above grid, return 6. Note the answer is not 11, because the island must be connected 4-directionally.

 Example 2:
 [[0,0,0,0,0,0,0,0]]
 Given the above grid, return 0.
 Note: The length of each dimension in the given grid does not exceed 50.
 */
public class Code_695_MaxAreaOfIsland {
    private int m;
    private int n;

    //用来标记是否是同一个岛屿
    private boolean[][] visited;

    //TODO:二维数组四个方向查找的小技巧
    private int[][] d={{-1,0},{0,1},{1,0},{0,-1}};

    private boolean isArea(int x,int y){
        return (x>=0 && x<m) && (y>=0 && y<n);
    }

    //找到一个岛，就将这个岛标记
    //返回的值就是[x,y]所在岛屿的面积
    private int dfs(int[][] grid, int x, int y) {
        if(grid[x][y] == 0){
            return 0;
        }

        //[x,y]是陆地，并且标记为已经访问的了
        visited[x][y]=true;

        int area=1;

        //向四个方向去扩散
        for(int i=0;i<4;i++){
            int newx=x+d[i][0];
            int newy=y+d[i][1];
            if(isArea(newx,newy)){
                //[newx,newy]合法
                if(grid[newx][newy]==1 && visited[newx][newy]==false){
                    //[newx,newy]位置陆地，并且未被标记，进行深度优先遍历
                    //如果已经被标记了，则直接忽略
                    //找到一个岛，就将整个岛进行标记
                    area+=dfs(grid,newx,newy);
                }
            }
        }
        return area;
    }

    public int maxAreaOfIsland(int[][] grid) {
        m=grid.length;
        if(m==0){
            return 0;
        }
        n=grid[0].length;
        visited=new boolean[m][n];
        int res=0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                //有陆地，并且这块陆地未被标记，则就是一个新的岛屿
                if(grid[i][j]==1 && visited[i][j]==false){
                    //使用floodfill算法来标记这个新岛屿岛屿
                    res=Math.max(res,dfs(grid,i,j));
                }
            }
        }
        return res;
    }

    @Test
    public void test(){
       /* int[][] grid={
                {0,0,1,0,0,0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,1,1,1,0,0,0},
                {0,1,1,0,1,0,0,0,0,0,0,0,0},
                {0,1,0,0,1,1,0,0,1,0,1,0,0},
                {0,1,0,0,1,1,0,0,1,1,1,0,0},
                {0,0,0,0,0,0,0,0,0,0,1,0,0},
                {0,0,0,0,0,0,0,1,1,1,0,0,0},
                {0,0,0,0,0,0,0,1,1,0,0,0,0}
        };*/
        int[][] grid={
                {1,1,1,1,0},
                {1,1,0,1,0},
                {1,1,0,0,1},
                {0,0,0,0,1},
        };
        System.out.println(maxAreaOfIsland(grid));
    }
}
