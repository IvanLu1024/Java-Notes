package code_01_array;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 438. Find All Anagrams in a String
 *
 * Given a string s and a non-empty string p, find all the start indices of p's anagrams in s.
 * Strings consists of lowercase English letters only and the length of both strings s and p will not be larger than 20,100.
 * The order of output does not matter.
 *
 * Example 1:
 * Input:
 * s: "cbaebabacd" p: "abc"
 * Output:
 * [0, 6]
 * Explanation:
 * The substring with start index = 0 is "cba", which is an anagram of "abc".
 * The substring with start index = 6 is "bac", which is an anagram of "abc".
 *
 * Example 2:
 * Input:
 * s: "abab" p: "ab"
 * Output:
 * [0, 1, 2]
 * Explanation:
 * The substring with start index = 0 is "ab", which is an anagram of "ab".
 * The substring with start index = 1 is "ba", which is an anagram of "ab".
 * The substring with start index = 2 is "ab", which is an anagram of "ab".
 */
public class Code_438_FindAllAnagramsInAString {
    //思路一：固定该滑动窗口大小，逐步平移该窗口
    public List<Integer> findAnagrams1(String s, String p) {
        List<Integer> ret=new ArrayList<>();
        if(s==null || s==""){
            return ret;
        }
        int l=0;
        int r=l+p.length()-1;
        while(l<=r && r<s.length()){
            if(isAnagram(s.substring(l,r+1),p)){
                ret.add(l);
            }
            l++;
            r++;
        }
        return ret;
    }

    //判断两个字符串是否是Anagram
    //先判断长度是否相同，不相同，直接返回false
    //统计 s1字符串中每个小写字母出现的频率，根据s2是否出现相同的字母以及出现的字母的频率是否相同
    public boolean isAnagram(String s1,String s2){
        if(s1.length()!=s2.length()){
            return false;
        }
        int[] freq=new int[26];
        //统计小写字母出现的频率
        for(int i=0;i<s1.length();i++){
            freq[s1.charAt(i)-'a']++;
        }
        for(int i=0;i<s1.length();i++){
            char curChar=s2.charAt(i);
            if(freq[curChar-'a']==0){
                //说明s2中不存在curChar字符
                return false;
            }
            //如果curChar，频率减1，表示匹配了curChar
            freq[curChar-'a']--;
        }
        return true;
    }

    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> ret = new ArrayList<>();
        if (s == null || s == "") {
            return ret;
        }
        //统计字符串p中出现的小写字符的频率
        int[] pFreq=new int[256];
        //count是p中的字符数
        int count=p.length();

        for(int i=0;i<count;i++){
            pFreq[p.charAt(i)]++;
        }
        int l=0,r=0;
        //[l,r)表示滑动窗口
        while(r<s.length()){
            if(pFreq[s.charAt(r++)]-->=1){
                //每次有一个p中字符进入窗口，扩展窗口，并且count–1
                count--;
            }
            if(count==0){
                //当count == 0的时候，表明我们的窗口中包含了p中的全部字符，得到一个结果。
                ret.add(l);
            }

            if (r-l == p.length()) {
                //当窗口包含一个结果以后，为了进一步遍历，我们需要缩小窗口使窗口不再包含全部的p，
                //同样，如果pFreq[char]>=0，表明一个在p中的字符就要从窗口移动到p字符串中，那么count ++
                if (pFreq[s.charAt(l++)]++ >= 0) {
                    count++;   // one more needed to match
                }
            }
        }
        return ret;
    }

    @Test
    public void test(){
        String s="cbaebabacd";
        String p="abc";
        //String s="abab";
        //String p="ab";

        //String s="acdcaeccde";
        //String p="c";
        List<Integer> list=findAnagrams(s,p);
        System.out.println(list);
    }
}
