package code_07_dp;

import org.junit.Test;

/**
 * 求解LCS问题的具体解
 *
 * Sample Input:
 abcfbc         abfcab
 programming    contest
 abcd           mnp

 * Sample Output:
 "abcb"
 2
 ""
 */
public class LCSSolution {
    StringBuilder res;
    //记录最长公共子序列的走向 'c'表示斜方向 ‘l’表示左方向 ‘u’表示上方向
    char[][] x;

    public int LCSLength(String s,String t,int m,int n){
        res=new StringBuilder();
        if(m==0 || n==0){
            return 0;
        }
        int[][] lcs=new int[m+1][n+1];
        x=new char[m+1][n+1];

        for(int i=0;i<m+1;i++){
           for(int j=0;j<n+1;j++){
               lcs[i][j]=0;
           }
        }

        for(int i=1;i<m+1;i++){
            for(int j=1;j<n+1;j++){
                //注意：这里的i是从1开始的 i=m时，实际上取到的是s的第m个元素
                if(s.charAt(i-1)==t.charAt(j-1)){
                    lcs[i][j]=1+lcs[i-1][j-1];
                    x[i][j]='c';
                }else if(lcs[i-1][j]>=lcs[i][j-1]){
                    lcs[i][j]=lcs[i-1][j];
                    x[i][j]='u';
                }else if(lcs[i-1][j]<lcs[i][j-1]){
                    lcs[i][j]=lcs[i][j-1];
                    x[i][j]='l';
                }
            }
        }
        return lcs[m][n];
    }

    public void printLCS(String s,int m,int n){
        if(m==0 || n==0){
            return;
        }
        if(x[m][n]=='c'){
            printLCS(s,m-1,n-1);
            res.append(s.charAt(m-1));
        }else if(x[m][n]=='u'){
            printLCS(s,m-1,n);
        }else if(x[m][n]=='l'){
            printLCS(s,m,n-1);
        }
    }

    @Test
    public void test(){
        String s="abcfbc";
        String t="abfcab";
        int m=s.length();
        int n=t.length();
        //String s="a";
        //String t="ab";
        LCSLength(s,t,m,n);
        printLCS(s,m,n);
        System.out.println(res.toString());
    }
}
