package code_12_dataStructure;

/**
 * 795. Number of Subarrays with Bounded Maximum
 *
 * We are given an array A of positive integers, and two positive integers L and R (L <= R).
 * Return the number of (contiguous, non-empty) subarrays
 * such that the value of the maximum array element in that subarray is at least L and at most R.
 * (子数组最大值在L、R之间)
 */
public class Code_795_NumberOfSubarraysWithBoundedMaximum {
    /**
     * 思路：
     *
     * 子数组必须连续，利用最大值R对数组
     * (要求是连续数组，则该数组最大值在L和R之间，这旧要求子数组不能包含大于R的数组，则利用这样的数进行分段)进行分段，
     * 设定变量 left 记录每段开始位置（A[left]>R，A[left+1]≤R），初始值为-1。
     *
     * 遍历数组，通过A[i]大小判断：
     * (1)若 A[i] 在L、R之间，则 res+=(i-j)；
     * (2)若 A[i] 大于R，则更改左界 left=i；
     * (3)关键在于处理小于L的情况，由于需要子数组的最大值在L、R之间，单纯的 A[i] 肯定不能算，需要知道最近合法的数字，即右界。
     * 设定变量 right 记录合法的数字的右界（L≤A[right]≤R），
     * 当然，在A[i]大于R时，左界left和右界right都设置为i。这种情况下，res += (i-left)-(i-right)。
     */
    public int numSubarrayBoundedMax(int[] A, int L, int R) {
        int res = 0;
        int l = -1;
        int r = -1;
        for(int i=0;i<A.length;i++){
            if(A[i] > R){
                l = i;
                r = i;
            }else if(A[i]<L){
                res += (i-l)-(i-r);
            }else{
                res += (i-l);
                r = i;
            }
        }
        return res;
    }
}
