package code_10_math;

/**
 * 326. Power of Three
 *
 * Given an integer, write a function to determine if it is a power of three.
 * （判断该数是否是3的n次方）
 */
public class Code_326_PowerofThree {
    /**
     * 思路：一个数是3的次方，那么以3为底n的对数一定是整数。
     * 即log3(n) = log10(n) / log10(3) 是一个整数
     */
    public boolean isPowerOfThree(int n) {
        double res= Math.log10(n) / Math.log10(3);
        return (res-(int)res)==0? true : false;
    }
}
