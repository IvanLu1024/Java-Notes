package code_09_string;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 792. Number of Matching Subsequences
 * Given string S and a dictionary of words words,
 * find the number of words[i] that is a subsequence of S.
 *
 * Example :
 Input:
 S = "abcde"
 words = ["a", "bb", "acd", "ace"]
 Output: 3
 Explanation: There are three words in words that are a subsequence of S: "a", "acd", "ace".
 * Given string S and a dictionary of words words, find the number of words[i] that is a subsequence of S.
 */
public class Code_792_NumberOfMatchingSubsequences {
    /**
     * 思路：
     * 对words中的每一个word检查是否为S的一个子序列。
     * 但是如果每次检查都要扫描一遍S的话时间复杂度太高，所以就想到把S中字母的位置存储起来提高效率。
     *
     * 扫描一遍字符串S并建立一个字典，
     * 记录每一个字母出现的位置并按照升序排列。
     * 对每一个word，遍历word并查找字典中当前字母的出现位置并保持其相对位置不变。
     */
    public int numMatchingSubseq(String S, String[] words) {
        //建立字典-->将S中字母的位置存储起来提高效率
        List<Integer>[] pos=new ArrayList[26];
        for(int i=0;i<26;i++){
            pos[i]=new ArrayList<>();
        }

        //扫描一遍字符串S并建立一个字典，记录每一个字母出现的位置并按照升序排列。
        for(int i=0;i<S.length();i++){
            char c=S.charAt(i);
            pos[c-'a'].add(i);
        }

        int res=0;
        for(int i=0;i<words.length;i++){
            // 对每一个word，遍历word并查找字典中当前字母的出现位置并保持其相对位置不变。
            String word=words[i];
            //j是在具体的s中字母下标
            int j=0;
            //当前字母位置
            int cur=-1;
            while(j<word.length()){
                int k = 0;
                while (k != pos[word.charAt(j) - 'a'].size()) {
                    // keep the relative positions by greedy
                    if (pos[word.charAt(j) - 'a'].get(k) > cur) {
                        cur = pos[word.charAt(j) - 'a'].get(k);
                        break;
                    }
                    k++;
                }
                // when there is no match, break to prune
                if (k == pos[word.charAt(j) - 'a'].size()) {
                    break;
                }
                j++;
            }
            if(j==word.length()){
                res++;
            }
        }
        return res;
    }

    @Test
    public void test(){
        String S = "abcde";
        String[] words = {"a", "bb", "acd", "ace"};
        System.out.println(numMatchingSubseq(S,words));
    }
}
