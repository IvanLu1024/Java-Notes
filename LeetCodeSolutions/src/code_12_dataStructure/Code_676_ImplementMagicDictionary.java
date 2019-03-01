package code_12_dataStructure;

import org.junit.Test;

/**
 * 676. Implement Magic Dictionary
 *
 * Implement a magic directory with buildDict, and search methods.
 * For the method buildDict, you'll be given a list of non-repetitive(不重复的) words to build a dictionary.
 * For the method search, you'll be given a word,
 * and judge whether if you modify exactly one character into another character in this word,
 * the modified word is in the dictionary you just built.

 * Example 1:
 Input: buildDict(["hello", "leetcode"]), Output: Null
 Input: search("hello"), Output: False
 Input: search("hhllo"), Output: True
 Input: search("hell"), Output: False
 Input: search("leetcoded"), Output: False
 */
public class Code_676_ImplementMagicDictionary {
    class MagicDictionary {
        private class TrieNode{
            boolean isWord;
            TrieNode[] next;
            TrieNode(){
                next = new TrieNode[26];
                isWord = false;
            }
        }

        private TrieNode root;

        /** Initialize your data structure here. */
        public MagicDictionary() {
            root = new TrieNode();
        }

        /** Build a dictionary through a list of words */
        public void buildDict(String[] dict) {
            if(dict!=null){
                for(String word : dict){
                    add(word);
                }
            }
        }

        //向根节点中加入单词
        private void add(String word){
            TrieNode cur = root;
            for(int i=0;i<word.length();i++){
                int c = word.charAt(i) - 'a';
                if(cur.next[c] == null){
                    cur.next[c] = new TrieNode();
                }
                cur = cur.next[c];
            }
            if(!root.isWord){
                cur.isWord = true;
            }
        }

        /** Returns if there is any word in the trie that equals to the given word after modifying exactly one character */
        public boolean search(String word) {
            for (int i = 0; i < word.length(); i++) {
                String tmp = word;
                //将 word i位置上的字符全部替换成 其他字符
                for (char c = 'a'; c <= 'z'; c++) {
                    if (word.charAt(i) == c) {
                        continue;
                    }
                    //将 i 位置元素替换
                    tmp = tmp.substring(0,i)+(c+"")+tmp.substring(i+1);
                    if (contains(tmp)) {
                        return true;
                    }
                }
            }
            return false;
        }

        //查询单词是否在Trie中
        private boolean contains(String word){
            TrieNode cur=root;
            for(int i=0;i<word.length();i++){
                int c=word.charAt(i) - 'a';
                if(cur.next[c] == null){
                    return false;
                }
                cur=cur.next[c];
            }
            //注意：即使循环结束了，也不一定能确定该单词就在Trie中
            //如果Trie中已经有单词"panda"，此时要查询"pan"
            //循环结束后,cur此时指向'n'节点,'n'节点不是结尾节点,即"pan"不在Trie中
            return cur.isWord;
        }
    }

    @Test
    public void test(){
        String tmp = "leetcode";
        int i= 1;
        char c = 'f';
        tmp = tmp.substring(0,i)+(c+"")+tmp.substring(i+1);
        System.out.println(tmp);
    }
}
