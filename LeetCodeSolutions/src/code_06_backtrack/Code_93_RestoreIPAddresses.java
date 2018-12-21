package code_06_backtrack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 93. Restore IP Addresses
 *
 * Given a string containing only digits, restore it by returning all possible valid IP address combinations.
 *
 * Example:
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]

 */
public class Code_93_RestoreIPAddresses {
    /**
     * 在输入字符串中加入三个点，将字符串分为四段，每一段必须合法，求所有可能的情况。
     * 我们用k来表示当前还需要分的段数，如果k = 0，则表示三个点已经加入完成，四段已经形成，若这时字符串刚好为空，则将当前分好的结果保存。
     * 若k != 0,
     * 则对于每一段，我们分别用一位，两位，三位来尝试，分别判断其合不合法，如果合法，则调用递归继续分剩下的字符串，最终和求出所有合法组合。
     * @param s
     * @return
     */
    public List<String> restoreIpAddresses(String s){
        List<String> res=new ArrayList<>();
        if(s.length()<4 || s.length()>12){
            return res;
        }
        restore(s,1,"",res);
        return res;
    }

    //count表示的是取的是第count段
    //p存储的前段的IP地址
    //s表示的每一段的IP地址
    private void restore(String s,int count,String p,List<String> res){
        System.out.println("s:"+s);
        if(count==4 && isValid(s)){
            res.add(p+s);
            return;
        }
        //Math.min(4, s.length())后面几位少于4的情况比如，0000
        for(int i=1;i< Math.min(4,s.length());i++){
            String cur=s.substring(0,i);
            if(isValid(cur)){
                System.out.println("p:"+p);
                System.out.println("cur:"+cur);
                System.out.println();
                restore(s.substring(i),count+1 ,p+cur+".",res);
                System.out.println("count="+count);
            }
        }
        return;
    }

    /**
     * IP地址总共有四段，
     * 每一段可能有一位，两位或者三位，范围是[0, 255]，
     * 题目明确指出输入字符串只含有数字，所以当某段是三位时，我们要判断其是否越界（>255)，
     * 还有一点很重要的是，当只有一位时，0可以成某一段，
     * 如果有两位或三位时，像 00， 01， 001， 011， 000等都是不合法的，
     * 所以我们还是需要有一个判定函数来判断某个字符串是否合法。
     */
    private boolean isValid(String s){
        //该字符串的第一个元素是0,则该字符串要合法的话，就只能是0
        if(s.charAt(0)=='0'){
            return "0".equals(s);
        }
        int num=Integer.parseInt(s);
        return num>0 && num<256;
    }

    @Test
    public void test(){
        String s="234255123";
        List<String> list=restoreIpAddresses(s);
        //System.out.println(list);
    }
}
