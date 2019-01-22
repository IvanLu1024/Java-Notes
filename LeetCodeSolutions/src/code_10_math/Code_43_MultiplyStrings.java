package code_10_math;

import org.junit.Test;

import java.lang.annotation.Target;

/**
 * 43. Multiply Strings
 *
 * Given two non-negative integers num1 and num2 represented as strings,
 * return the product of num1 and num2, also represented as a string.
 *
 * Example 1:
 * Input: num1 = "2", num2 = "3"
 * Output: "6"
 *
 * Example 2:
 * Input: num1 = "123", num2 = "456"
 * Output: "56088"
 *
 *
 * Note:
 * The length of both num1 and num2 is < 110.
 * Both num1 and num2 contain only digits 0-9.
 * Both num1 and num2 do not contain any leading zero, except the number 0 itself.
 * You must not use any built-in BigInteger library or convert the inputs to integer directly.
 */
public class Code_43_MultiplyStrings {
    /**
     * 思路：
     * 根据数字乘法的计算规则，从一个数个位开始依次求出与另一个数的乘积并逐位相加。
     */
    public String multiply(String num1, String num2) {
        //num1或者num2中有一个为0,相乘结果就为0
        if("0".equals(num1) || "0".equals(num2)){
            return "0";
        }
        int m = num1.length();
        int n = num2.length();

        int[] pos=new int[m+n];

        for(int i=m-1;i>=0;i--){
            for(int j=n-1;j>=0;j--){
                int mul = (num1.charAt(i)-'0') * (num2.charAt(j)-'0');

                //p1和p2始终是相邻的
                int p1 = i + j;
                int p2 = i + j + 1;

                // nul + pos[p2]的最大数值是81+9=90，
                // 每次只需要 p1和p2存储相应数据就行了。
                int sum = mul + pos[p2];
                pos[p1] += sum / 10;
                pos[p2] = sum % 10;
            }
        }

        StringBuilder res = new StringBuilder();
        for(int num : pos){
            //从以一个不是0的num开始存入数据
            if(!(res.length()==0 && num==0)){
                res.append(num);
            }
        }
        return res.length()==0 ? "0" : res.toString();
    }

    @Test
    public void test(){
        String num1 = "99";
        String num2 = "99";
        System.out.println(multiply(num1,num2));
    }
}
