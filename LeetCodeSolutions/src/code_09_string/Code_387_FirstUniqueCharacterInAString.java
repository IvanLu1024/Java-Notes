package code_09_string;

/**
 * Given a string, find the first non-repeating
 * character in it and return it's index.
 * If it doesn't exist, return -1.
 *
 * Examples:
 * s = "leetcode"
 return 0.

 * s = "loveleetcode",
 return 2.
 Note: You may assume the string contain only lowercase letters.
 */
public class Code_387_FirstUniqueCharacterInAString {
    public int firstUniqChar(String s) {
        //统计字符出现的次数，因为只有小写字母，所以数组大小是26
        int[] count=new int[26];
        for(int i=0;i<s.length();i++){
            count[s.charAt(i)-'a']++;
        }
        for(int i=0;i<s.length();i++){
            int index=s.charAt(i)-'a';
            if(count[index]==1){
                return i;
            }
        }
        return -1;
    }
}
