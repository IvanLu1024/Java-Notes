package code_02_find;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 884. Uncommon Words from Two Sentences
 *
 * We are given two sentences A and B.
 * (A sentence is a string of space separated words.  Each word consists only of lowercase letters.)
 * A word is uncommon if it appears exactly once in one of the sentences, and does not appear in the other sentence.
 * Return a list of all uncommon words.
 *
 * Example 1:
 Input: A = "this apple is sweet", B = "this apple is sour"
 Output: ["sweet","sour"]

 * Example 2:
 Input: A = "apple apple", B = "banana"
 Output: ["banana"]

 * Note:
 0 <= A.length <= 200
 0 <= B.length <= 200
 A and B both contain only spaces and lowercase letters.
 */
public class Code_884_UncommonWordsfromTwoSentences {
    public String[] uncommonFromSentences(String A, String B) {
        StringBuilder res=new StringBuilder();
        Map<String,Integer> map=new HashMap<>();
        if(A.length()!=0){
            String[] arr=A.split(" ");
            for(String s:arr){
                map.put(s,map.getOrDefault(s,0)+1);
            }
        }
        if(B.length()!=0){
            String[] arr=B.split(" ");
            for(String s:arr){
                map.put(s,map.getOrDefault(s,0)+1);
            }
        }
        for(String s:map.keySet()){
            Integer num=map.get(s);
            if(num==1){
                res.append(s).append(" ");
            }
        }
        //res为空，说明没有解，则直接返回空字符串数组
        if(res.length()==0){
            return new String[]{};
        }
        return res.toString().split(" ");
    }

    @Test
    public void test(){
        String A="this apple is sweet";
        String B="this apple is sour";
        String[] arr=uncommonFromSentences(A,B);
        for(String s:arr){
            System.out.println(s);
        }
    }
}
