package code_01_array;

/**
 * 852. Peak Index in a Mountain Array
 *
 * Let's call an array A a mountain if the following properties hold:( A.length >= 3)
 There exists some 0 < i < A.length - 1 such that A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ... > A[A.length - 1]
 Given an array that is definitely a mountain,
 return any i such that A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ... > A[A.length - 1].

* Note:
 3 <= A.length <= 10000
 0 <= A[i] <= 10^6
 A is a mountain, as defined above.
 */
public class Code_852_PeakIndexInAMountainArray {
    /**
     * 思路一：
     * 寻找第一个下降位置的前一个位置
     * 时间复杂度：O（n）
     */
    public int peakIndexInMountainArray1(int[] A) {
        //寻找第一个下降位置
        for(int i=0;i<A.length-1;i++){
            if(A[i]>A[i+1]){
               return i;
            }
        }
        return -1;
    }

    /**
     * 思路二：使用二分查找
     * 找出某个位置使得其左边递增同时其右边递减。
     会有三种情况：
     （1）A[mid-1] < A[mid] && A[mid] < A[mid+1]
     （2）A[mid-1] > A[mid] && A[mid] > A[mid+1]
     （3）A[mid-1] < A[mid] && A[mid] > A[mid+1]
     */
    public int peakIndexInMountainArray(int[] A) {
        int l=0;
        int r=A.length-1;
        while(l<=r){
            int mid=l+(r-l)/2;
            if(A[mid-1]<A[mid] && A[mid]>A[mid+1]){
                return mid;
            }else if(A[mid-1] < A[mid] && A[mid] < A[mid+1]){
                l=mid+1;
            }else if(A[mid-1] > A[mid] && A[mid] > A[mid+1]){
                r=mid-1;
            }
        }
        return -1;
    }
}
