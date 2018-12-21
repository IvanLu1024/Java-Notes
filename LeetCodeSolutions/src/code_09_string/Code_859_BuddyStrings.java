package code_09_string;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * 859. Buddy Strings
 *
 * Given two strings A and B of lowercase letters,
 * return true if and only if(当且仅当) we can swap two letters in A so that the result equals B.
 *
 * Example 1:
 Input: A = "ab", B = "ba"
 Output: true

 Example 2:
 Input: A = "ab", B = "ab"
 Output: false

 * Note:
 0 <= A.length <= 20000
 0 <= B.length <= 20000
 A and B consist only of lowercase letters.
 */
public class Code_859_BuddyStrings {
    public boolean buddyStrings(String A, String B) {
        if(A==null ||  B==null){
            return false;
        }
        if(A.length()!=B.length()){
            return false;
        }
        if(A.equals(B)){
            //若A中没有重复元素，则返回false
            Set<Character> set=new HashSet<Character>();
            for(int i=0;i<A.length();i++) {
                set.add(A.charAt(i));
            }
            if(set.size()==A.length()) {
                //A中没有重复元素
                return false;
            }
            return true;
        }
        //统计A和B字符串中的不同元素，count==2说明只有两个元素不同
        int count=0;
        //存储A和B中在同一位置出现的不同字符
        Set<Character> setA=new HashSet<>();
        //存储B和A中在同一位置出现的不同字符
        Set<Character> setB=new HashSet<>();
        for(int i=0;i<A.length();i++){
            if(A.charAt(i)!=B.charAt(i)){
                setA.add(A.charAt(i));
                setB.add(B.charAt(i));
                count++;
            }
            if(count>2){
                return false;
            }
        }
        //count==2说明只有两个元素不同
        //setA.equals(setB) 说明元素内容是相同的
        //count==2 && setA.equals(setB) 就说明这两个字符串只是两个字符的位置不同
        if(count==2 && setA.equals(setB)){
            return true;
        }
        return false;
    }

    @Test
    public void test(){
        //String A = "ab";
        //String B = "ba";
        String A = "aaaaaaabc";
        String B = "aaaaaaacb";
        System.out.println(buddyStrings(A,B));
    }
}
