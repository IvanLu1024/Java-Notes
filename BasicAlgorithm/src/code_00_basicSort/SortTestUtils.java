package code_00_basicSort;

/**
 * 测试算法的性能测试的工具类
 */
public class SortTestUtils {
    private SortTestUtils(){

    }

    //判断数组是否有序(按照升序排列)
    public static boolean isSorted(Comparable[] arr){
        for(int i=0;i<arr.length-1;i++){
            if(arr[i].compareTo(arr[i+1])>0){
                return false;
            }
        }
        return true;
    }

    // 测试sortClassName所对应的排序算法排序arr数组所得到结果的正确性和算法运行时间
    public static void testSort(Sort sort, Comparable[] arr){
        try{
            Class sortClass=sort.getClass();

            long startTime = System.currentTimeMillis();
            // 调用排序函数
            sort.sort(arr);
            long endTime = System.currentTimeMillis();

            assert isSorted( arr );

            System.out.println(sortClass.getSimpleName()+ " : " + (endTime-startTime) + "ms" );
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
