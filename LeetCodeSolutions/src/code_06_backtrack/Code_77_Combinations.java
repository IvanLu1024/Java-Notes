package code_06_backtrack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 77. Combinations
 *
 * Given two integers n and k, return all possible combinations of k numbers out of 1 ... n.
 *
 * Example:
 * Input: n = 4, k = 2
 * Output:
 [
 [2,4],
 [3,4],
 [2,3],
 [1,2],
 [1,3],
 [1,4],
 ]
 */
public class Code_77_Combinations {
    private List<List<Integer>> res;

    //c存储已经找到的组合
    //从start开始搜索新的元素
    private void findCombination(int n,int k,int start,List<Integer> c){
        //递归结束条件，这里就是c中有k个元素
        if(k==c.size()){
            res.add(new ArrayList<>(c));
            return;
        }
        for(int i=start;i<=n-(k-c.size())+1;i++){
            c.add(i);
            findCombination(n,k,i+1,c);
            c.remove(c.size()-1);
        }
        /*for(int i=start;i<=n;i++){
            c.add(i);
            findCombination(n,k,i+1,c);
            c.remove(c.size()-1);
        }*/
    }

    public List<List<Integer>> combine(int n, int k) {
        res=new ArrayList<>();
        if(n<=0 || k<=0 || k>n){
            return res;
        }
        List<Integer> c=new ArrayList<>();
        findCombination(n,k,1,c);
        return res;
    }

    @Test
    public void test(){
        System.out.println(combine(4,2));
    }
}
