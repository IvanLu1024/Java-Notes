package code_02_find;

import org.junit.Test;

import java.util.Arrays;

/**
 *869. Reordered Power of 2(2的幂)
 *
 * Starting with a positive integer N,
 * we reorder the digits in any order (including the original order) such that the leading digit is not zero.
 * Return true if and only if we can do this in a way such that the resulting number is a power of 2.
 *
 * Note:
 * 1 <= N <= 10^9
 */
public class Code_869_ReorderedPowerof2 {
    /**
     * 思路一：
     * 暴力解法-->列举N重排序后的所有可能结果
     */
    public boolean reorderedPowerOf21(int N) {
        char[] digits=(N+"").toCharArray();
        return generatePermutation(digits,0);
    }

    private boolean generatePermutation(char[] digits, int index){
        if(index==digits.length){
            return isPowerOf2(new String(digits));
        }
        for(int i=index;i<digits.length;i++){
            if(index>0 || (digits[i]-'0')>0){
                swap(digits,index,i);
                if(generatePermutation(digits,index+1)){
                    return true;
                }
                swap(digits,index,i);
            }
        }
        return false;
    }

    //判定一个数字是否是2的幂
    private boolean isPowerOf2(String digits) {
        int N=Integer.parseInt(digits);
        if((N & (N-1))==0){
            return true;
        }
        return false;
    }

    private void swap(char[] digits,int i,int j){
        char tmp=digits[i];
        digits[i]=digits[j];
        digits[j]=tmp;
    }

    /**
     * 思路二：
     * 先把int范围下的2的N幂算出来，然后一个一个验证给出的数能不能拼成。
     */
    public boolean reorderedPowerOf2(int N) {
       int[] cnt=count(N);
       for(int i=0;i<31;i++){
           if(Arrays.equals(cnt,count(1<<i))){
               return true;
           }
       }
       return false;
    }

    private int[] count(int num){
        //1 <= N <= 10^9,N的最大数字是10^9
        int[] res=new int[10];
        while(num>0){
            int index=num%10;
            res[index]++;
            num/=10;
        }
        return res;
    }

    @Test
    public void test(){
        int[] digits={1,2,3,0,9};
        int N=0;
        int p=1;
        for(int i=digits.length-1;i>=0;i--){
            N+=digits[i]*p;
            p*=10;
        }
        System.out.println(N);
    }
}
