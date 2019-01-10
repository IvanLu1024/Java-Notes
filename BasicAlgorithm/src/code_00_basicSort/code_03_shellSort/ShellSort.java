package code_00_basicSort.code_03_shellSort;

import code_00_basicSort.Sort;

/**
 * Created by 18351 on 2019/1/10.
 */
public class ShellSort<T extends Comparable<T>> extends Sort<T> {
    @Override
    public void sort(T[] arr) {
        int N=arr.length;
        int h=1;

        while(h < N/3){
            h=3*h+1;
            // 1, 4, 13, 40, ...
        }

        while(h>=1){
            //类比排序算法
            for(int i=h;i<N;i++){
                for(int j=i;j>=h && less(arr[j],arr[j-h]);j-=h){
                    swap(arr,j,j-h);
                }
            }
            h/=3;
        }
    }
}
