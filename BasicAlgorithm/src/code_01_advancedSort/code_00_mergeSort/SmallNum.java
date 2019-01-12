package code_01_advancedSort.code_00_mergeSort;

/**
 * Created by 18351 on 2019/1/10.
 */
public class SmallNum {
    /**
     * 使用归并排序思路求解逆序对
     */
    public int getSmallNum(int[] arr){
        return sort(arr);
    }

    private int sort(int[] arr){
        return sort(arr,0,arr.length-1);
    }

    public int sort(int[] arr,int l,int r){
        if(l==r){
            return 0;
        }
        int m=l+(r-l)/2;
        return sort(arr,l,m)+sort(arr,m+1,r)+merge(arr,l,m,r);
    }

    public int merge(int[] arr,int l,int mid,int r){
        int[] aux=new int[r-l+1];
        int k=0;
        //i数值在[l,mid]之间
        int i=l;
        //j数值在[mid+1,r]之间
        int j=mid+1;
        int res=0;
        while(i<=mid && j<=r){
            if(arr[i]<arr[j]){
                //右侧(r-j+1)个元素都比arr[i]大
                //arr[i]对于这些元素就是小数
                res+=(r-j+1)*arr[i];
                aux[k++]=arr[i++];
            }else{
                aux[k++]=arr[j++];
            }
        }
        while(i<=mid){
            aux[k++]=arr[i++];
        }
        while(j<=r){
            aux[k++]=arr[j++];
        }

        //arr的[l,r]
        for(int index=0;index<k;index++){
            arr[index+l]=aux[index];
        }
        return res;
    }
}
