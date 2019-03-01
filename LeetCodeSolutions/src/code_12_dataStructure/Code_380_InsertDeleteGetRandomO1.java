package code_12_dataStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 380. Insert Delete GetRandom O(1)
 *
 * Design a data structure that supports all following operations in average O(1) time.
 * insert(val): Inserts an item val to the set if not already present.
 * remove(val): Removes an item val from the set if present.
 * getRandom: Returns a random element from current set of elements.
 * Each element must have the same probability of being returned.

 * Example:
 // Init an empty set.
 RandomizedSet randomSet = new RandomizedSet();

 // Inserts 1 to the set. Returns true as 1 was inserted successfully.
 randomSet.insert(1);

 // Returns false as 2 does not exist in the set.
 randomSet.remove(2);

 // Inserts 2 to the set, returns true. Set now contains [1,2].
 randomSet.insert(2);

 // getRandom should return either 1 or 2 randomly.
 randomSet.getRandom();

 // Removes 1 from the set, returns true. Set now contains [2].
 randomSet.remove(1);

 // 2 was already in the set, so return false.
 randomSet.insert(2);

 // Since 2 is the only number in the set, getRandom always return 2.
 randomSet.getRandom();
 */
public class Code_380_InsertDeleteGetRandomO1 {
    class RandomizedSet {
        //存储插入的数据
        private ArrayList<Integer> data;
        //存储<value,index>，即存储该值和该值的下标
        private Map<Integer,Integer> valueIndex;
        private Random random;

        /** Initialize your data structure here. */
        public RandomizedSet() {
            data=new ArrayList<>();
            valueIndex=new HashMap<>();
            random=new Random();
        }

        /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
        public boolean insert(int val) {
            if(!valueIndex.containsKey(val)){
                valueIndex.put(val,data.size());
                data.add(val);
                return true;
            }
            return false;
        }

        /** Removes a value from the set. Returns true if the set contained the specified element. */
        public boolean remove(int val) {
            //这里讲要删除的元素交换到data的最后一个位置，实际上就是将最后一个元素值赋值到val位置，这样保证时间复杂度是O(1)
            if(valueIndex.containsKey(val)){
                //获取val位置
                int index=valueIndex.get(val);
                //val不是最后一个元素
                if(index!=data.size()-1){
                    //获取最后一个元素
                    int lastEle=data.get(data.size()-1);
                    //将最后一个元素值赋值到val位置
                    data.set(index,lastEle);
                    valueIndex.put(lastEle,index);
                }
                //删除data中最后一个元素
                data.remove(data.size()-1);
                valueIndex.remove(val);
                return true;
            }
            return false;
        }

        /** Get a random element from the set. */
        public int getRandom() {
            return data.get(random.nextInt(data.size()));
        }
    }
}
