package code_03_othersSort;

/**
 * Created by 18351 on 2019/1/11.
 */
public class MaxGap {
    public static int maxGap(int[] arr){
        if(arr==null || arr.length<2){
            return 0;
        }
        int len=arr.length;
        int min=Integer.MAX_VALUE;
        int max=Integer.MIN_VALUE;
        for(int i=0;i<len;i++){
            min=Math.min(min,arr[i]);
            max=Math.max(max,arr[i]);
        }
        if(max==min){
            return 0;
        }

        //记录该桶中是否有元素
        boolean[] hasNum=new boolean[len+1];
        //记录该桶中的最大值
        int[] maxs=new int[len+1];
        //记录该桶中的最小值
        int[] mins=new int[len+1];

        //遍历源数组
        //确定该数组元素所在桶编号，以及桶中的数据
        for(int i=0;i<len;i++){
            //计算该元素所在桶编号
            int bid=bucket(arr[i],len,min,max);
            maxs[bid]=hasNum[bid]?Math.max(maxs[bid],arr[i]):arr[i];
            mins[bid]=hasNum[bid]?Math.min(mins[bid],arr[i]):arr[i];
            hasNum[bid]=true;
        }

        //如果相邻两个桶A、B都不为空，则A桶中的最大值的相邻元素就是B桶中最小元素

        //先找到第一个有数组元素的桶
        int index=0;
        //记录第一个有元素的桶中最大值
        int lastMax=0;
        while(index<=len){
            if(hasNum[index++]){
                lastMax=maxs[index-1];
                break;
            }
        }
        int res=0;
        //从第一个有元素的桶开始，遍历各个桶
        while(index<=len){
            if(hasNum[index]){
                res=Math.max(res,mins[index]-lastMax);
                lastMax=maxs[index];
            }
            index++;
        }
        return res;
    }

    //计算该元素所在桶编号
    //使用long防止相乘时溢出
    //比如数组 [9,3,1,10]，max=10,min=1，则9所在的桶号就是 (9-1)*4/(10-1)=3
    //arr中有length个数，则min一定在第1个桶中，max一定在第(N+1)个桶中。
    private static int bucket(long num,long length,long min,long max){
        return (int)((num-min)*length /(max-min));
    }

    public static void main(String[] args) {
        int[] arr={9,3,1,10};
        System.out.println(maxGap(arr));
    }
}
