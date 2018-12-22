package code_05_setAndMap.set;

import code_02_linkedlist.Node;

/**
 * Created by DHA on 2018/11/21.
 */
public class LinkedList<E> {
    private Node dummyHead;
    private int size;

    public LinkedList(){
        dummyHead=new Node(null,null);
        size=0;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public int getSize(){
        return size;
    }

    //在链表index位置[从0开始]插入元素
    //这项操作在链表中并不常用
    public void add(int index,E e){
        if(index<0 || index> size){
            throw new IllegalArgumentException("Index is illegal");
        }
        Node prev=dummyHead;
        for(int i=0;i<index;i++){
            prev=prev.next;
        }
        Node node=new Node(e);
        node.next=prev.next;
        prev.next=node;
        size++;
    }

    //在链表头添加元素
    public void addFirst(E e){
        add(0,e);
    }

    public void addLast(E e){
        add(size,e);
    }

    //获取链表index位置[从0开始]元素
    //这项操作在链表中并不常用
    public E get(int index){
        if(index<0 || index>=size){
            throw new IllegalArgumentException("Index is illegal");
        }
        Node cur=dummyHead.next;
        for(int i=0;i<index;i++){
            cur=cur.next;
        }
        return (E)cur.e;
    }

    public E getFirst(){
        return get(0);
    }

    public E getLast(){
        return get(size-1);
    }

    //修改链表index位置[从0开始]元素
    public void set(int index,E e){
        if(index<0 || index>=size){
            throw new IllegalArgumentException("Index is illegal");
        }
        Node cur=dummyHead.next;
        for(int i=0;i<index;i++){
            cur=cur.next;
        }
        cur.e=e;
    }

    //查找链表中是否有元素e
    public boolean contains(E e){
        Node cur=dummyHead.next;
        while(cur!=null){
            if(cur.e.equals(e)){
                return true;
            }
            cur=cur.next;
        }
        return false;
    }

    //删除链表index位置[从0开始]元素
    public E remove(int index){
        if(index<0 || index>=size){
            throw new IllegalArgumentException("Index is illegal");
        }
        Node prev=dummyHead;
        for(int i=0;i<index;i++){
            prev=prev.next;
        }
        Node delNode= prev.next;
        prev.next=delNode.next;
        delNode.next=null;
        size--;
        return (E)delNode.e;
    }

    public E removeFirst(){
        return remove(0);
    }

    public E removeLast(){
        return remove(size-1);
    }

    //从链表中删除元素e
    public void removeElement(E e){
        Node prev=dummyHead;
        while(prev.next!=null){
            if(prev.next.e.equals(e)){
               break;
            }
            prev=prev.next;
        }
        if(prev.next!=null){
            Node delNode=prev.next;
            prev.next=delNode.next;
            delNode.next=null;
        }
        size--;
    }

    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder();
        Node cur=dummyHead.next;
        while(cur!=null){
            builder.append(cur+"->");
            cur=cur.next;
        }
        builder.append("NULL");
        return builder.toString();
    }
}
