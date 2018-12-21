package code_07_dp;

import org.junit.Test;

/**
 * 70. Climbing Stairs
 *
 * You are climbing a stair case. It takes n steps to reach to the top.
 * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
 * Note: Given n will be a positive integer.
 *
 * Example 1:
 * Input: 2
 * Output: 2
 *
 * Explanation: There are two ways to climb to the top.
 1. 1 step + 1 step
 2. 2 steps

 * Example 2:
 * Input: 3
 * Output: 3
 * Explanation: There are three ways to climb to the top.
 1. 1 step + 1 step + 1 step
 2. 1 step + 2 steps
 3. 2 steps + 1 step
 */
public class Code_70_ClimbingStairs {
    private int calcWays1(int n){
        if(n==1){
            return 1;
        }
        if(n==2){
            return 2;
        }
        return calcWays1(n-1)+calcWays1(n-2);
    }

    public int climbStairs1(int n) {
        return calcWays1(n);
    }

    private int[] memo2;
    private int calcWays2(int n){
        if(n==1){
            return 1;
        }
        if(n==2){
            return 2;
        }
        if(memo2[n]==-1){
            memo2[n]=calcWays2(n-1)+calcWays2(n-2);
        }
        return memo2[n];
    }

    public int climbStairs2(int n) {
        memo2=new int[n+1];
        for(int i=0;i<n+1;i++){
            memo2[i]=-1;
        }
        return calcWays2(n);
    }

    public int climbStairs(int n) {
        int[] memo=new int[n+1];
        if(n<=1){
            return 1;
        }
        memo[1]=1;
        //注意：这里 小标是2,说明数组长度至少为3,n至少为2，所以前面要对n进行判断
        memo[2]=2;
        for(int i=3;i<=n;i++){
            memo[i]=memo[i-1]+memo[i-2];
        }
        return memo[n];
    }

    @Test
    public void test(){
        System.out.println(climbStairs(25));
    }
}
