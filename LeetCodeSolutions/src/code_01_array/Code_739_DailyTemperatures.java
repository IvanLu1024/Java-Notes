package code_01_array;

import org.junit.Test;

import java.util.Stack;

/**
 * 739. Daily Temperatures
 *
 * Given a list of daily temperatures T, return a list such that,
 * for each day in the input, tells you how many days you would have to wait until a warmer temperature.
 * If there is no future day for which this is possible, put 0 instead.
 * For example, given the list of temperatures T = [73, 74, 75, 71, 69, 72, 76, 73],
 * your output should be [1, 1, 4, 2, 1, 1, 0, 0].

 * Note: The length of temperatures will be in the range [1, 30000].
 * Each temperature will be an integer in the range [30, 100]
 */
public class Code_739_DailyTemperatures {
    /**
     * 思路一：暴力解法
     * t通过break,缩短循环的执行时间
     */
    public int[] dailyTemperatures1(int[] T) {
        if(T.length==1){
            return new int[]{0};
        }
        int[] res=new int[T.length];
        for(int i=0;i<T.length;i++){
            for(int j=i+1;j<T.length;j++){
                if(T[i]<T[j]){
                    res[i]=j-i;
                    break;
                }
            }
        }
        return res;
    }

    /**
     * 思路二：递减栈法
     * 使用递减栈Descending Stack来做，
     * 栈里只有递减元素，思路是这样的，我们遍历数组，
     * 如果栈不空，且当前数字大于栈顶元素，那么如果直接入栈的话就不是递减栈了，
     * 所以我们取出栈顶元素，那么由于当前数字大于栈顶元素的数字，而且一定是第一个大于栈顶元素的数，那么我们直接求出下标差就是二者的距离了，
     * 然后继续看新的栈顶元素，直到当前数字小于等于栈顶元素停止，然后将数字入栈，这样就可以一直保持递减栈。
     */
    public int[] dailyTemperatures(int[] T) {
        if(T.length==1){
            return new int[]{0};
        }
        //保持递减栈，栈里存的是递减元素的下标
        Stack<Integer> stack=new Stack<>();
        int[] res=new int[T.length];
        for(int i=0;i<T.length;i++){
            while(!stack.isEmpty() && T[i]>T[stack.peek()]){
                int top=stack.pop();
                res[top]=i-top;
            }
            stack.push(i);
        }
        return res;
    }

    @Test
    public void test(){
        int[] T={73, 74, 75, 71, 69, 72, 76, 73};
        int[] arr=dailyTemperatures(T);
        for(int i:arr){
            System.out.println(i);
        }
    }
}
