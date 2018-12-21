package code_06_backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 47. Permutations II
 *
 * Given a collection of numbers that might contain duplicates, return all possible unique permutations.
 *
 * Example:
 * Input: [1,1,2]
 * Output:
 [
 [1,1,2],
 [1,2,1],
 [2,1,1]
 ]
 */
public class Code_47_PermutationsII {
    private List<List<Integer>> res;

    private boolean[]  vistied;

    private void generatePermutation(int[] nums,int index,List<Integer> p){
        if(nums.length==index){
            res.add(new ArrayList<>(p));
            return;
        }
        for(int i=0;i<nums.length;i++){
            if(vistied[i]==false){
                //nums[i]是重复元素，并且相邻两个元素都没被访问过
                if(i>0 && nums[i]==nums[i-1] && vistied[i-1]==false){
                    continue;
                }
                //要加入的元素，与前一个元素不能相同
                p.add(nums[i]);
                vistied[i]=true;

                generatePermutation(nums,index+1,p);

                p.remove(p.size()-1);
                vistied[i]=false;
            }
        }
        return;
    }

    /**
     * 思路：
     * 看到重复元素，首先要想到能否使用排序。
     * 这里如果已经访问过的数字如果是重复元素,排序后，相邻元素相同，则该元素是重复元素
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        res=new ArrayList<>();
        if(nums.length==0){
            return res;
        }
        //先对数组进行排序，方便后面的
        Arrays.sort(nums);
        List<Integer> p=new ArrayList<>();
        vistied=new boolean[nums.length];
        generatePermutation(nums,0,p);
        return res;
    }
}
