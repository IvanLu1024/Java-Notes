package code_09_string;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * 796. Rotate String
 *
 * We are given two strings, A and B.
 * A shift on A consists of taking string A and
 * moving the leftmost character to the rightmost position.
 * For example, if A = 'abcde',
 * then it will be 'bcdea' after one shift on A.
 * Return True if and only if A can become B after some number of shifts on A.

 Example 1:
 Input: A = 'abcde', B = 'cdeab'
 Output: true

 Example 2:
 Input: A = 'abcde', B = 'abced'
 Output: false
 */
public class Code_796_RotateString {
    public boolean rotateString1(String A, String B) {
        if(A.length()==0 && B.length()==0){
            return true;
        }
        if(A.length()!=B.length()){
            return false;
        }
        Set<String> res=new HashSet<>();
        for(int i=0;i<A.length();i++){
            String tmp=A.substring(i)+A.substring(0,i);
            res.add(tmp);
        }
        if(res.contains(B)){
            return true;
        }
        return false;
    }

    public boolean rotateString(String A, String B) {
        if(A.length()==0 && B.length()==0){
            return true;
        }
        if(A.length()!=B.length()){
            return false;
        }
        for(int i=0;i<A.length();i++){
            String tmp=A.substring(i)+A.substring(0,i);
            if(tmp.equals(B)){
                return true;
            }
        }
        return false;
    }

    @Test
    public void test(){
        String A = "abcde";
        String B = "cdeab";
        //String B="abced";
        System.out.println(rotateString(A,B));
    }
}
