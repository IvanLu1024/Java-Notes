package code_09_string;

import org.junit.Test;

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
    public String longestCommonPrefix(String[] strs) {
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

    @Test
    public void test(){
        String[] strs={"flower","flow","flight"};
        System.out.println(longestCommonPrefix(strs));
    }
}
