package code_02_heapSort;

import java.util.Comparator;

/**
 * 最大堆：
 * 每个根节点的值大于等于子节点值
 */
public class MaxHeap<T extends Comparable<T>> {
    private T[] heap;
    private int N;

    MaxHeap(int maxN){
        heap=(T[])new Comparable[maxN];
        this.N=0;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    private boolean less(int i, int j) {
        return heap[i].compareTo(heap[j]) < 0;
    }

    private void swap(int i, int j) {
        T t = heap[i];
        heap[i] = heap[j];
        heap[j] = t;
    }

    //返回一个索引的父节点的索引
    private int parent(int index){
        if(index==0){
            throw new IllegalArgumentException("index-0 does not have parment");
        }
        return (index-1)/2;
    }


    //获取index节点的左孩子索引
    private int leftChild(int index){
        assert index>=0 && index<N;
        return 2*index+1;
    }

    //获取index节点的右孩子索引
    private int rightChild(int index){
        assert index>=0 && index<N;
        return 2*index+2;
    }

    /**
     * 上浮操作
     * 在堆中，当一个节点比父节点大，那么需要交换这个两个节点。
     * 交换后还可能比它新的父节点大，因此需要不断地进行比较和交换操作，把这种操作称为上浮。
     */
    private void swim(int k){
        //less(parentIndex(k),k)
        while (k>0 && less(parent(k),k)){
            swap(k,parent(k));
            k=parent(k);
        }
    }

    /**
     * 下沉操作
     * 当一个节点比子节点来得小，也需要不断地向下进行比较和交换操作，把这种操作称为下沉。
     一个节点如果有两个子节点，应当与两个子节点中最大那个节点进行交换。
     */
    private void sink(int k){
        while(leftChild(k)<N){
            //leftChild(k)<size 下标为k的元素存在左子树
            int j=leftChild(k);
            if(j+1<N && less(leftChild(k),rightChild(k))){
                //j+1<N 表示存在右子树
                j=rightChild(k);
            }
            //j元素为两个子节点中最大那个节点小标
            if(less(j,k)){
                break;
            }
            swap(k,j);
            k=j;
        }
    }

    //插入元素
    public void insert(T v){
        heap[N]=v;
        swim(N);
        N++;
    }

    //删除最大元素
    public T delMax(){
        if(N==0){
            throw new IllegalArgumentException("Can not find max when maxheap is empty!");
        }
        T max=heap[0];
        //将根元素与最后一个元素交换
        swap(0,N-1);
        N--;
        //进行下沉操作
        sink(0);
        return max;
    }
}
