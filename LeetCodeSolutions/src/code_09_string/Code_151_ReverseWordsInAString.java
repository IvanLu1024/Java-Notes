package code_09_string;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Given an input string, reverse the string word by word.
 Example:
 Input: "the sky is blue",
 Output: "blue is sky the".

 Note:
 A word is defined as a sequence of non-space characters.
 TODO:Input string may contain leading or trailing spaces.However, your reversed string should not contain leading or trailing spaces.
 You need to reduce multiple spaces between two words to a single space in the reversed string.

 * Follow up: For C programmers, try to solve it in-place in O(1) space.
 */
public class Code_151_ReverseWordsInAString {
    public String reverseWords(String s) {
        if(isEmptyStr(s)){
            return "";
        }
        s=s.trim();
        String[] strArr=s.split(" ");

        List<String> list=new ArrayList<>();
        for(String str:strArr){
            if(!isEmptyStr(str)){
                list.add(str);
            }
        }

        StringBuilder res=new StringBuilder();
        for(int i=list.size()-1;i>0;i--){
            res.append(list.get(i)+" ");
        }
        res.append(list.get(0));
        return res.toString();
    }

    //判断s是否是空字符串,这里得空字符串指的是没有字母字符，只有空格的字符串
    private boolean isEmptyStr(String s){
        if(s.length()==0){
            return true;
        }
        int i=0;
        while(i<s.length()){
            if(s.charAt(i)!=' '){
                return false;
            }
            i++;
        }
        return true;
    }

    @Test
    public  void test(){
        String s="the sky is    blue";
        System.out.println(reverseWords(s));
    }
}
