package code_12_dataStructure;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 648. Replace Words
 *
 * In English, we have a concept called root,
 * which can be followed by some other words to form another longer word - let's call this word successor.
 * For example, the root an, followed by other, which can form another word another.

 * Now, given a dictionary consisting of many roots and a sentence.
 * You need to replace all the successor in the sentence with the root forming it.
 * If a successor has many roots can form it, replace it with the root with the shortest length.

 * You need to output the sentence after the replacement.
 *
 * Example 1:
 Input: dict = ["cat", "bat", "rat"]
 sentence = "the cattle was rattled by the battery"
 Output: "the cat was rat by the bat"

 * Note:
 The input will only have lower-case letters.
 1 <= dict words number <= 1000
 1 <= sentence words number <= 1000
 1 <= root length <= 100
 1 <= sentence words length <= 1000
 */
public class Code_648_ReplaceWords {
    private class TrieNode{
        boolean isWord;
        TrieNode[] next;
        TrieNode(){
            isWord = false;
            next = new TrieNode[26];
        }
    }

    public void add(TrieNode node,String word){
        for(int i=0;i<word.length();i++){
            int c = word.charAt(i)-'a';
            if(node.next[c] == null){
                node.next[c] = new TrieNode();
            }
            node = node.next[c];
        }
        if(!node.isWord){
            node.isWord=true;
        }
    }

    //前缀查询
    private String findPrefix(TrieNode node,String prefix){
        StringBuilder cur = new StringBuilder();
        for(int i=0;i<prefix.length();i++){
            int c = prefix.charAt(i)-'a';
            if(node.next[c]==null){
                break;
            }
            cur.append(prefix.charAt(i));
            node = node.next[c];
            if(node.isWord){
                return cur.toString();
            }
        }
        return prefix;
    }

    public String replaceWords(List<String> dict, String sentence) {
        StringBuilder res = new StringBuilder();
        TrieNode root = new TrieNode();
        for(String word : dict){
            add(root,word);
        }

        String[] words = sentence.split(" ");
        if(words != null){
            for(String word : words){
                res.append(findPrefix(root,word)).append(" ");
            }
        }
        return res.toString().trim();
    }

    @Test
    public void test(){
        String[] words = {"cat", "bat", "rat"};
        List<String> dict = new ArrayList<>();
        for(String word : words){
            dict.add(word);
        }

        String sentence = "the cattle was rattled by the battery";
        System.out.println(replaceWords(dict,sentence));
    }
}
