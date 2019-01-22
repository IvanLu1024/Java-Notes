package code_10_math;

import java.util.Arrays;

/**
 * 169. Majority Element
 *
 * Given an array of size n, find the majority element.
 * The majority element is the element that appears more than ⌊ n/2 ⌋ times.
 *
 * You may assume that the array is non-empty and the majority element always exist in the array.
 *
 * Example 1:
 Input: [3,2,3]
 Output: 3

 * Example 2:
 Input: [2,2,1,1,1,2,2]
 Output: 2
 */
public class Code_169_MajorityElement {
    /**
     * 思路一：
     * 先对数组排序，最中间那个数出现次数一定多于 n / 2。
     */
    public int majorityElement1(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length/2];
    }

    /**
     * 思路二：
     * 利用 Boyer-Moore Majority Vote Algorithm 来解决这个问题，使得时间复杂度为 O(N)。
     * 可以这么理解该算法：
     * 使用 cnt 来统计一个元素出现的次数，当遍历到的元素和统计元素不相等时，令 cnt--。
     * 如果前面查找了 i 个元素，且 cnt == 0，说明前 i 个元素没有 majority，或者有 majority，
     * 但是出现的次数少于 i / 2，因为如果多于 i / 2 的话 cnt 就一定不会为 0。
     * 此时剩下的 n - i 个元素中，majority 的数目依然多于 (n - i) / 2，因此继续查找就能找出 majority。
     */
    public int majorityElement(int[] nums) {
        int cnt=0;
        //假设第一个元素是主元素
        int majority=nums[0];
        for(int i=0;i<nums.length;i++){
            int num=nums[i];
            /**
             * 如果前面查找了 i 个元素，且 cnt == 0，说明前 i 个元素没有 majority，
             * 或者有 majority，但是出现的次数少于 i / 2，因为如果多于 i / 2 的话 cnt 就一定不会为 0。
             */
            if(cnt==0){
                majority=num;
            }
            /**
             * 剩下的 n - i 个元素中，majority 的数目依然多于 (n - i) / 2，
             * 因此继续查找就能找出 majority。
             */
            if(majority==num){
                cnt++;
            }else{
                cnt--;
            }
        }
        return majority;
    }
}
