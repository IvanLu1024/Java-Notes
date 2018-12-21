package code_01_array;

import org.junit.Test;

/**
 * 153. Find Minimum in Rotated Sorted Array
 *
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 * (i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).
 * Find the minimum element.
 * You may assume no duplicate exists in the array.
 *
 * Example 1:
 Input: [3,4,5,1,2]
 Output: 1

 * Example 2:
 Input: [4,5,6,7,0,1,2]
 Output: 0
 */
public class Code_153_FindMinimumInRotatedSortedArray {
    public int findMin1(int[] nums) {
        for(int i=0;i<nums.length-1;i++){
            if(nums[i]>nums[i+1]){
                return nums[i+1];
            }
        }
        return nums[0];
    }

    public int findMin(int[] nums) {
        int n=nums.length;
        if(n==1){
            return nums[0];
        }
        if(n==2){
            return Math.min(nums[0],nums[1]);
        }
        //查找区间在[l,h)
        int l=0;
        int h=n-1;
        while(l<=h){
            if(l==h){
                return nums[l];
            }
            int mid=l+(h-l)/2;
            if(nums[mid]>nums[mid+1]){
                return nums[mid+1];
            }
            if(nums[mid]>nums[h]){
                l=mid;
            }else if(nums[mid]<nums[h]){
                h=mid;
            }
        }
        return nums[l];
    }

    @Test
    public void test(){
        //int[] arr={4,5,6,7,0,1,2};
        int[] arr={3,4,5,1,2};
        System.out.println(findMin(arr));
    }
}
