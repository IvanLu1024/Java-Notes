package code_02_find;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 819. Most Common Word
 *
 * Given a paragraph and a list of banned words,
 * return the most frequent word that is not in the list of banned words.
 * It is guaranteed there is at least one word that isn't banned, and that the answer is unique.
 * Words in the list of banned words are given in lowercase, and free of punctuation.
 * Words in the paragraph are not case sensitive.  The answer is in lowercase.
 *
 * Example:
 Input:
 paragraph = "Bob hit a ball, the hit BALL flew far after it was hit."
 banned = ["hit"]
 Output: "ball"
 Explanation:
 "hit" occurs 3 times, but it is a banned word.
 "ball" occurs twice (and no other word does), so it is the most frequent non-banned word in the paragraph.
 Note that words in the paragraph are not case sensitive,
 that punctuation is ignored (even if adjacent to words, such as "ball,"),
 and that "hit" isn't the answer even though it occurs more because it is banned.

 * Note:
 1 <= paragraph.length <= 1000.
 1 <= banned.length <= 100.
 1 <= banned[i].length <= 10.
 The answer is unique, and written in lowercase (even if its occurrences in paragraph may have uppercase symbols, and even if it is a proper noun.)
 paragraph only consists of letters, spaces, or the punctuation symbols !?',;.
 There are no hyphens(连字符) or hyphenated words(连词).
 Words only consist of letters, never apostrophes or other punctuation symbols.
 */
public class Code_819_MostCommonWord {
    public String mostCommonWord(String paragraph, String[] banned) {
        //存储--禁止单词，方便检查paragraph中是否有“禁止单词”
        Set<String> bannedSet=new HashSet<>();
        for(String word:banned){
            bannedSet.add(word);
        }

        //存储单词和单词出现的频率
        Map<String,Integer> map=new HashMap<>();
        int start=getFirstLetter(paragraph,0);
        for(int i=start+1;i<=paragraph.length();){
            if(i==paragraph.length() || !Character.isLetter(paragraph.charAt(i))){
                //!Character.isLetter(paragraph.charAt(i) i位置不是字符就要截取
                String word=paragraph.substring(start,i);
                //将该单词中字母都转化为小写
                word=word.toLowerCase();
                if(!bannedSet.contains(word)){ //word不能是“禁止单词”
                    int freq=map.getOrDefault(word,0);
                    map.put(word,++freq);
                }
                start=getFirstLetter(paragraph,i+1);
                i=start+1;
            }else{
                i++;
            }
        }
        String res="";
        int maxFreq=0;
        for(String word:map.keySet()){
            if(map.get(word)>maxFreq){
                res=word;
                maxFreq=map.get(word);
            }
        }
        return res;
    }

    //获取字符串s的首字母的下标
    private int getFirstLetter(String s,int start){
        for(int i=start;i<s.length();i++){
            if(Character.isLetter(s.charAt(i))){
                return i;
            }
        }
        //没有字母，则返回该字符串长度
        return s.length();
    }

    @Test
    public void test(){
        //String pars="a, a, a, a, b,b,b,c, c";
        String pars="Bob hit a ball, the hit BALL flew far after it was hit.";
        //String[] b={"a"};
        String[] b={"hit"};
        System.out.println(mostCommonWord(pars,b));
    }
}
