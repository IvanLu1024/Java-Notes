package code_01_advancedSort.code_00_mergeSort;

/**
 * Created by 18351 on 2019/1/10.
 */
public class ReverseNumTest {
    public static void main(String[] args) {
        ReverseNum reverseNum=new ReverseNum();

        int[] arr={5,4,2,6,3,1};


        System.out.println(reverseNum.getNum1(arr));
        System.out.println(reverseNum.getNum2(arr));

        //int[] arr1={1,2,6,3,4,5};
        //System.out.println(reverseNum.merge(arr1,0,2,5));

        //System.out.println(reverseNum.sort(arr1,0,5));
    }
}
