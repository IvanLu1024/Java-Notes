package code_01_array;

import org.junit.Test;

/**
 * 498. Diagonal Traverse(对角线遍历)
 *
 * Given a matrix of M x N elements (M rows, N columns),
 * return all elements of the matrix in diagonal order as shown in the below image.
 *
 * Input:
 [
 [ 1, 2, 3 ],
 [ 4, 5, 6 ],
 [ 7, 8, 9 ]
 ]
 Output:  [1,2,4,7,5,3,6,8,9]
 */
public class Code_498_DiagonalTraverse {
    //记录该二维数组的长度和宽度
    private int m;
    private int n;

    private boolean inArea(int x,int y){
        return ((x>=0 && x<m) && (y>=0  && y<n));
    }

    /**
     * 思路：
     * 判断一下边界，用一个up变量记录方向，碰到边界改变方向
     */
    public int[] findDiagonalOrder(int[][] matrix) {
        m=matrix.length;
        if(m==0){
            return new int[0];
        }
        n=matrix[0].length;

        int[] res=new int[m*n];
        int x = 0, y = 0;
        int nextX, nextY;
        //up记录方向，true表示斜向上
        boolean up = true;
        int index=0;
        while(true){
            res[index++]=matrix[x][y];
            if(up){
                //斜向上
                nextX = x - 1;
                nextY = y + 1;
            }else{
                nextX = x + 1;
                nextY = y - 1;
            }
            if(inArea(nextX,nextY)){
                x=nextX;
                y=nextY;
            }else if(up){
                if(inArea(x, y + 1)){
                    y ++;
                }else{
                    x ++;
                }
                up = false;
            }else{
                if(inArea(x + 1, y)) {
                    x++;
                }else{
                    y ++;
                }
                up = true;
            }
            if(!inArea(x, y)){
                break;
            }
        }
        return res;
    }

    @Test
    public void test(){
        int[][] m={
                {1,2,3},
                {4,5,6},
                {7,8,9}
        };
        int[] arr=findDiagonalOrder(m);
        for(int i:arr){
            System.out.println(i);
        }
    }
}
