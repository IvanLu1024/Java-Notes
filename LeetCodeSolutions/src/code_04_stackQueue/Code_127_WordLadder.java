package code_04_stackQueue;

import java.util.*;

/**
 * 127. Word Ladder
 * Given two words (beginWord and endWord), and a dictionary's word list,
 * find the length of shortest transformation sequence from beginWord to endWord, such that:
 * 1.Only one letter can be changed at a time.
 * 2.Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
 *
 * Note:
 Return 0 if there is no such transformation sequence.
 All words have the same length.
 All words contain only lowercase alphabetic characters.
 You may assume no duplicates in the word list.
 You may assume beginWord and endWord are non-empty and are not the same.
 *
 *
 * Example 1:
 Input:
 beginWord = "hit",
 endWord = "cog",
 wordList = ["hot","dot","dog","lot","log","cog"]
 Output: 5
 Explanation: As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
 return its length 5.

 * Example 2:
 Input:
 beginWord = "hit"
 endWord = "cog"
 wordList = ["hot","dot","dog","lot","log"]
 Output: 0
 Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.
 *
 */
public class Code_127_WordLadder {
    /**
     * BFS
     * 将这个问题看成图问题:
     * word1-->word2 的路径就是word1和word2 "相似"（word1和wordd2根据题目规则可以相互转化）
     * 最后从beginWord走到endWord.
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        //使用set去除重复元素-->BFS更高效
        Set<String> wordSet=new HashSet<>();
        for(String word:wordList){
            wordSet.add(word);
        }

        //存储已经访问过的节点，保证下次不会再走了
        Set<String> visited = new HashSet<>();

        //从beginWord开始进行BFS
        Queue<Pair> q=new LinkedList<>();
        //从 begin开始，走的是第一步
        q.add(new Pair(beginWord,1));
        while (!q.isEmpty()){
            Pair p=q.poll();
            //访问当前节点
            String curWord=p.word;
            int curStep=p.step;
            visited.clear();
            //看下一个节点的情况
            for(String word:wordSet){
                if(isSimilar(curWord,word)){ //isSimilar(curWord,word) 说明beginWod-->word有路径
                    if(word.equals(endWord)){
                        return curStep+1;
                    }
                    //将word加入队列，即访问了word
                    q.add(new Pair(word,curStep+1));
                    visited.add(word);
                }
            }
            //删除已经访问过的节点
            for(String w:visited){
                wordSet.remove(w);
            }
        }
        return 0;
    }

    private class Pair{
        String word;
        int step;
        Pair(String word,int step){
            this.word=word;
            this.step=step;
        }
    }

    //判断word1和word2是否相似，即word1和word是否可以相互转化
    private boolean isSimilar(String word1,String word2){
        if(word1.length()!=word2.length() || word1.equals(word2)){
            return false;
        }
        int diff=0;
        for(int i=0;i<word1.length();i++){
            if(word1.charAt(i)!=word2.charAt(i)){
                diff++;
            }
            if(diff>1){
                return false;
            }
        }
        return true;
    }
}
