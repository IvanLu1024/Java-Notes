package code_02_find;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * 893. Groups of Special-Equivalent Strings
 *
 * You are given an array A of strings.
 * Two strings S and T are special-equivalent if after any number of moves, S == T.
 * A move consists of choosing two indices i and j with i % 2 == j % 2, and swapping S[i] with S[j].
 * Now, a group of special-equivalent strings from A is a non-empty subset S of A
 * such that any string not in S is not special-equivalent with any string in S.
 * Return the number of groups of special-equivalent strings from A.
 *
 * Example 1:
 Input: ["a","b","c","a","c","c"]
 Output: 3
 Explanation: 3 groups ["a","a"], ["b"], ["c","c","c"]

 * Example 2:
 Input: ["aa","bb","ab","ba"]
 Output: 4
 Explanation: 4 groups ["aa"], ["bb"], ["ab"], ["ba"]

 * Example 3:
 Input: ["abc","acb","bac","bca","cab","cba"]
 Output: 3
 Explanation: 3 groups ["abc","cba"], ["acb","bca"], ["bac","cab"]

 * Example 4:
 Input: ["abcd","cdab","adcb","cbad"]
 Output: 1
 Explanation: 1 group ["abcd","cdab","adcb","cbad"]
 */
public class Code_893_GroupsOfSpecial_EquivalentStrings {
    public int numSpecialEquivGroups(String[] A) {
        Set<String> set=new HashSet<>();
        for(String s:A){
            set.add(getHashCode(s));
        }
        return set.size();
    }

    private String getHashCode(String s){
        //0-25统计s的偶数下标的字符频率
        //26-52统计s的奇数下标的字符频率
        int[] freq=new int[52];
        for(int i=0;i<s.length();i++){
            freq[(i%2)*26+(s.charAt(i)-'a')]++;
        }
        System.out.println(getStr(freq));
        return getStr(freq);
    }

    private String getStr(int[] freq) {
        StringBuilder builder=new StringBuilder();
        for(int e:freq){
            builder.append(e).append(" ");
        }
        return builder.toString();
    }

    @Test
    public void test(){
        /*System.out.println(isSpecialEquivalent("abcd","abcd"));
        System.out.println(isSpecialEquivalent("abcd","cdab"));
        System.out.println(isSpecialEquivalent("abcd","adcb"));
        System.out.println(isSpecialEquivalent("abcd","cbad"));


        System.out.println(isSpecialEquivalent("abc","cba"));
        System.out.println(isSpecialEquivalent("acb","bca"));
        System.out.println(isSpecialEquivalent("bac","cab"));*/
        //String[] A={"aa","bb","ab","ba"};
        //String[] A={"a","b","c","a","c","c"};
        String[] A={"abcd","cdab","adcb","cbad"};
        System.out.println(numSpecialEquivGroups(A));
    }
}
