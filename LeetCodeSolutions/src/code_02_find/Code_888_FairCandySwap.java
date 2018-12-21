package code_02_find;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * 888. Fair Candy Swap
 *
 * Alice and Bob have candy bars of different sizes:
 * A[i] is the size of the i-th bar of candy that Alice has,
 * and B[j] is the size of the j-th bar of candy that Bob has.
 * Since they are friends, they would like to exchange one candy bar each so that after the exchange,
 * they both have the same total amount of candy.
 * (The total amount of candy a person has is the sum of the sizes of candy bars they have.)
 *
 * Return an integer array ans where ans[0] is the size of the candy bar that Alice must exchange,
 * and ans[1] is the size of the candy bar that Bob must exchange.
 *
 * If there are multiple answers, you may return any one of them.  It is guaranteed an answer exists.
 *
 * Example 1:
 Input: A = [1,1], B = [2,2]
 Output: [1,2]

 * Example 2:
 Input: A = [1,2], B = [2,3]
 Output: [1,2]

 * Example 3:
 Input: A = [2], B = [1,3]
 Output: [2,3]

 * Example 4:
 Input: A = [1,2,5], B = [2,4]
 Output: [5,4]

 * Note:
 1 <= A.length <= 10000
 1 <= B.length <= 10000
 1 <= A[i] <= 100000
 1 <= B[i] <= 100000
 It is guaranteed that Alice and Bob have different total amounts of candy.
 It is guaranteed there exists an answer.
 */
public class Code_888_FairCandySwap {
    /**
     * 思路一：暴力法
     * 但是会超出时间限制
     */
    public int[] fairCandySwap1(int[] A, int[] B) {
        int[] res=new int[2];
        int sum=0;
        for(int i=0;i<A.length;i++){
            sum+=A[i];
        }
        for(int i=0;i<B.length;i++){
            sum+=B[i];
        }
        for(int i=0;i<A.length;i++){
            for(int j=0;j<B.length;j++){
                //A的i位置与B的j位置互换元素
                int res1=getSum(A,i)+B[j];
                int res2=getSum(B,j)+A[i];
                if(res1==sum/2 && res2==sum/2){
                    res[0]=A[i];
                    res[1]=B[j];
                    break;
                }
            }
        }
        return res;
    }

    //获取数组中元素和，除去交换位置i
    private int getSum(int[] arr,int swapIndex){
        int sum=0;
        for(int i=0;i<arr.length;i++){
            if(i!=swapIndex){
                sum+=arr[i];
            }
        }
        return sum;
    }

    public int[] fairCandySwap(int[] A, int[] B) {
        int[] res = new int[2];
        Set<Integer> setA=new HashSet<>();
        Set<Integer> setB=new HashSet<>();
        int sumA=0;
        int sumB=0;
        for(int i=0;i<A.length;i++){
            setA.add(A[i]);
            sumA+=A[i];
        }
        for(int i=0;i<B.length;i++){
            setB.add(B[i]);
            sumB+=B[i];
        }
        int sum=(sumA+sumB)/2;
        for(int a:A){
            if(setB.contains(sum-(sumA-a))){
                //(sumA-a) 表示数组中除去a元素的元素和
                //sum是交换后，数组和
                //sum-(sumA-a)就是要交换的元素值
                res[0]=a;
                res[1]=sum-(sumA-a);
                break;
            }
        }
        return res;
    }

    @Test
    public void test(){
        //int[] A = {1,2,5};
        //int[] B = {2,4};
        int[] A={2};
        int[] B={1,3};
        int[] res=fairCandySwap(A,B);
        for(int ele:res){
            System.out.println(ele);
        }
    }
}
