package code_01_array;

import org.junit.Test;

/**
 * Given an array of integers nums sorted in ascending order, find the starting and ending position of a given target value.
 * Your algorithm's runtime complexity must be in the order of O(log n).
 * If the target is not found in the array, return [-1, -1].
 * Example 1:
 Input: nums = [5,7,7,8,8,10], target = 8
 Output: [3,4]

 * Example 2:
 Input: nums = [5,7,7,8,8,10], target = 6
 Output: [-1,-1]

 */
public class Code_34_FindFirstAndLast {
    public int[] searchRange(int[] nums, int target) {
        int[] res=new int[2];
        res[0]=binarySearchFirst(nums,target);
        res[1]=binarySearchLast(nums,target);
        return res;
    }

    //注意：这里是查找元素是target的第一个下标的二分查找
    private int binarySearchFirst(int[] nums,int target){
        int low=0;
        int hi=nums.length-1;
        int res=-1;
        while(low<=hi){
            int mid=low+(hi-low)/2;
            //mid-1是关键
            if(nums[mid]>=target) {
                hi = mid - 1;
            }else{
                low=mid+1;
            }
            if(nums[mid]==target){
                //不断res，一直到其是第一个target的下标
                res=mid;
            }
        }
        return res;
    }

    //注意：这里是查找元素是target的最后一个下标的二分查找
    private int binarySearchLast(int[] nums,int target){
        int low=0;
        int hi=nums.length-1;
        int res=-1;
        while(low<=hi){
            int mid=low+(hi-low)/2;
            //mid+1是关键
            if(nums[mid]<=target) {
                low= mid+1;
            }else{
                hi = mid - 1;
            }
            if(nums[mid]==target){
                //不断res，一直到其是最后一个target的下标
                res=mid;
            }
        }
        return res;
    }

    @Test
    public void test(){
        int[] nums={5,7,7,8,8,10};
        int target = 8;
        //int target=6;
        int[] res=searchRange(nums,target);
        for(int i:res){
            System.out.println(i);
        }
    }
}
