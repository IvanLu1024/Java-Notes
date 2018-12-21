package code_06_backtrack;

import org.junit.Test;

/**
 * 733. Flood Fill
 *
 * An image is represented by a 2-D array of integers,
 * each integer representing the pixel value of the image (from 0 to 65535).
 *
 * Given a coordinate (sr, sc) representing the starting pixel (row and column) of the flood fill,
 * and a pixel value newColor, "flood fill" the image.

 To perform a "flood fill", consider the starting pixel, plus any pixels connected 4-directionally
 to the starting pixel of the same color as the starting pixel,
 plus any pixels connected 4-directionally to those pixels (also with the same color as the starting pixel),
 and so on. Replace the color of all of the aforementioned pixels with the newColor.
 At the end, return the modified image.
 */
public class Code_733_FloodFill {
    private int m;
    private int n;

    //用来标记是否是同一中颜色
    private boolean[][] visited;

    //TODO:二维数组四个方向查找的小技巧
    private int[][] d={{-1,0},{0,1},{1,0},{0,-1}};

    private boolean isArea(int x,int y){
        return (x>=0 && x<m) && (y>=0 && y<n);
    }

    //对颜色进行标记
    private void dfs(int[][] image, int color,int newColor,int x, int y) {
        visited[x][y]=true;
        image[x][y]=newColor;
        //向四个方向去扩散
        for(int i=0;i<4;i++){
            int newx=x+d[i][0];
            int newy=y+d[i][1];
            if(isArea(newx,newy)==true){
                //[newx,newy]合法
                if(image[newx][newy]==color && visited[newx][newy]==false){
                    //[newx,newy]，并且未被标记，进行深度优先遍历
                    image[newx][newy]=newColor;
                    dfs(image,color,newColor,newx,newy);
                }
            }
        }
    }

    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        m=image.length;
        if(m==0){
            return image;
        }
        n=image[0].length;
        visited=new boolean[m][n];
        int color=image[sr][sc];
        dfs(image,color,newColor,sr,sc);
        return image;
    }

    @Test
    public void test(){
         int[][] image={
                 {1,1,1},
                 {1,1,0},
                 {1,0,1}
         };
         floodFill(image,1,1,2);
         for(int i=0;i<image.length;i++){
             for(int j=0;j<image[0].length;j++){
                 System.out.print(image[i][j]+" ");
             }
             System.out.println();
         }
    }
}
