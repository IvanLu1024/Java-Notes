package code_06_backtrack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 51. N-Queens
 * The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that no two queens attack each other.
 * Given an integer n, return
 *
 * TODO: all distinct solutions to the n-queens puzzle.

 * Each solution contains a distinct board configuration of the n-queens' placement,
 * where 'Q' and '.' both indicate a queen and an empty space respectively.
 */
public class Code_51_NQueens {
    private List<List<String>> res;

    //用于判断是否在同一竖线上，因为index表示行数，是变化的，所以不用判断是否在相同行
    private boolean col[];
    //判断是是否在1类对角线上
    private boolean dia1[];
    //判断是是否在2类对角线上
    private boolean dia2[];

    //在 n*n 棋盘的index行上放皇后
    //row用于存储在index行能够放皇后的位置
    private void putQueuen(int n,int index,List<Integer> row){
        if(index==n){
            //generateBoard(n,row)用于产生棋盘
            res.add(generateBoard(n,row));
            return;
        }
        for(int j=0;j<n;j++){
            //TODO:对角线的表示
            // 1类对角线 i+j = 同一元素 （i，j为二维数组的下标）
            // 2类对角线 i-j+n-1 = 同一元素
            if(col[j]==false && dia1[index+j]==false && dia2[index-j+n-1]==false){
                row.add(j);
                col[j]=true;
                dia1[index+j]=true;
                dia2[index-j+n-1]=true;
                putQueuen(n,index+1,row);
                dia2[index-j+n-1]=false;
                dia1[index+j]=false;
                col[j]=false;
                row.remove(row.size()-1);
            }
        }
        return;
    }

    //row用于存储index行能够放皇后的位置
    private List<String> generateBoard(int n,List<Integer> row){
        char[][] board=new char[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                board[i][j]='.';
            }
        }
        for(int i=0;i<n;i++){
            board[i][row.get(i)]='Q';
        }
        List<String> list=new ArrayList<>();
        for(int i=0;i<n;i++){
            list.add(new String(board[i]));
        }
        return list;
    }

    public List<List<String>> solveNQueens(int n) {
        res=new ArrayList<>();
        if(n==0){
            return res;
        }
        col=new boolean[n];
        dia1=new boolean[2*n-1];
        dia2=new boolean[2*n-1];
        List<Integer> row=new ArrayList<>();
        putQueuen(n,0,row);
        return res;
    }

    @Test
    public void test(){
        List<List<String>> list=solveNQueens(4);
        for(List<String> l:list){
            System.out.println(l);
        }

    }
}
