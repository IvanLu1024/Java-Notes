package code_06_backtrack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 78. Subsets
 *
 * Given a set of distinct integers, nums, return all possible subsets (the power set).
 * Note: The solution set must not contain duplicate subsets.
 *
 * Example:
 *
 * Input: nums = [1,2,3]
 * Output:
 [
 [3],
 [1],
 [2],
 [1,2,3],
 [1,3],
 [2,3],
 [1,2],
 []
 ]
 */
public class Code_78_Subsets {
    private List<List<Integer>> res;

    private void findSubset(int[] nums,int index,List<Integer> p){
        //这里不需要递归的结束条件
        //因为当index==nums.length之后，就会自动终止递归
        //为什么自己不递归？这样会导致解缺失的情况
        //if(index==nums.length){
        //    res.add(new ArrayList<>(p));
        //return;
        //}
        //结果：[[1, 2, 3], [1, 3], [2, 3], [3]]
        //因为每次递归对数组长度是没有要求的，加上条件后，每次只能获取定长的数据
        res.add(new ArrayList<>(p));
        for(int i=index;i<nums.length;i++){
            p.add(nums[i]);
            findSubset(nums,i+1,p);
            p.remove(p.size()-1);
        }
    }

    public List<List<Integer>> subsets(int[] nums) {
        res=new ArrayList<>();
        if(nums.length==0){
            return res;
        }
        List<Integer> p=new ArrayList<>();
        findSubset(nums,0,p);
        return res;
    }

    @Test
    public void test(){
        int[] arr={1,2,3};
        System.out.println(subsets(arr));
    }
}
