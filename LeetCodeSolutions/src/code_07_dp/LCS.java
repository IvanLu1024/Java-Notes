package code_07_dp;

import org.junit.Test;

/**
 * Sample Input:
 abcfbc         abfcab
 programming    contest
 abcd           mnp

 * Sample Output:
 4
 2
 0
 */
public class LCS {
    public int LCS(String s,String t){
        int m=s.length();
        int n=t.length();
        if(m==0 || n==0){
            return 0;
        }
        int[][] lcs=new int[m+1][n+1];
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
                }else{
                    lcs[i][j]=Math.max(lcs[i-1][j],lcs[i][j-1]);
                }
            }
        }
        return lcs[m][n];
    }

    @Test
    public void test(){
        String s="abcfbc";
        String t="abfcab";
        //String s="a";
        //String t="ab";
        System.out.println(LCS(s,t));
    }
}
