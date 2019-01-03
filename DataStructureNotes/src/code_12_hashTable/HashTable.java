package code_12_hashTable;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by 18351 on 2019/1/3.
 */
public class HashTable<K,V> {
    private TreeMap<K,V>[]  hashtable;
    private int M;
    private int size;

    public HashTable(int M){
        this.M=M;
        this.size=0;
        this.hashtable=new TreeMap[M];
        for(int i=0;i<M;i++){
            hashtable[i]=new TreeMap<>();
        }
    }

    public HashTable(){
        this(97);
    }

    private int hash(K key){
        return (key.hashCode() & 0x7fffffff) % M;
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
        }
    }

    public V remove(K key){
        TreeMap<K,V> map=hashtable[hash(key)];
        V ret=null;
        if(map.containsKey(key)){
            ret=map.remove(key);
            size--;
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
