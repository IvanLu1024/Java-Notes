package code_01_array;

/**
 *88 Merge Sorted Array
 *
 * Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.
 *
 * Note:
 * The number of elements initialized in nums1 and nums2 are m and n respectively.
 * You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold additional elements from nums2.

 * Example:
 * Input:
 * nums1 = [1,2,3,0,0,0], m = 3
 * nums2 = [2,5,6],       n = 3
 * Output:
 * [1,2,2,3,5,6]
 */
public class Code_88_MergeSortedArray {
    //思路：采用合并排序的思路,这里要想空间复杂度为O（1）,则将两个数组中元素从后向前合并.
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        //从后向前合并
        int index=m+n-1;
        int i=m-1;
        int j=n-1;
        while(i>=0 && j>=0){
            if(nums1[i]>nums2[j]){
                nums1[index--]=nums1[i--];
            }else{
                nums1[index--]=nums2[j--];
            }
        }
        //nums2仍然有剩余元素，且是最小的那部分元素，将这些元素直接复制到num1的前面
        while(j>=0){
            nums1[index--]=nums2[j--];
        }
    }
}
