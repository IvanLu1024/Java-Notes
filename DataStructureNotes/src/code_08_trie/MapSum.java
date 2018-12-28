package code_08_trie;

/**
 * Created by 18351 on 2018/12/28.
 */
public class MapSum {
    private class Node{
        public Node[] next;
        public int value;

        public Node(int value){
            this.next=new Node[26];
            this.value=value;
        }

        public Node(){
            this(0);
        }
    }

    private Node root;

    /** Initialize your data structure here. */
    public MapSum() {
        root=new Node();
    }

    public void insert(String key, int val) {
        Node cur=root;
        for(int i=0;i<key.length();i++){
            char c=key.charAt(i);
            if(cur.next[c-'a']==null){
                cur.next[c-'a']=new Node();
            }
            cur=cur.next[c-'a'];
        }
        cur.value=val;
    }

    public int sum(String prefix) {
        Node cur=root;
        for(int i=0;i<prefix.length();i++){
            char c=prefix.charAt(i);
            if(cur.next[c-'a']==null){
                return 0;
            }
            cur=cur.next[c-'a'];
        }
        return sum(cur);
    }

    private int sum(Node node){
        //说明node是叶子结点
        if(node.next==null){
            return node.value;
        }
        int res=node.value;
        //node.next 是当前结点的所有的后继结点
        for(Node nextNode:node.next){
            if(nextNode!=null){
                res+=sum(nextNode);
            }
        }
        return res;
    }
}