package code_06_backtrack;

/**
 * 79. Word Search
 *
 * Given a 2D board and a word, find if the word exists in the grid.
 * The word can be constructed from letters of sequentially adjacent cell,
 * where "adjacent" cells are those horizontally or vertically neighboring.
 * The same letter cell may not be used more than once.
 *
 * Example:
 board =
 [
 ['A','B','C','E'],
 ['S','F','C','S'],
 ['A','D','E','E']
 ]

 * Given word = "ABCCED", return true.
 * Given word = "SEE", return true.
 * Given word = "ABCB", return false.
 */
public class Code_79_WordSearch {
    //TODO：使用一个数组来表示要查找的方向,这是一个小技巧
    private int[][] d={{-1,0},{0,1},{1,0},{0,-1}};

    //因为要求在一次查找中字符只能出现一次
    private boolean[][] visited;

    //m，n分别表示该维数组的行和列
    private int m;
    private int n;

    //从board[startx][starty]开始寻找word[index...word.ength()]
    private boolean searchBoard(char[][] board, String word,int index,int startx,int starty){
        if(index==word.length()-1){
            //递归到最后一个字符，看看从[startx,starty]位置开始，是否有该字符
            return board[startx][starty]==word.charAt(index);
        }
        if(board[startx][starty]==word.charAt(index)){
            visited[startx][starty]=true;
            //从[startx,starty]位置开始，向四个方向查找下一元素
            for(int i=0;i<4;i++){
                //TODO：对四个方向都进行查找
                int newx=startx+d[i][0];
                int newy=starty+d[i][1];
                if(inArea(newx,newy) && visited[newx][newy]==false){
                    //从board[newx][newy]开始寻找word[index+1...word.ength()]
                    if(searchBoard(board,word,index+1,newx,newy)){
                        return true;
                    }
                }
            }
            visited[startx][starty]=false;
        }
        return false;
    }

    private boolean inArea(int x,int y){
        return (x>=0 && x<m) && (y>=0 && y<n);
    }

    public boolean exist(char[][] board, String word) {
        m=board.length;
        assert m>0;
        n=board[0].length;
        visited=new boolean[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(searchBoard(board,word,0,i,j)){
                    return true;
                }
            }
        }
        return false;
    }
}
