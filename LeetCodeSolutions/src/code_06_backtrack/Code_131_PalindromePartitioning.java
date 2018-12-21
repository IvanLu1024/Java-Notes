package code_06_backtrack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a string s, partition s such that every substring of the partition is a palindrome.
 *
 * Return all possible palindrome partitioning of s.

 * Example:
 * Input: "aab"
 * Output:
 [
 ["aa","b"],
 ["a","a","b"]
 ]
 */
public class Code_131_PalindromePartitioning {
    private List<List<String>> res;
    public List<List<String>> partition(String s) {
        res=new ArrayList<>();
        if(s.length()==0){
            return res;
        }
        List<String> p=new ArrayList<>();
        findPalindrome(s,0,p);
        return res;
    }

    private void findPalindrome(String s,int index,List<String> p){
        if(index==s.length()){
            res.add(new ArrayList<>(p));
            return;
        }
        for(int i=index;i<s.length();i++){
            // 字符串[index,i+1) ，一共有 i-index+1个元素
            String tmp=s.substring(index,i+1);
            if(isPalindrome(tmp)){
                p.add(tmp);
                findPalindrome(s,i+1,p);
                p.remove(p.size()-1);
            }
        }
    }

    //判断字符串是否是回文串
    private boolean isPalindrome(String s){
        int start=0;
        int end=s.length()-1;
        while(start<end){
            if(s.charAt(start)!=s.charAt(end)){
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    @Test
    public void test(){
        String s="aab";
        List<List<String>> res=partition(s);
        System.out.println(res);
    }
}
