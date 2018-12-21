package code_01_array;

/**
 * 189. Rotate Array
 *
 * Given an array, rotate the array to the right by k steps, where k is non-negative.
 *
 */
public class Code_189_RotateArray {
    /**
     * 思路：
     * 三次反转数组即可
     * 注意：k的取值有可能超过数组长度，所以数组实际反转的步数是 k%数组长度
     */
    public void rotate(int[] nums, int k) {
        k=k%nums.length;
        reverse(nums,0,nums.length-1);
        reverse(nums,0,k-1);
        reverse(nums,k,nums.length-1);
    }

    //反转nums在[start...end]之间的数组
    private void reverse(int[] nums,int start,int end){
        if(start>end){
            return;
        }
        while(start<end){
            int tmp=nums[start];
            nums[start]=nums[end];
            nums[end]=tmp;
            start++;
            end--;
        }
    }
}
