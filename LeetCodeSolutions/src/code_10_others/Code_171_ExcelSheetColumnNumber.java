package code_10_others;

import org.junit.Test;

/**
 * 171. Excel Sheet Column Number
 *
 * Given a column title as appear in an Excel sheet, return its corresponding column number.
 *
 * For example:
 A -> 1
 B -> 2
 C -> 3
 ...
 Z -> 26
 AA -> 27
 AB -> 28
 ...

 * Example 1:
 Input: "A"
 Output: 1

 * Example 2:

 Input: "AB"
 Output: 28

 * Example 3:
 Input: "ZY"
 Output: 701
 */
public class Code_171_ExcelSheetColumnNumber {
    /**
     * 思路：
     * 看成二十六进制就好了
     */
    public int titleToNumber(String s) {
        // 'A'就对应1
        // 'B'就对应2
        char[] arr = {
                1,2,3,4,5,6,7,
                8,9,10,11,12,13,14,
                15,16,17,18,19,20,21,
                22,23,24,25,26
        };

        int res=0;
        for(int i=s.length()-1;i>=0;i--){
            char c=s.charAt(i);
            res+= arr[c-'A']*Math.pow((int)26,(int)(s.length()-1-i));
        }
        return res;
    }

    @Test
    public void test(){
        System.out.println(titleToNumber("A"));
        System.out.println(titleToNumber("ZY"));
    }
}
