package code_01_array;

import org.junit.Test;

/**
 * 485. Max Consecutive Ones
 *
 * Given a binary array, find the maximum number of consecutive 1s in this array.
 *
 * Example 1:
 Input: [1,1,0,1,1,1]
 Output: 3
 Explanation: The first two digits or the last three digits are consecutive 1s.
 The maximum number of consecutive 1s is 3.

 * Note:
 The input array will only contain 0 and 1.
 The length of input array is a positive integer and will not exceed 10,000
 */
public class Code_485_MaxConsecutiveOnes {
    public int findMaxConsecutiveOnes(int[] nums) {
        int n=nums.length;
        if(n==0){
            return 0;
        }

        int res=0;
        //count记录连续出现1的个数
        int count=0;
        for(int i=0;i<n;i++){
            if(nums[i]==1){
                count++;
                res=Math.max(res,count);
            }else{
                count=0;
            }
        }
        return res;
    }

    @Test
    public void test(){
        int[] nums={1,1,0,1,1,1};
        System.out.println(findMaxConsecutiveOnes(nums));
    }
}
