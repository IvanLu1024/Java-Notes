package code_06_backtrack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 784. Letter Case Permutation
 *
 * Given a string S, we can transform every letter individually to be
 * lowercase or uppercase to create another string.
 * Return a list of all possible strings we could create.
 *
 * Examples:
 Input: S = "a1b2"
 Output: ["a1b2", "a1B2", "A1b2", "A1B2"]

 Input: S = "3z4"
 Output: ["3z4", "3Z4"]

 Input: S = "12345"
 Output: ["12345"]

 Note:

 S will be a string with length between 1 and 12.
 S will consist only of letters or digits.
 */
public class Code_784_LetterCasePermutation {
    private List<String> res;

    //处理index位置的字符
    private void replace(int index,StringBuilder builder){
        if(index==builder.length()){
            res.add(builder.toString());
            return;
        }
        char ch=builder.charAt(index);
        if(Character.isLetter(ch)){
            builder.setCharAt(index,Character.toLowerCase(ch));
            replace(index+1,builder);
            builder.setCharAt(index,Character.toUpperCase(ch));
            replace(index+1,builder);
        }else{
            replace(index+1,builder);
        }
    }

    public List<String> letterCasePermutation(String S) {
        res=new ArrayList<>();
        if(S.length()==0){
            res.add("");
            return res;
        }
        StringBuilder builder=new StringBuilder(S);
        replace(0,builder);
        return res;
    }

    @Test
    public void test(){
        String S="a1b2";
        //String S="12345";
        System.out.println(letterCasePermutation(S));
    }
}
