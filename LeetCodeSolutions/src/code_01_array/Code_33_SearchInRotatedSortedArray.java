package code_01_array;

import org.junit.Test;

/**
 * 33. Search in Rotated Sorted Array
 *
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 (i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).
 You are given a target value to search. If found in the array return its index, otherwise return -1.
 You may assume no duplicate exists in the array.
 Your algorithm's runtime complexity must be in the order of O(log n).

 * Example 1:
 Input: nums = [4,5,6,7,0,1,2], target = 0
 Output: 4

 * Example 2:
 Input: nums = [4,5,6,7,0,1,2], target = 3
 Output: -1
 */
public class Code_33_SearchInRotatedSortedArray {
    public int search(int[] nums, int target) {
        int index=0;
        int i=1;
        while(i<nums.length){
            if(nums[i]<nums[i-1]){
                index=i;
            }
            i++;
        }
        if(index==0){
            return binarySearch(nums,0,nums.length,target);
        }
        int res=binarySearch(nums,0,index,target);
        if(res==-1){
            res=binarySearch(nums,index,nums.length,target);
        }
        return res;
    }

    //在[low,hi)之间查找
    private int binarySearch(int[] nums,int low,int hi,int target){
        while(low<hi){
            int mid=low+(hi-low)/2;
            if(nums[mid]==target){
                return mid;
            }else if(nums[mid]>target){
                hi= mid;
            }else{
                low=mid+1;
            }
        }
        return -1;
    }

    @Test
    public void test(){
        int[] nums={4,5,6,7,0,1,2};
        //int target = 0;
        int target = 3;
    }
}
