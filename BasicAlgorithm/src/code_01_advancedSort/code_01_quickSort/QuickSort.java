package code_01_advancedSort.code_01_quickSort;

import code_00_basicSort.Sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by 18351 on 2019/1/10.
 */
public class QuickSort<T extends Comparable<T>> extends Sort<T> {
    @Override
    public void sort(T[] arr) {
        shuffle(arr);
        sort(arr,0,arr.length-1);
    }

    private void sort(T[] arr,int l,int r){
        if(l>=r){
            return;
        }
        int j=partition(arr,l,r);
        sort(arr,l,j);
        sort(arr,j+1,r);
    }

    private int partition(T[] arr,int l,int r){
        T pivot=arr[l];
        int i=l;
        int j=r;

        while(i<j){
            //从数组的右端向左扫描找到第一个小于等于pivot的元素，交换这两个元素
            while(less(pivot,arr[j]) && i<j){
                j--;
            }
            arr[i]=arr[j];
            //从数组的左端向右扫描找到第一个大于等于pivot的元素，交换这两个元素
            while(less(arr[i],pivot) && i<j){
                i++;
            }
            arr[j]=arr[i];
        }
        arr[i]=pivot;
        return i;
    }

    //优化
    private void shuffle(T[] arr) {
        List<Comparable> list = Arrays.asList(arr);
        Collections.shuffle(list);
        list.toArray(arr);
    }
}
