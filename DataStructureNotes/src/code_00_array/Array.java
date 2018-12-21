package code_00_array;

/**
 * 二次封装属于我们自己的数组
 */
public class Array<E> {
    private E[] data;
    private int size;

    public Array(int capacity){
        data=(E[])new Object[capacity];
        size=0;
    }

    //无参构造函数，默认数组的容量是10
    public Array(){
        this(10);
    }

    public int getSize(){
        return size;
    }

    public int getCapacity(){
        return data.length;
    }

    public boolean isEmpty(){
        return size==0;
    }

    //在所有元素后添加新元素
    public void addLast(E e) {
        //要先判断是否有空间来容纳新元素
        /*if(size==data.length){
            throw new IllegalArgumentException("AddLast failed,array is full");
        }
        data[size]=e;
        size++;*/
        add(size,e);
    }

    //在所有元素前添加新元素
    public void addFirst(E e){
        add(0,e);
    }

    //在index位置插入元素
    public void add(int index,E e){
       /* if(size==data.length){
            throw new IllegalArgumentException("Add failed,array is full");
        }*/
        if(size==data.length){
            resize(data.length*2);
        }
        if(index<0 || index>size){
            throw new IllegalArgumentException("Add Failed,0<=index<=size is required");
        }
        //index位置后的元素向右移动
        for(int i=size;i>index;i--){
            data[i]=data[i-1];
        }
        data[index]=e;
        size++;
    }

    //获取index位置的元素
    public E get(int index){
        if(index<0 || index>=size){
            throw new IllegalArgumentException("Get failed,index is illegal");
        }
        return data[index];
    }

    //设置index位置元素值为e
    public void set(int index,E e){
        if(index<0 || index>=size){
            throw new IllegalArgumentException("Get failed,index is illegal");
        }
        data[index]=e;
    }

    //查找数组中是否有元素e,有就返回下标，没有就返回-1
    public boolean contains(E e){
        for(int i=0;i<size;i++){
            if(data[i]==e){
                return true;
            }
        }
        return false;
    }

    //查找数组中元素e所在索引
    public int find(E e){
        for(int i=0;i<size;i++){
            if(data[i]==e){
                return i;
            }
        }
        return -1;
    }

    //删除指定位置元素
    public E remove(int index){
        /*if(size==0){
            throw new IllegalArgumentException("Remove failed,array is empty");
        }*/
       /* if(size==data.length/2){
            resize(data.length/2);
        }*/
        if(size==data.length/4 && data.length/2!=0){
            resize(data.length/2);
        }
        if(index<0 || index>=size){
            throw new IllegalArgumentException("Remove failed,index is illegal");
        }
        E ret=data[index];
        for(int i=index+1;i<size;i++){
            data[i-1]=data[i];
        }
        size--;
        data[size]=null;//loitering objects
        return ret;
    }

    public E removeFirst(){
        return remove(0);
    }

    public E removeLast(){
        return remove(size-1);
    }

    public void removeElement(E e){
        int index=find(e);
        if(index!=-1){
            remove(index);
        }
    }

    //调整数组大小
    private void resize(int newCapacity){
        E[] newData=(E[])new Object[newCapacity];
        for(int i=0;i<size;i++){
            newData[i]=data[i];
        }
        data=newData;
    }

    @Override
    public String toString(){
        StringBuilder builder=new StringBuilder();
        builder.append(String.format("Array size=%d,capacity=%d\n",size,data.length));
        builder.append("[");
        for(int i=0;i<size;i++){
            builder.append(data[i]);
            if(i!=size-1){
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}
