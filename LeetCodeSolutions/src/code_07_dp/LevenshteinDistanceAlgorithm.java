package code_07_dp;

import org.junit.Test;

/**
 * Created by 18351 on 2018/12/10.
 */
public class LevenshteinDistanceAlgorithm {
    private int min(int a,int b,int c) {
        int tmp=a<b?a:b;
        return tmp<c?tmp:c;
    }


    public int editDistance(String s1,String s2){
        int m=s1.length();
        int n=s2.length();
        int[][] edit=new int[m+1][n+1];

        for(int i=0;i<=m;i++){
            edit[i][0]=i;
        }

        for(int j=0;j<=n;j++){
            edit[0][j]=j;
        }

        for(int i=1;i<=m;i++){
            for(int j=1;j<=n;j++){
                int flag=0;
                if(s1.charAt(i-1)!=s2.charAt(j-1)){
                    flag=1;
                }
                edit[i][j]=min(edit[i-1][j]+1,edit[i][j-1]+1,edit[i-1][j-1]+flag);
            }
        }
        return edit[m][n];
    }

    @Test
    public void test(){
        String s1="jary";
        String s2="jerry";
        System.out.println(editDistance(s1,s2));
    }
}
