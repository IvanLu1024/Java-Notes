package code_06_backtrack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 216. Combination Sum III
 *
 * Find all possible combinations of k numbers that add up to a number n,
 * given that only numbers from 1 to 9 can be used and each combination should be a unique set of numbers.
 *
 * Example 1:
 * Input: k = 3, n = 7
 * Output: [[1,2,4]]
 *
 * Example 2:
 * Input: k = 3, n = 9
 * Output: [[1,2,6], [1,3,5], [2,3,4]]
 */
public class Code_216_CombinationSumIII {
    private List<List<Integer>> res;

    public List<List<Integer>> combinationSum3(int k, int n) {
        res=new ArrayList<>();
        if(k>n || k==0){
            return res;
        }
        List<Integer> p=new ArrayList<>();
        //数值是从1开始的
        findCombination(k,n,1,p);
        return res;
    }

    private void findCombination(int k,int n,int num,List<Integer> p){
        if(k==0 && n==0){
            res.add(new ArrayList<>(p));
            return;
        }
        for(int i=num;i<=9;i++){
            if(n>=i){
                p.add(i);
                findCombination(k-1,n-i,i+1,p);
                p.remove(p.size()-1);
            }
        }
    }

    @Test
    public void test(){
        int k=3;
        int n=7;
        List<List<Integer>> res=combinationSum3(k,n);
        System.out.println(res);
    }
}
