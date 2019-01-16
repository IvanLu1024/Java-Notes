package code_10_others;

import java.util.Random;

/**
 * 398. Random Pick Index
 *
 * Given an array of integers with possible duplicates,
 * randomly output the index of a given target number. You can assume that the given target number must exist in the array.
 *
 * Note:
 * The array size can be very large.
 * Solution that uses too much extra space will not pass the judge.
 */
public class Code_398_RandomPickIndex {
    class Solution {
        private int[] nums;
        private Random random=new Random();

        public Solution(int[] nums) {
            this.nums=nums;
        }

        public int pick(int target) {
            int res=-1;
            int count=0;
            for(int i=0;i<nums.length;i++){
                if(nums[i]==target && random.nextInt(++count)==0){
                    //res相当于大小是1的池子，不断替换
                    res=i;
                }
            }
            return res;
        }
    }
}
