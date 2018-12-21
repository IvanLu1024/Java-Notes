package code_07_dp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 139. Word Break
 * Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be segmented into a space-separated sequence of one or more dictionary words.
 * 给定词典的情况下，看看原非空字符串能不能全部成功地被给定的词典分割。
 *
 * Note:
 TODO:The same word in the dictionary may be reused multiple times in the segmentation.
 You may assume the dictionary does not contain duplicate words.

 * Example 1:
 Input: s = "leetcode", wordDict = ["leet", "code"]
 Output: true
 Explanation: Return true because "leetcode" can be segmented as "leet code".

 * Example 2:
 Input: s = "applepenapple", wordDict = ["apple", "pen"]
 Output: true
 Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
 Note that you are allowed to reuse a dictionary word.

 * Example 3:
 Input: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 Output: false
 */
public class Code_139_WordBreak {
    /**
     * 思路：
     * dp[i]表示字符串s[0...i)能否拆分成符合要求的子字符串。
     * 我们可以看出，如果s[j...i)在给定的字符串组中，且dp[j]为True（即字符串s[0...j)能够拆分成符合要求的子字符串），那么此时dp[i]也就为True了。
     * 状态转移方程：
     * dp[0]=true;
     * dp[i]=dp[j] && DICT（s[j...i)）,0<=j<i
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        int n=s.length();
        boolean[] memo=new boolean[n+1];
        memo[0]=true;
        for (int i = 1; i <= n; i++){
            for (int j = 0; j < i; j++) {
                // 注意substring是前闭后开
                String tmp = s.substring(j, i);
                if (memo[j] && wordDict.contains(tmp)) {
                    memo[i] = true;
                    break;
                }
            }
        }
        return memo[n];
    }

    @Test
    public void test(){
        //String s="catsandog";
        String s="applepenapple";
        List<String> wordDict=new ArrayList<>();
       /* wordDict.add("cats");
        wordDict.add("dog");
        wordDict.add("sand");
        wordDict.add("and");
        wordDict.add("cat");*/
        wordDict.add("apple");
        wordDict.add("pen");
        System.out.println(wordBreak(s,wordDict));
    }
}
