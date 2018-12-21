package code_02_find;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 890. Find and Replace Pattern
 *
 * You have a list of words and a pattern, and you want to know which words in words matches the pattern.
 *
 * A word matches the pattern if there exists a permutation of letters p so that
 * after replacing every letter x in the pattern with p(x),we get the desired word.
 * (Recall that a permutation of letters is a bijection(双射) from letters to letters:
 * every letter maps to another letter, and no two letters map to the same letter.)
 * Return a list of the words in words that match the given pattern.
 * You may return the answer in any order.
 *
 * Example 1:
 Input: words = ["abc","deq","mee","aqq","dkd","ccc"], pattern = "abb"
 Output: ["mee","aqq"]
 Explanation: "mee" matches the pattern because there is a permutation {a -> m, b -> e, ...}.
 "ccc" does not match the pattern because {a -> c, b -> c, ...} is not a permutation,
 since a and b map to the same letter.
 */
public class Code_890_FindAndReplacePattern {
    public List<String> findAndReplacePattern(String[] words, String pattern) {
        List<String> res=new ArrayList<>();
        for(String word:words){
            if(ok(word,pattern)){
                res.add(word);
            }
        }
        return res;
    }

   /* private boolean ok(String word,String pattern){
        if(word.length()!=pattern.length()){
            return false;
        }
        //用于存储映射关系的Map
        Map<Character, Character> map = new HashMap<>();
        for(int i=0;i<word.length();i++){
            char p=pattern.charAt(i);
            char w=word.charAt(i);
            if(map.containsKey(p)){
                //pattern中的字母已经被映射，这时候只要判断对应的word中是否有对应的字母
                char value=map.get(p);
                if(w!=value){
                    return false;
                }
            }else{
                //判断单词中的字母是否被映射，防止模式多个字母都映射单词中的同一个字母
                if(map.containsValue(w)){
                   return false;
                }else {
                    map.put(p, w);
                }
            }
        }
        return true;
    }*/

    private boolean ok(String word, String pattern) {
        if(word.length()!=pattern.length()){
            return false;
        }

        //记录word中字符和pattern中字符的映射关系
        Map<Character, Character> wordMap = new HashMap();
        //记录pattern中字符和word中字符的映射关系
        Map<Character, Character> patternMap = new HashMap();
        //如果匹配，则 wordMap、patternMap中映射关系都要全部满足

        for(int i = 0; i < word.length(); i ++) {
            char c = word.charAt(i);
            char p = pattern.charAt(i);

            if(wordMap.containsKey(c)) {
                if(p != wordMap.get(c)) {
                    return false;
                }
            } else {
                wordMap.put(c, p);
            }
            if(patternMap.containsKey(p)) {
                if(c != patternMap.get(p)) {
                    return false;
                }
            } else {
                patternMap.put(p, c);
            }
        }
        return true;
    }

    @Test
    public void test(){
        String[] words={"abc","deq","mee","aqq","dkd","ccc"};
        String pattern="abb";
        System.out.println(findAndReplacePattern(words,pattern));
    }
}
