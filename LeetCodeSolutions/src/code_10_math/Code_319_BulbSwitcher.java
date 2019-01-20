package code_10_math;

import org.junit.Test;

/**
 * Created by 18351 on 2019/1/15.
 *
 * There are n bulbs that are initially off.
 * You first turn on all the bulbs.
 * Then, you turn off every second bulb.
 * On the third round, you toggle every third bulb
 * (turning on if it's off or turning off if it's on).
 * For the i-th round, you toggle every i bulb.
 * For the n-th round, you only toggle the last bulb. Find how many bulbs are on after n rounds.
 *
 * Example:
 Input: 3
 Output: 1
 Explanation:
 At first, the three bulbs are [off, off, off].
 After first round, the three bulbs are [on, on, on].
 After second round, the three bulbs are [on, off, on].
 After third round, the three bulbs are [on, off, off].
 */
public class Code_319_BulbSwitcher {
    /**
     * 思路一：
     *  第 1 轮，你打开所有的灯泡。
     *  第 2 轮，每两个灯泡你关闭一次。
     *  第 3 轮，每三个灯泡切换一次开关（如果关闭则开启，如果开启则关闭）。
     *  第 i 轮，每 i 个灯泡切换一次开关。
     *  对于第 n 轮，你只切换最后一个灯泡的开关。 找出 n 轮后有多少个亮着的灯泡。
     *
     *  但是超时了。
     */
    public int bulbSwitch1(int n) {
        if(n<=1){
            return n;
        }
        if(n==2){
            return 0;
        }

        int[] bulbs=new int[n];

        //第一轮
        //0表示灯是关的
        //1表示灯是开的
        for(int i=0;i<n;i++){
            bulbs[i] = 1;
        }

        //第二轮
        for(int i=1;i<=n;i++){
            if(i%2==0){
                bulbs[i-1]=0;
            }
        }

        //第三到(n-1)轮
        for (int k = 3; k < n; k++) {
            for(int i=1;i<=n;i++) {
                if(i % k ==0){
                    bulbs[i-1]=1-bulbs[i-1];
                }
            }
        }

        //第n轮
        bulbs[n-1]=1-bulbs[n-1];

       int count=0;
       for(int i=0;i<bulbs.length;i++){
           if(bulbs[i]==1){
               count++;
           }
       }
       return count;
    }

    /**
     * 思路二：
     * TODO：当一个灯泡被执行偶数次switch操作时它是关着的，
     * TODO：当被执行奇数次switch操作时它是开着的，那么这题就是要找出哪些编号的灯泡会被执行奇数次操作。
     *
     * 现在假如我们执行第i次操作，
     * 即从编号i开始对编号每次+i进行switch操作(i,2*i,3*i...)，
     * 对于这些灯来说，如果其编号j（j=1,2,3,⋯,n）能够整除i，则编号j的灯需要执行switch操作。
     * 具备这样性质的i是成对出现的，比如：
     * j=12时，编号为12的灯，在第1次，第12次；第2次，第6次；第3次，第4次一定会被执行Switch操作，这样的话，编号为12的灯肯定为灭。
     * 但是当完全平方数36就不一样了，因为他有一个特殊的因数6，这样当i=6时，只能被执行一次Switch操作，
     * 这样推出，完全平方数一定是亮着的，所以本题的关键在于找完全平方数的个数。
     */
    public int bulbSwitch(int n) {
        /*int i=0;
        while(i*i<=n){
            i++;
        }
        return i-1;*/
        return (int)Math.sqrt(n);
    }

    @Test
    public void test(){
        System.out.println(bulbSwitch(3));
    }
}
