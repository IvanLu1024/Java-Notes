package code_07_dp;

import org.junit.Test;

/**
 * Created by 18351 on 2018/11/8.
 */
public class Fib {
    public int fib(int n){
        if(n==0){
            return 0;
        }
        if(n==1){
            return 1;
        }
        return fib(n-1)+fib(n-2);
    }

    //改进上面的方法：采用记忆化搜索
    private int[] memo;

    public int fib2(int n){
        if(n==0){
            return 0;
        }
        if(n==1){
            return 1;
        }
        if(memo[n]==-1){
            //memo[n]==-1说明是首次计算，下一次遇到该值就直接返回了
            memo[n]=fib2(n-1)+fib2(n-2);
        }
        return memo[n];
    }

    @Test
    public void test(){
        //int n=10; //time:2.0921E-5s
        //int n=20; //time:0.009386947s
        //int n=30; //time:0.049079365s
        //int n=40;//time:1.157836107s
        int n=42; //time:2.560889825s
        //可以看到时间是成指数级增长的
        long startTime=System.nanoTime();
        int res=fib(n);
        long endTime=System.nanoTime();
        System.out.println("fib("+n+")="+res);
        System.out.println("time:"+(endTime-startTime)/1000000000.0+"s");
    }

    @Test
    public void test2(){
        //int n=10; //time:7.106E-6s
        //int n=20; //time:8.289E-6s
        //int n=30; //time:2.0132E-5s
        //int n=40;//time:1.2632E-5s
        int n=42; //time:1.1843E-5s
        //为memo分配空间，并且初始值设为-1,因为斐波那契数列中是没有-1的
        memo=new int[n+1];
        for(int i=0;i<n+1;i++){
            memo[i]=-1;
        }
        long startTime=System.nanoTime();
        int res=fib2(n);
        long endTime=System.nanoTime();
        System.out.println("fib2("+n+")="+res);
        System.out.println("time:"+(endTime-startTime)/1000000000.0+"s");
    }
}
