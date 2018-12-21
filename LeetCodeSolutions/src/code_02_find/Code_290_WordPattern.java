package code_02_find;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 290 Word Pattern
 * Given a pattern and a string str, find if str follows the same pattern.
 * Here follow means a full match, such that there is a bijection(映射) between a letter in pattern and a non-empty word in str.
 *
 * Example 1:
 * Input: pattern = "abba", str = "dog cat cat dog"
 * Output: true
 *
 * Example 2:
 * Input:pattern = "abba", str = "dog cat cat fish"
 * Output: false
 *
 * Example 3:
 * Input: pattern = "aaaa", str = "dog cat cat dog"
 * Output: false
 *
 * Example 4:
 * Input: pattern = "abba", str = "dog dog dog dog"
 * Output: false
 */
public class Code_290_WordPattern {
    public boolean wordPattern(String pattern, String str) {
        //维护一个hash表，key为pattern中的字符，value为被切割的字符串
        Map<Character,String> map=new HashMap<>();
        //题目要求str是非空的
        String[] strs=null;
        if(str!=null){
            strs = str.split(" ");
        }
        if(pattern.length() != strs.length){
            return false;
        }

        for(int i=0;i<pattern.length();i++){
            char c=pattern.charAt(i);
            if(map.containsKey(c)){
                String v=map.get(c);
                if(!v.equals(strs[i])){
                    return false;
                }
            }else{
                if(map.containsValue(strs[i])){
                    return false;
                }
                map.put(c,strs[i]);
            }
        }
        return true;
    }
}
