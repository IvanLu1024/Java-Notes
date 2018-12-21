package code_06_backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * 46. Permutations
 *
 * Given a collection of distinct integers, return all possible permutations.
 *
 * Example:
 * Input: [1,2,3]
 * Output:
 [
 [1,2,3],
 [1,3,2],
 [2,1,3],
 [2,3,1],
 [3,1,2],
 [3,2,1]
 ]
 */
public class Code_46_Permutations {
    private List<List<Integer>> res;

    //用来去除已经访问过的元素,该数组长度为nums.lenth,其下标表示的元素和nums的下标表示的元素相同
    private boolean[] visitecd;

    //p中保存一个有index的元素的排列
    //向这个排列的末尾添加第(index+1)个元素，获的有(index+1)个元素的排列
    private void generatePermutation(int[] nums,int index,List<Integer> p){
        if(index==nums.length){
            //这里要尤其注意，p传过来的是引用
            res.add(new ArrayList<>(p));
            return;
        }
        for(int i=0;i<nums.length;i++){
            //判断nums[i]是否在p元素中
            if(visitecd[i]==false){
                //将nums[i]元素直接放入p中
                p.add(nums[i]);
                visitecd[i]=true;

                generatePermutation(nums,index+1,p);

                //回溯法的精髓
                p.remove(p.size()-1);
                visitecd[i]=false;
            }
        }
        return;
    }

    public List<List<Integer>> permute(int[] nums) {
        res=new ArrayList<>();
        if(nums.length==0){
            return res;
        }
        visitecd=new boolean[nums.length];
        List<Integer> p=new ArrayList<>();
        generatePermutation(nums,0,p);
        return res;
    }
}
