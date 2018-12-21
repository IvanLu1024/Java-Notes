package code_06_backtrack;

import javafx.util.Pair;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Given a 2D board containing 'X' and 'O' (the letter O), capture all regions surrounded by 'X'.
 * A region is captured by flipping all 'O's into 'X's in that surrounded region.

 * Example:
 X X X X
 X O O X
 X X O X
 X O X X

 * After running your function, the board should be:
 X X X X
 X X X X
 X X X X
 X O X X

 * Explanation:

 Surrounded regions shouldn’t be on the border, which means that any 'O' on the border of the board are not flipped to 'X'.
 Any 'O' that is not on the border and it is not connected to an 'O' on the border will be flipped to 'X'.
 Two cells are connected if they are adjacent cells connected horizontally or vertically.
 */
public class Code_130_SurroundedRegions {
    private int m;
    private int n;

    //标记边界上的元素是否是'O'
    private boolean[][] isBoardO;

    private int[][] d={{-1,0},{0,1},{1,0},{0,-1}};

    private boolean isArea(int x,int y){
        return (x>=0 && x<m) && (y>=0 && y<n);
    }

    private void bfs(char[][] board, int x, int y) {
        isBoardO[x][y]=true;
        Queue<Pair<Integer,Integer>> q=new LinkedList<>();
        q.add(new Pair<>(x,y));
        while(!q.isEmpty()){
            Pair<Integer,Integer> p=q.poll();
            x=p.getKey();
            y=p.getValue();
            for(int i=0;i<4;i++){
                int newx=x+d[i][0];
                int newy=y+d[i][1];
                if(isArea(newx,newy) && board[newx][newy]=='O' && isBoardO[newx][newy]==false){
                    q.add(new Pair<>(newx,newy));
                    isBoardO[newx][newy]=true;
                }
            }
        }
    }

    /**
     * 思路：
     * 除了和边界有接触的’O'的区域，其他的‘O'的区域都会变成'X'。
     * 所以扫描一遍边界，对于在边界上的’O', 通过BFS标记与它相邻的'O'。
     */
    public void solve(char[][] board) {
        m=board.length;
        if(m==0){
            return;
        }
        n=board[0].length;
        isBoardO= new boolean[m][n];
        //对边界进行广度优先遍历，标记‘O’
        for(int j=0;j<n;j++){
            if(board[0][j]=='O' && isBoardO[0][j]==false){
                bfs(board,0,j);
            }
            if(board[m-1][j]=='O'&& isBoardO[m-1][j]==false){
                bfs(board,m-1,j);
            }
        }
        for(int i=0;i<m;i++){
            if(board[i][0]=='O' && isBoardO[i][0]==false){
                bfs(board,i,0);
            }
            if(board[i][n-1]=='O' && isBoardO[i][n-1]==false){
                bfs(board,i,n-1);
            }
        }

        //对整个数组进行遍历，覆盖非边界的 'O'
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(isBoardO[i][j]==false && board[i][j]=='O'){
                    board[i][j]='X';
                }
            }
        }
    }

    @Test
    public void test(){
       /* char[][] board={
                {'O','O','O'},
                {'O','O','O'},
                {'O','O','O'}
        };*/
       char[][] board={
               {'X','X','X','X'},
               {'X','O','O','X'},
               {'X','X','O','X'},
               {'X','O','X','X'}
       };
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("========================");
        solve(board);
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
    }

}
