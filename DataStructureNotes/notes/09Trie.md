<!-- GFM-TOC -->
* [Trie](#Trie)
    * [什么是Trie](#什么是Trie)
    * [Trie基础](#Trie基础)
    * [Trie字典树查询](#Trie字典树查询)
    * [Trie字典树前缀查询](#Trie字典树前缀查询)
    * [Trie字典树和简单的模式匹配](#Trie字典树和简单的模式匹配)
<!-- GFM-TOC -->
# Trie
## 什么是Trie
- Trie字典树/前缀树的直观感受

<div align="center"><img src="pics//trie//trie_1.png" width="600"/></div>

- Trie只用来处理字符串

<div align="center"><img src="pics//trie//trie_2.png" width="600"/></div>

其中蓝色就是单词结尾节点。

```java
class Node{
    boolean isWord; //表示该字母是否是单词的结尾
    Map<char,Node> next;
}
```

## Trie基础
```java
public class Trie {
    private class Node{
        public boolean isWord;//标记该字符是否是单词结尾
        public TreeMap<Character,Node> next;
        public Node(boolean isWord){
            this.isWord=isWord;
            next=new TreeMap<>();
        }
        public Node(){
            this(false);
        }
    }

    private Node root;
    private int size;

    public Trie(){
        root=new Node();
        size=0;
    }

    //获取Trie中存储的单词数量
    public int getSize(){
        return size;
    }

    //向Trie中添加一个新单词word
    public void add(String word){
        Node cur=root;
        for(int i=0;i<word.length();i++){
            char c=word.charAt(i);
            if(cur.next.get(c)==null){
                cur.next.put(c,new Node());
            }
            cur=cur.next.get(c);
        }
        //循环结束后，cur不一定是叶子节点，比如Trie中已经有 "panda"，此时add("pan"),
        // cur指向'n'节点，显然'n'不是叶子节点,那么就要标记为结束位置
        if(!cur.isWord){
            //!cur.isWord 表示该节点未被标识为结束位置
            cur.isWord=true;
            size++;
        }
    }
}
```

## Trie字典树查询
```java
//查询单词是否在Trie中
public boolean contains(String word){
    Node cur=root;
    for(int i=0;i<word.length();i++){
        char c=word.charAt(i);
        if(cur.next.get(c)==null){
            return false;
        }
        cur=cur.next.get(c);
    }
    //注意：即使循环结束了，也不一定能确定该单词就在Trie中
    //如果Trie中已经有单词"panda"，此时要查询"pan"
    //循环结束后,cur此时指向'n'节点,'n'节点不是结尾节点,即"pan"不在Trie中
    return cur.isWord;
}
```

## Trie字典树前缀查询
```java
//查询是否在Trie中存在以prefix为前缀的单词
public boolean isPrefix(String prefix){
    Node cur=root;
    for(int i=0;i<prefix.length();i++){
        char c=prefix.charAt(i);
        if(cur.next.get(c)==null){
            return false;
        }
        cur=cur.next.get(c);
    }
    //注意：循环结束后,cur不管是单词的结尾节点还是非结尾节点，都成立
    //单词本身就是该单词的前缀
    return true;
}
```

- LeetCode 208题 实现Trie字典树
```java
class Trie {
    private class Node{
        public boolean isWord;//标记该字符是否是单词结尾
        public TreeMap<Character,Node> next;
        public Node(boolean isWord){
            this.isWord=isWord;
            next=new TreeMap<>();
        }
        public Node(){
            this(false);
        }
    }

    private Node root;
    
    /** Initialize your data structure here. */
    public Trie() {
        root=new Node();
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        Node cur=root;
        for(int i=0;i<word.length();i++){
            char c=word.charAt(i);
            if(cur.next.get(c)==null){
                cur.next.put(c,new Node());
            }
            cur=cur.next.get(c);
        }
        //循环结束后，cur不一定是叶子节点，比如Trie中已经有 "panda"，此时add("pan"),
        // cur指向'n'节点，显然'n'不是叶子节点,那么就要标记为结束位置
        if(!cur.isWord){
            //!cur.isWord 表示该节点未被标识为结束位置
            cur.isWord=true;
        }
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        Node cur=root;
        for(int i=0;i<word.length();i++){
            char c=word.charAt(i);
            if(cur.next.get(c)==null){
                return false;
            }
            cur=cur.next.get(c);
        }
        //注意：即使循环结束了，也不一定能确定该单词就在Trie中
        //如果Trie中已经有单词"panda"，此时要查询"pan"
        //循环结束后,cur此时指向'n'节点,'n'节点不是结尾节点,即"pan"不在Trie中
        return cur.isWord;
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        Node cur=root;
        for(int i=0;i<prefix.length();i++){
            char c=prefix.charAt(i);
            if(cur.next.get(c)==null){
                return false;
            }
            cur=cur.next.get(c);
        }
        //注意：循环结束后,cur不管是单词的结尾节点还是非结尾节点，都成立
        //单词本身就是该单词的前缀
        return true;
    }
}
```

## Trie字典树和简单的模式匹配