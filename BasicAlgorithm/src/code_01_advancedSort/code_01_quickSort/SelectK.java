package code_01_advancedSort.code_01_quickSort;

/**
 * Created by 18351 on 2019/1/10.
 */
public class SelectK {
    public int select(int[] nums, int k) {
        int l = 0;
        int h = nums.length - 1;
        while (l < h) {
            int j = partition(nums, l, h);
            if (j == k) {
                return nums[k];
            } else if (j > k) {
                h = j - 1;
            } else {
                l = j + 1;
            }
        }
        return nums[k];
    }

    private int partition(int[] arr,int l,int r){
        int pivot=arr[l];
        int i=l;
        int j=r;
        while(i<j){
            //从右向左遍历，找出第一个 <= pivot的元素
            while(arr[j]>pivot && i<j){
                j--;
            }
            arr[i]=arr[j];
            //从左向右遍历,找出第一个 >= pivot的元素
            while(arr[i]<pivot && i<j){
                i++;
            }
            arr[j]=arr[i];
        }
        arr[i]=pivot;
        return i;
    }
}
