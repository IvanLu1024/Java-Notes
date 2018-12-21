package code_01_array;

/**
 * 28. Implement strStr()
 *
 * Implement strStr().
 * Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
 *
 * Example 1:
 Input: haystack = "hello", needle = "ll"
 Output: 2

 * Example 2:
 Input: haystack = "aaaaa", needle = "bba"
 Output: -1

*  Clarification:
 What should we return when needle is an empty string? This is a great question to ask during an interview.
 For the purpose of this problem, we will return 0 when needle is an empty string.
 This is consistent（一贯的；一致的） to C's strstr() and Java's indexOf().
 */
public class Code_28_ImplementStrStrMethod {
    public int strStr(String haystack, String needle) {
        if(needle==null || needle.length()==0){
            return 0;
        }
        haystack.indexOf(needle);
        return -1;
    }
}
