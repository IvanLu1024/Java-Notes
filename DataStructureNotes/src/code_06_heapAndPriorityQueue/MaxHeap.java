package code_06_heapAndPriorityQueue;

import code_00_array.Array;

import java.util.Comparator;

/**
 * Created by 18351 on 2018/12/24.
 */
public class MaxHeap<E extends Comparable<E>> {
    private E[] data;
    private int size;
    //记录堆中元素个数

    public MaxHeap(int capacity){
        data=(E[])new Comparable[capacity];
    }

    public MaxHeap(){
        this(10);
    }

    //返回堆中元素个数
    public int size(){
        return size;
    }

    //判断堆是否为空
    public boolean isEmpty(){
        return size==0;
    }

    //返回一个索引的父节点的索引
    private int parent(int index){
        if(index==0){
            throw new IllegalArgumentException("index-0 does not have parment");
        }
        return (index-1)/2;
    }

    //返回一个索引的左孩子节点的索引
    private int leftChild(int index){
        return 2*index+1;
    }

    //返回一个索引的左孩子节点的索引
    private int rightChild(int index){
        return 2*index+2;
    }

    private void swap(int i,int j){
        if(i<0 || i>=size || j<0 || j>=size){
            throw new IllegalArgumentException("Index is illegal");
        }
        E tmp=data[i];
        data[i]=data[j];
        data[j]=tmp;
    }

    //调整数组大小
    private void resize(int newCapacity){
        E[] newData=(E[])new Comparable[newCapacity];
        for(int i=0;i<size;i++){
            newData[i]=data[i];
        }
        data=newData;
    }

    public void add(E e){
        if(size==data.length){
            resize(data.length*2);
        }
        data[size]=e;
        size++;
        swim(size-1);
    }

    private void swim(int k){
        while(k>0 && data[k].compareTo(data[parent(k)])>0){
            swap(k,parent(k));
            k=parent(k);
        }
    }

    //查看堆中最大元素
    public E findMax(){
        if(size==0){
            throw new IllegalArgumentException("Can not find max when maxheap is empty!");
        }
        return data[0];
    }

    public E extractMax(){
        if(size==0){
            throw new IllegalArgumentException("MaxHeap is empty");
        }
        E ret=findMax();
        swap(0,size-1);
        size--;
        sink(0);
        return ret;
    }

    private void sink(int k){
        while (leftChild(k)<size){ //leftChild(k)<size 下标为k的元素存在左子树
            int j=leftChild(k);
            if(j+1<size &&
                    data[j].compareTo(data[j+1])<0){
                j=j+1;
            }
            //j是data[leftChild(k)]和data[rightChild(k)]的较大值的下标
            if(data[k].compareTo(data[j])>=0){
                break;
            }
            swap(k,j);
            k=j;
        }
    }
}