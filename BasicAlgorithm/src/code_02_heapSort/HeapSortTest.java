package code_02_heapSort;

import code_00_basicSort.SortTestHelper;
import code_00_basicSort.code_00_selectionSort.Student;

/**
 * Created by 18351 on 2019/1/11.
 */
public class HeapSortTest {
    public static void main(String[] args) {
        HeapSort heapSort=new HeapSort();

        // 测试Integer
        Integer[] a = {10,9,8,7,6,5,4,3,2,1};
        heapSort.sort(a);
        SortTestHelper.printArray(a);

        // 测试Double
        Double[] b = {4.4, 3.3, 2.2, 1.1};
        heapSort.sort(b);
        SortTestHelper.printArray(b);

        // 测试String
        String[] c = {"D", "C", "B", "A"};
        heapSort.sort(c);
        SortTestHelper.printArray(c);

        // 测试自定义的类 Student
        Student[] d = new Student[4];
        d[0] = new Student("D",90);
        d[1] = new Student("C",100);
        d[2] = new Student("B",95);
        d[3] = new Student("A",95);
        heapSort.sort(d);
        SortTestHelper.printArray(d);
    }
}
