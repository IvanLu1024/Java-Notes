package code_12_others;

import org.junit.Test;

/**
 * 191. Number of 1 Bits
 *
 * Write a function that takes an unsigned integer and
 * returns the number of '1' bits it has (also known as the Hamming weight).
 *
 * Example 1:
 Input: 11
 Output: 3
 Explanation: Integer 11 has binary representation 00000000000000000000000000001011

 * Example 2:
 Input: 128
 Output: 1
 Explanation: Integer 128 has binary representation 00000000000000000000000010000000
 */
public class Code_191_NumberOf1Bits {
    // you need to treat n as an unsigned value
    public int hammingWeight1(int n) {
        //直接使用Java中方法
        return Integer.bitCount(n);
    }

    private int[] bit={
            0,1,1,2,
            1,2,2,3,
            1,2,2,3,
            2,3,3,4
    };

    public int hammingWeight2(int n){
        if(n==0){
            return 0;
        }
        int count=0;
        for(int i=0;i<8;i++){
            int num=n&0xf;
            count+=bit[num];
            n=n>>4;
        }
        return count;
    }

    public int hammingWeight(int n){
        if(n==0){
            return 0;
        }
        int res=0;
        for(int i=0;i<32;i++){
            res+=(n&1);
            n=(n>>1);
        }
        return res;
    }

    @Test
    public void test(){
        System.out.println(hammingWeight2(-8));
    }
}
