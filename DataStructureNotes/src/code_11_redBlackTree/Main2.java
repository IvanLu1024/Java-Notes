package code_11_redBlackTree;

import java.util.ArrayList;
import java.util.Random;

/**
 - 对于完全随机的数据，建议使用普通的BST。但是在极端情况下，会退化成链表！(或者高度不平衡)

 - 对于查询较多的使用情况，建议使用AVL树。

 - 红黑树牺牲了平衡性(2log n的高度)，统计性能更优(增删改查所有的操作)
 */
public class Main2 {

    public static void main(String[] args) {

        // int n = 20000000;
        int n = 2000000;

        Random random = new Random(n);
        ArrayList<Integer> testData = new ArrayList<>(n);
        for(int i = 0 ; i < n ; i ++)
            testData.add(random.nextInt(Integer.MAX_VALUE));

        // Test BST
        long startTime = System.nanoTime();

        BST<Integer, Integer> bst = new BST<>();
        for (Integer x: testData)
            bst.add(x, null);

        long endTime = System.nanoTime();

        double time = (endTime - startTime) / 1000000000.0;
        System.out.println("BST: " + time + " s");


        // Test AVL
        startTime = System.nanoTime();

        AVLTree<Integer, Integer> avl = new AVLTree<>();
        for (Integer x: testData)
            avl.add(x, null);

        endTime = System.nanoTime();

        time = (endTime - startTime) / 1000000000.0;
        System.out.println("AVL: " + time + " s");


        // Test RBTree
        startTime = System.nanoTime();

        RBTree<Integer, Integer> rbt = new RBTree<>();
        for (Integer x: testData)
            rbt.add(x, null);

        endTime = System.nanoTime();

        time = (endTime - startTime) / 1000000000.0;
        System.out.println("RBTree: " + time + " s");
    }
}
