package code_02_find;

import java.util.HashMap;
import java.util.Map;

/**
 * Given a non-empty array of integers, every element appears twice except for one. Find that single one.
 * Note:
 *
 * Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?

 * Example 1:
 * Input: [2,2,1]
 * Output: 1
 * Example 2:

 * Input: [4,1,2,1,2]
 * Output: 4
 */
public class Code_136_SingleNumber {
    /**
     * 思路一：借助map
     * @param nums
     * @return
     */
    public int singleNumber1(int[] nums) {
        Map<Integer,Integer> map=new HashMap<>();
        for(int i=0;i<nums.length;i++){
            int freq=map.getOrDefault(nums[i],0);
            map.put(nums[i],++freq);
        }
        int ret=0;
        for(Integer key:map.keySet()){
            int value=map.get(key);
            if(value==1){
                ret=key;
                break;
            }
        }
        return ret;
    }

    /**
     * 思路二：
     * 既然除了一个整数之外其他的整数都是“成对出现”，那要是有办法把这个一对直接消去就好了。
     * 方法很简单，用异或（^）运算来进行消除，这样成对的整数会被消成0，最后0和单独的整数异或结果不变。
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        int ret=nums[0];
        for(int i=1;i<nums.length;i++){
            ret^=nums[i];
        }
        return ret;
    }
}
