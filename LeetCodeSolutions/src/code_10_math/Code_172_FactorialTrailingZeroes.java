package code_10_math;

import org.junit.Test;

/**
 * 172. Factorial Trailing Zeroes
 *
 * Given an integer n, return the number of trailing zeroes in n!.
 * (统计阶乘尾部有多少个 0)
 */
public class Code_172_FactorialTrailingZeroes {
    /**
     * 递归写法
     */
    public int trailingZeroes1(int n) {
        return n==0? 0 : n/5 + trailingZeroes1(n/5);
    }

    /**
     * 非递归写法
     */
    public int trailingZeroes(int n) {
        int res=0;
        while(n!=0){
            n /= 5;
            res += n;
        }
        return res;
    }

    @Test
    public void test(){
        System.out.println(trailingZeroes(3));
        System.out.println(trailingZeroes(5));
        System.out.println(trailingZeroes(10));
        System.out.println(trailingZeroes(13));
    }
}
