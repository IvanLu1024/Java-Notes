package code_01_advancedSort.code_00_mergeSort;

/**
 * 使用归并排序思路求解逆序对
 */
public class ReverseNum {
    /**
     * 暴力求解
     */
    public int getNum1(int[] arr){
        int res=0;
        int N=arr.length;
        for(int i=0;i<N-1;i++){
            for(int j=i+1;j<N;j++){
                if(arr[i]>arr[j]){
                    res++;
                }
            }
        }
        return res;
    }

    /**
     * 使用归并排序思路求解逆序对
     */
    public int getNum2(int[] arr){
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
        int res=0;
        res+=sort(arr,l,m);
        res+=sort(arr,m+1,r);
        res+=merge(arr,l,m,r);
        return res;
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
            if(arr[i]<=arr[j]){
                aux[k++]=arr[i++];
            }else{
                res+=mid-i+1;
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
