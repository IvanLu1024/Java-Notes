package code_09_string;

import org.junit.Test;

/**
 * 806. Number of Lines To Write String
 *
 * We are to write the letters of a given string S, from left to right into lines.
 * Each line has maximum width 100 units, and if writing a letter would cause the width of the line to exceed 100 units,
 * it is written on the next line.
 *
 * We are given an array widths,
 * an array where widths[0] is the width of 'a', widths[1] is the width of 'b', ..., and widths[25] is the width of 'z'.
 * Now answer two questions:
 * how many lines have at least one character from S,
 * and what is the width used by the last such line? Return your answer as an integer list of length 2.

 * Example :
 Input:
 widths = [10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10]
 S = "abcdefghijklmnopqrstuvwxyz"
 Output: [3, 60]
 Explanation:
 All letters have the same length of 10. To write all 26 letters,
 we need two full lines and one line with 60 units.
 */
public class Code_806_NumberofLinesToWriteString {
    /**
     * 思路：
     * 一次遍历，
     * sum作为每一行被字母占据后的长度
     * line统计行数
     */
    public int[] numberOfLines(int[] widths, String S) {
        int sum=0;
        int line=1;
        for(int i=0;i<S.length();i++){
            sum+= widths[S.charAt(i)-'a'];
            if(sum==100) {//刚好一行
                line++;
                sum = 0;
            }else if(sum>100){ //加多了，也就是widths[S.charAt(i)-'a']不能加在这一行
                i--;
                line++;
                sum=0;
            }
        }
        return new int[]{line,sum};
    }

    @Test
    public void test(){
        //int[] arr={4,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10};
        int[] arr={10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10};
        //String S="bbbcccdddaaa";
        String S="abcdefghijklmnopqrstuvwxyz";
        int[] nums=numberOfLines(arr,S);
        for(int num:nums){
            System.out.println(num);
        }
    }
}
