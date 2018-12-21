package code_09_string;

/**
 * 434. Number of Segments in a String
 *
 * Count the number of segments in a string,
 * where a segment is defined to be a contiguous sequence of non-space characters.
 * Please note that the string does not contain any non-printable characters.

 Example:
 Input: "Hello, my name is John"
 Output: 5
 */
public class Code_434_NumberOfSegmentsInAString {
    /**
     * 思路：
     * 从第二个单词起，每出现一个单词的条件必是空字符后出现非空字符，
     * 例如：‘ ’+'m'  出现my;  ' '+'n' 出现name  等等。
     * 因此，只需统计空字符后紧跟着出现非空字符的次数即可。
     * 别忘了如果字符串刚开始就出现空字符，则不用加上第一个单词。
     */
    public int countSegments(String s) {
        if(s==null || s.length()==0){
            return 0;
        }
        int res=1;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)==' '){
                if(i<s.length()-1 && s.charAt(i+1)!=' '){
                    res++;
                }
            }
        }
        if(s.charAt(0)==' '){
            res--;
        }
        return res;
    }
}
