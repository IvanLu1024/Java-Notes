package code_02_linkedlist;

import code_01_stackAndQueue.Stack;

/**
 * Created by DHA on 2018/12/7.
 */
public class LinkedListStack<E> implements Stack<E>{
    private LinkedList<E> linkedList;

    public LinkedListStack(){
        linkedList=new LinkedList<>();
    }

    @Override
    public int getSize() {
        return linkedList.getSize();
    }

    @Override
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    @Override
    public void push(E e) {
        linkedList.addFirst(e);
    }

    @Override
    public E pop() {
        return linkedList.removeFirst();
    }

    @Override
    public E peek() {
        return linkedList.getFirst();
    }

    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder();
        builder.append("Stack:top ");
        builder.append(linkedList);
        return builder.toString();
    }

    public static void main(String[] args) {
        Stack<Integer> stack=new LinkedListStack<>();
        for(int i=0;i<5;i++){
            stack.push(i);
            System.out.println(stack);
        }
        stack.pop();
        System.out.println(stack);
    }
}
