package code_01_array;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by 18351 on 2018/10/26.
 */
public class ArrayUtils {
    private ArrayUtils(){ }

    /**
     * 生成 n个元素的随机数组，每个元素的随机范围为[rangeL,rangeR]
     * @param n
     * @param rangeL
     * @param rangeR
     * @return
     */
    public static Integer[] generateRandomArray(int n,int rangeL,int rangeR){
        assert rangeL<=rangeR;

        Integer[] arr=new Integer[n];
        for(int i=0;i<n;i++) {
            arr[i] = new Integer((int) Math.random() * (rangeR - rangeL + 1) + rangeL);
        }
        return arr;
    }

    /**
     * 生成有序的数组
     * @param n
     * @return
     */
    public static Integer[] generateOrderedArray(int n){

        Integer[] arr = new Integer[n];
        for( int i = 0 ; i < n ; i ++ ){
            arr[i] = new Integer(i);
        }
        return arr;
    }

    /**
     * 生成一个近乎有序的数组
     * @param n
     * @param swapTimes
     * @return
     */
    public static Integer[] generateNearlyOrderedArray(int n, int swapTimes){

        Integer[] arr = new Integer[n];
        for( int i = 0 ; i < n ; i ++ ){
            arr[i] = new Integer(i);
        }
        for( int i = 0 ; i < swapTimes ; i ++ ){
            int a = (int)(Math.random() * n);
            int b = (int)(Math.random() * n);
            int t = arr[a];
            arr[a] = arr[b];
            arr[b] = t;
        }

        return arr;
    }

    /**
     * 复制一个完整的数组
     * @param arr
     * @return
     */
    public static Integer[] copyArray(Integer[] arr){
        Integer[] newArr=new Integer[arr.length];
        for(int i=0;i<arr.length;i++){
            newArr[i]=arr[i];
        }
        return newArr;
    }

    /**
     * 打印数组
     * @param arr
     */
    public static void printArray(Comparable[] arr){
        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i]);
            System.out.print(' ');
        }
        System.out.println();
    }

    /**
     * 判断arr数组是否有序,默认是升序排列
     * @param arr
     * @return
     */
    public static boolean isSorted(Comparable[] arr){
        for(int i=0;i<arr.length-1;i++){
            if(arr[i].compareTo(arr[i+1])>0){
                return false;
            }
        }
        return true;
    }

    /**
     * 测试sortClassName所对应的排序算法排序arr数组所得到结果的正确性和算法运行时间
     */
    public static void testSort(String sortName,Comparable[] arr){
        // 通过Java的反射机制，通过排序的类名，运行排序函数
        try {
            Class sortClass=Class.forName(sortName);
            Method sortedMethod=sortClass.getMethod("sort",new Class[]{Comparable[].class});
            // 排序参数只有一个，是可比较数组arr
            Object[] params = new Object[]{arr};
            long startTime=System.currentTimeMillis();
            //调用排序方法
            sortedMethod.invoke(null,params);
            long endTime=System.currentTimeMillis();

            assert isSorted(arr);
            //判断arr是否有序

            System.out.println( sortClass.getSimpleName()+ " : " + (endTime-startTime) + "ms" );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

