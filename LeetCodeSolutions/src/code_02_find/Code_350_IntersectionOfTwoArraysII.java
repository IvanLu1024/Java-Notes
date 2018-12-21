package code_02_find;

import org.junit.Test;

import java.util.*;

/**
 * 350 .Given two arrays, write a function to compute their intersection.
 *
 * Example 1:
 * Input: nums1 = [1,2,2,1], nums2 = [2,2]
 * Output: [2,2]

 * Example 2:
 * Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
 * Output: [4,9]
 */
public class Code_350_IntersectionOfTwoArraysII {
    public int[] intersect(int[] nums1, int[] nums2) {
        Map<Integer,Integer> map=new HashMap<>();
        //map记录元素出现的次数
        for(int i=0;i<nums1.length;i++){
            Integer value=map.get(nums1[i]);
            value=(value==null?1:value+1);
            map.put(nums1[i],value);
        }

        List<Integer> list=new ArrayList<>();
        for (int j = 0; j < nums2.length; j++) {
            int c=map.get(nums2[j])==null?0:map.get(nums2[j]);
            //c=0，说明nums2[j]不是公共元素
            if(c>0){
                list.add(nums2[j]);
                map.put(nums2[j],map.get(nums2[j])-1);
                //map对应的键值频率要减1，相当于从nums1[]数组中取出一个值为nums2[j]的元素
            }
        }

        int[] result = new int[list.size()];
        for (int k = 0; k < list.size(); k++) {
            result[k] = list.get(k);
        }
        return result;
    }

    @Test
    public void test(){
        int[] nums={1,2,2,3,3};
        intersect(nums,nums);
    }
}
