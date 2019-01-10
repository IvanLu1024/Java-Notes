package code_01_advancedSort.code_00_mergeSort;

import code_00_basicSort.Sort;

/**
 * Created by 18351 on 2019/1/10.
 */
public class Down2UpMergeSort<T extends Comparable<T>> extends MergeSort<T> {
    @Override
    public void sort(T[] arr) {
        int N=arr.length;
        aux=(T[])new Comparable[N];
        for(int sz=1;sz<N;sz+=sz){
            for(int lo=0;lo<N-sz;lo+=(sz+sz)){
                merge(arr,lo,lo+sz-1,Math.min(lo+sz+sz-1,N-1));
            }
        }
    }
}
