package code_10_math;

/**
 *
 * Given an integer,write an algorithm to convert it to hexadecimal.
 * For negative integer, two’s complement method is used.
 *
 * Note:
 All letters in hexadecimal (a-f) must be in lowercase.
 The hexadecimal string must not contain extra leading 0s. If the number is zero, it is represented by a single zero character '0'; otherwise, the first character in the hexadecimal string will not be the zero character.
 The given number is guaranteed to fit within the range of a 32-bit signed integer.
 You must not use any method provided by the library which converts/formats the number to hex directly.
 */
public class Code_405_ConvertANumberToHexadecimal {
    /**
     * 思路：
     * 本质上就是获取10进制的数的补码
     */
    public String toHex(int num) {
        if(num==0){
            return "0";
        }
        char[] map = {
                '0','1','2','3','4','5','6','7',
                '8','9', 'a','b','c','d','e','f'
        };

        StringBuilder res=new StringBuilder();
        while (num != 0){
            //num & 0b1111 每次获取 num 的4位的补码
            res.append( map [num & 0b1111]);
            //无符号右移四位
            num = num >>> 4;
        }
        return res.reverse().toString();
    }
}
