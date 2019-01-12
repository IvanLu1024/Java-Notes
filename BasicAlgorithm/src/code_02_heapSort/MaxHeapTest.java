package code_02_heapSort;

import java.util.Comparator;

/**
 * 最大堆：
 * 每个根节点的值大于等于子节点值
 */
public class MaxHeapTest{
    public static void main(String[] args) {
        MaxHeap<Integer> maxHeap=new MaxHeap<>(20);

        maxHeap.insert(3);
        maxHeap.insert(1);
        maxHeap.insert(5);
        maxHeap.insert(6);
        maxHeap.insert(8);
        maxHeap.insert(9);
        maxHeap.insert(-100);
        maxHeap.insert(10);
        maxHeap.insert(12);
        maxHeap.insert(13);
        maxHeap.insert(1000);
        maxHeap.insert(190);
        maxHeap.insert(-1);
        maxHeap.insert(-10);

        System.out.println(maxHeap.delMax());
        System.out.println(maxHeap.delMax());
        System.out.println(maxHeap.delMax());
        System.out.println(maxHeap.delMax());
        System.out.println(maxHeap.delMax());
        System.out.println(maxHeap.delMax());
        System.out.println(maxHeap.delMax());
    }
}
