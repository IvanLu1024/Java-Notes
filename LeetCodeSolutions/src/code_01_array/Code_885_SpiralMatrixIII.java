package code_01_array;

import org.junit.Test;

/**
 * 885. Spiral Matrix III
 *
 * On a 2 dimensional grid with R rows and C columns, we start at (r0, c0) facing east.
 *
 * Here, the north-west corner of the grid is at the first row and column,
 * and the south-east corner of the grid is at the last row and column.
 *
 * Now, we walk in a clockwise spiral(顺时针螺旋) shape to visit every position in this grid.
 * Whenever we would move outside the boundary of the grid, we continue our walk outside the grid (but may return to the grid boundary later.)
 * Eventually, we reach all R * C spaces of the grid.、
 * Return a list of coordinates representing the positions(位置) of the grid in the order they were visited.
 *
 * Example 1:
 Input: R = 1, C = 4, r0 = 0, c0 = 0
 Output:
 [
  [0,0],[0,1],[0,2],[0,3]
 ]

 * Example 2:
 Input: R = 5, C = 6, r0 = 1, c0 = 4
 Output:
 [
  [1,4],[1,5],[2,5],[2,4],[2,3],[1,3],
  [0,3],[0,4],[0,5],[3,5],[3,4],[3,3],
  [3,2],[2,2],[1,2],[0,2],[4,5],[4,4],
  [4,3],[4,2],[4,1],[3,1],[2,1],[1,1],
  [0,1],[4,0],[3,0],[2,0],[1,0],[0,0]
 ]

 * Note:
 1 <= R <= 100
 1 <= C <= 100
 0 <= r0 < R
 0 <= c0 < C
 */
public class Code_885_SpiralMatrixIII {
    //螺旋的具体方向
    private int[][] d={
            {0,1}, //向右
            {1,0}, //向下
            {0,-1},//向左
            {-1,0},//向上
    };

    public int[][] spiralMatrixIII(int R, int C, int r0, int c0) {
        int[][] res=new int[R*C][2];

        //标记R*C矩阵找那个元素数量
        int count=1;

        //每次螺旋的步长，第一次是1，第二次就是2
        int step=1;
        //[posx,posy]是每次螺旋的起始位置
        int posx=r0;
        int posy=c0;
        res[0][0]=posx;
        res[0][1]=posy;
        //curD是螺旋的方向，curD初始值为0，表示是从右开始的
        int curD=0;
        while(count<R*C){
            for(int i=0;i<2;i++){
                for(int j=0;j<step;j++){
                    posx+=d[curD][0];
                    posy+=d[curD][1];
                    if(inArea(posx,posy,R,C)){
                        res[count][0]=posx;
                        res[count][1]=posy;
                        count++;
                    }
                }
                //每次螺旋，都要换方向。螺旋四次后，又是从右边开始
                curD=(curD+1)%4;
            }
            step++;
        }
        return res;
    }

    private boolean inArea(int x,int y,int R,int C){
        return (x>=0 && x<R) && (y>=0 && y<C);
    }

    @Test
    public void test(){
        int R = 5, C = 6, r0 = 1, c0 = 4;
        int[][] res=spiralMatrixIII(R,C,r0,c0);
        ArrayUtils2.print2DimenArray(res);
    }
}
