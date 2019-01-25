package code_13_others;

/**
 * 9. Palindrome Number
 *
 * Determine whether an integer is a palindrome.
 * An integer is a palindrome when it reads the same backward as forward.
 *
 * Follow up:
 * Coud you solve it without converting the integer to a string?
 */
public class Code_9_PalindromeNumber {
    /**
     * 思路一：传统做法，直接转化为字符串
     */
    public boolean isPalindrome1(int x) {
        String s = x+"";
        return isPalindome(s,0,s.length()-1);
    }

    private boolean isPalindome(String s,int start,int end){
        while(start < end){
            if(s.charAt(start) != s.charAt(end)){
                return false;
            }
            start ++;
            end --;
        }
        return true;
    }

    /**
     * 思路二：
     * 1.负数，肯定不是回文数
     * 2.对于正数，计算倒置后的数，然后与x进行比较即可。
     */
    public boolean isPalindrome(int x) {
        if(x < 0){
            return false;
        }

        int reverseX = 0;
        int b = x;
        while( b>0){
            reverseX = reverseX * 10 + b % 10;
            b /= 10;
        }
        return reverseX == x ? true : false;
    }
}
