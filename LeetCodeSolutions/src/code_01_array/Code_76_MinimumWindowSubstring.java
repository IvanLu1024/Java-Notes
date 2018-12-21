package code_01_array;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 76. Minimum Window Substring
 *
 * Given a string S and a string T,
 * find the minimum window in S which will contain all the characters in T in complexity O(n).
 *
 * Example:
 * Input: S = "ADOBECODEBANC", T = "ABC"
 * Output: "BANC"

 * Note:
 * If there is no such window in S that covers all characters in T, return the empty string "".
 * If there is such window, you are guaranteed that there will always be only one unique minimum window in S.
 */
public class Code_76_MinimumWindowSubstring {
    /**
     * 思路：
     * 我们可以考虑哈希表，其中key是T中的字符，value是该字符出现的次数。

     - 我们最开始先扫描一遍T，把对应的字符及其出现的次数存到哈希表中。

     - 然后开始遍历S，遇到T中的字符，就把对应的哈希表中的value减一，
     直到包含了T中的所有的字符，纪录一个字串并更新最小字串值。

     - 将子窗口的左边界向右移，略掉不在T中的字符，
     如果某个在T中的字符出现的次数大于哈希表中的value，则也可以跳过该字符。
     */
    public String minWindow(String s, String t) {
        if(s.length()<t.length()){
            return "";
        }

        //统计t中字符的出现次数
        Map<Character,Integer> map=new HashMap<>();
        for(int i=0;i<t.length();i++){
            int freq=map.getOrDefault(t.charAt(i),0);
            map.put(t.charAt(i),++freq);
        }

        int l=0,r=0;
        //[l,r]为滑动窗口，开始时，没有元素
        int count=0;
        //在窗口中出现的字符串T中的元素个数
        String ret="";
        int minLen=s.length()+1;
        //记录最长子段的长度
        while(r<s.length()){
            //s.charAt(r)表示s中字符
            if(map.containsKey(s.charAt(r))){
                int freq=map.get(s.charAt(r));
                map.put(s.charAt(r),--freq);
                //count统计字符串s中t中字符出现的次数
                if(freq>=0){
                    count++;
                }
                //s中出现的字符数刚好包含了t中所有的字符
                while (count == t.length()) {
                    //[l...r]窗口就是最字符串短
                    if (r - l + 1 < minLen) {
                        minLen = r - l + 1;
                        ret = s.substring(l, r + 1);
                    }
                    //缩小窗口
                    if (map.containsKey(s.charAt(l))) {
                        int sfreq = map.get(s.charAt(l));
                        map.put(s.charAt(l), ++sfreq);
                        if (sfreq > 0) {
                            --count;
                        }
                    }
                    ++l;
                }
            }
            r++;
        }
        return ret;
    }

    @Test
    public void test(){
        String S = "ADOBECODEBANC";
        String T = "ABC";
        System.out.println(minWindow(S,T));
    }
}
