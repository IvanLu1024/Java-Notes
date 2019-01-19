package code_10_math;

/**
 * 204. Count Primes
 * Count the number of prime numbers less than a non-negative number, n.
 */
public class Code_204_CountPrimes {
    /**
     * 思路：
     * 在每次找到一个素数时，将能被素数整除的数排除掉。
     */
    public int countPrimes(int n) {
        //下标对应 0--> n 的数据，元素值表示该元素是否是素数。true 表示不是素数，false表示是素数
        boolean[] notPrimes=new boolean[n+1];
        int count=0;
        //2 是最小的素数，i<n即可，因为是统计小于n的素数的个数
        for(int i=2;i<n;i++){
            if(notPrimes[i]){
                continue;
            }
            count++;
            for(long j=(long)(i)*i;j<n;j+=i){
                notPrimes[(int)j]=true;
            }
        }
        return count;
    }
}
