package code_09_string;

import org.junit.Test;

/**
 * 38. Count and Say
 *
 * The count-and-say sequence is the sequence of integers with the first five terms as following:
 1.     1
 2.     11
 3.     21
 4.     1211
 5.     111221
 1 is read off as "one 1" or 11.
 11 is read off as "two 1s" or 21.
 21 is read off as "one 2, then one 1" or 1211.
 (
 1 读作“1个1”或11.
 11 读作“2个1”或21.
 21 读作“1个2，1个1”或1211.
 )

 Given an integer n where 1 ≤ n ≤ 30, generate the nth term of the count-and-say sequence.

 Note: Each term of the sequence of integers will be represented as a string.

 * Example 1:
 Input: 1
 Output: "1"

 * Example 2:
 Input: 4
 Output: "1211"
 */
public class Code_38_CountAndSay {
    /**
     * 思路
     */
    public String countAndSay(int n) {
        String s = "1";
        if(n == 1)
            return s;

        for(int i = 2; i <= n; i ++){
            s = next(s);
        }
        return s;
    }

    private  String next(String s){
        StringBuilder ret = new StringBuilder();
        int start = 0;
        for(int i = start + 1; i <= s.length(); i ++)
            if(i == s.length() || s.charAt(i) != s.charAt(start)){
                ret.append(i - start).append(s.charAt(start));
                start = i;
            }
        return ret.toString();
    }

    @Test
    public void test(){
        System.out.println(countAndSay(5));
    }
}
