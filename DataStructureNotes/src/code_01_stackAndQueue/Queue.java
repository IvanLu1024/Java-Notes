package code_01_stackAndQueue;

/**
 * Created by 18351 on 2018/11/17.
 */
public interface Queue<E> {
    int getSize();
    boolean isEmpty();
    void enqueue(E e);
    E dequeue();
    E getFront();
}
