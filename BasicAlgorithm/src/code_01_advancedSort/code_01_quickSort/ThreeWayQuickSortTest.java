package code_01_advancedSort.code_01_quickSort;

import code_00_basicSort.SortTestHelper;

/**
 * Created by 18351 on 2019/1/10.
 */
public class ThreeWayQuickSortTest {
    public static void main(String[] args) {
        Integer[] arr={2,2,3,3,4,1,1,1,1,1,1};
        ThreeWayQuickSort sort=new ThreeWayQuickSort();
        sort.sort(arr);
        SortTestHelper.printArray(arr);
    }
}
