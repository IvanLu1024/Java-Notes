package code_04_stackQueue;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * 优先队列的底层实现是堆
 */
public class PriorityQueueUsing {
    public static void main(String[] args) {
        Random random=new Random();

        //Java语言，默认情况下是最小堆
        PriorityQueue<Integer> pq=new PriorityQueue<>();
        for(int i=0;i<10;i++){
            int num=random.nextInt(100)+1;
            //产生[1,100]之间的随机数
            System.out.println("insert "+num+" into priority queue.");
            pq.add(num);
        }
        while(!pq.isEmpty()){
            int num=pq.poll();
            System.out.print(num+" ");
        }
        System.out.println();

        //底层使用最小堆
        PriorityQueue<Integer> pq2=new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                int num=o2-o1;
                return num;
            }
        });
        for(int i=0;i<10;i++){
            int num=random.nextInt(100)+1;
            //产生[1,100]之间的随机数
            System.out.println("insert "+num+" into priority queue.");
            pq2.add(num);
        }
        while(!pq2.isEmpty()){
            int num=pq2.poll();
            System.out.print(num+" ");
        }
        System.out.println();

        //自定义Comparator的优先队列
        PriorityQueue<Integer> pq3=new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                int num=o2%10-o1%10;
                //这里是按照个位上的数字，得到的最大堆
                return num;
            }
        });
        for(int i=0;i<10;i++){
            int num=random.nextInt(100)+1;
            //产生[1,100]之间的随机数
            System.out.println("insert "+num+" into priority queue.");
            pq3.add(num);
        }
        while(!pq3.isEmpty()){
            int num=pq3.poll();
            System.out.print(num+" ");
        }
        System.out.println();
    }
}
