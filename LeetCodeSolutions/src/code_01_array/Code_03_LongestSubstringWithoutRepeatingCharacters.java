package code_01_array;

/**
 * 3 Longest Substring Without Repeating Characters
 *
 * Given a string, find the length of the longest substring without repeating characters.
 *
 * Example 1:
 * Input: "abcabcbb"
 *Output: 3
 *Explanation: The answer is "abc", with the length of 3.
 *
 * Example 2:
 * Input: "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.

 * Example 3:
 * Input: "pwwkew"
 * Output: 3
 */
public class Code_03_LongestSubstringWithoutRepeatingCharacters {
    public int lengthOfLongestSubstring(String s) {
        int n=s.length();

        int[] freq=new int[256];
        //记录每个字符出现的频率，用于判断滑动窗口中是否有重复元素
        int l=0,r=-1;
        //[l,r]为滑动窗口，开始时，没有元素
        int ret=0;
        //记录最长子段
        while(l<n){
            if(r+1<n && freq[s.charAt(r+1)]==0){
                //没有重复元素，扩展窗口
                r++;
                freq[s.charAt(r)]++;
            }else{
                //缩小窗口
                freq[s.charAt(l)]--;
                l++;
            }
            ret=Math.max(ret,r-l+1);
        }
        return ret;
    }
}
