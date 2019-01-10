package code_01_advancedSort.code_01_quickSort;

import code_00_basicSort.Sort;

/**
 * Created by 18351 on 2019/1/10.
 */
public class ThreeWayQuickSort<T extends Comparable<T>> extends Sort<T> {
    @Override
    public void sort(T[] arr) {
        sort(arr,0,arr.length-1);
    }

    private void sort(T[] arr,int l,int r){
        if(l>=r){
            return;
        }
        int lt = l, i = l + 1, gt = r;
        T v = arr[l];
        while(i<=gt){
            int cmp=arr[i].compareTo(v);
            if(cmp<0){
                swap(arr,lt++,i);
                i++;
            }else if(cmp>0){
                swap(arr,i,gt--);
            }else{
                i++;
            }
        }
        sort(arr,l,lt-1);
        sort(arr,gt+1,r);
    }
}
