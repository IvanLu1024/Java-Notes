package code_07_dp;

/**
 * 377. Combination Sum IV
 *
 * Given an integer array with all positive numbers and no duplicates,
 * find the number of possible combinations that add up to a positive integer target.
 *
 * Example:
 nums = [1, 2, 3]
 target = 4

 * The possible combination ways are:
 (1, 1, 1, 1)
 (1, 1, 2)
 (1, 2, 1)
 (1, 3)
 (2, 1, 1)
 (2, 2)
 (3, 1)

 * Note that different sequences are counted as different combinations.

 * Therefore the output is 7.
 */
public class Code_377_CombinationSumIV {
    private int[] memo;
    private int findCombination(int[] nums,int target){
        if(target==0){
            return 1;
        }
        if(memo[target]!=-1){
            return memo[target];
        }
        int res=0;
        for(int i=0;i<nums.length;i++){
            if(target>=nums[i]){
                res+=findCombination(nums,target-nums[i]);
            }
        }
        memo[target]=res;
        return memo[target];
    }

    public int combinationSum4(int[] nums, int target) {
        int n=nums.length;
        if(n==0){
            return 0;
        }
        memo=new int[target+1];
        for(int i=0;i<target+1;i++){
            memo[i]=-1;
        }
        findCombination(nums,target);
        return memo[target];
    }

    public int combinationSum41(int[] nums, int target) {
        int n=nums.length;
        if(n==0){
            return 0;
        }
        //memo[i]表示数组中表示存储的是目标i所得的组合数。
        int[] memo=new int[target+1];
        memo[0]=1;

        for(int i=1;i<target+1;i++){
            for(int j=0;j<n;j++){
                if(i>=nums[j]){
                    memo[i]=memo[i]+memo[i-nums[j]];
                }
            }
        }
        return memo[target];
    }
}
