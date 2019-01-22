package code_10_math;

/**
 * Given a positive integer num, write a function which returns True if num is a perfect square else False.
 * Note: Do not use any built-in library function such as sqrt.
 *
 * Example 1:
 Input: 16
 Output: true
 * Example 2:
 Input: 14
 Output: false
 */
public class Code_367_ValidPerfectSquare {
    /**
     * 思路：
     * 平方序列：1,4,9,16,..间隔：3,5,7,...
     * 间隔为等差数列，使用这个特性可以得到从 1 开始的平方序列。
     */
    public boolean isPerfectSquare(int num) {
        int subNum=1;
        while(num>0){
            num -= subNum;
            subNum += 2;
        }
        return num==0;
    }
}
