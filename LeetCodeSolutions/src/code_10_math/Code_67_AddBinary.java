package code_10_math;

import org.junit.Test;

/**
 * 67. Add Binary
 *
 * Given two binary strings, return their sum (also a binary string).
 * The input strings are both non-empty and contains only characters 1 or 0.

 Example 1:
 Input: a = "11", b = "1"
 Output: "100"
 */
public class Code_67_AddBinary {
    /**
     * 思路:从后向前相加
     */
    public String addBinary(String a, String b) {
        int i=a.length()-1;
        int j=b.length()-1;

        //a,b上各个二进制位相加所得的结果
        int c=0;

        StringBuilder res=new StringBuilder();
        //c==1 是考虑像 Example 1 这种情况
        while(i>=0 || j>=0 || c==1) {
            //a = "11", b = "1" 实际上就变成 a = "11",b = "01"
            c += (i >= 0) ? a.charAt(i) - '0' : 0;
            c += (j >= 0) ? b.charAt(j) - '0' : 0;
            res.append(c%2);
            c /= 2;
            i--;
            j--;
        }
        return res.reverse().toString();
    }

    @Test
    public void test(){
        System.out.println(addBinary("11","1"));
    }
}
