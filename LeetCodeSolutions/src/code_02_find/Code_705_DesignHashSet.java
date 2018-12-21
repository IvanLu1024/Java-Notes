package code_02_find;

/**
 * 705. Design HashSet
 *
 * Design a HashSet without using any built-in hash table libraries.
 * To be specific, your design should include these functions:
 add(value): Insert a value into the HashSet.
 contains(value) : Return whether the value exists in the HashSet or not.
 remove(value): Remove a value in the HashSet. If the value does not exist in the HashSet, do nothing.

 * Note:
 All values will be in the range of [0, 1000000].
 The number of operations will be in the range of [1, 10000].
 Please do not use the built-in HashSet library.
 （不使用任何内建的哈希表库设计一个哈希集合）
 *
 */
public class Code_705_DesignHashSet {
    /**
     * 思路：
     * 动态数组思路
     */
    class MyHashSet {
        private int[] data;
        private int capacity=10;
        private int size;

        private void resize(int newCapacity){
            int[] newdata=new int[newCapacity];
            for(int i=0;i<size;i++){
                newdata[i]=data[i];
            }
            data=newdata;
        }

        private int find(int key){
            for(int i=0;i<size;i++){
                if(data[i]==key){
                    return i;
                }
            }
            return -1;
        }

        /** Initialize your data structure here. */
        public MyHashSet() {
            data=new int[capacity];
            size=0;
        }

        public void add(int key) {
            if(!contains(key)){
                if(size==data.length){
                    resize(data.length*2);
                }
                data[size++]=key;
            }
        }

        public void remove(int key) {
            if(contains(key)){
                if(size==data.length/4 && data.length/2!=0){
                    resize(data.length/2);
                }
                int index=find(key);
                for(int i=index;i<size-1;i++){
                    data[i]=data[i+1];
                }
                size--;
            }
        }

        /** Returns true if this set contains the specified element */
        public boolean contains(int key) {
            if(find(key)!=-1){
                return true;
            }
            return false;
        }
    }
}
