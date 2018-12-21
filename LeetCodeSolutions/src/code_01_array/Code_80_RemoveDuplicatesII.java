package code_01_array;

/**
 * 80. Remove Duplicates from Sorted Array II
 *
 * Given a sorted array nums, remove the duplicates in-place such that duplicates appeared at most twice and return the new length.
 * Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
 *
 * Example 1:
 * Given nums = [1,1,1,2,2,3],
 * Your function should return length = 5, with the first five elements of nums being 1, 1, 2, 2 and 3 respectively.
 * It doesn't matter what you leave beyond the returned length.
 *
 * Example 2:
 * Given nums = [0,0,1,1,1,1,2,3,3],
 * Your function should return length = 7, with the first seven elements of nums being modified to 0, 0, 1, 1, 2, 3 and 3 respectively.
 * It doesn't matter what values are set beyond the returned length.
 */
public class Code_80_RemoveDuplicatesII {
    public int removeDuplicates(int[] nums) {
        if (nums.length<= 2){
            return nums.length;
        }

        int k = 1;
        //k=1时，前两个元素必然在数组中
        int cnt = 1;
        for(int i=1;i < nums.length;i++) {
            if (nums[i] != nums[i-1]) {
                //相邻元素不相等,元素个数为1
                cnt = 1;
                nums[k++] = nums[i];
            } else {
                //存在相邻元素
                if (cnt < 2) {
                    //cnt<2说明值已经进来1个元素，此时再加1个元素
                    nums[k++] = nums[i];
                    cnt++;
                }
            }
        }
        return k;
    }

    /**
     * 拓展：
     * 给定一个有序数组，对数组中的元素去重，使得原数组中每个元素最多保留n个。返回去重后数组的长度值。
     */
    public int removeDuplicates(int[] nums,int n) {
        if (nums.length<= n){
            return nums.length;
        }

        int k = 1;
        //k=1时，前两个元素必然在数组中
        int cnt = 1;
        for(int i=1;i < nums.length;i++) {
            if (nums[i] != nums[i-1]) {
                //相邻元素不相等,元素个数为1
                cnt = 1;
                nums[k++] = nums[i];
            } else {
                //存在相邻元素
                if (cnt < n) {
                    //cnt<2说明值已经进来1个元素，此时再加1个元素
                    nums[k++] = nums[i];
                    cnt++;
                }
            }
        }
        return k;
    }
}
