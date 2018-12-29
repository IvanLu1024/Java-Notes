package code_10_avl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 18351 on 2018/12/29.
 */
public class AVLTree<K extends Comparable<K>,V>{
    private class Node{
        public K key;
        public V value;
        public Node left,right;
        public int height;
        public Node(K key,V value){
            this.key=key;
            this.value=value;
            this.left=null;
            this.right=null;
            //叶子节点的高度是1
            height=1;
        }
    }

    private Node root;
    private int size;

    public void add(K key, V value) {
        root=add(root,key,value);
    }

    //检查该树是否是平衡二叉树
    public boolean isBST(){
        List<K> keys=new ArrayList<>();
        inOrder(root,keys);
        for(int i=1;i<keys.size();i++){
            if(keys.get(i-1).compareTo(keys.get(i))>0){
                return false;
            }
        }
        return true;
    }

    //判断该二叉树是否是一棵平衡二叉树
    public boolean isBalancedTree(){
        return isBalancedTree(root);
    }

    private boolean isBalancedTree(Node node){
        if(node==null){
            return true;
        }
        int balancedFactor=getBalancedFactor(node);
        if(Math.abs(balancedFactor)>1){
            return false;
        }
        return isBalancedTree(node.left) && isBalancedTree(node.right);
    }

    private void inOrder(Node node, List<K> keys){
        if(node==null){
            return;
        }
        inOrder(node.left,keys);
        keys.add(node.key);
        inOrder(node.right,keys);
    }

    //计算节点的高度
    private int getNodeHeight(Node node){
        if(node==null){
            return 0;
        }
        return node.height;
    }

    //获取节点的平衡因子，左子树高度-右子树高度
    private int getBalancedFactor(Node node){
        if(node==null){
            return 0;
        }
        return getNodeHeight(node.left)-getNodeHeight(node.right);
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
        //更新height
        node.height=1+Math.max(getNodeHeight(node.left),getNodeHeight(node.right));
        //计算平衡因子
        int balancedFactor=getBalancedFactor(node);
        if(Math.abs(balancedFactor)>1){
            System.out.println("unblance:"+balancedFactor);
        }
        return node;
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
            throw new IllegalArgumentException(key+"does not exist");
        }
        node.value=newValue;
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

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size==0;
    }
}