package code_04_stackQueue;

import org.junit.Test;

import java.util.*;

/**
 * 692. Top K Frequent Words
 *
 * Example 1:
 Input: ["i", "love", "leetcode", "i", "love", "coding"], k = 2
 Output: ["i", "love"]
 Explanation: "i" and "love" are the two most frequent words.
 Note that "i" comes before "love" due to a lower alphabetical order.

 * Example 2:
 Input: ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], k = 4
 Output: ["the", "is", "sunny", "day"]
 Explanation: "the", "is", "sunny" and "day" are the four most frequent words,
 with the number of occurrence being 4, 3, 2 and 1 respectively.

 * Note:
 You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
 Input words contain only lowercase letters.
 */
public class Code_692_TopKFrequentWords {
    public List<String> topKFrequent(String[] words, int k) {
        List<String> res=new ArrayList<>();
        PriorityQueue<Pair> pq=new PriorityQueue<>(new Comparator<Pair>() {
            @Override
            public int compare(Pair p1, Pair p2) {
                //先按照词频进行升序排列
                int num=p1.freq-p2.freq;
                //再按照word种地字母顺序进行排序
                int num2=(num==0)?p2.word.compareTo(p1.word):num;
                return num2;
            }
        });

        //统计单词及词频
        HashMap<String,Integer> map=new HashMap<>();
        for(String word:words){
            int freq=map.getOrDefault(word,0);
            map.put(word,++freq);
        }

        for(String word:map.keySet()){
            pq.add(new Pair(word,map.get(word)));
            if (pq.size() > k){
                pq.poll();
            }
        }

        while(!pq.isEmpty()){
            Pair p=pq.poll();
            res.add(p.word);
        }
        Collections.reverse(res);
        return res;
    }

    //封装单词和对应的词频
    private class Pair{
        String word;
        int freq;
        public Pair(String word,int freq){
               this.word=word;
               this.freq=freq;
        }
    }

    @Test
    public void test(){
        String[] arr={"i", "love", "leetcode", "i", "love", "coding"};
        int k = 1;
        //String[] arr={"the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"};
        //int k = 4;
        System.out.println(topKFrequent(arr,k));
    }
}
