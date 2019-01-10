package code_00_basicSort.code_00_selectionSort;

import code_00_basicSort.Sort;

/**
 * Created by 18351 on 2019/1/10.
 */
public class SelectionSort<T extends Comparable<T>> extends Sort<T> {
    @Override
    public void sort(T[] arr) {
        int N= arr.length;
        for(int i=0;i<N-1;i++){
            int minIndex=i;
            for(int j=i+1;j<N;j++){
                if(less(arr[j],arr[minIndex])){
                    minIndex=j;
                }
            }
            swap(arr,minIndex,i);
        }
    }
}
