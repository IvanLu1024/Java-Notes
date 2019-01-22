package code_10_math;

import org.junit.Test;

/**
 * 415. Add Strings
 *
 * Given two non-negative integers num1 and num2 represented as string,
 * return the sum of num1 and num2.
 *
 * Note:
 * The length of both num1 and num2 is < 5100.
 * Both num1 and num2 contains only digits 0-9.Both num1 and num2 does not contain any leading zero.
 * You must not use any built-in BigInteger library or convert the inputs to integer directly.
 */
public class Code_415_AddStrings {
    public String addStrings(String num1, String num2) {
        int i=num1.length()-1;
        int j=num2.length()-1;

        int c=0;

        StringBuilder res=new StringBuilder();
        while(i>=0 || j>=0 || c==1){
            c += (i>=0) ? num1.charAt(i)-'0' : 0;
            c += (j>=0) ? num2.charAt(j)-'0' : 0;
            res.append(c%10);
            c /= 10;
            i--;
            j--;
        }
        return res.reverse().toString();
    }

    @Test
    public void test(){
        System.out.println(addStrings("98","9"));
    }
}
