package code_07_dp;

import org.junit.Test;

/**
 * 416. Partition Equal Subset Sum
 *
 * Given a non-empty array containing only positive integers, find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.

 * Note:
 * Each of the array element will not exceed 100.
 * The array size will not exceed 200.

 * Example 1:
 * Input: [1, 5, 11, 5]
 * Output: true
 * Explanation: The array can be partitioned as [1, 5, 5] and [11].
 */
public class Code_416_PartitionEqualSubsetSum {
    //使用记忆化搜索
    //这里使用int类型：
    //-1：表示未计算 0：表示不可以填充 1：表示可以填充
    private int[][] memo1;

    //使用[0...index]之间的物品，是否可以填充容量为C的背包
    private boolean tryPartition(int[] nums, int index, int C) {
        //填满背包
        if(C==0){
            return true;
        }
        //C<0 说明背包放不下了
        if(C<0 || index<0){
            return false;
        }
        if(memo1[index][C]!=-1){
            return memo1[index][C]==1;
        }
        memo1[index][C]=(tryPartition(nums,index-1,C) || tryPartition(nums,index-1,C-nums[index]))==true?1:0;
        return memo1[index][C]==1;
    }

    public boolean canPartition1(int[] nums) {
        int n=nums.length;
        if(n==0){
            return false;
        }
        int sum=0;
        for(int i=0;i<nums.length;i++){
            sum+=nums[i];
        }
        if(sum%2==1){
            return false;
        }
        memo1=new int[n][sum/2+1];
        for(int i=0;i<n;i++){
            for(int j=0;j<sum/2+1;j++){
                memo1[i][j]=-1;
            }
        }
        return tryPartition(nums,nums.length-1,sum/2);
    }

    public boolean canPartition(int[] nums) {
        int n=nums.length;
        int sum=0;
        for(int i=0;i<n;i++){
            sum+=nums[i];
        }
        if(sum%2==1){
            return false;
        }
        int C=sum/2;
        boolean[] memo=new boolean[C+1];
        for(int j=0;j<C+1;j++){
            //因为要刚好装满，尝试放编号为0的物品
            memo[j]=(j==nums[0]);
        }
        for(int i=1;i<n;i++){
            for(int j=C;j>=nums[i];j--){
                memo[j]=(memo[j] || memo[j-nums[i]]);
            }
        }
        return memo[C];
    }

    @Test
    public void test(){
        int[] nums={1,2,3,4,5,6,7};
        System.out.println(canPartition(nums));
    }
}
