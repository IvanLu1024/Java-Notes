package code_09_string;

import org.junit.Test;

import java.util.HashSet;

/**
 * 409. Longest Palindrome
 *
 * Given a string which consists of lowercase or uppercase letters,
 * find the length of the longest palindromes that can be built with those letters.
 * This is case sensitive, for example "Aa" is not considered a palindrome here.
 *
 * Note:
 * Assume the length of given string will not exceed 1,010.
 *
 * Example:
 Input:
 "abccccdd"

 Output:
 7

 Explanation:
 * One longest palindrome that can be built is "dccaccd", whose length is 7.
 */
public class Code_409_LongestPalindrome {
    /**
     * 思路：
     * 将字符串转变为字符数组。使用count统计字符频率，
     * 然后遍历该数组，判断对应字符是否在HashSet中，
     * 如果不在就加进去，如果在就让count++，然后移除该字符！
     * 这样就能找到出现次数为偶数的字符个数。
     */
    public int longestPalindrome(String s) {
        if(s.length()==0){
            return 0;
        }
        char[] chs=s.toCharArray();

        //维护一个set集合，用于统计字符出现次数为偶数的个数
        HashSet<Character> set=new HashSet<>();
        //统计字符出现次数为偶数的个数,比如 “bbaaass”，我们这样认为：'b'出现次数为2、'a'出现次数为2，‘s’出现次数为2，则此时count=3
        int count=0;

        for(int i=0;i<chs.length;i++){
            if(!set.contains(chs[i])){
                set.add(chs[i]);
            }else{
                set.remove(chs[i]);
                count++;
            }
        }
        return set.isEmpty()? (2*count):(2*count+1);
    }

    @Test
    public void test(){
        System.out.println(longestPalindrome("abccccdd"));
    }
}
