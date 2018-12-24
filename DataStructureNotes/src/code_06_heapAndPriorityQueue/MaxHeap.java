package code_06_heapAndPriorityQueue;

import code_00_array.Array;

/**
 * Created by 18351 on 2018/12/24.
 */
public class MaxHeap<E extends Comparable<E>> {
    private Array<E> data;

    public MaxHeap(int capacity){
        data=new Array<>(capacity);
    }

    public MaxHeap(){
        data=new Array<>();
    }

    //返回堆中元素个数
    public int size(){
        return data.getSize();
    }

    //返回一个boolean，表示堆中是否为空
    public boolean isEmpty(){
        return data.isEmpty();
    }

    //一个索引所表示的父节点的索引
    private int parent(int index){
        if(index==0){
            throw new IllegalArgumentException("index--0 do not have parnet");
        }
        return (index-1)/2;
    }

    //一个索引所表示的左孩子节点的索引
    private int leftChild(int index){
        return 2*index+1;
    }

    //一个索引所表示的右孩子节点的索引
    private int rightChild(int index){
        return 2*index+2;
    }
}