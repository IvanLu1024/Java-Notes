package code_01_advancedSort.code_01_quickSort;

/**
 * Created by 18351 on 2019/1/10.
 */
public class SelectKTest {
    public static void main(String[] args) {
        int[] arr={1,2,3,4,5,6};

        SelectK selectK=new SelectK();
        //k的下标是从0开始的
        System.out.println(selectK.select(arr,2));
    }
}
