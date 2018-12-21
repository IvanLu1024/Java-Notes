package code_01_array;

import org.junit.Test;

/**
 * 344. Reverse String
 * Write a function that takes a string as input and returns the string reversed.
 *
 * Example 1:
 * Input: "hello"
 * Output: "olleh"
 *
 * Example 2:
 * Input: "A man, a plan, a canal: Panama"
 * Output: "amanaP :lanac a ,nalp a ,nam A"
 */
public class Code_344_ReverseString {
    public String reverseString(String s) {
        int i=0;
        int j=s.length()-1;
        char[] chs=s.toCharArray();
        while(i<j){
            char tmp=chs[i];
            chs[i]=chs[j];
            chs[j]=tmp;
            i++;
            j--;
        }
        return new String(chs);
    }

    @Test
    public void test(){
        String s=reverseString("A man, a plan, a canal: Panama");
        System.out.println(s);
    }
}
