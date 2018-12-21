package code_06_backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * 39. Combination Sum
 *
 * Given a set of candidate numbers (candidates) (without duplicates) and a target number (target),
 * find all unique combinations in candidates where the candidate numbers sums to target.
 * The same repeated number may be chosen from candidates unlimited number of times.
 *
 * Note:
 * All numbers (including target) will be positive integers.
 * The solution set must not contain duplicate combinations.
 * Example 1:
 *
 * Input: candidates = [2,3,6,7], target = 7,
 * A solution set is:
 [
 [7],
 [2,2,3]
 ]
 */
public class Code_39_CombinationSum {
    private List<List<Integer>> res;

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        res=new ArrayList<>();
        if(candidates.length==0){
            return res;
        }
        List<Integer> p=new ArrayList<>();
        solve(candidates,0,target,p);
        return res;
    }

    private void solve(int[] candidates,int startIndex,int target,List<Integer> p){
        if(target==0){
            res.add(new ArrayList<>(p));
        }
        for(int i=startIndex;i<candidates.length;i++){
            if(target>=candidates[i]){
                p.add(candidates[i]);
                solve(candidates,i,target-candidates[i],p);
                p.remove(p.size()-1);
            }
        }
    }
}
