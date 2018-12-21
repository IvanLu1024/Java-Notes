package code_06_backtrack;

import org.junit.Test;

/**
 * 37. Sudoku Solver
 */
public class Code_37_SudokuSolver {
    private boolean canPutNum(char[][] board,int pos,
                        boolean[][] row, boolean[][] col, boolean[][] block){
        if(pos==81){
            return true;
        }
        //next位置为pos位置后下一个要填写的位置
        int next = pos + 1;
        for(; next < 81; next ++){
            if(board[next / 9][next % 9] == '.'){
                break;
            }
        }

        //pos的位置在表格中是[x,y]
        int x = pos / 9;
        int y = pos % 9;

        //pos位置要填写的是数字[1...9]
        for(int i = 1; i <= 9; i ++){
            if(row[x][i]==false && col[y][i]==false && block[x/3*3+y/3][i]==false){
                row[x][i]=true;
                col[y][i]=true;
                block[x/3*3+y/3][i]=true;
                board[x][y]=(char)(i+'0');
                if(canPutNum(board,next,row,col,block)){
                    return true;
                }
                block[x/3*3+y/3][i]=false;
                col[y][i]=false;
                row[x][i]=false;
                board[x][y]='.';
            }
        }
        return false;
    }

    public void solveSudoku(char[][] board) {
        boolean[][] row = new boolean[9][10];
        boolean[][] col = new boolean[9][10];
        boolean[][] block = new boolean[9][10];

        //对于表格中已经有的数据，设置成true
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '0';
                    row[i][num] = true;
                    col[j][num] = true;
                    block[i / 3 * 3 + j / 3][num] = true;
                }
            }
        }
        for(int i=0;i<81;i++){
            if(board[i/9][i%9]=='.'){
                if(canPutNum(board,i,row,col,block)==false){
                    continue;
                }
            }
        }
    }

    @Test
    public void test(){
       char[][] board={
               {'5','3','.','.','7','.','.','.','.'},
               {'6','.','.','1','9','5','.','.','.'},
               {'.','9','8','.','.','.','.','6','.'},
               {'8','.','.','.','6','.','.','.','3'},
               {'4','.','.','8','.','3','.','.','.'},
               {'7','.','.','.','2','.','.','.','.'},
               {'.','6','.','.','.','.','2','8','.'},
               {'.','.','.','4','1','9','.','.','5'},
               {'.','.','.','.','8','.','.','7','9'}
       };
       solveSudoku(board);
       for(int i=0;i<9;i++){
           for (int j=0;j<9;j++){
               System.out.print(board[i][j]+" ");
           }
           System.out.println();
       }
    }
}
