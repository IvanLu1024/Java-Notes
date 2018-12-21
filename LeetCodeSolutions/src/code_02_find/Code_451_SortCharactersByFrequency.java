package code_02_find;

import org.junit.Test;

import java.util.*;

/**
 * 451. Sort Characters By Frequency
 *
 * Given a string, sort it in decreasing order based on the frequency of characters.
 *
 * Example 1:
 * Input:
 * "tree"
 * Output:
 * "eert"
 * Explanation:
 * 'e' appears twice while 'r' and 't' both appear once.
 * So 'e' must appear before both 'r' and 't'. Therefore "eetr" is also a valid answer.

 * Example 2:
 * Input:
 * "cccaaa"
 * Output:
 * "cccaaa"
 * Explanation:
 * Both 'c' and 'a' appear three times, so "aaaccc" is also a valid answer.
 * Note that "cacaca" is incorrect, as the same characters must be together.

 * Example 3:
 * Input:
 * "Aabb"

 * Output:
 * "bbAa"

 * Explanation:
 * "bbaA" is also a valid answer, but "Aabb" is incorrect.
 * Note that 'A' and 'a' are treated as two different characters.
 */
public class Code_451_SortCharactersByFrequency {
    //字母区分大小写吗？区分
    //相同字母的顺序与小大写有关吗？无关
    public String frequencySort(String s) {
        //map存储字符及其出现的频率
        Map<Character,Integer> map=new HashMap<>();

        for(int i=0;i<s.length();i++){
            int freq=map.get(s.charAt(i))==null?0:map.get(s.charAt(i));
            map.put(s.charAt(i),++freq);
        }

        //通过优先队列对map按照值进行降序排序（优先队列默认是升序）
        PriorityQueue<Map.Entry<Character,Integer>> priorityQueue=
                new PriorityQueue<>(new Comparator<Map.Entry<Character, Integer>>() {
            @Override
            public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                return o2.getValue()-o1.getValue();
            }
        });
        priorityQueue.addAll(map.entrySet());

        StringBuilder builder=new StringBuilder();
        while(!priorityQueue.isEmpty()){
            Map.Entry<Character,Integer> e=priorityQueue.poll();
            for(int i=0;i<e.getValue();i++){
                builder.append(e.getKey());
            }
        }
        return builder.toString();
    }

    @Test
    public void test(){
        String s="tree";
        System.out.println(frequencySort(s));
    }
}
