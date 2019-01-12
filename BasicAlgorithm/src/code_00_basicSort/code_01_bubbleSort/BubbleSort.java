package code_00_basicSort.code_01_bubbleSort;

import code_00_basicSort.Sort;

/**
 * Created by 18351 on 2019/1/10.
 */
public class BubbleSort<T extends Comparable<T>> extends Sort<T> {
    @Override
    public void sort(T[] arr) {
        int N=arr.length;
        //设定一个标记，若为true，则表示此次循环没有进行交换，也就是待排序列已经有序，排序已然完成。
        boolean hasSorted=false;
        for(int i=N-1;i>0 && !hasSorted;i--){
            hasSorted=true;
            for(int j=0;j<i;j++){
                if(less(arr[j+1],arr[j])){
                    hasSorted=false;
                    swap(arr,j+1,j);
                }
            }
        }
    }
}
