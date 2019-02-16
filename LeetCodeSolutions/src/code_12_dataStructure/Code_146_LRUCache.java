package code_12_dataStructure;

import java.util.HashMap;

/**
 * Design and implement a data structure for Least Recently Used (LRU) cache.
 * It should support the following operations: get and put.
 *
 * get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
 * put(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.
 *
 * Follow up:
 * Could you do both operations in O(1) time complexity?
 */
public class Code_146_LRUCache {
    //双向链表的节点
    private class Node{
        int key;
        int value;
        Node pre,next;
        //创建的节点既没有前驱，也没有后继
        Node(int key,int value){
            this.key = key;
            this.value = value;
        }
    }

    class LRUCache {
        //双向链表的头节点和尾节点
        private Node head,tail;
        private int size;

        //存储 <key,该key所在的节点>
        private HashMap<Integer,Node> hashMap;

        //在该双向链表中删除 node节点
        // very important method
        private void remove(Node node){
            //node节点不是头节点
            if(node != head){
                //preNode 是 node的前一个节点
                Node preNode = node.pre;
                preNode.next = node.next;
            }else{
                //时候头结点，直接删除，
                //并且该节点的下一个节点就是头节点
                head = node.next;
            }
            if(node != tail){
                Node nextNode = node.next;
                nextNode.pre = node.pre;
            }else{
                tail = node.pre;
            }
            //注意这里不会销毁node节点
        }

        //将 node设置成头结点
        private void setHead(Node node){
            node.next = head;
            node.pre = null;

            if(head !=null){
                head.pre = node;
            }
            head = node;
            if(tail == null){
                tail = head;
            }
        }

        public LRUCache(int capacity) {
            size = capacity;
            hashMap = new HashMap<>();
        }

        public int get(int key) {
            if(hashMap.containsKey(key)){
                Node node = hashMap.get(key);

                //如果命中，则将该节点移动到头部(最近访问的节点都移动到头部)
                remove(node);
                //这里将 node从双向链表中删除，
                //但是没有删除该节点
                setHead(node);
                return node.value;
            }
            return -1;
        }

        public void put(int key, int value) {
            if(hashMap.containsKey(key)){
                Node node = hashMap.get(key);
                //存在key，则更新value值
                node.value=value;

                //如果命中，则将该节点移动到头部(最近访问的节点都移动到头部)
                remove(node);
                setHead(node);
            }else{
                Node node = new Node(key,value);
                setHead(node);
                hashMap.put(key,node);

                //如果 存入的节点过多，则删除多余的节点节点
                if(hashMap.size()>size){
                    Node delNode = hashMap.get(tail.key);
                    //从链表中原来的尾节点
                    remove(tail);
                    hashMap.remove(delNode.key);
                    //方便垃圾回收
                    delNode = null;
                }
            }
        }
    }
}
