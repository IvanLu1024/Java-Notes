package code_09_string;

import org.junit.Test;

/**
 * 5. Longest Palindromic Substring
 *
 * Given a string s, find the longest palindromic substring in s.
 * You may assume that the maximum length of s is 1000.
 *
 * Example 1:
 Input: "babad"
 Output: "bab"
 Note: "aba" is also a valid answer.

 * Example 2:
 Input: "cbbd"
 Output: "bb"
 */
public class Code_5_LongestPalindromicSubstring {
    /**
     * 思路：中心扩展法
     * 中心扩展就是把给定的字符串的每一个字母当做中心，
     * 向两边扩展，这样来找最长的子回文串。算法复杂度为O(N^2)。
     * 但是要考虑两种情况：
     * （1）像aba，这样长度为奇数。
     * （2）想abba，这样长度为偶数。
     */
    public String longestPalindrome(String s) {
        if(s.length()< 2){
            return s;
        }

        int len=s.length();
        int maxLen=0;

        //最长回文串的起始位置和结束位置
        int startIndex=0;
        int endIndex=0;

        //类似于aba这种情况，以i为中心向两边扩展
        for(int i=1;i<len;i++){
            //以i为中心向两边扩展
            int l=i-1;
            int r=i+1;
            while((l>=0 && r<len) && s.charAt(l)==s.charAt(r)){
                int tmpLen=r-l+1;
                if(tmpLen>maxLen){
                    maxLen=tmpLen;
                    startIndex=l;
                    endIndex=r;
                }
                l--;
                r++;
            }
        }

        //类似于abba这种情况，以i，i+1为中心向两边扩展
        for(int i=0;i<len;i++){
            int l=i;
            int r=i+1;
            while((l>=0 && r<len) && s.charAt(l)==s.charAt(r)){
                int tmpLen=r-l+1;
                if(tmpLen>maxLen){
                    maxLen=tmpLen;
                    startIndex=l;
                    endIndex=r;
                }
                l--;
                r++;
            }
        }
        return s.substring(startIndex,endIndex+1);
    }

    @Test
    public void test(){
        System.out.println(longestPalindrome("babad"));
    }
}
