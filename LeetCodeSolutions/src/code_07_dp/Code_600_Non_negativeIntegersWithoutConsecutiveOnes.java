package code_07_dp;

import org.junit.Test;

/**
 * 600. Non-negative Integers without Consecutive（连续的） Ones （连续的1）
 *
 * Given a positive integer n,
 * find the number of non-negative integers less than or equal to n,
 * whose binary representations（二进制表示） do NOT contain consecutive ones.
 *
 * Example 1:
 Input: 5
 Output: 5
 Explanation:
 Here are the non-negative integers <= 5
 with their corresponding binary representations:

 0 : 0
 1 : 1
 2 : 10
 3 : 11
 4 : 100
 5 : 101

 Among them, only integer 3 disobeys the rule
 (two consecutive ones) and the other 5 satisfy the rule.
 */
public class Code_600_Non_negativeIntegersWithoutConsecutiveOnes {
    /**
     * 思路一：直接法，先判断num是否有连续的1
     */
    public int findIntegers1(int num) {
        int res=0;
        for(int i=0;i<=num;i++){
            if(hasConsecutiveOnes(i)){
                res++;
            }
        }
       return res;
    }

    //判断num是否有连续的1
    private boolean hasConsecutiveOnes(int num){
        for(int i=0;i<32;i++){
            int bit1=num&1;
            int bit2=(num>>1)&1;
            if(bit1==1 && bit2==1){
                return false;
            }
            num=(num>>1);
        }
        return true;
    }

    /**
     * 思路二：动态规划思路
     * (1)将原始n转换为二进制表示字符串
     * (2)设置两个数组
     * dp0 [i]：当前位设置为0时的整数的个数
     * dp1 [i]：当前位设置为1时的整数的个数
     * (3)任何整数都不能包含任何连续的1：
     * dp0[i]=dp1[i-1]+dp0[i-1] (二进制表示的数中0前面要么是1，要么是0)
     * dp1[i]=dp0[i-1]（二进制表示的数中1前面就只能是1）
     * (4) 进行最后的处理以找到小于或等于n的整数。
     * 1.由于要求的是<=n的整数个数，我们只对最高的有效位（数组最后一位）进行减操作
     * 2.如果这个num出现了2个连续的1，则剩下的<num的数他们的解必然<=num的解，则此um的解就是最优解！
     * 3.如果num中出现 0后面接着1，对解没有影响，1后面接着0，也是如此。
     * 4.如果num中出现 2个连续的0，对于第一个0的情况有“00”和“01”，
     * 此时只需要“00"的作为解，所以要减去"01"这种情况。
     */
    public int findIntegers(int num) {
        String str_num=toBinaryStr(num);

        int size = str_num.length();
        int[] dp0=new int[size];
        int[] dp1=new int[size];

        dp0[0] = 1;
        dp1[0] = 1;
        for(int i = 1; i < size; i++){
            dp0[i] = dp0[i - 1] + dp1[i - 1];
            dp1[i] = dp0[i - 1];
        }

        int cnt = dp0[size - 1] + dp1[size - 1];
        for (int i = size - 2; i >= 0; i--) {
            if (str_num.charAt(i) == '1' && str_num.charAt(i+1) == '1') {
                break;
            }
            if (str_num.charAt(i) == '0' && str_num.charAt(i+1) == '0') {
                cnt -= dp1[i];
            }
        }
        return cnt;
    }

    //得到该数的倒序的二进制字符串，这样方便后续操作
    private String toBinaryStr(int num){
       StringBuilder res=new StringBuilder();
       while(num>0){
           res.append(num%2+"");
           num/=2;
       }
       return res.toString();
    }

    @Test
    public void test(){
        System.out.println(findIntegers(5));
    }
}
