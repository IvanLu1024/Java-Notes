package code_07_dp;

import org.junit.Test;

/**
 * 343. Integer Break
 *
 *Given a positive integer n, break it into the sum of at least two positive
 * integers and maximize the product of those integers. Return the maximum product you can get.
 *
 * Example 1:
 Input: 2
 Output: 1
 Explanation: 2 = 1 + 1, 1 × 1 = 1.

 * Example 2:
 Input: 10
 Output: 36
 Explanation: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36.
 */
public class Code_343_IntegerBreak {
    private int max3(int a,int b,int c){
        int tmp=(a>b)?a:b;
        return c>tmp?c:tmp;
    }

    private int[] memo1;
    private int breakInteger(int n){
        if(n==1){
            return 1;
        }
        if(memo1[n]!=-1){
            return memo1[n];
        }
        int res=-1;
        for(int i=1;i<n;i++){
            //i*(n-i)
            res=max3(res,i*(n-i),i*breakInteger(n-i));
        }
        memo1[n]=res;
        return res;
    }

    public int integerBreak1(int n) {
        memo1=new int[n+1];
        for(int i=0;i<n+1;i++){
            memo1[i]=-1;
        }
        return breakInteger(n);
    }

    public int integerBreak(int n) {
        assert n>=2;
        int[] memo=new int[n+1];
        memo[1]=1;
        //2--n个数字都能进行拆分
        for(int i=2;i<=n;i++){
            for(int j=1;j<i;j++){
                // j*(i-j)
                memo[i]=max3(memo[i],j*(i-j),j*memo[i-j]);
            }
        }
        return memo[n];
    }

    @Test
    public void test(){
        System.out.println(integerBreak(4));
    }
}
