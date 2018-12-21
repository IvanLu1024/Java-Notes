package code_09_string;

import org.junit.Test;

/**
 * 443. String Compression
 *
 * Given an array of characters, compress it in-place.(给定一组字符，就地压缩它)
 * The length after compression must always be smaller than or equal to the original array.
 * Every element of the array should be a character (not int) of length 1.
 * After you are done modifying the input array in-place, return the new length of the array.
 *
 * Follow up:
 * Could you solve it using only O(1) extra space?
 *
 * Example 1:
 Input:
 ["a","a","b","b","c","c","c"]
 Output:
 Return 6, and the first 6 characters of the input array should be: ["a","2","b","2","c","3"]

 * Example 2:
 Input:
 ["a"]
 Output:
 Return 1, and the first 1 characters of the input array should be: ["a"]

 * Example 3:
 Input:
 ["a","b","b","b","b","b","b","b","b","b","b","b","b"]
 Output:
 Return 4, and the first 4 characters of the input array should be: ["a","b","1","2"].

 Explanation:
 Since the character "a" does not repeat, it is not compressed. "bbbbbbbbbbbb" is replaced by "b12".
 Notice each digit has it's own entry in the array.

 * Note:
 All characters have an ASCII value in [35, 126].
 1 <= len(chars) <= 1000.
 */
public class Code_443_StringCompression {
    /**
     * 思路：
     * 指针i指向修改内容的位置，指针j遍历整个数组chars，
     * 当下一个字符与当前字符不相同时，直接将该字符赋值到i处，然后i++，j++；
     * 否则若相同，k指向j所在位置，j继续向前出发遍历所有与k处相同的字符，则相同的个数为j-k，
     * 将j-k转化为字符串s，
     * 将s的每一个字符都赋值在i所在位置开始的chars中～最后直到j>=n时退出循环，此时i的值即为in-place后新数组中的个数
     */
    public int compress(char[] chars) {
        int i = 0, j = 0;
        int n=chars.length;
        while (j<n) {
            if (j == n - 1 || chars[j] != chars[j + 1]) {
                chars[i++] = chars[j++];
            } else {
                chars[i++] = chars[j];
                int k = j;
                while (j < n && chars[j] == chars[k]){
                    j++;
                }
                String s = (j - k)+"";
                for(int m=0;m<s.length();m++){
                    chars[i++]=s.charAt(m);
                }
            }
        }
        return i;
    }

    @Test
    public void test(){
        //char[] chs={'a','b','b','b','b','b','b','b','b','b','b','b','b'};
        char[] chs={'a','a','b','b','c','c','c'};
        System.out.println(compress(chs));
    }
}
