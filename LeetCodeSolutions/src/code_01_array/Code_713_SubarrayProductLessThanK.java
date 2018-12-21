package code_01_array;

import org.junit.Test;

/**
 * 713. Subarray Product(乘积) Less Than K
 *
 * Your are given an array of positive integers nums.
 * Count and print the number of (contiguous) subarrays
 * where the product of all the elements in the subarray is less than k.
 *
 * Example:
 Input: nums = [10, 5, 2, 6], k = 100
 Output: 8
 Explanation: The 8 subarrays that have product less than 100 are:
 [10], [5], [2], [6], [10, 5], [5, 2], [2, 6], [5, 2, 6].
 Note that [10, 5, 2] is not included as the product of 100 is not strictly less than k.
 */
public class Code_713_SubarrayProductLessThanK {
    /**
     * 思路：
     * 采用滑动窗口的解法：维护一个数字乘积刚好小于k的滑动窗口窗口，
     * 用变量l来记录其左边界的位置，右边界r就是当前遍历到的位置。
     * 遍历原数组，用product乘上当前遍历到的数字，
     * 然后进行while循环，如果product大于等于k，
     * 则滑动窗口的左边界需要向右移动一位，删除最左边的数字，那么少了一个数字，乘积就会改变，
     * 所以用product除以最左边的数字，然后左边右移一位，即l自增1。
     * 当我们确定了窗口的大小后，就可以统计子数组的个数了，就是窗口的大小。

     * 为什么子数组的个数就是窗口的大小？
     * 比如[5 2 6]这个窗口，k还是100，右边界刚滑到6这个位置，
     * 这个窗口的大小就是包含6的子数组乘积小于k的个数，即[6], [2 6], [5 2 6]，正好是3个。
     * 所以窗口每次向右增加一个数字，然后左边去掉需要去掉的数字后，
     * 窗口的大小就是新的子数组的个数，每次加到结果res中即可。
     *
     * 注意：
     * 这里要求子集的乘积值必须小于k
     */
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if(k<=1){
            return 0;
        }

        int l=0;
        int r=0;
        int res=0;
        //[l..r]表示的是乘积和<k的窗口
        int product=1;
        while(r<nums.length){
           product*=nums[r];
           while(product>=k){
               product/=nums[l];
               l++;
           }
           //r-l+1表示的就是[l...r]窗口的长度
           res+=(r-l+1);
           r++;
        }
        return res;
    }

    @Test
    public void test(){
        int[] arr= {10, 5, 2, 6};
        int k = 100;
        System.out.println(numSubarrayProductLessThanK(arr,k));
    }
}
