package code_00_timeComplexity;

import org.junit.Test;

/**
 * Created by 18351 on 2018/10/27.
 */
public class Code_00_TimeComplexityTest {
    //数据规模倍乘测试
    //O(n)
    @Test
    public void test(){
        for(int i=10;i<=26;i++){
            //2^26 <= 10^8
            int n=(int)Math.pow(2,i);
            int[] arr=TimeComplextiyUtils.generateRandomArray(n,0,100000000);
            long startTime=System.currentTimeMillis();
            TimeComplextiyUtils.sum(n);
            long endTime=System.currentTimeMillis();
            System.out.println("data size 2^"
                    +i+"="+n+"\tTime cost:"+(endTime-startTime)+"ms");
        }
    }

    //O(n^2)
    @Test
    public void test2(){
        for(int i=10;i<=23;i++){
            int n=(int)Math.pow(2,i);
            int[] arr=TimeComplextiyUtils.generateRandomArray(n,0,100000000);
            long startTime=System.currentTimeMillis();
            TimeComplextiyUtils.selectionSort(arr,n);
            long endTime=System.currentTimeMillis();
            System.out.println("data size 2^"
                    +i+"="+n+"\tTime cost:"+(endTime-startTime)+"ms");
        }
    }

    //O(nlogn)
    @Test
    public void test3(){
        for( int i = 10 ; i <= 26 ; i ++ ){
            int n = (int)Math.pow(2,i);
            int[] arr = TimeComplextiyUtils.generateRandomArray(n, 0, 1000000000);

            long startTime = System.currentTimeMillis();
            TimeComplextiyUtils.mergeSort(arr,n);
            long endTime = System.currentTimeMillis();

            System.out.print("data size 2^" + i + " = " + n + "\t");
            System.out.println("Time cost: " + (endTime - startTime) + "ms");
        }
    }
}
