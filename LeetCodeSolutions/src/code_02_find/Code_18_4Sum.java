package code_02_find;

import org.junit.Test;

import java.util.*;

/**
 * Given an array nums of n integers and an integer target, are there elements a, b, c, and d in nums
 * such that a + b + c + d = target?
 * Find all unique quadruplets in the array which gives the sum of target.
 *
 * Given array nums = [1, 0, -1, 0, -2, 2], and target = 0.
 *
 * A solution set is:
 * [
 * [-1,  0, 0, 1],
 * [-2, -1, 1, 2],
 * [-2,  0, 0, 2]
 * ]
 *
 * Note:The solution set must not contain duplicate quadruplets.
 */
public class Code_18_4Sum {
    //返回的结果存在一些重复的解的情况，惭愧！
    public List<List<Integer>> fourSum1(int[] nums, int target) {
        List<List<Integer>> ret=new ArrayList<>();
        if(nums.length<4){
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
            //numCount就是num元素出现的次数
            int numCount=map.get(num);
            if(numCount>=4){
                //如果该元素出现的次数>=4,如果存在 4*num==target的情况，(num,num,num,num)就是其中的一个解
                if(4*num==target){
                    ret.add(Arrays.asList(num,num,num,num));
                }
            }
            if(numCount>=3){
                //如果该元素出现的次数>=3，
                int num2=target-3*num;
                if(num!=num2 && map.containsKey(num2)){
                    //num!=num2,防止出现（num,num,num,num）的情况
                    ret.add(Arrays.asList(num,num,num,num2));
                }
            }
            if(numCount>=2){
                for (int num2:map.keySet()){
                    if(num!=num2){
                        int num3 = target - 2 * num - num2;
                        //这里要保证 num！=num2  并且 num!=num3 并且num!=num2
                        if (num2 < num3 && map.containsKey(num2) && map.containsKey(num3)) {
                            ret.add(Arrays.asList(num, num, num2, num3));
                        }
                        if(num2==num3 && num<num3 && map.get(num2)>=2){
                            ret.add(Arrays.asList(num, num, num2, num3));
                        }
                    }
                }
            }
            for(int num2:map.keySet()){
                if(num!=num2){
                    for(int num3:map.keySet()){
                        if(num2!=num3){
                            int num4=target-num-num2-num3;
                            //这里要保证 num<num2<num3<num4
                            if(num>=num2 || num2>=num3 || num3>=num4 || !map.containsKey(num4)){
                                continue;
                            }
                            ret.add(Arrays.asList(num,num2,num3,num4));
                        }
                    }
                }
            }
        }
        return ret;
    }

    //思路二：对撞指针思路
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> ret=new ArrayList<>();
        if(nums.length<=3){
            return ret;
        }
        Arrays.sort(nums);

        //从第一个元素开始遍历，一直到倒数第四个元素
        for(int i=0;i<nums.length-3;i++){
            //相邻元素相等，就直接看下一个元素
            if(i>0 && nums[i-1]==nums[i]){
                continue;
            }
            if(nums[i]*4>target) {
                // Too Big!!太大了，后续只能更大(因为数组是按照升序排列的)，可以直接结束循环
                break;
            }
            if(nums[i]+3*nums[nums.length-1]<target){
                ////Too Small！太小了，当前值不需要再算，可以继续循环尝试后面的值。
                continue;
            }
            for(int j=i+1;j<nums.length-2;j++){
                //相邻元素相等，就直接看下一个元素
                if(j>i+1&&nums[j]==nums[j-1]){
                    continue;
                }
                if(nums[j]*3>target-nums[i]){
                    //Too Big！ 注意此时不能结束i的循环，因为j是移动的 当j移动到后面的时候继续i循环也sum可能变小
                    break;
                }
                if(nums[j]+2*nums[nums.length-1]<target-nums[i]){
                    continue;// Too Small
                }

                int begin=j+1;
                int end=nums.length-1;
                while(begin<end){
                    int sum=nums[i]+nums[j]+nums[begin]+nums[end];
                    if(sum==target){
                        ret.add(Arrays.asList(nums[i],nums[j],nums[begin],nums[end]));
                        //处理相邻的重复元素,否则存在重复的解
                        while(begin<end && nums[begin]==nums[begin+1]){begin++;}
                        while(begin<end && nums[end]==nums[end-1]){end--;}
                        begin++;
                        end--;
                    }else if(sum<target){
                        begin++;
                    }else{
                        end--;
                    }
                }
            }
        }
        return ret;
    }

    @Test
    public void test(){
        //int[] arr={1,0,-1,0,-2,2};
        //int target=0;

        //int[] arr={0,4,-5,2,-2,4,2,-1,4};
        //int target=12;

        //int[] arr={0,1,5,0,1,5,5,-4};
        //int target=11;

        //int[] arr={2,0,3,0,1,2,4};
        //int target=7;

        //int[] arr={-5,5,4,-3,0,0,4,-2};
        //int target=4;

        //Output:
//[[-9,-8,-4,1],[-9,-8,-2,-1],[-9,-9,-8,6],[-9,-9,-7,5],[-9,-9,-4,2],[-8,-7,-4,-1],[-7,-7,-4,-2],[-9,-7,-7,3],[-8,-7,-7,2],[-7,-7,-4,-2]]
        //Expected:
//[[-9,-8,-4,1],[-9,-8,-2,-1],[-9,-9,-8,6],[-9,-9,-7,5],[-9,-9,-4,2],[-8,-7,-4,-1],[-7,-7,-4,-2],[-9,-7,-7,3],[-8,-7,-7,2],]
        //

        int[] arr={3,1,-9,-9,9,-4,-2,5,10,6,8,-7,-8,-7,8,2,9,-1};
        int target=-20;
        List<List<Integer>> list=fourSum(arr,target);
        System.out.println(list);
    }
}
