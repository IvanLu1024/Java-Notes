package code_00_basicSort.code_02_insertionSort;

import code_00_basicSort.Sort;

/**
 * 直接插入排序基本思想是每一步将一个待排序的记录，
 * 插入到前面已经排好序的有序序列中去，直到插完所有元素为止。
 */
public class InsertionSort<T extends Comparable<T>> extends Sort<T> {
    @Override
    public void sort(T[] arr) {
        int N=arr.length;
        for(int i=1;i<N;i++){
            for(int j=i;j>0 && less(arr[j],arr[j-1]);j--){
                swap(arr,j-1,j);
            }
        }
    }
}