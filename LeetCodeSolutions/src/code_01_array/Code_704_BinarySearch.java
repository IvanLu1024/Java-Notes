package code_01_array;

/**
 * 704. Binary Search
 *
 * Given a sorted (in ascending order) integer array nums of n elements and a target value,
 * write a function to search target in nums. If target exists, then return its index, otherwise return -1.
 */
public class Code_704_BinarySearch {
    public int search(int[] nums, int target) {
        int start=0;
        int end=nums.length-1;
        while(start<=end){
            int mid=start+(end-start)/2;
            if(target==nums[mid]){
                return mid;
            }else if(target<nums[mid]){
                end=mid-1;
            }else{
                start=mid+1;
            }
        }
        return -1;
    }
}
