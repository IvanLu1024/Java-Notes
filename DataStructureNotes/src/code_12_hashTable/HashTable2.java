package code_12_hashTable;

import java.util.TreeMap;

/**
 * Created by 18351 on 2019/1/3.
 */
public class HashTable2<K,V> {
    private static final int upperTol=10;
    private static final int lowerTol=2;
    private static final int initCapacity=7;

    private TreeMap<K,V>[]  hashtable;
    private int M;
    private int size;

    public HashTable2(int M){
        this.M=M;
        this.size=0;
        this.hashtable=new TreeMap[M];
        for(int i=0;i<M;i++){
            hashtable[i]=new TreeMap<>();
        }
    }

    public HashTable2(){
        this(initCapacity);
    }

    private int hash(K key){
        return (key.hashCode() & 0x7fffffff) % M;
    }

    private void resize(int newM){
        TreeMap<K,V>[] newHashtable=new TreeMap[newM];
        for(int i=0;i<newM;i++){
            newHashtable[i]=new TreeMap<>();
        }

        int oldM=M;
        this.M=newM;
        for(int i=0;i<oldM;i++){
            TreeMap<K,V> map=hashtable[i];
            for(K key:map.keySet()){
                newHashtable[hash(key)].put(key,map.get(key));
            }
        }
        this.hashtable=newHashtable;
    }

    public int getSize(){
        return size;
    }

    public void add(K key,V value){
        TreeMap<K,V> map=hashtable[hash(key)];
        if(map.containsKey(key)){
            map.put(key,value);
        }else{
            map.put(key,value);
            size++;
            if(size >= upperTol*M){
                resize(2*M);
            }
        }
    }

    public V remove(K key){
        TreeMap<K,V> map=hashtable[hash(key)];
        V ret=null;
        if(map.containsKey(key)){
            ret=map.remove(key);
            size--;
            if(size < lowerTol*M && M/2>=initCapacity){
                resize(M/2);
            }
        }
        return ret;
    }

    public void set(K key,V value){
        TreeMap<K,V> map=hashtable[hash(key)];
        if(!map.containsKey(key)) {
            throw new IllegalArgumentException(key + "does not exist!");
        }
        map.put(key,value);
    }

    public boolean contains(K key){
        return hashtable[hash(key)].containsKey(key);
    }

    public V get(K key){
        return hashtable[hash(key)].get(key);
    }
}
