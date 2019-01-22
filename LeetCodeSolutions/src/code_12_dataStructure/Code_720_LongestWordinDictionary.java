package code_12_dataStructure;

import org.junit.Test;

import java.util.*;

/**
 * 720. Longest Word in Dictionary
 *
 * Given a list of strings words representing an English Dictionary,
 * find the longest word in words that can be built one character at a time by other words in words.
 * If there is more than one possible answer,
 * return the longest word with the smallest lexicographical order.
 * If there is no answer, return the empty string.
 */
public class Code_720_LongestWordinDictionary {
    /**
     * 思路：
     * 题目要求找到字典排序的一个字符串。
     * 这样的字符串可以利用其中的部分字符组成比他短的其他字符串。
     */
    public String longestWord(String[] words) {
        TreeSet<String> set=new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                //如果调用compare方法大于0，就把前一个数和后一个数交换(升序)
                int num =s2.length() - s1.length();
                int num2 = (num == 0) ? s1.compareTo(s2) : num;
                return num2;
            }
        });

        for(String word:words){
            set.add(word);
        }
        System.out.println("set:"+set);
        for(String word :words){
           if(contains(set,word)){
               return word;
           }
        }
        return "";
    }

    private boolean contains(TreeSet<String> set,String word){
        for(int index = word.length(); index > 0; index--){
            System.out.println("word:"+word.substring(0,index));
            if(!set.contains(word.substring(0,index))){
                return false;
            }
        }
        return true;
    }

    @Test
    public void test(){
        String[] str={"w","wo","wor","worl","world"};
        longestWord(str);
    }
}
