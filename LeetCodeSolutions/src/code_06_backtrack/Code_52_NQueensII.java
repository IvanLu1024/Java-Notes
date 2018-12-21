package code_06_backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * 52. N-Queens II
 *
 * The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that no two queens attack each other.
 * Given an integer n, return the number of distinct solutions to the n-queens puzzle.
 */
public class Code_52_NQueensII {
    private int num=0;

    private boolean[] col;
    private boolean[] dia1;
    private boolean[] dia2;

    //在 n*n 棋盘的index行上放皇后
    //row用于存储在index行能够放皇后的位置
    private void putQuenen(int n,int index,List<Integer> row){
        if(index==n){
            num++;
            return;
        }
        for(int j=0;j<n;j++){
            if(col[j]==false && dia1[index+j]==false && dia2[index-j+n-1]==false){
                row.add(j);
                col[j]=true;
                dia1[index+j]=true;
                dia2[index-j+n-1]=true;
                putQuenen(n,index+1,row);
                dia2[index-j+n-1]=false;
                dia1[index+j]=false;
                col[j]=false;
                row.remove(row.size()-1);
            }
        }
    }

    public int totalNQueens(int n) {
        col=new boolean[n];
        dia1=new boolean[2*n-1];
        dia2=new boolean[2*n-1];
        List<Integer> row=new ArrayList<>();
        putQuenen(n,0,row);
        return num;
    }
}
