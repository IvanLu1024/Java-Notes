package code_05_setAndMap;

/**
 * Created by 18351 on 2018/12/20.
 */
public class BSTSet<E extends Comparable<E>> implements Set<E> {
    private BST<E> bst;

    public BSTSet(){
        bst=new BST<>();
    }

    @Override
    public void add(E e) {
        bst.add(e);
    }

    @Override
    public void remove(E e) {
        bst.del(e);
    }

    @Override
    public boolean contains(E e) {
        return bst.contains(e);
    }

    @Override
    public int getSize() {
        return bst.size();
    }

    @Override
    public boolean isEmpty() {
        return bst.isEmpty();
    }
}
