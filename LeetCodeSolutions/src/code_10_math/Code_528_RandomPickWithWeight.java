package code_10_math;

import org.junit.Test;

import java.util.Random;

/**
 * 528. Random Pick with Weight
 *
 * Given an array w of positive integers,where w[i] describes the weight of index i,
 * write a function pickIndex which randomly picks an index in proportion to(与某事物成比例) its weight.

 Note:
 * 1 <= w.length <= 10000
 * 1 <= w[i] <= 10^5
 * pickIndex will be called at most 10000 times.
 */
public class Code_528_RandomPickWithWeight {
    /**
     * 思路：前缀和数组
     * 比如对于数组[1,10,1000]，
     * 生成前缀和数组[1,11,1011]。
     * 那么如果我用1011的上限生成一个[0, 1011)的随机数字，
     * 那么我规定[0, 1)的数字归1管，[1, 11)的数字归10管，[11, 1011)的数字归1000管，那就ok了。
     * 生成随机数字target后，你用binary search找到“第一个>target的数字的下标”，就是我们的答案了。
     */
    class Solution {
        //前缀和数组
        private int[] sum;
        private Random random;

        public Solution(int[] w) {
            random=new Random();
            sum = new int[w.length];
            sum[0] = w[0];
            for(int i=1;i<w.length;i++){
                sum [i] = sum[i-1] + w[i];
            }
        }

        public int pickIndex() {
            int target = random.nextInt(sum[sum.length - 1]);
            int low = 0;
            int high = sum.length - 1;
            while (low < high){
                int mid = (high - low) /2 + low;
                if(sum[mid] <= target){
                    low = mid + 1;
                }else if(sum[mid] > target){
                    high = mid;
                }
            }
            return low;
        }
    }

    @Test
    public void test(){
        int[] nums={1,2,3,8,9,10};
        int[] sumNum= new int[nums.length];

        sumNum[0] = nums[0];
        for(int i=1;i<nums.length;i++){
            sumNum[i] = sumNum[i-1] + nums[i];
        }
        for(int i :sumNum){
            System.out.println(i);
        }
    }
}
