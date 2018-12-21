package code_06_backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 40. Combination Sum II
 *
 * Given a collection of candidate numbers (candidates) and a target number (target), find all unique combinations in candidates where the candidate numbers sums to target.
 * Each number in candidates may only be used once in the combination.
 *
 * Note:
 * All numbers (including target) will be positive integers.
 * The solution set must not contain duplicate combinations.
 *
 * Example 1:
 * Input: candidates = [10,1,2,7,6,1,5], target = 8,
 * A solution set is:
 [
 [1, 7],
 [1, 2, 5],
 [2, 6],
 [1, 1, 6]
 ]
 */
public class Code_40_CombinationSumII {
    private List<List<Integer>> res;

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        res=new ArrayList<>();
        if(candidates.length==0){
            return res;
        }
        Arrays.sort(candidates);
        List<Integer> p=new ArrayList<>();
        solve(candidates,0,target,p);
        return res;
    }

    private void solve(int[] candidates,int startIndex,int target,List<Integer> p){
        if(target==0){
            res.add(new ArrayList<>(p));
            return;
        }
        for(int i=startIndex;i<candidates.length;i++){
            if(i > startIndex && candidates[i] == candidates[i-1])
                continue;
            if(target>=candidates[i]){
                p.add(candidates[i]);
                solve(candidates,i+1,target-candidates[i],p);
                p.remove(p.size()-1);
            }
        }
        return;
    }
}
