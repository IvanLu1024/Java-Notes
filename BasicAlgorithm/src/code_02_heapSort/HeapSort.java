package code_02_heapSort;

import code_00_basicSort.Sort;

/**
 * Created by 18351 on 2019/1/11.
 */
public class HeapSort<T extends Comparable<T>> extends Sort<T> {
    @Override
    public void sort(T[] arr) {
        int N=arr.length;
        for(int i=N/2;i>=0;i--){
            sink(arr,i,N);
        }

        while (N>1){
            swap(arr,0,N-1);
            N--;
            //与第一个元素交换后，最大元素就是arr[N-1]
            sink(arr,0,N);
        }
    }

    //下沉操作
    private void sink(T[] arr,int k,int N){
        while(leftChild(k)<N-1){
            int j=leftChild(k);
            if(j+1<N-1 && less(arr[j],arr[j+1])){
                j=rightChild(k);
            }
            if(!less(arr[k],arr[j])){
                break;
            }
            swap(arr,k,j);
            k=j;
        }
    }

    private int leftChild(int k){
        return 2*k+1;
    }

    private int rightChild(int k){
        return 2*k+2;
    }
}
