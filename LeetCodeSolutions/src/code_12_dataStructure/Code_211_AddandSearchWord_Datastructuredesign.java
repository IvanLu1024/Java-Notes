package code_12_dataStructure;

import java.util.TreeMap;

/**
 * Created by 18351 on 2019/1/26.
 */
public class Code_211_AddandSearchWord_Datastructuredesign {
    class WordDictionary {
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
        public WordDictionary() {
            root=new Node();
        }

        /** Adds a word into the data structure. */
        public void addWord(String word) {
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

        /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
        public boolean search(String word) {
            return match(root,word,0);
        }

        //判断word在index位置是否匹配
        private boolean match(Node node,String word,int index){
            if(index==word.length()){
                return node.isWord;
            }
            char c=word.charAt(index);
            if(c!='.') {
                //c是小写字母
                if (node.next.get(c) == null) {
                    return false;
                }
                return match(node.next.get(c), word, index + 1);
            }else{
                //遍历所有从以该点为根节点的子树
                for(char nextChar:node.next.keySet()){
                    if(match(node.next.get(nextChar),word,index+1)){
                        return true;
                    }
                }
                return false;
            }
        }
    }
}
