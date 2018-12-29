package code_10_avl;

import code_05_setAndMap.map.Map;

/**
 * Created by 18351 on 2018/12/22.
 */
public class BSTMap<K extends Comparable<K>,V> implements Map<K,V>{
    private class Node{
        public K key;
        public V value;
        public Node left,right;
        public Node(K key,V value){
            this.key=key;
            this.value=value;
            this.left=null;
            this.right=null;
        }
    }

    private Node root;
    private int size;

    //返回以node为根节点的二分搜索树中，key所在的节点
    private Node getNode(Node node,K key){
        if(node==null){
            return null;
        }
        if(key.compareTo(node.key)<0){
            return getNode(node.left,key);
        }else if(key.compareTo(node.key)>0){
            return getNode(node.right,key);
        }else{ //key.compareTo(node.key)==0
            return node;
        }
    }

    @Override
    public void add(K key, V value) {
        root=add(root,key,value);
    }

    private Node add(Node node,K key,V value){
        if(node==null){
            size++;
            return new Node(key,value);
        }
        if(key.compareTo(node.key)<0){
            node.left=add(node.left,key,value);
        }else if(key.compareTo(node.key)>0){
            node.right=add(node.right,key,value);
        }else{
            node.value=value;
        }
        return node;
    }



    @Override
    public V remove(K key) {
        Node node=getNode(root,key);
        if(node!=null){
            root=del(root,key);
            size--;
        }
        return null;
    }

    //获取Map中的最小的key
    private Node min(Node node){
        if(node.left==null){
            return node;
        }
        return min(node.left);
    }

    //删除以node为根结点的Map中的key最小的元素
    private Node delMin(Node node){
        if(node.left==null){
            Node nodeRight=node.right;
            node.right=null;
            size--;
            return nodeRight;
        }
        node.left=delMin(node.left);
        return node;
    }

    ////删除以node为根结点的Map中的键值为key的元素
    private Node del(Node node, K key){
        if(node==null){
            return null;
        }
        if(key.compareTo(node.key)<0){
            return del(node.left,key);
        }else if(key.compareTo(node.key)>0){
            return del(node.right,key);
        }else{
            //节点node就是要删除的节点
            //该节点只右有子树
            if(node.left==null){
                Node rightNode=node.right;
                node.right=null;
                size--;
                return rightNode;
            }
            //该节点只有左子树
            if(node.right==null){
                Node leftNode=node.left;
                node.left=null;
                size--;
                return leftNode;
            }
            //删除既有左子树又有右子树的节点
            Node s=min(node.right);
            s.right=delMin(node.right);
            s.left=node.left;

            //删除node
            node.left=node.right=null;
            return s;
        }
    }


    @Override
    public boolean contains(K key) {
        return getNode(root,key)!=null;
    }

    @Override
    public V get(K key) {
        Node node=getNode(root,key);
        return node==null?null:node.value;
    }

    @Override
    public void set(K key, V newValue) {
        Node node=getNode(root,key);
        if(node==null){
            throw new IllegalArgumentException(key+"does not exist");
        }
        node.value=newValue;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }
}
