package code_10_math;

import org.junit.Test;

/**
 * 504 Base 7
 *
 * Given an integer, return its base 7 string representation.
 Example 1:
 Input: 100
 Output: "202"
 Example 2:
 Input: -7
 Output: "-10"
 */
public class Code_504_Base7 {
    public String convertToBase71(int num) {
        if(num==0){
            return "0";
        }
        StringBuilder res=new StringBuilder();

        //判断num是否是正数
        boolean isNegative=false;
        if(num<0){
            num=-num;
            isNegative=true;
        }

        while(num!=0){
            res.append(num % 7);
            num /=7;
        }
        String ret=res.reverse().toString();
        return isNegative? ("-"+ret): ret;
    }
}
