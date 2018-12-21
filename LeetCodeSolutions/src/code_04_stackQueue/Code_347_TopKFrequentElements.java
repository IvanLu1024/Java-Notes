package code_04_stackQueue;

import java.util.*;

/**
 * Given a non-empty array of integers, return the k most frequent elements.
 *
 * Example 1:
 * Input: nums = [1,1,1,2,2,3], k = 2
 * Output: [1,2]
 *
 * Example 2:
 * Input: nums = [1], k = 1
 * Output: [1]
 *
 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
 * Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
 */
public class Code_347_TopKFrequentElements {
    private class Pair implements Comparable<Pair> {
        int numFreq;
        int num;
        Pair(int numFreq,int num){
            this.numFreq=numFreq;
            this.num=num;
        }

        @Override
        public int compareTo(Pair o) {
            return this.numFreq-o.numFreq;
        }
    }

    public List<Integer> topKFrequent(int[] nums, int k) {
        //统计数字出现的频率
        Map<Integer,Integer> map=new HashMap<>();
        for(int num:nums){
            int freq=map.get(num)==null?0:map.get(num);
            map.put(num,++freq);
        }

        //维护一个优先队列，最小堆，维护当前频率最高的元素
        PriorityQueue<Pair> priorityQueue=new PriorityQueue<>();
        //pair存的是（频率，元素）的形式
        for(Integer num:map.keySet()){
            int numFreq=map.get(num);
            if(priorityQueue.size()==k){
                if(numFreq>priorityQueue.peek().numFreq){
                    priorityQueue.poll();
                    priorityQueue.add(new Pair(numFreq,num));
                }
            }else{
                priorityQueue.add(new Pair(numFreq,num));
            }
        }

        List<Integer> ret=new ArrayList<>();
        while(!priorityQueue.isEmpty()){
            ret.add(priorityQueue.poll().num);
        }
        return ret;
    }
}
