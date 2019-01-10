package code_00_basicSort.code_00_selectionSort;

import code_00_basicSort.SortTestHelper;
import code_00_basicSort.SortTestUtils;

/**
 * Created by 18351 on 2019/1/10.
 */
public class SelectionSortTest {
    public static void main(String[] args) {
        SelectionSort selectionSort=new SelectionSort();

        // 测试Integer
        Integer[] a = {10,9,8,7,6,5,4,3,2,1};
        selectionSort.sort(a);
        SortTestHelper.printArray(a);

        // 测试Double
        Double[] b = {4.4, 3.3, 2.2, 1.1};
        selectionSort.sort(b);
        SortTestHelper.printArray(b);

        // 测试String
        String[] c = {"D", "C", "B", "A"};
        selectionSort.sort(c);
        SortTestHelper.printArray(c);

        // 测试自定义的类 Student
        Student[] d = new Student[4];
        d[0] = new Student("D",90);
        d[1] = new Student("C",100);
        d[2] = new Student("B",95);
        d[3] = new Student("A",95);
        selectionSort.sort(d);
        SortTestHelper.printArray(d);
    }
}
