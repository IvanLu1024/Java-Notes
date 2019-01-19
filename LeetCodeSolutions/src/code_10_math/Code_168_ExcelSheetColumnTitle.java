package code_10_math;

import org.junit.Test;

/**
 * 168. Excel Sheet Column Title
 *
 * Given a positive integer, return its corresponding column title as appear in an Excel sheet.

 For example:

 1 -> A
 2 -> B
 3 -> C
 ...
 26 -> Z
 27 -> AA
 28 -> AB
 */
public class Code_168_ExcelSheetColumnTitle {
    /**
     * 思路一：
     * 实际上就是将n转化为26进制的数字
     */
    public String convertToTitle(int n) {
        String[] num_char = {
                "A","B","C","D","E","F","G","H","I","J","K","L","M",
                "N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
        };
        StringBuilder res=new StringBuilder();
        n--;
        while(n >= 0){
            res.append(num_char[n % 26]);
            n /=26;
            n--;
        }
        return res.reverse().toString();
    }

    /**
     * 思路二：递归写法
     */
    private String[] map = {
            "A","B","C","D","E","F","G","H","I","J","K","L","M",
            "N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
    };

    public String convertToTitle1(int n) {
        if(n==0){
            return "";
        }
        n--;
        return convertToTitle1(n/26) + map[n%26];
    }

    @Test
    public void test(){
        System.out.println(convertToTitle(701));
    }
}
