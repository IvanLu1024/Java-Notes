package code_12_hashTable;

import java.util.ArrayList;

/**
 * Created by 18351 on 2019/1/3.
 */
public class HashTableMain {
    public static void main(String[] args) {

        System.out.println("mix");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("mix.txt", words)) {
            System.out.println("Total words: " + words.size());

            // Collections.sort(words);

            // Test BST
            long startTime = System.nanoTime();

            BST<String, Integer> bst = new BST<>();
            for (String word : words) {
                if (bst.contains(word))
                    bst.set(word, bst.get(word) + 1);
                else
                    bst.add(word, 1);
            }

            for(String word: words)
                bst.contains(word);

            long endTime = System.nanoTime();

            double time = (endTime - startTime) / 1000000000.0;
            System.out.println("BST: " + time + " s");


            // Test AVL
            startTime = System.nanoTime();

            AVLTree<String, Integer> avl = new AVLTree<>();
            for (String word : words) {
                if (avl.contains(word))
                    avl.set(word, avl.get(word) + 1);
                else
                    avl.add(word, 1);
            }

            for(String word: words)
                avl.contains(word);

            endTime = System.nanoTime();

            time = (endTime - startTime) / 1000000000.0;
            System.out.println("AVL: " + time + " s");


            // Test RBTree
            startTime = System.nanoTime();

            RBTree<String, Integer> rbt = new RBTree<>();
            for (String word : words) {
                if (rbt.contains(word))
                    rbt.set(word, rbt.get(word) + 1);
                else
                    rbt.add(word, 1);
            }

            for(String word: words)
                rbt.contains(word);

            endTime = System.nanoTime();

            time = (endTime - startTime) / 1000000000.0;
            System.out.println("RBTree: " + time + " s");


            // Test HashTable
            startTime = System.nanoTime();

            HashTable<String, Integer> ht = new HashTable<>();
            //HashTable<String, Integer> ht = new HashTable<>(131071);
            for (String word : words) {
                if (ht.contains(word))
                    ht.set(word, ht.get(word) + 1);
                else
                    ht.add(word, 1);
            }

            for(String word: words)
                ht.contains(word);

            endTime = System.nanoTime();

            time = (endTime - startTime) / 1000000000.0;
            System.out.println("HashTable: " + time + " s");

            // Test HashTable2
            startTime = System.nanoTime();

            HashTable2<String, Integer> ht2 = new HashTable2<>();
            for (String word : words) {
                if (ht2.contains(word))
                    ht2.set(word, ht2.get(word) + 1);
                else
                    ht2.add(word, 1);
            }

            for(String word: words)
                ht2.contains(word);

            endTime = System.nanoTime();

            time = (endTime - startTime) / 1000000000.0;
            System.out.println("HashTable2: " + time + " s");

            //Test HashTable3
            startTime = System.nanoTime();

            HashTable3<String, Integer> ht3 = new HashTable3<>();
            for (String word : words) {
                if (ht3.contains(word))
                    ht3.set(word, ht3.get(word) + 1);
                else
                    ht3.add(word, 1);
            }

            for(String word: words)
                ht3.contains(word);

            endTime = System.nanoTime();

            time = (endTime - startTime) / 1000000000.0;
            System.out.println("HashTable3: " + time + " s");
        }
        System.out.println();
    }
}
