package code_10_avl.map;

/**
 * Created by 18351 on 2018/12/22.
 */
public interface Map<K,V> {
    void add(K key, V value);
    V remove(K key);
    boolean contains(K key);
    V get(K key);
    void set(K key, V newValue);
    int getSize();
    boolean isEmpty();
}
