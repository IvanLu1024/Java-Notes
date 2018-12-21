package code_00_timeComplexity;

import org.junit.Test;

/**
 * 均摊复杂度分析
 *
 * 以动态数组为例
 */
public class Code_01_MyVector {
    private int[] data;
    private int capacity;
    //数组中可以容纳的元素的最大的元素的个数
    private int size;
    //数组中的元素个数

    //初始化该动态数组
    public Code_01_MyVector(){
        capacity=10;
        data=new int[capacity];
        size=0;
    }

    //均摊时间复杂度O(1)
    public void push_back(int ele){
        if(size==capacity){
            //数组已经满了，这时候要为数组扩容
            resize(2*capacity);
        }
        data[size++]=ele;
    }

    //均摊时间复杂度O(1)
    public int pop_back(){
        assert size>0;
        int ret=data[size-1];
        if(size==capacity/2){
            resize(capacity/2);
        }
        return ret;
    }

    private void resize(int newCapacity) {
        assert  newCapacity>=size;
        int[] newData=new int[newCapacity];
        for(int i=0;i<size;i++){
            newData[i]=data[i];
        }
        data=newData;
        capacity=newCapacity;
    }

    @Test
    public void test() {
        for( int i = 10 ; i <= 26 ; i ++ ){
            int n = (int)Math.pow(2,i);

            long startTime = System.currentTimeMillis();
            Code_01_MyVector myVector=new Code_01_MyVector();
            for(int j=0;j<n;j++){
                myVector.push_back(j);
            }
            long endTime = System.currentTimeMillis();

            System.out.print("data size 2^" + i + " = " + n + "\t");
            System.out.println("Time cost: " + (endTime - startTime) + "ms");
        }
    }
}
