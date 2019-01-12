package code_03_othersSort;

/**
 * Created by 18351 on 2019/1/11.
 */
public class RadixSortTest {
    public static void main(String[] args) {
        RadixSort sort=new RadixSort();
        int[] arr={3,44,38,5,47,15,36,26,27,28};
        sort.sort(arr);
        for(int i=0;i<arr.length;i++){
            System.out.println(arr[i]);
        }
    }
}
