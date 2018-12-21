package code_02_linkedlist;

import code_01_stackAndQueue.Queue;

/**
 * Created by DHA on 2018/12/7.
 */
public class LinkedListQueue<E> implements Queue<E>{
    private Node head;
    private Node tail;
    private int size;

    public LinkedListQueue(){
        head=null;
        tail=null;
        size=0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public void enqueue(E e) {
        if(tail==null){
            tail=new Node(e);
            head=tail;
        }else{
            tail.next=new Node(e);
            tail=tail.next;
        }
        size++;
    }

    @Override
    public E dequeue() {
        if(isEmpty()){
            throw new IllegalArgumentException("Can not dequeue from empty queue.");
        }
        Node delNode=head;
        head=head.next;
        delNode.next=null;
        if(head==null){
            tail=null;
        }
        size--;
        return (E)delNode.e;
    }

    @Override
    public E getFront() {
        if(isEmpty()){
            throw new IllegalArgumentException("Can not dequeue from empty queue.");
        }
        return (E)head.e;
    }

    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder();
        builder.append("Queue: front ");
        Node cur=head;
        while(cur!=null){
            builder.append(cur+"->");
            cur=cur.next;
        }
        builder.append("Null tail");
        return builder.toString();
    }

    public static void main(String[] args) {
        Queue<Integer> queue=new LinkedListQueue<>();
        for(int i=0;i<11;i++){
            queue.enqueue(i);
            System.out.println(queue);
            if(i%3==2){
                queue.dequeue();
                System.out.println(queue);
            }
        }
    }
}
