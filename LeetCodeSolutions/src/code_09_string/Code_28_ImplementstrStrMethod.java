package code_09_string;

/**
 * 28. Implement strStr()
 * Implement strStr().
 * Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.

 * Example 1:
 Input: haystack = "hello", needle = "ll"
 Output: 2

 *  Example 2:
 Input: haystack = "aaaaa", needle = "bba"
 Output: -1

 * Clarification:
 What should we return when needle is an empty string?
 This is a great question to ask during an interview.
 For the purpose of this problem, we will return 0 when needle is an empty string.
 This is consistent to C's strstr() and Java's indexOf().
 */
public class Code_28_ImplementstrStrMethod {
    /**
     * 思路一：直接求解
     */
    public int strStr1(String haystack, String needle) {
        if(needle==null || needle.length()==0){
            return 0;
        }
        int i=0,j=0;
        int k=i;
        while(i<haystack.length() && j<needle.length()){
            if(haystack.charAt(i)==needle.charAt(j)){
                i++;
                j++;
            }else{
                j=0;
                i=++k;
            }
        }
        if(j==needle.length()){
            return k;
        }
        return -1;
    }

    /**
     * 思路二：KMP算法求解
     */
    public int strStr(String haystack, String needle) {
        if(needle==null || needle.length()==0){
            return 0;
        }
        int[] next=getNext(needle);
        int i=0,j=0;
        while(i<haystack.length() && j<needle.length()){
            if(j==-1 || haystack.charAt(i)==needle.charAt(j)){
                i++;
                j++;
            }else{
                j=next[j];
            }
        }
        if(j==needle.length()){
            return i-needle.length();
        }
        return -1;
    }

    private int[] getNext(String needle) {
        int[] next=new int[needle.length()];
        next[0]=-1;
        int j=0,t=-1;
        while (j<needle.length()-1){
            if(t==-1 || needle.charAt(j)==needle.charAt(t)){
                next[j+1]=t+1;
                t++;
                j++;
            }else{
                t=next[t];
            }
        }
        return next;
    }
}
