package code_02_find;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 697. Degree of an Array
 *
 * Given a non-empty array of non-negative integers nums,
 * TODO:the degree of this array is defined as the maximum frequency of any one of its elements.
 * Your task is to find the smallest possible length of a (contiguous) subarray of nums,
 * that has the same degree as nums.
 *
 * Example 1:
 Input: [1, 2, 2, 3, 1]
 Output: 2
 Explanation:
 The input array has a degree of 2 because both elements 1 and 2 appear twice.
 Of the subarrays that have the same degree:
 [1, 2, 2, 3, 1], [1, 2, 2, 3], [2, 2, 3, 1], [1, 2, 2], [2, 2, 3], [2, 2]
 The shortest length is 2. So return 2.

* Example 2:
 Input: [1,2,2,3,1,4,2]
 Output: 6

* Note:
 nums.length will be between 1 and 50,000.
 nums[i] will be an integer between 0 and 49,999.
 */
public class Code_697_DegreeOfAnArray {
    //pos记录元素的起始位置和终止位置（中间可能包含其他元素）
    //比如 [2,2,1,2]中2元素的起始位置是0，终止位置是3（这中间包括了1元素）
    private class Pos{
        int start;
        int end;
        public Pos(int start,int end){
            this.start=start;
            this.end=end;
        }

        @Override
        public String toString() {
            StringBuilder builder=new StringBuilder();
            builder.append("[").append(start+","+end).append("]");
            return builder.toString();
        }
    }

    public int findShortestSubArray(int[] nums) {
        if(nums.length<=1){
            return nums.length;
        }
        //记录元素及其出现的频率
        Map<Integer,Integer> freq=new HashMap<>();
        //记录该元素的起始位置和终止位置
        Map<Integer,Pos> pos=new HashMap<>();
        for(int i=0;i<nums.length;i++){
            int num=nums[i];
            if(freq.get(num)==null){
                freq.put(num,0);
                pos.put(num,new Pos(i,i));
            }else{
                freq.put(num,freq.get(num)+1);
                pos.get(num).end=i;
            }
        }

        //获取该数组的度
        int degree=0;
        for(Integer key:freq.keySet()){
            Integer f=freq.get(key);
            degree=Math.max(degree,f);
        }

        //看看度为degree的元素，哪个是结果
        int len=nums.length;
        for(Integer num:freq.keySet()){
            if(freq.get(num)==degree){
                len=Math.min(len,pos.get(num).end-pos.get(num).start+1);
            }
        }
        return len;
    }

    @Test
    public void test(){
        int[] nums={1,2,2,3,1,4,2};
        System.out.println(findShortestSubArray(nums));
    }
}
