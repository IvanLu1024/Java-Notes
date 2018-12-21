package code_06_backtrack;

import org.junit.Test;

/**
 * 698. Partition to K Equal Sum Subsets
 *
 * Given an array of integers nums and a positive integer k,
 * find whether it's possible to divide this array into k non-empty subsets whose sums are all equal.
 *
 * Example 1:
 Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
 Output: True
 Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
 */
public class Code_698_PartitiontoKEqualSumSubsets {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        if(nums==null || nums.length<k){
            return false;
        }
        int sum=0;
        for(int num:nums){
            sum+=num;
        }
        if(sum%k!=0){
            return false;
        }
        int[] sums=new int[k];
        int target=sum/k;
        return canPartition(nums,sums,k,0,target);
    }

    private boolean canPartition(int[] nums,int[] sums,int k,int index,int target){
        if(index==nums.length){
            return true;
        }
        for(int i=0;i<k;i++){
            if(sums[i]+nums[index]>target){
                continue;
            }
            if(index<k && i>index){
                break;
            }
            sums[i]+=nums[index];
            if(canPartition(nums,sums,k,index+1,target)){
                return true;
            }
            sums[i]-=nums[index];
        }
        return false;
    }

    @Test
    public void test(){
        int[] arr={4, 3, 2, 3, 5, 2, 1};
        int k=4;
        System.out.println(canPartitionKSubsets(arr,k));
    }
}
