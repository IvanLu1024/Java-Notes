package code_10_avl.set;

/**
 * Created by 18351 on 2018/12/20.
 */
public interface Set<E> {
    void add(E e);
    void remove(E e);
    boolean contains(E e);
    int getSize();
    boolean isEmpty();
}
