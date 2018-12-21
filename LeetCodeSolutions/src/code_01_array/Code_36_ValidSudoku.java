package code_01_array;

/**
 * 36. Valid Sudoku
 *
 * Determine if a 9x9 Sudoku board is valid. Only the filled cells need to be validated according to the following rules:

 * Each row must contain the digits 1-9 without repetition.
 * Each column must contain the digits 1-9 without repetition.
 * Each of the 9 3x3 sub-boxes of the grid must contain the digits 1-9 without repetition.
 */
public class Code_36_ValidSudoku {
    public boolean isValidSudoku(char[][] board) {
        for(int i=0;i<9;i++){
            //查看每行是否有重复元素
            boolean[] row = new boolean[10];
            //查看每列是否有重复元素
            boolean[] col = new boolean[10];
            //查看每个九宫格是否有重复元素
            boolean[] block = new boolean[10];
            for(int j=0;j<9;j++){
                if(board[i][j]!='.'){
                    if(row[board[i][j]-'0']==true){
                        return false;
                    }
                    row[board[i][j]-'0']=true;
                }
                if(board[j][i]!='.'){
                    if(col[board[j][i]-'0']==true){
                        return false;
                    }
                    col[board[j][i]-'0']=true;
                }
                if(board[i/3*3+j/3][i%3*3+j%3]!='.'){
                    if(block[board[i/3*3+j/3][i%3*3+j%3]-'0']==true){
                        return false;
                    }
                    block[board[i/3*3+j/3][i%3*3+j%3]-'0']=true;
                }
            }
        }
        return true;
    }
}
