package code_06_backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * 417. Pacific Atlantic Water Flow
 *
 * 给定一个 m x n 的非负整数矩阵来表示一片大陆上各个单元格的高度。
 * “太平洋”处于大陆的左边界和上边界，而“大西洋”处于大陆的右边界和下边界。
 * 规定水流只能按照上、下、左、右四个方向流动，
 * TODO:且只能从高到低或者在同等高度上流动。
 * 请找出那些水流既可以流动到“太平洋”，又能流动到“大西洋”的陆地单元的坐标。
 * Given the following 5x5 matrix:

 Pacific
    ~   ~   ~   ~   ~
 ~  1   2   2   3  (5) *
 ~  3   2   3  (4) (4) *
 ~  2   4  (5)  3   1  *
 ~ (6) (7)  1   4   5  *
 ~ (5)  1   1   2   4  *
   *   *   *   *   * Atlantic
 */
public class Code_417_PacificAtlanticWaterFlow {
    private int m;
    private int n;

    private boolean[][] pacific;
    private boolean[][] atlantic;


    //TODO:二维数组四个方向查找的小技巧
    private int[][] d={{-1,0},{0,1},{1,0},{0,-1}};

    private boolean isArea(int x,int y){
        return (x>=0 && x<m) && (y>=0 && y<n);
    }

    private void dfs(int[][] matrix,boolean[][] visited,int x,int y){
        visited[x][y]=true;
        for(int i=0;i<4;i++){
            int newx=x+d[i][0];
            int newy=y+d[i][1];
            if(isArea(newx,newy)){
                //[newx,newy]地势高，并且未被标记
                if(visited[newx][newy]==false && (matrix[newx][newy]>=matrix[x][y])){
                    dfs(matrix,visited,newx,newy);
                }
            }
        }
    }

    public List<int[]> pacificAtlantic(int[][] matrix) {
        List<int[]> res=new ArrayList<>();
        m=matrix.length;
        if(m==0){
            return res;
        }
        n=matrix[0].length;
        pacific=new boolean[m][n];
        atlantic=new boolean[m][n];

        for(int i=0;i<m;i++){
            dfs(matrix,pacific,i,0);
            dfs(matrix,atlantic,i,n-1);
        }
        for(int j=0;j<n;j++){
            dfs(matrix,pacific,0,j);
            dfs(matrix,atlantic,m-1,j);
        }
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(pacific[i][j] && atlantic[i][j]){
                    res.add(new int[]{i,j});
                }
            }
        }
        return res;
    }
}
