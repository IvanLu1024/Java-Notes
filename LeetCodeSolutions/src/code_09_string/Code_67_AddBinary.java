package code_09_string;

import org.junit.Test;

/**
 * 67. Add Binary
 *
 * Given two binary strings, return their sum (also a binary string).
 * The input strings are both non-empty and contains only characters 1 or 0.
 *
 * Example 1:
 Input: a = "11", b = "1"
 Output: "100"

 * Example 2:
 Input: a = "1010", b = "1011"
 Output: "10101"
 */
public class Code_67_AddBinary {
    /**
     * 思路：从后向前相加
     */
    public String addBinary(String a, String b) {
        StringBuilder res=new StringBuilder();
        int indexA=a.length()-1;
        int indexB=b.length()-1;
        int c = 0;
        //进位初始化为0
        while(indexA>=0 || indexB>=0 || c==1){
            //这里这样写是为了有数的位数不够比如 11 + 1 -->转换成 11 + 01
            c+=(indexA>=0?a.charAt(indexA)-'0':0);
            c+=(indexB>=0?b.charAt(indexB)-'0':0);
            res.insert(0,c%2);
            //下一步循环的进位
            c/=2;
            indexA--;
            indexB--;
        }
        return res.toString();
    }

    @Test
    public void test(){
        String s="1010";
        String t="1011";
        System.out.println(addBinary(s,t));
    }
}
