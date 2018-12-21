package code_02_find;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * 202. Happy Number
 * Write an algorithm to determine if a number is "happy".
 *
 * A happy number is a number defined by the following process:
 * Starting with any positive integer, replace the number by the sum of the squares of its digits,
 * and repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1.
 * Those numbers for which this process ends in 1 are happy numbers.
 *
 * Example:
 *
 * Input: 19
 * Output: true
 * Explanation:
 * 1^2 + 9^2 = 82
 * 8^2 + 2^2 = 68
 * 6^2 + 8^2 = 100
 * 1^2 + 0^2 + 0^2 = 1
 */
public class Code_202_HappyNumber {
    public int getNumber(int n){
        int sum=0;
        while(n>0){
            sum+=(n%10)*(n%10);
            n/=10;
        }
        return sum;
    }

    public boolean isHappy(int n) {
        Set<Integer> set=new HashSet<>();
        //set存储运算过程中的数字（不重复）
        while(n!=1){
            if(set.contains(n)){
                //说明运算过程中出现重复元素，则肯定不是幸运数字
                return false;
            }else{
                set.add(n);
                n=getNumber(n);
            }
        }
        return true;
    }
}
