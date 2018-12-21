package code_01_array;

import org.junit.Test;

/**
 * 26. Remove Duplicates from Sorted Array
 *
 * Given a sorted array nums, remove the duplicates in-place
 * such that each element appear only once and return the new length.
 * Do not allocate extra space for another array,
 * you must do this by modifying the input array in-place with O(1) extra memory.

 * Example 1:
 * Given nums = [1,1,2],
 * Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively.
 * It doesn't matter what you leave beyond the returned length.

 * Example 2:
 * Given nums = [0,0,1,1,1,2,2,3,3,4],
 * Your function should return length = 5, with the first five elements of nums being modified to 0, 1, 2, 3, and 4 respectively.
 * It doesn't matter what values are set beyond the returned length.
 */
public class Code_26_RemoveDuplicates {
    /**
     * 思路：
     * 1、准备一个指针k,[0,k]是没有重复元素
     * 2、这是一个有序数组，遍历数组，
     * 当nums[i]!=nums[k]就说明遇到另外一个值不相同的元素，将该元素加入[0，k]间中
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        if(nums.length==0){
            return 0;
        }
        int k=0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]!=nums[k]){
                //nums[i]是值不同的元素，且i>=k，此时[0,k]存储是不同元素
                k++;
                nums[k]=nums[i];
            }
        }
        return k+1;
    }

    @Test
    public void test(){
        int[] nums={1,2,3,4,5,5,8,8,8,8};
        System.out.println(removeDuplicates(nums));
    }
}
