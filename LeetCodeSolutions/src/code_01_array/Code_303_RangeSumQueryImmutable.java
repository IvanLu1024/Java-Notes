package code_01_array;

import org.junit.Test;

/**
 * 303. Range Sum Query - Immutable
 *
 * Given an integer array nums,
 * find the sum of the elements between indices i and j (i ≤ j), inclusive.

 Example:
 Given nums = [-2, 0, 3, -5, 2, -1]

 sumRange(0, 2) -> 1
 sumRange(2, 5) -> -1
 sumRange(0, 5) -> -3
 Note:
 You may assume that the array does not change.
 There are many calls to sumRange function.
 */
public class Code_303_RangeSumQueryImmutable {
    /**
     * 思路一：
     * 直接法，但是时间会超出限制
     */
    class NumArray1 {
        private int[] data;

        public NumArray1(int[] nums) {
            data=new int[nums.length];
            for(int i=0;i<nums.length;i++){
                data[i]=nums[i];
            }
        }

        public int sumRange(int i, int j) {
            int sum=0;
            for(int k=i;k<=j;k++){
                sum+=data[k];
            }
            return sum;
        }
    }

    /**
     * 思路二：
     * 我们可以存储子序列和，每个下标处的值为[0,i]的所有元素和;
     那么[i,j]子序列和 sum[j]−sum[i−1]；
     注意，当i==0时，直接返回sum[j]即可。
     */
    class NumArray {
        //sums[i]就是[0...i]的元素和
        private int[] sums;

        public NumArray(int[] nums) {
            if(nums.length==0){
                return;
            }
            sums=new int[nums.length];
            //sums[0]就是[0,0]的元素和，也就是第一个元素
            sums[0]=nums[0];
            for(int i=1;i<nums.length;i++){
                sums[i]=sums[i-1]+nums[i];
            }
        }

        public int sumRange(int i, int j) {
            if(i==0){
                return sums[j];
            }
            if((i<0 || i>= sums.length)||
                    (j<0 || j>=sums.length) || (i>j)){
                return 0;
            }
            return sums[j]-sums[i-1];
        }
    }

    @Test
    public void test(){
        int[] nums={-2, 0, 3, -5, 2, -1};
        NumArray numArray=new NumArray(nums);
        System.out.println(numArray.sumRange(0,2));
        System.out.println(numArray.sumRange(2,5));
        System.out.println(numArray.sumRange(0,5));
    }
}
