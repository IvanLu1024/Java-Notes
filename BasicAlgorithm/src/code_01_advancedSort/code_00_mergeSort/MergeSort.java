package code_01_advancedSort.code_00_mergeSort;

import code_00_basicSort.Sort;

/**
 * Created by 18351 on 2019/1/10.
 */
public abstract class MergeSort<T extends Comparable<T>> extends Sort<T> {
    protected T[] aux;//自定义的辅助数组

    /**
     * 合并[l,mid]和[mid+1,r]
     * 其中[l,mid]、[mid+1，r]是已经有序的序列
     */
    protected void merge(T[] arr,int l,int mid,int r){
       int k=l;
       //i数值在[l,mid]之间
       int i=l;
       //j数值在[mid+1,r]之间
       int j=mid+1;
       while(i<=mid && j<=r){
           if(less(arr[i],arr[j])){
               aux[k++]=arr[i++];
           }else{
               aux[k++]=arr[j++];
           }
       }
       while(i<=mid){
           aux[k++]=arr[i++];
       }
       while(j<=r){
           aux[k++]=arr[j++];
       }

       for(int index=l;index<=r;index++){
           arr[index]=aux[index];
       }
    }
}
