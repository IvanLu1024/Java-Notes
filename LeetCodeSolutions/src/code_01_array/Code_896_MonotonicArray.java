package code_01_array;

import org.junit.Test;

/**
 * 896. Monotonic Array
 *
 * An array is monotonic if it is either monotone increasing or monotone decreasing.
 * An array A is monotone increasing if for all i <= j, A[i] <= A[j].
 * An array A is monotone decreasing if for all i <= j, A[i] >= A[j].
 * Return true if and only if the given array A is monotonic.
 *
 * Example 1:
 Input: [1,2,2,3]
 Output: true

 * Example 2:
 Input: [6,5,4,4]
 Output: true

 * Example 3:
 Input: [1,3,2]
 Output: false

 * Example 4:
 Input: [1,2,4,5]
 Output: true

 * Example 5:
 Input: [1,1,1]
 Output: true

 * Note:
 1 <= A.length <= 50000
 -100000 <= A[i] <= 100000
 */
public class Code_896_MonotonicArray {
    /**
     * 思路一：暴力法
     * @param A
     * @return
     */
    public boolean isMonotonic1(int[] A) {
        if(A.length==1){
            return true;
        }
        int k=1;
        while(k<A.length){
            if( A[k]==A[0]){
                k++;
            }else{
                break;
            }
        }
        if(k==A.length){
            return true;
        }
        if(A[k-1]<=A[k]){
            //递增序列
            for(int i=1;i<A.length;i++){
                if(A[i-1]>A[i]){
                    return false;
                }
            }
        }else if(A[k-1]>=A[k]){
            //递增序列
            for(int i=1;i<A.length;i++){
                if(A[i-1]<A[i]){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 思路二：
     * 遍历数组，标记升序/降序情况。
     * 当发现后面的元素与前面的标记冲突时，则返回 false, 否则返回 true
     */
    public boolean isMonotonic(int[] A) {
        if (A.length == 1) {
            return true;
        }
        //flag=0表示等于，默认就是等于
        //flag=-1 表示降序
        //flag=1 表示升序
        int flag=0;
        for(int i=0;i<A.length-1;i++){
            if(A[i]<A[i+1]){
                if(flag==0){
                    flag=1;
                }else if(flag==-1){
                    return false;
                }
            }else if(A[i]>A[i+1]){
                if(flag==0){
                    flag=-1;
                }else if(flag==1){
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    public void test(){
        int[] A={4,4,4};
        System.out.println(isMonotonic(A));
    }
}
