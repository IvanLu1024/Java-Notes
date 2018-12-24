package code_06_heapAndPriorityQueue;

import java.util.Random;

/**
 * Created by 18351 on 2018/12/24.
 */
public class HeapMain {
    public static void main(String[] args) {
        int n=1000000;
        MaxHeap<Integer> maxHeap=new MaxHeap<>();
        Random random=new Random();
        for(int i=0;i<n;i++){
            maxHeap.add(random.nextInt(Integer.MAX_VALUE));
        }
        int[] arr=new int[n];
        for(int i=0;i<arr.length;i++){
            arr[i]=maxHeap.extractMax();
        }

        for(int i=1;i<arr.length;i++){
            if(arr[i-1]<arr[i]){
                throw new IllegalArgumentException("Error");
            }
        }
        System.out.println("Complated");
    }
}
