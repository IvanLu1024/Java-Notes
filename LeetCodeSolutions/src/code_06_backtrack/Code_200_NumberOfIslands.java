package code_06_backtrack;

/**
 * 200. Number of Islands
 *
 * Given a 2d grid map of '1's (land) and '0's (water), count the number of islands.
 * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.
 * You may assume all four edges of the grid are all surrounded by water.
 *
 * Example 1:
 * Input:
 11110
 11010
 11000
 00000
 * Output: 1

 * Example 2:
 * Input:
 11000
 11000
 00100
 00011
 * Output: 3
 *
 */
public class Code_200_NumberOfIslands {
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
    private void dfs(char[][] grid, int x, int y) {
        //[x,y]是陆地，并且标记为已经访问的了
        visited[x][y]=true;
        //向四个方向去扩散
        for(int i=0;i<4;i++){
            int newx=x+d[i][0];
            int newy=y+d[i][1];
            if(isArea(newx,newy)==true){
                //[newx,newy]合法
                if(grid[newx][newy]=='1' && visited[newx][newy]==false){
                    //[newx,newy]位置陆地，并且未被标记，进行深度优先遍历
                    //如果已经被标记了，则直接忽略
                    ////找到一个岛，就将整个岛进行标记
                    dfs(grid,newx,newy);
                }
            }
        }
    }

    public int numIslands(char[][] grid) {
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
                if(grid[i][j]=='1' && visited[i][j]==false){
                    res++;
                    //使用floodfill算法来标记这个新岛屿岛屿
                    dfs(grid,i,j);
                }
            }
        }
        return res;
    }
}
