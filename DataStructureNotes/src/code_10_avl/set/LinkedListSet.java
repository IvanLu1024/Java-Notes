package code_10_avl.set;

/**
 * Created by 18351 on 2018/12/20.
 */
public class LinkedListSet<E> implements Set<E> {
    private LinkedList<E> linkedList;

    public LinkedListSet(){
        linkedList=new LinkedList<>();
    }

    @Override
    public void add(E e) {
        if(!contains(e)){
            linkedList.addFirst(e);
        }
    }

    @Override
    public void remove(E e) {
        if(contains(e)){
            linkedList.removeElement(e);
        }
    }

    @Override
    public boolean contains(E e) {
        return linkedList.contains(e);
    }

    @Override
    public int getSize() {
        return linkedList.getSize();
    }

    @Override
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }
}
