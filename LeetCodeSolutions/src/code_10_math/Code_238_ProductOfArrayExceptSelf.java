package code_10_math;

import java.util.Arrays;

/**
 * 238. Product of Array Except Self
 *
 * Given an array nums of n integers where n > 1,
 * return an array output such that output[i] is equal to the product of all the elements of nums except nums[i].
 *
 * Example:
 Input:  [1,2,3,4]
 Output: [24,12,8,6]
 * Note: Please solve it without division and in O(n).
 * （要求时间复杂度为 O(N)，并且不能使用除法。）
 */
public class Code_238_ProductOfArrayExceptSelf {
    /**
     * 思路：
     * 分别计算两个数: left、right，其中 leftProduct[i]为nums[i]的左边元素的乘积，rightProduct[i]为nums[i]右边元素的乘积。
     * products[i] = left[i] * right[i]
     */
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] products = new int[n];
        int[] leftProducts= new int[n];
        int[] rightProducts= new int[n];
        Arrays.fill(products,1);
        Arrays.fill(leftProducts,1);
        Arrays.fill(rightProducts,1);

        for(int i=1;i<n;i++){
            leftProducts[i] = leftProducts[i-1] * nums[i-1];
        }

        for(int i=n-2;i>=0;i--){
            rightProducts[i] = rightProducts[i+1] * nums[i+1];
        }

        for(int i=0;i<n;i++){
            products[i] = leftProducts[i] * rightProducts[i];
        }
        return products;
    }
}
