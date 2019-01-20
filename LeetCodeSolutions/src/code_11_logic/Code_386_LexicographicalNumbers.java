package code_11_logic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 386. Lexicographical Numbers
 *
 * Given an integer n, return 1 - n in lexicographical order(按字典顺序).
 * For example, given 13, return: [1,10,11,12,13,2,3,4,5,6,7,8,9].
 * Please optimize your algorithm to use less time and space.
 * The input size may be as large as 5,000,000.
 */
public class Code_386_LexicographicalNumbers {
    /**
     * 思路一：按字典顺序，首先就会想到字符串，
     * 将数字转换位字符串，然后字符串按照字典顺序排好后，
     * 再将排好序的字符串转换为整数
     */
    public List<Integer> lexicalOrder1(int n) {
        List<String> tmpRes=new ArrayList<>();
        for(int i=1;i<=n;i++){
            tmpRes.add(i+"");
        }
        Collections.sort(tmpRes);

        List<Integer> res=new ArrayList<>();
        while(!tmpRes.isEmpty()){
            String num=tmpRes.remove(0);
            res.add(Integer.parseInt(num));
        }
        return res;
    }

    /**
     * 思路二：
     * 用函数栈（递归）用来去完成字典序排序。
     */
    public List<Integer> lexicalOrder(int n){
        List<Integer> res=new ArrayList<>();
        for(int i=1;i<10;i++){
            if(i<=n){
                generateRes(res,i,n);
            }
        }
        return res;
    }

    /**
     * 获取以pre开头的数字排序的序列
     * @param pre <=n 的数字的第一个数字
     */
    private void generateRes(List<Integer> res,int pre,int n){
        if(pre>n){
            return;
        }
        res.add(pre);
        for(int i=0;i<10;i++){
            if(pre*10+i<=n){
                generateRes(res,pre*10+i,n);
            }
        }
    }

    @Test
    public void test(){
        System.out.println(lexicalOrder(13));
    }
}
