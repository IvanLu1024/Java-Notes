package code_01_stackAndQueue;

/**
 * Created by DHA on 2018/11/19.
 */
public class LoopQueue<E> implements Queue<E> {
    private E[] data;
    private int front,tail;
    private int size;

    public LoopQueue(int capacity){
        //循环队列会浪费一个单位空间
        data=(E[])new Object[capacity+1];
        front=0;
        tail=0;
        size=0;
    }

    public LoopQueue(){
        this(10);
    }

    public int getCapacity(){
        return data.length-1;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return front==tail;
    }

    @Override
    public void enqueue(E e) {
        //出队操作，先判断队列是否满了
        if((tail+1)%data.length==front){
            resize(getCapacity()*2);
        }
        data[tail]=e;
        tail=(tail+1)%data.length;
        size++;
    }

    @Override
    public E dequeue() {
        if(isEmpty()){
            throw new IllegalArgumentException("con not dequeue from empty queue");
        }
        E ret=data[front];
        data[front]=null;
        front=(front+1)%data.length;
        size--;
        if(size==getCapacity()/4 && getCapacity()/2!=0){
            resize(getCapacity()/2);
        }
        return ret;
    }

    @Override
    public E getFront() {
        if(isEmpty()){
            throw new IllegalArgumentException("con not dequeue from empty queue");
        }
        return data[front];
    }

    private void resize(int newCapacity) {
        E[] newData=(E[])new Object[newCapacity+1];
        for(int i=0;i<size;i++){
            newData[i]=data[(i+front)%data.length];
        }
        data=newData;
        front=0;
        tail=size;
    }

    @Override
    public String toString() {
        StringBuilder ret=new StringBuilder();
        ret.append(String.format("LooPQueue: size=%d,capacity=%d\n",size,getCapacity()));
        ret.append("font [");
        for(int i=front;i!=tail;i=(i+1)%data.length){
            ret.append(data[i]);
            if((i+1)%data.length!=tail){
                ret.append(", ");
            }
        }
        ret.append("] tail");
        return ret.toString();
    }
}