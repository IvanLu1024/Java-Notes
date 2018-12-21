package code_02_find;

/**
 * 706. Design HashMap
 *
 * Design a HashMap without using any built-in hash table libraries.

 To be specific, your design should include these functions:

 put(key, value) : Insert a (key, value) pair into the HashMap. If the value already exists in the HashMap, update the value.
 get(key): Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key.
 remove(key) : Remove the mapping for the value key if this map contains the mapping for the key.

*  Note:
 All keys and values will be in the range of [0, 1000000].
 The number of operations will be in the range of [1, 10000].
 Please do not use the built-in HashMap library.
 */
public class Code_706_DesignHashMap {
    /**
     * 思路：
     * 链表地址法，
     * 维护一个链表数组，数组的每个元素指向一个链表
     */
    class MyHashMap {
        //注意键值的范围是[1,1000000] 所以数组长度就是1000001
        private ListNode[] data;


        /** Initialize your data structure here. */
        public MyHashMap() {
            data=new ListNode[10000];
        }

        //根据key获取在数组中位置
        private int getIndex(int key){
            return Integer.hashCode(key) % data.length;
        }

        //data的元素就是一个链表，在链表中查找值为key的前一个元素，方便在链表尾部插入
        private ListNode find(ListNode bucket, int key) {
            ListNode node = bucket, prev = null;
            while (node != null && node.key != key) {
                prev = node;
                node = node.next;
            }
            return prev;
        }

        /** value will always be non-negative. */
        public void put(int key, int value) {
           int index=getIndex(key);
           if(data[index]==null){
               data[index]=new ListNode(-1,-1);
           }
            ListNode prev = find(data[index], key);
            if (prev.next == null){
                //不存在值为key的元素
                prev.next = new ListNode(key, value);
            }else{
                //存在值为key的元素，就直接更新
                prev.next.val = value;
            }
        }

        /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
        public int get(int key) {
            int index=getIndex(key);
            if(data[index]==null){
                return -1;
            }
            ListNode prev=find(data[index],key);
            return prev.next==null?-1:prev.next.val;
        }

        /** Removes the mapping of the specified value key if this map contains a mapping for the key */
        public void remove(int key) {
            int index=getIndex(key);
            if(data[index]==null){
                return;
            }
            ListNode prev=find(data[index],key);
            if(prev.next==null){
                //不存在值为key的元素
                return;
            }
            //直接删除值为key的元素
            prev.next=prev.next.next;
        }

        private class ListNode {
            int key, val;
            ListNode next;

            ListNode(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }
    }
}
