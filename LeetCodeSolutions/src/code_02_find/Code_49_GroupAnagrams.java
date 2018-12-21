package code_02_find;


import java.util.*;

/**
 * 49. Group Anagrams
 *
 * Given an array of strings, group anagrams together.
 *  Example:
 *  Input: ["eat", "tea", "tan", "ate", "nat", "bat"],
 *  Output:
 * [
 * ["ate","eat","tea"],
 * ["nat","tan"],
 * ["bat"]
 * ]
 *
 * Note:
 * All inputs will be in lowercase.
 * The order of your output does not matter.
 */
public class Code_49_GroupAnagrams {
    public ArrayList groupAnagrams(String[] strs) {
        Map<String, ArrayList<Object>> map=new HashMap<>();
        //map存储的是<字符串的字母按照字母排序后得到的字符串,与之相应的anagram>
        for(int i=0;i<strs.length;i++){
            char[] chs=strs[i].toCharArray();
            Arrays.sort(chs);
            //所有anagram排序后，得到的字符串是一致的
            String sortStr=new String(chs);
            if(!map.containsKey(sortStr)){
                map.put(sortStr,new ArrayList<>());
            }
            map.get(sortStr).add(strs[i]);
        }
        return new ArrayList<>(map.values());
    }
}
