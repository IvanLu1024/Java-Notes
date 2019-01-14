package code_09_string;

import org.junit.Test;

import java.util.Arrays;

/**
 * 14. Longest Common Prefix
 *
 * Write a function to find the longest common prefix string amongst an array of strings.
 * If there is no common prefix, return an empty string "".
 *
 * Example 1:
 Input: ["flower","flow","flight"]
 Output: "fl"

 * Example 2:
 Input: ["dog","racecar","car"]
 Output: ""
 Explanation: There is no common prefix among the input strings.
 */
public class Code_14_LongestCommonPrefix {
    /**
     * 思路一：
     * 要想获取公共前缀字符串,只需要以第一个字符串作为基准就好了
     * 取出第一个字符串中指定位置的元素，看后续字符串是否在该指定位置存在该元素。
     */
    public String longestCommonPrefix1(String[] strs) {
        if(strs==null || strs.length==0){
            return "";
        }
        if(strs.length==1){
            return strs[0];
        }
        int minLength=strs[0].length();
        for(int i=0;i<strs.length;i++){
            minLength=Math.min(minLength,strs[i].length());
        }
        StringBuilder res=new StringBuilder();
        res.append("");

        for(int i=0;i<minLength;i++){
            for(int j=0;j<strs.length;j++){
                if(strs[0].charAt(i)!=strs[j].charAt(i)){
                    return res.toString();
                }
            }
            res.append(strs[0].charAt(i));
        }
        return res.toString();
    }

    /**
     * 思路二：
     * 先利用Arrays.sort(strs)为数组排序，再将数组第一个元素和最后一个元素的字符从前往后对比即可！
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        if (strs.length == 1) {
            return strs[0];
        }
        Arrays.sort(strs);
        StringBuilder res=new StringBuilder();
        int minLen= Math.min(strs[0].length(),strs[strs.length-1].length());
        for(int i = 0; i<minLen;i++){
            if(strs[0].charAt(i)!=strs[strs.length-1].charAt(i)) {
                break;
            }
            res.append(strs[0].charAt(i));
        }
        return res.toString();
    }

    @Test
    public void test(){
        String[] strs = {"flower","flow","flight"};
        System.out.println(longestCommonPrefix(strs));
    }
}
