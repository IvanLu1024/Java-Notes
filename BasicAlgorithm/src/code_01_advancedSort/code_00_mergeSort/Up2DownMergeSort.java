package code_01_advancedSort.code_00_mergeSort;

/**
 * 将一个大数组分成两个小数组去求解。
 * 因为每次都将问题对半分成两个子问题，这种对半分的算法复杂度一般为 O(NlogN)。
 */
public class Up2DownMergeSort<T extends Comparable<T>> extends  MergeSort<T>{
    @Override
    public void sort(T[] arr) {
        //aux=new T[nums.length];//error 因为存在类型擦除
        //正确方式
        aux = (T[]) new Comparable[arr.length];
        sort(arr,0,arr.length-1);
    }

    private void sort(T[] arr,int l,int r){
        //l==r 就只有一个元素，已经排好序了
        if(l>=r){
            return;
        }
        int mid=(r-l)/2+l;
        sort(arr,l,mid);
        sort(arr,mid+1,r);
        merge(arr,l,mid,r);
    }
}



