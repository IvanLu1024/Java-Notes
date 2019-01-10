package code_00_basicSort;

/**
 * 随机生成算法测试用例
 */
public class SortTestHelper {
    // SortTestHelper不允许产生任何实例
    private SortTestHelper(){

    }

    //生成有n个整数元素的随机数组,每个元素的随机范围为[rangeL, rangeR]
    public static Integer[] generateRandomArray(int n,int rangeL,int rangeR){
        assert rangeL<=rangeR;

        Integer[] arr=new Integer[n];
        for(int i=0;i<n;i++){
            int randomElement=(int)Math.random()*(rangeR-rangeL+1)+rangeL;
            arr[i]=randomElement;
        }
        return arr;
    }

    //打印arr数组的所有内容
    public static void printArray(Object arr[]){
        StringBuffer buffer=new StringBuffer();
        buffer.append("[");
        for(int i=0;i<arr.length;i++){
            if(i==arr.length-1){
                buffer.append(arr[i]);
            }else{
                buffer.append(arr[i]+" ");
            }
        }
        buffer.append("]");
        System.out.println(buffer.toString());
    }

    // 判断arr数组是否有序
    public static boolean isSorted(Comparable[] arr){
        for( int i = 0 ; i < arr.length - 1 ; i ++ ){
            if( arr[i].compareTo(arr[i+1]) > 0 ){
                return false;
            }
        }
        return true;
    }
}
