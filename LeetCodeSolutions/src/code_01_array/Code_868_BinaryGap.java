package code_01_array;

import org.junit.Test;

/**
 * 868. Binary Gap
 *
 * Given a positive integer N,
 * find and return the longest distance between two consecutive 1's in the binary representation of N.
 * If there aren't two consecutive 1's, return 0.
 */
public class Code_868_BinaryGap {
    /**
     * 思路：
     * 利用按位与运算，对将N转化为二进制，逐步统计1的距离
     */
    public int binaryGap(int N) {
        int res=0;
        //记录前一个1出现的位置
        int preIndex=-1;
        for(int i=0;i<32;i++){ //int是32bit所以取32,每次移动一位
            int ele=N & 1;
            if(ele==1){
                if(preIndex!=-1){
                    res=Math.max(res,i-preIndex);
                }
                preIndex=i;
            }
            N=N>>1;
        }
        return res;
    }

    @Test
    public void test(){
        int N=6;
        System.out.println(binaryGap(N));
    }
}
