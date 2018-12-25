package code_06_heapAndPriorityQueue;

import org.junit.Test;

import java.lang.annotation.Target;
import java.util.*;
import java.util.PriorityQueue;

/**
 * Created by 18351 on 2018/12/25.
 */
public class Solution347 {
    public List<Integer> topKFrequent(int[] nums, int k) {
        //统计数字出现的频率
        Map<Integer,Integer> map=new HashMap<>();
        for(int num:nums){
            int freq=map.get(num)==null?0:map.get(num);
            map.put(num,++freq);
        }

        //维护一个优先队列，最小堆，维护当前频率最高的元素
        PriorityQueue<Integer> priorityQueue=new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return map.get(a)-map.get(b);
            }
        });
        //pair存的是（频率，元素）的形式
        for(Integer num:map.keySet()){
            priorityQueue.add(num);
            if(priorityQueue.size()>k) {
                priorityQueue.poll();
            }
        }

        List<Integer> ret=new ArrayList<>();
        while(!priorityQueue.isEmpty()){
            ret.add(priorityQueue.poll());
        }
        Collections.reverse(ret);
        return ret;
    }

    @Test
    public void test(){
        int[] nums = {1,1,1,2,2,3};
        int k = 2;
        System.out.println(topKFrequent(nums,k));
    }
}