package code_09_string;

import org.junit.Test;

/**
 * 125. Valid Palindrome
 *
 * Given a string, determine if it is a palindrome,
 * considering only alphanumeric characters and ignoring cases.
 *
 * Note: For the purpose of this problem, we define empty string as valid palindrome.
 */
public class Code_125_ValidPalindrome {
    /**
     * 思路:
     * 这里只考虑字母（忽略大小写）和数字，
     * 使用Character来进行判断。
     */
    public boolean isPalindrome1(String s) {
        if(s==null || s.length()==0){
            return true;
        }
        //将所有的字母都转换成小写
        s=s.toLowerCase();
        int start=0;
        char[] chs=new char[s.length()];
        for(int i=0;i<s.length();i++){
            if(Character.isLetterOrDigit(s.charAt(i))){
                chs[start++]=s.charAt(i);
            }
        }
        s=new String(chs,0,start);
        return isPalindrome(s,0,s.length()-1);
    }

    private boolean isPalindrome(String s,int start,int end){
        while (start<=end){
            if(s.charAt(start) != s.charAt(end)){
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    /**
     * 改进自private boolean isPalindrome(String,int,int)方法
     */
    public boolean isPalindrome(String s) {
        if(s==null || s.length()==0){
            return true;
        }
        int start=0;
        int end=s.length()-1;
        while(start<=end){
            if(!Character.isLetterOrDigit(s.charAt(start))){
                //start位置不是字母或者字符
                start++;
            }else if(!Character.isLetterOrDigit(s.charAt(end))){
                //end位置不是字母或者字符
                end--;
            }else{
                char c1=Character.toLowerCase(s.charAt(start));
                char c2=Character.toLowerCase(s.charAt(end));
                if(c1!=c2){
                    return false;
                }
                start++;
                end--;
            }
        }
        return true;
    }


    @Test
    public void test(){
        System.out.println(isPalindrome("A man, a plan, a canal: Panama"));
        //isPalindrome("race a car");
    }
}
