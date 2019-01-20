package code_12_dataStructure;

import java.util.Stack;

/**
 * 155. Min Stack
 *
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 * push(x) -- Push element x onto stack.
 * pop() -- Removes the element on top of the stack.
 * top() -- Get the top element.
 * getMin() -- Retrieve the minimum element in the stack.
 *
 * Example:
 MinStack minStack = new MinStack();
 minStack.push(-2);
 minStack.push(0);
 minStack.push(-3);
 minStack.getMin();   --> Returns -3.
 minStack.pop();
 minStack.top();      --> Returns 0.
 minStack.getMin();   --> Returns -2.
 */
public class Code_155_MinStack {
    class MinStack {
        //准备两个栈，一个用来存普通元素，一个栈顶的元素始终是当前最小值
        private Stack<Integer> stack;
        private Stack<Integer> minStack;

        /** initialize your data structure here. */
        public MinStack() {
            stack=new Stack<>();
            minStack=new Stack<>();
        }

        public void push(int x) {
            if(minStack.isEmpty() || x<=minStack.peek()){
                minStack.push(x);
            }
            stack.push(x);
        }

        public void pop() {
            if(stack.peek().equals(minStack.peek())){
                minStack.pop();
            }
            stack.pop();
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }
}
