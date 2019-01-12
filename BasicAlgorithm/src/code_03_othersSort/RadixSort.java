package code_03_othersSort;

/**
 * Created by 18351 on 2019/1/11.
 */
public class RadixSort {
    //取得数组中的最大数，并取得位数
    private int maxBits(int[] arr){
        int max=Integer.MIN_VALUE;
        for(int i=0;i<arr.length;i++){
            max=Math.max(max,arr[i]);
        }
        int res=0;
        while(max!=0){
            res++;
            max/=10;
        }
        return res;
    }

    //获取x数字d位上的数字
    private int getDigit(int x,int d){
        //根据d来决定是10、100、1000 ...
        int tmp=(int)Math.pow(10,d-1);
        return ((x / tmp) % 10);
    }

    //arr为原始数组，从最低位开始取每个位组成radix数组
    private void sort(int[] arr,int begin,int end,int digit){
        final int radix=10;
        //计数数组，用于计数排序
        int[] count=new int[radix];
        //元素小标 0-9,刚好任何数字的各个位上都是0-9的数字
        int[] bucket=new int[end-begin+1];

        int i=0;
        //j表示数组中元素上个位的数字
        int j=0;
        //根据位数对各个数进行排序
        for(int d=1;d<=digit;d++){
            for(i=0;i<radix;i++){
                count[i]=0;
            }
            for(i=begin;i<=end;i++){
                j=getDigit(arr[i],d);
                count[j]++;
            }
            for(i=1;i<radix;i++){
                count[i]=count[i]+count[i-1];
            }
            for(i=end;i>=begin;i--){
                j=getDigit(arr[i],d);
                bucket[count[j]-1]=arr[i];
                count[j]--;
            }
            for(i=begin,j=0;i<=end;i++,j++){
                arr[i]=bucket[i];
            }
        }
    }

    public void sort(int[] arr){
        if(arr==null || arr.length<2){
            return;
        }
        sort(arr,0,arr.length-1,maxBits(arr));
    }
}
