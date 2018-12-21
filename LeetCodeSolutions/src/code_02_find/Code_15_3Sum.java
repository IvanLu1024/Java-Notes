package code_02_find;

import org.junit.Test;

import java.util.*;

/**
 * 15. 3Sum
 *
 * Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.
 *
 * Note:
 *
 * The solution set must not contain duplicate triplets.
 *
 * Example:
 * Given array nums = [-1, 0, 1, 2, -1, -4],、
 * A solution set is:[[-1, 0, 1],[-1, -1, 2]]
 */
public class Code_15_3Sum {
    /**
     * 思路：
     * 类比twoSum中方法
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ret=new ArrayList<>();
        if(nums.length<3){
            return ret;
        }
        Map<Integer,Integer> map=new HashMap<>();
        //HashMap<Intager,Integer>,键存储的是该元素的值，值存储的是该元素出现的次数
        for(int num:nums){
            int freq=map.get(num)==null?0:map.get(num);
            map.put(num,++freq);
        }
        Set<Integer> keySet=map.keySet();
        for(Integer num:keySet){
            //num 表示数组中的元素值
            //numCount为该元素出现的次数
            int numCount=map.get(num);

            //三个相同元素相加的情况
            if(numCount>=3){
                 //num元素出现3次以上的，用该元素值相加和为0,只能是0了。
                if(num==0){
                    ret.add(Arrays.asList(0,0,0));
                }
            }

            //两个相同元素和另外一个元素相加的情况
            if(numCount>=2){
                //num元素出现次数为2,用该元素之和另外一个元素和为0，
                int target=0-2*num;
                if(map.containsKey(target) && target!=0){
                    //如果map中存在target值，则说明（num，num,target）是一组解
                    //target如果是0,那么就变成了（0,0,0）三个相同元素之和了
                    ret.add(Arrays.asList(num,num,target));
                }
            }

            //三个不同元素相加的情况
            for(Integer num2:keySet){
                int num3=0-num-num2;
                //假设 [num,num2,num3]是有序的并且num<num2<num3
                if(num>=num2 || num2>=num3 || map.get(num3)==null){
                    continue;
                }
                ret.add(Arrays.asList(num,num2,num3));
            }
        }
        return ret;
    }

    @Test
    public void test(){
        int[] arr={-1, 0, 1, 2, -1, -4};
        List<List<Integer>> list=threeSum(arr);
        System.out.println(list);
     }
}
