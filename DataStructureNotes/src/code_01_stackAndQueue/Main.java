package code_01_stackAndQueue;

import java.util.Random;

/**
 * Created by 18351 on 2018/11/13.
 */
public class Main {
    public static void main(String[] args) {
       /* ArrayStack<Integer> stack=new ArrayStack<>();
        for(int i=0;i<5;i++){
            stack.push(i);
            System.out.println(stack);
        }
        stack.pop();
        System.out.println(stack);*/

      /* Queue<Integer> queue=new ArrayQueue<>();
       for(int i=0;i<10;i++){
           queue.enqueue(i);
           System.out.println(queue);
           if(i%3==2){
               queue.dequeue();
               System.out.println(queue);
           }
       }*/
       /* Queue<Integer> queue=new LoopQueue<>();
        for(int i=0;i<11;i++){
            queue.enqueue(i);
            System.out.println(queue);
            if(i%3==2){
                queue.dequeue();
                System.out.println(queue);
            }
        }*/

       int opCount=100000;
       ArrayQueue<Integer> arrayQueue=new ArrayQueue<>();
       double t1=testQueue(arrayQueue,opCount);
       System.out.println("Array Queue time:"+t1+"s");

        LoopQueue<Integer> loopQueue=new LoopQueue<>();
        double t2=testQueue(loopQueue,opCount);
        System.out.println("Loop Queue time:"+t2+"s");
    }

    //测试使用q运行opCount个enqueue和dequeue操作所需要的时间，单位：秒
    private static double testQueue(Queue<Integer> q,int opCount){
        long startTime=System.nanoTime();

        Random random=new Random();
        for(int i=0;i<opCount;i++){
            q.enqueue(random.nextInt(Integer.MAX_VALUE));
        }
        for(int i=0;i<opCount;i++){
            q.dequeue();
        }
        long endTime=System.nanoTime();
        return (endTime-startTime)/1000000000.0;
    }
}
