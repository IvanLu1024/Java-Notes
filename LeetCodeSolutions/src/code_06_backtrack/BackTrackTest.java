package code_06_backtrack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 回溯法测试
 */
public class BackTrackTest {
    //注意：0和1是不表示字母的
    private String[] letterMap={
            " ",
            "",
            "abc",//2
            "def",//3
            "ghi",//4
            "jkl",//5
            "mno",//6
            "pqrs",//7
            "tuv",//8
            "wxyz"//9
    };

    private ArrayList<String> res;

    //s中保存从digits[0...index-1]翻译得到的一个字母字符串
    //寻找和digits[index]匹配的字母，获得digits[0...index]翻译得到的字符串
    private void findCombination(String digits,int index,String s){
        System.out.println(index+":"+s);
        //递归结束条件
        if(index==digits.length()){
            //保存s
            res.add(s);
            System.out.println("get "+s+" ,return");
            return;
        }
        // c表示digits的index位置的数字
        char c=digits.charAt(index);
        assert  c>='0' && c<='9'&& c!='0';
        String letters=letterMap[c-'0'];
        for(int i=0;i<letters.length();i++){
            System.out.println("digits["+index+"]="+c+" ,use "+letters.charAt(i));
            findCombination(digits,index+1,s+letters.charAt(i));
        }
        System.out.println("digits["+index+"]="+c+" complete,return");
    }

    public List<String> letterCombinations(String digits) {
        res=new ArrayList<>();
        //当digit为空字符串时，返回的是空集合
        if("".equals(digits)){
            return res;
        }
        findCombination(digits,0,"");
        return res;
    }

    @Test
    public void test(){
        List<String> res=letterCombinations("23");
        System.out.println(res);
    }
}
