package code_06_backtrack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * 90. Subsets II
 *
 * Given a collection of integers that might contain duplicates, nums, return all possible subsets (the power set).
 * Note: The solution set must not contain duplicate subsets.
 *
 * Example:
 * Input: [1,2,2]
 * Output:
 [
 [2],
 [1],
 [1,2,2],
 [2,2],
 [1,2],
 []
 ]
 */
public class Code_90_SubsetsII {
    private List<List<Integer>> res;

    private void findSubsets(int[] nums,int index,List<Integer> p){
        res.add(new ArrayList<>(p));
        for(int i=index;i<nums.length;i++){
            //剪枝去重复元素，对于搜索的任何一层决不能在本层出现重复，也就是说在相同index下不能出现重复的元素
            //(i>0 && nums[i-1]==nums[i]) 相邻的重复元素
            //i==index 表示在同一层
            if(i!=index && (i>0 && nums[i-1]==nums[i])){
                continue;
            }
            p.add(nums[i]);
            findSubsets(nums,i+1,p);
            p.remove(p.size()-1);
        }
    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        res=new ArrayList<>();
        if(nums.length==0){
            return res;
        }
        Arrays.sort(nums);
        List<Integer> p=new ArrayList<>();
        findSubsets(nums,0,p);
        return res;
    }

    @Test
    public void test(){
        int[] nums={1,2,2};
        System.out.println(subsetsWithDup(nums));
    }
}
