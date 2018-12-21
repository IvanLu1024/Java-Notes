package code_07_dp;

import org.junit.Test;

/**
 * Created by DHA on 2018/11/23.
 */
public class Knapsack01 {
    private int[][] memeo1;

    //用[0...index]的物品，填充容积为C的背包的最大价值
    private int bestValue(int[] w, int[] v, int index, int C) {
        if (index < 0 || C <= 0) {
            return 0;
        }
        if (memeo1[index][C] != -1) {
            return memeo1[index][C];
        }
        //不放入index物品
        int res = bestValue(w, v, index - 1, C);
        //先判断背包能否放入index物品
        if (C >= w[index]) {
            res = Math.max(res, v[index] + bestValue(w, v, index - 1, C - w[index]));
        }
        memeo1[index][C] = res;
        return res;
    }

    public int knapsack011(int[] w, int[] v, int C) {
        int n = w.length;
        memeo1 = new int[n][C + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < C + 1; j++) {
                memeo1[i][j] = -1;
            }
        }
        return bestValue(w, v, n - 1, C);
    }

    public int knapsack012(int[] w, int[] v, int C) {
        int n=w.length;
        if(n==0 || C==0){
            return 0;
        }
        int[][] memo=new int[n][C+1];
        for(int i=0;i<n;i++){
            for(int j=0;j<C+1;j++){
                memo[i][j]=-1;
            }
        }

        //将标号为0的物品，装入背包中
        for(int j=0;j<C+1;j++){
            //标号为0的物品，放入容量为j的背包中
            memo[0][j]=(j>=w[0]?v[0]:0);
        }

        //memo[i][C]存储[0...i]填充容积为C的背包的最大价值
        for(int i=1;i<n;i++){
            for(int j=0;j<C+1;j++){
                //不放入编号为i的物品
                memo[i][j]=memo[i-1][j];
                if(j>=w[i]){
                    memo[i][j]=Math.max(memo[i][j],v[i]+memo[i-1][j-w[i]]);
                }
            }
        }
        return memo[n-1][C];
    }

    //0-1背包问题的优化
    public int knapsack013(int[] w, int[] v, int C) {
        int n=w.length;
        if(n==0 || C==0){
            return 0;
        }
        int[][] memo=new int[2][C+1];
        for(int i=0;i<2;i++){
            for(int j=0;j<C+1;j++){
                memo[i][j]=-1;
            }
        }

        for(int j=0;j<C+1;j++){
            memo[0][j]=j>=w[0]?w[0]:0;
        }
        //时间复杂度O(n*C)
        for(int i=1;i<n;i++){
            for(int j=0;j<C+1;j++){
                memo[i%2][j]=memo[(i-1)%2][j];
                if(j>=w[i]){
                    memo[i%2][j]=Math.max(memo[i%2][j],v[i]+memo[(i-1)%2][j-w[i]]);
                }
            }
        }
        return memo[(n-1)%2][C];
    }

    public int knapsack01(int[] w, int[] v, int C) {
        int n=w.length;
        if(n==0 || C==0){
            return 0;
        }
        int[] memo=new int[C+1];
        for(int i=0;i<C+1;i++){
            memo[i]=-1;
        }
        for(int j=0;j<C+1;j++){
            memo[j]=j>=w[0]?w[0]:0;
        }
        for(int i=1;i<n;i++){
            for(int j=C;j>=w[i];j--){
                memo[j]=Math.max(memo[j],v[i]+memo[j-w[i]]);
            }
        }
        return memo[C];
    }

    @Test
    public void test(){
        int[] w={1,2,3};
        int[] v={6,10,12};
        System.out.println(knapsack011(w,v,5));
        System.out.println(knapsack012(w,v,5));
        System.out.println(knapsack013(w,v,5));
        System.out.println(knapsack01(w,v,5));
    }
}
