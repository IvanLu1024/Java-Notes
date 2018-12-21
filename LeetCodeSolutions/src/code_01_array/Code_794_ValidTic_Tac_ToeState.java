package code_01_array;

/**
 * 794. Valid Tic-Tac-Toe State
 *
 * A Tic-Tac-Toe board is given as a string array board.
 * Return True if and only if it is possible to reach this board position
 * during the course of a valid tic-tac-toe game.
 * (玩家有可能将字符放置成游戏板所显示的状态时，才返回 true。)
 *
 * The board is a 3 x 3 array, and
 * consists of characters " ", "X", and "O".  The " " character represents an empty square.

 * Here are the rules of Tic-Tac-Toe:
 Players take turns placing characters into empty squares (" ").
 The first player always places "X" characters, while the second player always places "O" characters.
 "X" and "O" characters are always placed into empty squares, never filled ones.
 The game ends when there are 3 of the same (non-empty) character filling any row, column, or diagonal.
 The game also ends if all squares are non-empty.
 No more moves can be played if the game is over.

 * Note:
 board is a length-3 array of strings, where each string board[i] has length 3.
 Each board[i][j] is a character in the set {" ", "X", "O"}.

 * Example 1:
 Input: board = ["O  ", "   ", "   "]
 Output: false
 Explanation: The first player always plays "X".

 * Example 2:
 Input: board = ["XOX", " X ", "   "]
 Output: false
 Explanation: Players take turns making moves.

 * Example 3:
 Input: board = ["XXX", "   ", "OOO"]
 Output: false

 * Example 4:
 Input: board = ["XOX", "O O", "XOX"]
 Output: true
 */
public class Code_794_ValidTic_Tac_ToeState {
    public boolean validTicTacToe(String[] board) {
        //统计board上的X和O的数目
        int Onum=0;
        int Xnum=0;
        for(int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                if(board[i].charAt(j)=='O'){
                    Onum++;
                }
                if(board[i].charAt(j)=='X'){
                    Xnum++;
                }
            }
        }

        //Xnum==Onum或者Xnum=Onum+1才是可以的
        if(Math.abs(Onum-Xnum)>=2){
            return false;
        }

        boolean Xwin=win(board,'X');
        boolean Owin=win(board,'O');
        if(Xwin && Owin){
            return false;
        }else if(Xwin && !Owin){
            return Xnum>Onum;
        }else if(!Xwin && Owin){
            return Xnum==Onum;
        }else{
            //!Xwin && !Owin
            return Xnum>=Onum;
        }
    }

    //ch 表示是 'X' 'O'
    //本函数检查是画'X'的赢还是画'O'的赢
    //The game ends when there are 3 of the same (non-empty) character filling any row, column, or diagonal(对角线)
    private boolean win(String[] board,char ch){
        //有一行相同，就赢了
       /* for(int i=0;i<3;i++){
            if(board[i].charAt(0)==ch && board[i].charAt(1)==ch && board[i].charAt(2)==ch){
                return true;
            }
        }
        //有一列相同就赢了
        for(int j=0;j<3;j++){
            if(board[0].charAt(j)==ch && board[1].charAt(j)==ch && board[2].charAt(j)==ch){
                return true;
            }
        }*/
       //改进版
        for(int i=0;i<3;i++){
            //有一行相同，就赢了
            if(board[i].charAt(0)==ch && board[i].charAt(1)==ch && board[i].charAt(2)==ch){
                return true;
            }
            //有一列相同就赢了
            if(board[0].charAt(i)==ch && board[1].charAt(i)==ch && board[2].charAt(i)==ch){
                return true;
            }
        }

        //对角线相同就赢了
        if(board[0].charAt(0)==ch && board[1].charAt(1)==ch && board[2].charAt(2)==ch){
            return true;
        }
        if(board[2].charAt(0)==ch && board[1].charAt(1)==ch && board[0].charAt(2)==ch){
            return true;
        }
        return false;
    }
}
