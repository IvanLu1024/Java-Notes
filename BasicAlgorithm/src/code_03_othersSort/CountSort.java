package code_03_othersSort;

import code_00_basicSort.Sort;

/**
 * Created by 18351 on 2019/1/11.
 */
public class CountSort{
    public void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }
        int[] bucket = new int[max + 1];
        for (int i = 0; i < arr.length; i++) {
            bucket[arr[i]]++;
        }
        int sortedIndex = 0;
        for (int j = 0; j < bucket.length; j++) {
            while (bucket[j]-- > 0) {
                arr[sortedIndex++] = j;
            }
        }
    }
}
