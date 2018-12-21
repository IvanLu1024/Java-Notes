package code_01_array;

import org.junit.Test;

/**
 *
 * 125. Valid Palindrome
 * Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
 * Note: For the purpose of this problem, we define empty string as valid palindrome.
 *
 * Example 1:
 * Input: "A man, a plan, a canal: Panama"
 * Output: true

 * Example 2:
 * Input: "race a car"
 * Output: false
 */
public class Code_125_ValidPalindrome {
    public boolean isPalindrome1(String s) {
        StringBuilder sb=new StringBuilder();
        for(int k=0;k<s.length();k++){
            if(isAlpha(s.charAt(k)) || isNumber(s.charAt(k))){
                if(s.charAt(k)>='A' && s.charAt(k)<='Z'){
                    sb.append((char)(s.charAt(k)+32));
                }else{
                    sb.append(s.charAt(k));
                }
            }
        }
        s=sb.toString();
        int i=0;
        int j=s.length()-1;
        while(i<j){
            char c1=s.charAt(i);
            char c2=s.charAt(j);
            if((c1 !=c2)){
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    //思路二：对撞指针。只不过要注意遇到不是字母或者数字的字符要跳过，字符之间的比较是忽略大小写的
    public boolean isPalindrome(String s) {
        int i=0;
        int j=s.length()-1;
        while(i<j){
            char c1=s.charAt(i);
            char c2=s.charAt(j);
            if(!isNumber(c1) && !isAlpha(c1)){
                i++;
                continue;
            }
            if(!isNumber(c2) && !isAlpha(c2)){
                j--;
                continue;
            }
            if(!isEqual(c1,c2)){
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    //比较两个字符是否相等,如果是字母，就忽略大小写
    public boolean isEqual(char c1,char c2){
        if(c1==c2){
            return true;
        }else{
            if((isAlpha(c1) && isAlpha(c2)) && Math.abs(c1-c2)==32){
                return true;
            }
        }
        return false;
    }

    public boolean isNumber(char c){
        if(c>='0' && c<='9'){
            return true;
        }
        return false;
    }

    public boolean isAlpha(char c){
        if((c>='A' && c<='Z') || (c>='a' && c<='z')){
            return true;
        }
        return false;
    }

    @Test
    public void test(){
        //boolean flag=isPalindrome("0P");
        boolean flag=isPalindrome("A man, a plan, a canal: Panama");
        System.out.println(flag);
    }
}
