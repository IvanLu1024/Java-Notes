package code_01_stackAndQueue;

import code_00_array.Array;

/**
 * Created by 18351 on 2018/11/17.
 */
public class ArrayQueue<E> implements Queue<E> {
    Array<E> array;

    public ArrayQueue(int capacity){
        array=new Array<>(capacity);
    }

    public ArrayQueue(){
        array=new Array<>();
    }

    @Override
    public int getSize() {
        return array.getSize();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public void enqueue(E e) {
        array.addLast(e);
    }

    @Override
    public E dequeue() {
        return array.removeFirst();
    }

    @Override
    public E getFront() {
        return array.get(0);
    }

    @Override
    public String toString() {
        StringBuilder ret=new StringBuilder();
        ret.append("Queue: ");
        ret.append("font [");
        for(int i=0;i<array.getSize();i++){
            ret.append(array.get(i));
            if(i!=array.getSize()-1){
                ret.append(", ");
            }
        }
        ret.append("] tail");
        return ret.toString();
    }
}

