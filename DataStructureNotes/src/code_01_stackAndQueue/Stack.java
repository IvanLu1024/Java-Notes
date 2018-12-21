package code_01_stackAndQueue;

/**
 * Created by 18351 on 2018/11/13.
 */
public interface Stack<E> {
    int getSize();
    boolean isEmpty();
    void push(E e);
    E pop();
    E peek();
}
