package code_13_others;

import org.junit.Test;

/**
 * 880. Decoded String at Index
 *
 * An encoded string S is given.
 * To find and write the decoded string to a tape,
 * the encoded string is read one character at a time and the following steps are taken:
    * If the character read is a letter, that letter is written onto the tape.
    * If the character read is a digit (say d), the entire current tape is repeatedly written d-1 more times in total.
 * Now for some encoded string S, and an index K, find and return the K-th letter (1 indexed) in the decoded string.
 *
 * Example 1:
 Input: S = "leet2code3", K = 10
 Output: "o"
 Explanation:
 The decoded string is "leetleetcodeleetleetcodeleetleetcode".
 The 10th letter in the string is "o".

 * Example 2:
 Input: S = "ha22", K = 5
 Output: "h"
 Explanation:
 The decoded string is "hahahaha".  The 5th letter is "h".

 * Example 3:
 Input: S = "a2345678999999999999999", K = 1
 Output: "a"
 Explanation:
 The decoded string is "a" repeated 8301530446056247680 times.  The 1st letter is "a".
 */
public class Code_880_DecodedStringAtIndex {
    /**
     * 思路：逆向思考
     * 1. 如果我们有一个像 apple apple apple apple apple apple 这样的解码字符串
     * 和 K=24这样的索引，那么如果 K=4，答案是相同的。
     *
     * 2. 一般来说，当解码的字符串等于某个长度为 size 的单词重复某些次数
     * （例如 apple与size=5组合重复6次）时，索引 K的答案与索引 K % size的答案相同。
     *
     * 3. 我们可以通过逆向工作，跟踪解码字符串的大小来使用这种洞察力。
     * 每当解码的字符串等于某些单词 word 重复 d次时，我们就可以将 K 减少到 K % (Word.Length)。
     */
    public String decodeAtIndex(String S, int K) {
        //记录得到的新的字符串的长度
        long size = 0;

        for(int i=0;i<S.length();i++){
            char c = S.charAt(i);
            if(Character.isDigit(c)){
                size *= (c - '0');
            }else{
                size ++;
            }
        }

        //逆向
        for(int i=S.length()-1;i>=0;i--){
            char c = S.charAt(i);
            K %= size;
            if(K==0 && Character.isLetter(c)){
                // K 能被size整除
                return Character.toString(c);
            }
            if(Character.isDigit(c)){
                size /= (c-'0');
            }else{
                size --;
            }

        }
        return null;
    }

    @Test
    public void test(){
        //String S = "ha22";
        //int K = 5;
        String S = "leet2code3";
        int K = 10 ;
        System.out.println(decodeAtIndex(S,K));
    }
}
