package code_11_redBlackTree;

import java.io.BufferedReader;

/**
 * Created by 18351 on 2018/12/31.
 */
public class RBTree<K extends Comparable<K>,V>{
    private static final boolean RED=true;
    private static final boolean BLACK=false;

    private class Node{
        public K key;
        public V value;
        public Node left,right;
        public boolean color;
        public Node(K key,V value){
            this.key=key;
            this.value=value;
            this.left=null;
            this.right=null;
            this.color=RED;
        }
    }

    private Node root;
    private int size;

    //判断节点是否是红色
    private boolean isRed(Node node){
        if(node==null){
            return BLACK;
        }
        return node.color;
    }

    //左旋转
    //   node                     x
    //  /   \     左旋转         /  \
    // T1   x   --------->   node   T3
    //     / \              /   \
    //    T2 T3            T1   T2
    private Node leftRotate(Node node){
        Node x=node.right;

        //左旋转
        node.right=x.left;
        x.left=node;

        //改变节点颜色
        x.color=node.color;
        node.color=RED;

        return x;
    }

    //右旋转
    //     node                   x
    //    /   \     右旋转       /  \
    //   x    T2   ------->   y   node
    //  / \                       /  \
    // y  T1                     T1  T2
    private Node rightRotate(Node node) {
        Node x=node.left;

        //右旋转
        node.left=x.right;
        x.right=node;

        x.color=node.color;
        node.color=RED;

        return x;
    }

    //颜色翻转
    private void flipColors(Node node){
        node.color=RED;
        node.left.color=BLACK;
        node.right.color=BLACK;
    }

    public void add(K key, V value) {
        root=add(root,key,value);
        //红黑树性质： 2.根节点是黑色的
        root.color=BLACK;
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

        //    黑
        //    /
        //   红
        //    \
        //    红
        //左旋转
        if(isRed(node.right) && !isRed(node.left)){
            node=leftRotate(node);
        }

        //    黑
        //    /
        //   红
        //   /
        // 红
        //右旋转
        if(isRed(node.left) && isRed(node.left.left)){
            node=rightRotate(node);
        }

        //     黑
        //    /  \
        //   红   红
        // 颜色翻转
        if(isRed(node.left) && isRed(node.right)){
            flipColors(node);
        }
        return node;
    }

    public V remove(K key) {
        Node node=getNode(root,key);
        if(node!=null){
            root=del(root,key);
            size--;
        }
        return null;
    }

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
            node.left=del(node.left,key);
            return node;
        }else if(key.compareTo(node.key)>0){
            node.right=del(node.right,key);
            return node;
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

    public boolean contains(K key) {
        return getNode(root,key)!=null;
    }

    public V get(K key) {
        Node node=getNode(root,key);
        return node==null?null:node.value;
    }

    public void set(K key, V newValue) {
        Node node=getNode(root,key);
        if(node==null){
            throw new IllegalArgumentException(key+" does not exist");
        }
        node.value=newValue;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size==0;
    }
}