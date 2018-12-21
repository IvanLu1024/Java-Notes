package code_01_array;

import org.junit.Test;

/**
 *
 * 215. Kth Largest Element in an Array
 * Find the kth largest element in an unsorted array.
 * Note that it is the kth largest element in the sorted order, not the kth distinct element.
 *
 * Example 1:
 * Input: [3,2,1,5,6,4] and k = 2
 * Output: 5

 * Example 2:
 * Input: [3,2,3,1,2,4,5,5,6] and k = 4
 * Output: 4
 *
 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ array's length.
 */
public class Code_215_KthLargestElementInAnArray {
    //根据快速排序中partion划分，
    //这里找的是第k大的元素，言下之意，就是要找到第(nums.length-k+1)小的元素
    public int findKthLargest(int[] nums, int k) {
        int low=0;
        int high=nums.length-1;
        k=nums.length-k;
        while(low<high){
            int m=partition(nums,low,high);
            //m坐标元素的左边都小于 nums[m],右边都大于元素nums[i]
            if(m==k){
                break;
            }else if(m<k){ //说明
                low=m+1;
            }else if(m>k){
                high=m-1;
            }
        }
        return nums[k];
    }

    //在[l,r]中已nums[0]为pivot进行划分
    public int partition(int[] nums,int l,int r){
        int pivot=nums[l];
        while(l<r){
            //从右向左遍历，找出第一个<pivot的元素
            while(l<r && nums[r]>=pivot){
                r--;
            }
            nums[l]=nums[r];
            //从右向左遍历，找出第一个>pivot的元素
            while(l<r && nums[l]<=pivot){
                l++;
            }
            nums[r]=nums[l];
        }
        nums[l]=pivot;
        return l;
    }

    @Test
    public void test(){
        int[] nums={3,2,1,5,6,4};
        int index=partition(nums,0,nums.length-1);
        for(int num:nums){
            System.out.print(num+" ");
        }
        System.out.println();
        System.out.println(index);
        int index2=partition(nums,3,nums.length-1);
        for(int num:nums){
            System.out.print(num+" ");
        }
        System.out.println();
         int k = 2;
         int ele=findKthLargest(nums,k);
        System.out.println("ele:"+ele);
    }
}
