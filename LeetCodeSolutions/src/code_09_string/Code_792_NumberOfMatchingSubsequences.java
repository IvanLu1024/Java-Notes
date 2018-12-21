package code_09_string;

import org.junit.Test;

import java.util.List;

/**
 * 792. Number of Matching Subsequences
 * Example :
 Input:
 S = "abcde"
 words = ["a", "bb", "acd", "ace"]
 Output: 3
 Explanation: There are three words in words that are a subsequence of S: "a", "acd", "ace".
 * Given string S and a dictionary of words words, find the number of words[i] that is a subsequence of S.
 */
public class Code_792_NumberOfMatchingSubsequences {
    /**
     * 基本思路是对words中的每一个word检查是否为S的一个子序列。
     * 但是如果每次检查都要扫描一遍S的话时间复杂度太高。
     *
     * 记录下来S中每个字符的出现次数。
     * 然后依次对于每个单词，在存储S中字符的有序数组中二分查找，每次找到之后，就从下一个位置上开始再找下一个字符。
     * 这样在处理每个words[i]的时候，我们是跳着走的，而不是顺序走的，
     * 就将原来判断一个word是不是S的子序列的时间复杂度从O(m)降低到了O(logm)这个量级。
     */
    private class Pair{
        String word;
        int pos;
        Pair(String word,int pos){
            this.word=word;
            this.pos=pos;
        }
    }
    public int numMatchingSubseq(String S, String[] words) {
        Pair[] table=new Pair[26];
        for(String word:words){
            table[word.charAt(0)-'a']=new Pair(word,0);
        }
        int res=0;
        for(int i=0;i<S.length();i++){
            char c=S.charAt(i);
            Pair[] oldTable=new Pair[26];
            for(int j=0;j<26;j++){
                oldTable[i]=table[i];
            }
            table[c-'a']=null;
            for(Pair p:oldTable) {
                if(p!=null){
                    p.pos++;
                    if (p.pos == p.word.length()) {
                        res++;
                    } else {
                        table[p.word.charAt(p.pos) - 'a']= p;
                    }
                }
            }
        }
        return res;
    }

   /* int upper_bound(int *array, int size, int key)
    {
        int first = 0, len = size-1;
        int half, middle;

        while(len > 0){
            half = len >> 1;
            middle = first + half;
            if(array[middle] > key)     //中位数大于key,在包含last的左半边序列中查找。
                len = half;
            else{
                first = middle + 1;    //中位数小于等于key,在右半边序列中查找。
                len = len - half - 1;
            }
        }
        return first;
    }*/

   //算法返回一个非递减序列中第一个大于val的位置。
    private int upperBound(List<Integer> list,int val){
        int first = 0, len = list.size()-1;
        int half, middle;

        while(len > 0){
            half = len >> 1;
            middle = first + half;
            if(list.get(middle) > val)     //中位数大于key,在包含last的左半边序列中查找。
                len = half;
            else{
                first = middle + 1;    //中位数小于等于key,在右半边序列中查找。
                len = len - half - 1;
            }
        }
        return first;
    }

    @Test
    public void test(){
        //String S="aaaabbb";
        /*int[] res=charFrequency(word);
        for(int i=0;i<res.length;i++){
            System.out.print((char)(i+'a'));
            System.out.println(" "+res[i]);
        }*/

        String S = "abcde";
        String[] words = {"a", "bb", "acd", "ace"};
        System.out.println(numMatchingSubseq(S,words));
    }
}
