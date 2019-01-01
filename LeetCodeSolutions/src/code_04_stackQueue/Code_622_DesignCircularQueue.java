package code_04_stackQueue;

/**
 * 622. Design Circular Queue
 *
 * Design your implementation of the circular queue. The circular queue is a linear data structure in which the operations are performed based on FIFO (First In First Out) principle and the last position is connected back to the first position to make a circle. It is also called "Ring Buffer".

 One of the benefits of the circular queue is that we can make use of the spaces in front of the queue. In a normal queue, once the queue becomes full, we cannot insert the next element even if there is a space in front of the queue. But using the circular queue, we can use the space to store new values.

 * Your implementation should support following operations:

 MyCircularQueue(k): Constructor, set the size of the queue to be k.
 Front: Get the front item from the queue. If the queue is empty, return -1.
 Rear: Get the last item from the queue. If the queue is empty, return -1.
 enQueue(value): Insert an element into the circular queue. Return true if the operation is successful.
 deQueue(): Delete an element from the circular queue. Return true if the operation is successful.
 isEmpty(): Checks whether the circular queue is empty or not.
 isFull(): Checks whether the circular queue is full or not.

 * Note:
 All values will be in the range of [0, 1000].
 The number of operations will be in the range of [1, 1000].
 Please do not use the built-in Queue library.
 */
public class Code_622_DesignCircularQueue {
    static class MyCircularQueue {
        private int[] data;

        //front==tail 队列为空
        //(tail+1)/data.length==front队列为空
        private int front,tail;
        private int size;

        /** Initialize your data structure here. Set the size of the queue to be k. */
        public MyCircularQueue(int k) {
            //损失一个1单位，用来区分队列是满的还是空的
            data=new int[k+1];
            front=0;
            tail=0;
            size=0;
        }

        /** Insert an element into the circular queue. Return true if the operation is successful. */
        public boolean enQueue(int value) {
            //先判断队列是否满了
            if(isFull()){
                return false;
            }
            data[tail]=value;
            tail=(tail+1)%data.length;
            size++;
            return true;
        }

        /** Delete an element from the circular queue. Return true if the operation is successful. */
        public boolean deQueue() {
            if(isEmpty()){
                return false;
            }
            //E保存出队的元素
            int E=data[front];
            front=(front+1)%data.length;
            size--;
            return true;
        }

        /** Get the front item from the queue. */
        public int Front() {
            if(isEmpty()){
                return -1;
            }
            return data[front];
        }

        /** Get the last item from the queue. */
        public int Rear() {
            if(isEmpty()) {
                return -1;
            }
            //注意这是循环队列，tail的值是循环变化的
            return data[(tail+data.length-1)%data.length];
        }

        /** Checks whether the circular queue is empty or not. */
        public boolean isEmpty() {
            return front==tail;
        }

        /** Checks whether the circular queue is full or not. */
        public boolean isFull() {
            return (tail+1)%data.length==front;
        }
    }

    public static void main(String[] args) {
        MyCircularQueue queue=new MyCircularQueue(3);
        System.out.println(queue.enQueue(1));
        System.out.println(queue.enQueue(2));
        System.out.println(queue.enQueue(3));
        System.out.println(queue.enQueue(4));
        System.out.println(queue.Rear());
        System.out.println(queue.isFull());
        System.out.println(queue.deQueue());
        System.out.println(queue.enQueue(4));
        System.out.println(queue.Rear());
    }
}
