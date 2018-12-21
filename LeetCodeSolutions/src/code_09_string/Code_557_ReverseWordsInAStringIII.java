package code_09_string;

/**
 * Given a string, you need to reverse the order of characters
 * in each word within a sentence while still preserving whitespace and initial word order.
 *
 * Example 1:
 * Input: "Let's take LeetCode contest"
 * Output: "s'teL ekat edoCteeL tsetnoc"
 *
 * Note: In the string, each word is separated by single space and
 * there will not be any extra space in the string.
 */
public class Code_557_ReverseWordsInAStringIII {
    public String reverseWords(String s) {
        if(s==null){
            return null;
        }
        StringBuilder res=new StringBuilder();
        String[] strArr=s.split(" ");
        for(String str:strArr){
            res.append(new StringBuffer(str).reverse()).append(" ");
        }
        return res.toString().trim();
    }
}
