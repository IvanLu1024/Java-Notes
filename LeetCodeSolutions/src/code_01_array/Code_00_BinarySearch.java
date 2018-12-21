package code_01_array;
import org.junit.Test;

/**
 * Created by 18351 on 2018/10/26.
 */
public class Code_00_BinarySearch {
    //时间复杂度是O(n)
    //空间复杂度是O(1)
    public int binarySearch(Comparable[] arr,Comparable target){
        int l=0,r=arr.length-1; //在区间 [l,r]中查找target元素
        while(l<=r){ //当 l==r时，区间[l,r]仍然是有效的
            int mid=(r-l)/2+l;
            int cmp=arr[mid].compareTo(target);
            if(cmp==0){
                return mid;
            }else if(cmp>0){
                r=mid-1; //target在[l,mid-1]中
            }else{
                l=mid+1; //target在[mid+1,r]中
            }
        }
        return -1;
    }

    //二分查找的另一种写法
    public int binarySearch2(Comparable[] arr,Comparable target){
        int l=0,r=arr.length; //在区间 [l,r)中查找target元素
        while(l<r){ //当 l==r时，区间[l,r)是无效的
            int mid=(r-l)/2+l;
            int cmp=arr[mid].compareTo(target);
            if(cmp==0){
                return mid;
            }else if(cmp>0){
                r=mid; //target在[l,mid)中
            }else{
                l=mid+1; //target在[mid+1,r)中
            }
        }
        return -1;
    }

    /**
     * 小数据量测试
     */
    @Test
    public void test(){
        Integer[] arr={10,25,34,90,99,101};
        System.out.println(binarySearch(arr,25));
    }

    /**
     * 大数据量测试
     */
    @Test
    public void test2(){
        int n=10000000;
        Integer[] arr= ArrayUtils.generateOrderedArray(n);
                //ArrayUtils.generateOrderedArray(n);

        long startTime=System.currentTimeMillis();
        for(int i=0;i<n;i++){
            assert i==binarySearch2(arr,i);
        }
        long endTime=System.currentTimeMillis();
        System.out.println("binary Search complete.");
        System.out.println("Time cost:"+(endTime-startTime)+"ms");
    }
}
