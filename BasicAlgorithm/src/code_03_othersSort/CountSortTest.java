package code_03_othersSort;

import code_00_basicSort.SortTestHelper;

/**
 * Created by 18351 on 2019/1/11.
 */
public class CountSortTest {
    public static void main(String[] args) {
        CountSort countSort=new CountSort();

        int[] arr = {10,9,8,7,6,5,4,3,2,1};
        countSort.sort(arr);
        for(int i=0;i<arr.length;i++){
            System.out.println(arr[i]);
        }
    }
}
