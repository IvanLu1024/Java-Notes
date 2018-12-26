package code_08_trie;

import java.util.ArrayList;

/**
 * Created by 18351 on 2018/12/26.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Mix of two fiction");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("mix.txt", words)){
            long startTime = System.nanoTime();
            BSTSet<String> set = new BSTSet<>();
            for(String word: words){
                set.add(word);
            }
            for(String word: words){
                set.contains(word);
            }
            long endTime = System.nanoTime();
            double time = (endTime - startTime) / 1000000000.0;
            System.out.println("Total different words: " + set.getSize());
            System.out.println("BSTSet: " + time + " s");

            // ---
            startTime = System.nanoTime();
            TrieSet trieSet = new TrieSet();
            for(String word: words){
                trieSet.add(word);
            }
            for(String word: words){
                trieSet.contains(word);
            }
            endTime = System.nanoTime();
            time = (endTime - startTime) / 1000000000.0;
            System.out.println("Total different words: " + trieSet.getSize());
            System.out.println("Trie: " + time + " s");
        }
    }
}
