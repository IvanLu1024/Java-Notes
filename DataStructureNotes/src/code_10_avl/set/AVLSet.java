package code_10_avl.set;

import code_10_avl.AVLTree;

/**
 * Created by 18351 on 2018/12/31.
 */
public class AVLSet<K extends Comparable<K>> implements Set<K>{
    private AVLTree<K,Object> avlTree;

    public AVLSet(){
        avlTree=new AVLTree<>();
    }

    @Override
    public void add(K k) {
        avlTree.add(k,null);
    }

    @Override
    public void remove(K k) {
        avlTree.remove(k);
    }

    @Override
    public boolean contains(K k) {
        return avlTree.contains(k);
    }

    @Override
    public int getSize() {
        return avlTree.getSize();
    }

    @Override
    public boolean isEmpty() {
        return avlTree.isEmpty();
    }
}