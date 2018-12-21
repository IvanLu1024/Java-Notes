package code_01_array;

import org.junit.Test;

/**
 * 268. Missing Number
 *
 * Given an array containing n distinct numbers taken from 0, 1, 2, ..., n,
 * find the one that is missing from the array.
 *
 * Example 1:

 Input: [3,0,1]
 Output: 2
 Example 2:

 Input: [9,6,4,2,3,5,7,0,1]
 Output: 8
 */
public class Code_268_MissingNumber {
    public int missingNumber(int[] nums) {
        int n=nums.length;
        if(n==0){
            return 0;
        }

        int res=nums[0]^0;
        for(int i=1;i<n;i++){
            res^=(nums[i]^i);
        }
        res^=n;
        return res;
    }

    @Test
    public void test(){
        //int[] nums={9,6,4,2,3,5,7,0,1};
        int[] nums={3,0,1};
        System.out.println(missingNumber(nums));
    }
}
