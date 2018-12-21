package code_04_stackQueue;

import java.util.Stack;

/**
 * Implement the following operations of a queue using stacks.
 push(x) -- Push element x to the back of queue.
 pop() -- Removes the element from in front of queue.
 peek() -- Get the front element.
 empty() -- Return whether the queue is empty.

 Example:

 MyQueue queue = new MyQueue();
 queue.push(1);
 queue.push(2);
 queue.peek();  // returns 1
 queue.pop();   // returns 1
 queue.empty(); // returns false
 */
public class Code_232_ImplementQueueUsingStacks {
    /**
     * 思路一：
     * 使用一个栈来存储数据
     * 在push时使用另外一个栈进行转换
     * 使之符合队列FIFO的特性
     */
    class MyQueue1 {
        Stack<Integer> s;

        /** Initialize your data structure here. */
        public MyQueue1() {
           s=new Stack<>();
        }

        /** Push element x to the back of queue. */
        public void push(int x) {
            Stack<Integer> s2=new Stack<>();
            //先将s中元素放入s2中
            while(!s.isEmpty()){
                s2.push(s.pop());
            }
            s.push(x);
            //再将s2中元素放入s中
            while(!s2.isEmpty()){
                s.push(s2.pop());
            }
        }

        /** Removes the element from in front of queue and returns that element. */
        public int pop() {
            return s.pop();
        }

        /** Get the front element. */
        public int peek() {
            return s.peek();
        }

        /** Returns whether the queue is empty. */
        public boolean empty() {
            return s.isEmpty();
        }
    }

    /**
     * 思路二：
     * 准备两个栈
     * 一个栈s1用于入队操作，若栈为空，此时进入的元素就是队首元素
     * 一个栈s2用于出队操作，若栈为空，则将s1中元素压入s2中，弹出s2的栈顶元素即可
     * 和一个front存储队列头元素
     */
    class MyQueue {
        //入队操作使用
        private Stack<Integer> s1;
        //出队操作是使用
        private Stack<Integer> s2;
        //存储队列头元素
        private int front;

        /** Initialize your data structure here. */
        public MyQueue() {
            s1=new Stack<>();
            s2=new Stack<>();
        }

        /** Push element x to the back of queue. */
        public void push(int x) {
            //方便后面的peek操作
           if(s1.isEmpty()){
               front=x;
           }
           s1.push(x);
        }

        /** Removes the element from in front of queue and returns that element. */
        public int pop() {
            if(s2.isEmpty()){
                while(!s1.isEmpty()){
                    s2.push(s1.pop());
                }
            }
            return s2.pop();
        }

        /** Get the front element. */
        public int peek() {
            if(!s2.isEmpty()){
               return s2.peek();
            }
            return front;
        }

        /** Returns whether the queue is empty. */
        public boolean empty() {
            return (s1.isEmpty() && s2.isEmpty());
        }
    }
}
